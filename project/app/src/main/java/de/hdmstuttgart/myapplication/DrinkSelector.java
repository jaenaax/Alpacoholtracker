package de.hdmstuttgart.myapplication;

import de.hdmstuttgart.myapplication.model.Drink;

public class DrinkSelector {
    private static DrinkSelector instance = null;
    private Drink selectedDrink;

    private DrinkSelector() {
    }

    public static DrinkSelector getInstance() {
        if (instance == null) {
            instance = new DrinkSelector();
        }
        return instance;
    }

    public Drink getSelectedDrink() {
        return selectedDrink;
    }

    public void setSelectedDrink(Drink selectedDrink) {
        this.selectedDrink = selectedDrink;
    }
}
