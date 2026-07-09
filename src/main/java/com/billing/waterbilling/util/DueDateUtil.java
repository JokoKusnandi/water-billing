package com.billing.waterbilling.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Component
public class DueDateUtil {

    public LocalDate dueDate(String period){

        YearMonth ym=YearMonth.parse(
                period,
                DateTimeFormatter.ofPattern("yyyyMM")
        );

        return ym.atDay(20);

    }

}
