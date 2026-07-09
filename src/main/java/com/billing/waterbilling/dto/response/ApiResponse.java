package com.billing.waterbilling.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private String code;

    private String message;

    private T data;

    private LocalDateTime timestamp;

}
