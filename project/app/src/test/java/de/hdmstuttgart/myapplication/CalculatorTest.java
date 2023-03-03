package de.hdmstuttgart.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.myapplication.model.Drink;
import de.hdmstuttgart.myapplication.model.DrinkConsumed;
import de.hdmstuttgart.myapplication.model.Person;

public class CalculatorTest {
    Calculator calculator = Calculator.getInstance();
    private List<DrinkConsumed> drinksConsumedList = new ArrayList<>();
    Drink drink1 = new Drink("beer", 5.0, 500, "beer.png");
    Drink drink2 = new Drink("wine", 12.0, 100, "wine.png");
    Drink drink3 = new Drink("shot", 40.0, 50, "shot.png");
    DrinkConsumed drinkConsumed1 = drink1.convertToDrinkConsumed();
    DrinkConsumed drinkConsumed2 = drink2.convertToDrinkConsumed();
    DrinkConsumed drinkConsumed3 = drink3.convertToDrinkConsumed();
    Person person = new Person(80, 2131231121);


    @Test
    public void testCalculateAlcoholLevel() {
        drinksConsumedList.add(drinkConsumed1);
        drinksConsumedList.add(drinkConsumed2);
        drinksConsumedList.add(drinkConsumed3);

        double alcoholLevel = calculator.calculateAlcoholLevel(drinksConsumedList, person);

        assertEquals(0.82, alcoholLevel, 0.01);
    }

    @Test
    public void testCalculateTimeWhenSober() {
        Calculator calculator = Calculator.getInstance();
        String timeWhenSober = calculator.calculateTimeWhenSober(0.00);
        assertEquals("You are sober", timeWhenSober);

        timeWhenSober = calculator.calculateTimeWhenSober(0.05);
        assertNotEquals("You are sober", timeWhenSober);
    }

    @Test
    public void testCalculateImpactOfDrink() {
        Calculator calculator = Calculator.getInstance();
        double impact = calculator.calculateImpactOfDrink(drinkConsumed1, person.getWeight(), person.getGender());
        assertEquals(0.36, impact, 0.01);
    }
}

