package uz.pdp.appatm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

    @NotNull(message = "qancha pul yechib olishni kiriting")
    private int amount;

    @NotNull(message = "qaysi bankomatdan pul yechilayapti")
    private UUID bankomatId;

    @NotNull(message = "qaysi carddan pul olinayapti")
    private UUID cardId;

    @NotNull(message = "card ning 4 xonalik kodi kiritilsin")
    private Integer specialCode;

}
