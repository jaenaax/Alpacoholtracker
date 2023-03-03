package de.hdmstuttgart.myapplication.ui.drinkOverview;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import de.hdmstuttgart.myapplication.DrinkSelector;
import de.hdmstuttgart.myapplication.database.DrinksConsumedRepository;
import de.hdmstuttgart.myapplication.model.Drink;
import de.hdmstuttgart.myapplication.model.DrinkConsumed;

public class DrinkOverviewViewModel extends AndroidViewModel {
    private final DrinksConsumedRepository repository;
    private final DrinkSelector drinkSelector;

    public DrinkOverviewViewModel(@NonNull Application application) {
        super(application);
        repository = new DrinksConsumedRepository(application);
        drinkSelector = DrinkSelector.getInstance();
    }

    public Drink getSelectedDrink() {
        return drinkSelector.getSelectedDrink();
    } //return selected drink

    public void insert(DrinkConsumed drinkConsumed) {
        repository.insert(drinkConsumed);
    } //insert drinkDrunk
}
