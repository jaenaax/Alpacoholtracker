package de.hdmstuttgart.myapplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import de.hdmstuttgart.myapplication.model.DrinkConsumed;
import de.hdmstuttgart.myapplication.model.Person;


public class Calculator {
    private long startingTime = 0;
    private final double alcoholReductionFactorPerMilliSecond = 0.15 / 3600000; //0.15g alcohol per hour
    private static Calculator instance = null;


    //create singleton
    private Calculator() {
    }

    public static Calculator getInstance() {
        if (instance == null) {
            instance = new Calculator();
        }
        return instance;
    }

    public double calculateImpactOfDrink(DrinkConsumed drinkConsumed, int weight, int gender) {
        int MALE = 2131231121;
        double gender1;
        if (gender == MALE) { //change when person is male
            gender1 = 0.69;
        } else { //when person is female
            gender1 = 0.57;
        }

        int amount = drinkConsumed.getAmount();
        double volume = drinkConsumed.getVolume();
        //0.79g alcohol per ml
        double alcoholDensity = 0.79;
        //calculate impact of one drink without time
        return (amount * volume / 100 * alcoholDensity) / (weight * gender1);
    }

    public double calculateAlcoholLevel(List<DrinkConsumed> drinksConsumedList, Person person) {
        if (drinksConsumedList.size() == 0) {
            startingTime = 0;
            return 0.00;
        }

        double alcoholLevelWithoutTime = 0.00;
        for (DrinkConsumed drinkConsumed : drinksConsumedList) {
            if (startingTime == 0) { // get starting time of first drink
                startingTime = drinkConsumed.getTime();
            }
            // calculate alcohol level without time
            alcoholLevelWithoutTime += calculateImpactOfDrink(drinkConsumed, person.getWeight(), person.getGender());
        }

        // get reduction after time
        double reductionAfterTime = (System.currentTimeMillis() - startingTime) * alcoholReductionFactorPerMilliSecond; //reduction after time

        // subtract reduction after time from alcohol level without time
        return alcoholLevelWithoutTime - reductionAfterTime;
    }

    public String calculateTimeWhenSober(Double alcoholLevel) {
        if (alcoholLevel == 0.00) {
            return "You are sober";
        }
        double timeToSoberUp = alcoholLevel / alcoholReductionFactorPerMilliSecond;

        LocalDateTime timeWhenSoberUnformatted = LocalDateTime.now().plus((long) timeToSoberUp, java.time.temporal.ChronoUnit.MILLIS);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM HH:mm");
        return timeWhenSoberUnformatted.format(timeFormatter);
    }
}