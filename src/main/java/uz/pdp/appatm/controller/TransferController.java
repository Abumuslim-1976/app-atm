package uz.pdp.appatm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appatm.payload.ApiResponse;
import uz.pdp.appatm.payload.TransferDto;
import uz.pdp.appatm.service.TransferService;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {


    @Autowired
    TransferService transferService;


    @PostMapping
    @Transactional
    public HttpEntity<?> transfer(@Valid @RequestBody TransferDto transferDto) {
        ApiResponse apiResponse = transferService.transferMoney(transferDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


}
