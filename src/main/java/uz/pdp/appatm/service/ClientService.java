package uz.pdp.appatm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appatm.entity.Client;
import uz.pdp.appatm.payload.ApiResponse;
import uz.pdp.appatm.payload.ClientDTO;
import uz.pdp.appatm.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;


    // clientlarni create qilish
    public ApiResponse addClient(ClientDTO clientDTO) {
        if (clientRepository.existsByPhoneNumber(clientDTO.getPhoneNumber()))
            return new ApiResponse("Bunday telefon raqam egasi mavjud", false);

        clientRepository.save(new Client(
                clientDTO.getFirstName(), clientDTO.getLastName(), clientDTO.getPhoneNumber()
        ));
        return new ApiResponse("muvoffaqiyatli saqlandi", true);
    }


    // clientlarni ko`rish
    public ApiResponse viewsClient() {
        List<Client> clientList = clientRepository.findAll();
        return new ApiResponse("Clientlar listi", true, clientList);
    }
}
