package com.parameta.employee.presentation.response;

import java.time.LocalDate;
import java.time.Period;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class PeriodInfo {
    private final int years;
    private final int months;
    private final int days;

    public static PeriodInfo from(LocalDate start, LocalDate end) {
        Period period = Period.between(start, end);
        return new PeriodInfo(period.getYears(), period.getMonths(), period.getDays());
    }
}
