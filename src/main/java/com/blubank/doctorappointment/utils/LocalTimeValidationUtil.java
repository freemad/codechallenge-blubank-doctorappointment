package com.blubank.doctorappointment.utils;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

public class LocalTimeValidationUtil {
    public static boolean startLocalTimeIsValid(@NotNull LocalTime startTime) {

        return startTime.isAfter(LocalTime.of(0, 0));
    }

    public static boolean endLocalTimeIsValid(@NotNull LocalTime endTime) {

        return endTime.isBefore(LocalTime.of(23, 59));
    }

    public static boolean timeOrderIsValid(@NotNull LocalTime startTime, @NotNull LocalTime endTime) {
        return startTime.isBefore(endTime);
    }
}
