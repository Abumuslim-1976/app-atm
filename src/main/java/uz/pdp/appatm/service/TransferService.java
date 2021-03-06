package uz.pdp.appatm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appatm.entity.Bankomat;
import uz.pdp.appatm.entity.Card;
import uz.pdp.appatm.entity.Income;
import uz.pdp.appatm.entity.Outcome;
import uz.pdp.appatm.payload.ApiResponse;
import uz.pdp.appatm.payload.TransferDto;
import uz.pdp.appatm.repository.BankomatRepository;
import uz.pdp.appatm.repository.CardRepository;
import uz.pdp.appatm.repository.IncomeRepository;
import uz.pdp.appatm.repository.OutcomeRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    BankomatRepository bankomatRepository;
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    OutcomeRepository outcomeRepository;


    //    Karta 2 martadan ortiq noto`g`ri parol kiritilsa block holatiga o`tkaziladi (x => sanoq uchun)
    int x = 0;


    // ---------- client bankomatdan naqt pul chiqarishi ------------
    public ApiResponse transferMoney(TransferDto transferDto) {

        //        ---------- card ni ID orqali topib oldik ------------
        Optional<Card> cardOptional = cardRepository.findById(transferDto.getCardId());
        if (!cardOptional.isPresent())
            return new ApiResponse("Karta topilmadi", false);


        //        ---------- bankomatni ni ID orqali topib oldik -----------
        Optional<Bankomat> bankomatOptional = bankomatRepository.findById(transferDto.getBankomatId());
        if (!bankomatOptional.isPresent())
            return new ApiResponse("Bankomat topilmadi", false);

        Card card = cardOptional.get();
        Bankomat bankomat = bankomatOptional.get();


        //        ------------- bankomatda kartani turi userni kartasini turi bilan to`g`ri kelishi tekshirilyapti ----------
        if (!card.getPlasticType().name().equals(bankomat.getPlasticType().name())) {
            return new ApiResponse("Plastik karta turi mos emas", false);
        }


        //        ----------- kartani muddati tekshirilyapti -----------
        LocalDate localDateNow = LocalDate.now();
        LocalDate localDateCard = card.getLocalDate();
        if (localDateNow.getYear() == localDateCard.getYear()) {
            if (localDateCard.getDayOfYear() < localDateNow.getDayOfYear()) {
                return new ApiResponse("Plastik karta muddati tugagan", false);
            }
        } else if (localDateNow.getYear() > localDateCard.getYear()) {
            return new ApiResponse("Plastik karta muddati allaqachon tugagan", false);
        }


        //        ---------- kartadan pul yechib olishdan oldin parol tekshiriladi -----------
        //        ------------- card dan pul yechilishi va bankomatga pul tushishi ------------
        if (card.getSpecialCode().equals(transferDto.getSpecialCode())) {
            if (card.isActive()) {
                // kartani holati yangilanadi (blockdan ochildi)
                x = 0;

                //client kiritgan pul miqdori tekshiriladi
                if (transferDto.getAmount() % 10_000 != 0)
                    return new ApiResponse("Bankomat 10 ming so`mlik kupyuradan kichik kupyura chiqarmaydi", false);

                // client kiritgan pul miqdori
                int amount = transferDto.getAmount();
                double commission = amount * 0.01;
                double amountWithCommission = amount + commission;


                if (card.getBalance() < amountWithCommission) {
                    return new ApiResponse("Karta ichida yetarli mablag` mavjud emas", false);
                }

                if (bankomat.getMaxMoney() <= amount) {
                    return new ApiResponse("Bankomatdan katta summa yechib olish mumkin emas", false);
                }

                // clientdan pul yechib olinayapti
                double balance = card.getBalance();
                double cardOutBalance = balance - amountWithCommission;
                card.setBalance(cardOutBalance);
                cardRepository.save(card);


                // bankomatga pul tushishi
                double plasticMoney = bankomat.getPlasticMoney();
                double allPlasticMoney = plasticMoney + amountWithCommission;
                bankomat.setPlasticMoney(allPlasticMoney);


                // bankomatni ichidan clientga pul chiqarib berish
                long readyMoney = bankomat.getReadyMoney();
                long minusReadyMoney = readyMoney - amount;
                bankomat.setReadyMoney(minusReadyMoney);

                Double a = Math.floor(amount / 100_000);            // 100 minglik nechtaligi
                double a1 = amount - a * 100_000;

                Double b = Math.floor(a1 / 50_000);                 // 50 minglik nechtaligi
                double b1 = a1 - b * 50_000;

                Double c = Math.floor(b1 / 10_000);                 // 10 minglik nechtaligi

                double oneHundredThousand = bankomat.getOneHundredThousandCount() - a;
                double fiftyThousand = bankomat.getFiftyThousandCount() - b;
                double tenThousand = bankomat.getTenThousandCount() - c;

                bankomatRepository.save(new Bankomat(
                        (int) oneHundredThousand,
                        (int) fiftyThousand,
                        (int) tenThousand)
                );

                //Kirim-chiqimlarni saqlab boramiz
                incomeRepository.save(new Income(cardOptional.get(), bankomatOptional.get(), amount, a, b, c));
                outcomeRepository.save(new Outcome(commission, amount, bankomatOptional.get(), cardOptional.get()));

                return new ApiResponse("Pul muvoffaqiyatli o`tkazildi", true);
            }
        } else {
            x++;
            if (x >= 3) {
                card.setActive(false);
                return new ApiResponse("Karta blok holatiga tushib qoldi", false);
            }
        }
        return new ApiResponse("Karta kodi xato", false);
    }


}



