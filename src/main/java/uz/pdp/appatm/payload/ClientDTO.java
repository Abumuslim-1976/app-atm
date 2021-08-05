package uz.pdp.appatm.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    @NotBlank(message = "first name bo`sh bo`lmasin")
    private String firstName;

    @NotBlank(message = "last name bo`sh bo`lmasin")
    private String lastName;

    @NotBlank(message = "phone number bo`sh bo`lishi mumkin emas")
    private String phoneNumber;

}
