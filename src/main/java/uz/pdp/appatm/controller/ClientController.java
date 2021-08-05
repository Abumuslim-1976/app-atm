package uz.pdp.appatm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appatm.payload.ApiResponse;
import uz.pdp.appatm.payload.ClientDTO;
import uz.pdp.appatm.service.ClientService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/client")
public class ClientController {


    @Autowired
    ClientService clientService;


    @PostMapping
    public ResponseEntity<?> addClient(@Valid @RequestBody ClientDTO clientDTO) {
        ApiResponse apiResponse = clientService.addClient(clientDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    @GetMapping
    public ResponseEntity<?> viewsClient() {
        ApiResponse apiResponse = clientService.viewsClient();
        return ResponseEntity.ok(apiResponse);
    }
}
