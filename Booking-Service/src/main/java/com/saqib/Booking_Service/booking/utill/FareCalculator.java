package com.saqib.Booking_Service.booking.utill;

import com.saqib.Booking_Service.model.Gender;
import com.saqib.Booking_Service.model.Passenger;
import org.springframework.stereotype.Component;

@Component
public class FareCalculator {

    public double calculateFare(Passenger passenger, String coachType) {
        double baseFare = getBaseFare(coachType);
        int age = passenger.getAge();
        Gender gender = passenger.getGender();

        if (Boolean.TRUE.equals(passenger.getChild())) {
            return baseFare * 0.5;
        }

        if (Boolean.TRUE.equals(passenger.getSeniorCitizen())) {
            if ((gender == Gender.MALE && age >= 60) || (gender == Gender.FEMALE && age >= 58)) {
                return baseFare * 0.6;
            }
        }

        return baseFare;
    }

    private double getBaseFare(String coachType) {
        switch (coachType.toUpperCase()) {
            case "SL": return 200.0;
            case "3AC": return 500.0;
            case "2AC": return 800.0;
            case "1AC": return 1200.0;
            default: return 300.0;
        }
    }
}
