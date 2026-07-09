package com.billing.waterbilling.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMeterRequest {

    @NotBlank
    private String tariffCode;

    @NotBlank
    private String serialNumber;

}
