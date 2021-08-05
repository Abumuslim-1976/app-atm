package uz.pdp.appatm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankomatDto {

//    @NotNull(message = "Bankomatdan max pul yechib olish miqdori")
    private Long maxMoney;                                                                  // bankamatdan max pul yechish miqdori

//    @NotNull(message = "Bankamatga 100 minglik kupyura kiritib keting")
    private Integer oneHundredThousandCount;                                                 // yuz ming so`mlik

//    @NotNull(message = "Bankomatga 50 minglik kupyura kiritib keting")
    private Integer fiftyThousandCount;                                                      // ellik ming so`mlik

//    @NotNull(message = "Iltimos 10 minglik kupyura kiriting , chunki bu kupyura ham mijoz uchun zarur")
    private Integer tenThousandCount;                                                         // o`n ming so`mlik

}
