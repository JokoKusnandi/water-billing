package com.billing.waterbilling.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCustomerRequest {

    @NotBlank
    private String customerId;

    @NotBlank

    private String customerName;

    @Email
    private String email;

    @NotBlank

    private String phone;

    @NotBlank

    private String address;

}
