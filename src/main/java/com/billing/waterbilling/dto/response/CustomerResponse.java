package com.billing.waterbilling.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private String customerId;

    private String customerName;

    private String email;

    private String phone;

    private String address;

}
