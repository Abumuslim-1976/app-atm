package uz.pdp.appatm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appatm.entity.*;
import uz.pdp.appatm.entity.enums.BankName;
import uz.pdp.appatm.entity.enums.PlasticType;
import uz.pdp.appatm.payload.ApiResponse;
import uz.pdp.appatm.payload.CardDto;
import uz.pdp.appatm.repository.BankomatRepository;
import uz.pdp.appatm.repository.CardRepository;
import uz.pdp.appatm.repository.ClientRepository;
import uz.pdp.appatm.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    BankomatRepository bankomatRepository;
    @Autowired
    ClientRepository clientRepository;


    //    ---------- client karta biriktirish -----------
    public ApiResponse createCardToUser(CardDto cardDto) {
        LocalDate localDate = LocalDate.now().plusYears(5);

        cardRepository.save(new Card(
                cardDto.getBalance(), cardDto.getSpecialNumber(),
                BankName.NBU_BANK, localDate, cardDto.getUsername(),
                cardDto.getSpecialCode(), PlasticType.UZCARD,
                clientRepository.findById(cardDto.getClientId()).orElseThrow(() -> new NullPointerException("client topilmadi"))
        ));
        return new ApiResponse("Karta saqlandi", true);
    }


    //    -----------kartani blockdan chiqarish -------------
    public ApiResponse unBlock(UUID id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent())
            return new ApiResponse("Karta topilmadi", false);

        Card card = optionalCard.get();
        card.setActive(true);
        cardRepository.save(card);
        return new ApiResponse("Karta aktiv holatga keltirildi", true);
    }

}
