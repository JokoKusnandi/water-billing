package com.billing.waterbilling.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String code;

    private String message;

    private Object errors;

    private LocalDateTime timestamp;

    private String path;

}
