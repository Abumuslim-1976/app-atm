package uz.pdp.appatm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    @NotNull(message = "Kartani ichiga pul mablag`i joylansin")
    private double balance;                                                     // kartani ichidagi pul miqdori

    @NotNull(message = "kartani maxsus raqami bo`lishi kerak")
    private String specialNumber;                                               // card ning 16 xonalik maxsus raqami

    private String cvvCode;                                                     // 3 xonali cvv kodi

    @NotNull(message = "kartani maxsus kodi kiritilsin")
    private Integer specialCode;                                                 // 4 xonali maxsus kod

    @NotNull(message = "kartani username si kiritilsin")
    private String username;

    @NotNull(message = "kartani egasi kiritilsin")
    private UUID clientId;                                                        // qaysi mijozga tegishli ekanligi

}


