package com.j2ns.backend.utils;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimerCont {

    public int getMinutesPassed() {
        // Hora atual
        LocalTime currentTime = LocalTime.now();

        // Definindo o tempo inicial (10:00)
        LocalTime startTime = LocalTime.of(10, 0);

        // Definindo o tempo final (14:00)
        LocalTime endTime = LocalTime.of(14, 0);

        // Se o horário atual for antes das 10:00, consideramos como 0 minutos
        if (currentTime.isBefore(startTime)) {
            return 0;
        }

        // Se o horário atual for depois das 14:00, consideramos como 240 minutos
        if (currentTime.isAfter(endTime)) {
            return 240;
        }

        // Calcula o número de minutos passados desde as 10:00
        long minutesPassed = ChronoUnit.MINUTES.between(startTime, currentTime);

        return (int) minutesPassed;
    }
}
