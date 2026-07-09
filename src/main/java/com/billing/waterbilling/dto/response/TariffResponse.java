package com.billing.waterbilling.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffResponse {

    /**
     * Tarif Code
     */
    private String tariffCode;

    /**
     * Nama Tarif
     */
    private String tariffName;

    /**
     * Harga per m3
     */
    private BigDecimal pricePerM3;

    /**
     * Persentase denda
     * contoh:
     * 0.015 = 1.5%
     * 0.025 = 2.5%
     * 5.000 = 500%
     */
    private BigDecimal penaltyPercent;

}
