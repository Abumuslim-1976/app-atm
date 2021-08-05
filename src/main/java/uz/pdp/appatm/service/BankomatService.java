package uz.pdp.appatm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.appatm.entity.Bankomat;
import uz.pdp.appatm.entity.enums.PlasticType;
import uz.pdp.appatm.payload.ApiResponse;
import uz.pdp.appatm.payload.BankomatDto;
import uz.pdp.appatm.repository.BankomatRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankomatService {

    @Autowired
    BankomatRepository bankomatRepository;
    @Autowired
    JavaMailSender javaMailSender;

    int hundred = 100_000;              // 100 ming so`mlik kupyura
    int fifty = 50_000;                 // 50 ming so`mlik kupyura
    int ten = 10_000;                   // 10 ming so`mlik kupyura

    //    ------------- bankomat create qilish --------------
    public ApiResponse createBankomat(BankomatDto bankomatDto) {

        Bankomat bankomat = new Bankomat();
        bankomat.setPlasticType(PlasticType.UZCARD);
        bankomat.setMaxMoney(bankomatDto.getMaxMoney());
        bankomat.setOneHundredThousandCount(bankomatDto.getOneHundredThousandCount());
        bankomat.setFiftyThousandCount(bankomatDto.getFiftyThousandCount());
        bankomat.setTenThousandCount(bankomatDto.getTenThousandCount());

        int sum = bankomatDto.getOneHundredThousandCount() * hundred +
                bankomatDto.getFiftyThousandCount() * fifty + bankomatDto.getTenThousandCount() * ten;

        bankomat.setReadyMoney(sum);
        bankomatRepository.save(bankomat);
        return new ApiResponse("Bankomat created", true);
    }


    //    ------------ bankomatni ichida pul kam qolganda mas`ul xodimning emailiga link jo`natish ------------
    public ApiResponse sendMessageToWorker() {
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        for (Bankomat bankomat : bankomatList) {
            if (bankomat.getReadyMoney() < 12_000_000) {
                try {
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setFrom("noreply@gmail.com");
                    mailMessage.setTo("bankomat2344@gmail.com");
                    mailMessage.setSubject("Bankomatda pul kam qoldi !!!");
                    mailMessage.setText("Hurmatli bankomatga mas`ul shaxs " + bankomat.getId() + " raqamli bankomatni pul bilan to`ldirib qo`ying");
                    javaMailSender.send(mailMessage);
                    return new ApiResponse("Bankka pul to`ldirib qo`yildi", true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new ApiResponse("Bankomatni ichida pul miqdori yetarli", false);
        }
        return new ApiResponse("Xatolik !!!", false);
    }


    //    ---------- mas`ul xodim bankomatga pul to`ldirishi -----------
    public ApiResponse fillMoneyToBankomat(UUID id, BankomatDto bankomatDto) {
        Optional<Bankomat> bankomatOptional = bankomatRepository.findById(id);
        if (!bankomatOptional.isPresent())
            return new ApiResponse("Bankomat topilmadi", false);

        Bankomat bankomat = bankomatOptional.get();
        int a = bankomatDto.getOneHundredThousandCount();
        int b = bankomatDto.getFiftyThousandCount();
        int c = bankomatDto.getTenThousandCount();

        bankomat.setOneHundredThousandCount(bankomat.getOneHundredThousandCount() + a);
        bankomat.setFiftyThousandCount(bankomat.getFiftyThousandCount() + b);
        bankomat.setTenThousandCount(bankomat.getTenThousandCount() + c);

        int sum = a * hundred + b * fifty + c * ten;

        bankomat.setReadyMoney(bankomat.getReadyMoney() + sum);
        bankomatRepository.save(bankomat);
        return new ApiResponse("Bankomat account replenished", true);
    }


}
