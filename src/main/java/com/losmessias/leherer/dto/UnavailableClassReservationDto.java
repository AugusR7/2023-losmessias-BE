package com.losmessias.leherer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnavailableClassReservationDto {
    private Long professorId;
    private LocalDate day;
    private LocalTime startingHour;
    private LocalTime endingHour;
    private Double duration;
}
