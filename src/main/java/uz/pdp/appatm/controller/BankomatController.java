package uz.pdp.appatm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appatm.payload.ApiResponse;
import uz.pdp.appatm.payload.BankomatDto;
import uz.pdp.appatm.service.BankomatService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/bankomat")
public class BankomatController {


    @Autowired
    BankomatService bankomatService;


    @PostMapping("/createATM")
    @PreAuthorize(value = "hasAuthority('ADD_ATM')")
    public HttpEntity<?> createATM(@Valid @RequestBody BankomatDto bankomatDto) {
        ApiResponse bankomat = bankomatService.createBankomat(bankomatDto);
        return ResponseEntity.status(bankomat.isSuccess() ? 201 : 409).body(bankomat);
    }


    @GetMapping
    @PreAuthorize(value = "hasAuthority('VIEWS_ATM')")
    public HttpEntity<?> sendMessage() {
        ApiResponse apiResponse = bankomatService.sendMessageToWorker();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PutMapping("/fillMoney/{id}")
    @PreAuthorize(value = "hasAuthority('EDIT_ATM')")
    public HttpEntity<?> fillMoney(@Valid @PathVariable UUID id, @RequestBody BankomatDto bankomatDto) {
        ApiResponse apiResponse = bankomatService.fillMoneyToBankomat(id, bankomatDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

}
