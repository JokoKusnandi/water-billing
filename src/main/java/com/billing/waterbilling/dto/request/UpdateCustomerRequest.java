package com.billing.waterbilling.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCustomerRequest {

    @NotBlank
    private String customerName;

    @Email
    private String email;

    private String phone;

    private String address;

}
