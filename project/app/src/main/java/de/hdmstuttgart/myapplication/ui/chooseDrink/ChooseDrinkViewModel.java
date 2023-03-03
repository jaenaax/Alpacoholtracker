package de.hdmstuttgart.myapplication.ui.chooseDrink;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import de.hdmstuttgart.myapplication.DrinkSelector;
import de.hdmstuttgart.myapplication.database.DrinksRepository;
import de.hdmstuttgart.myapplication.model.Drink;

public class ChooseDrinkViewModel extends AndroidViewModel {

    private final DrinksRepository repository;
    private final LiveData<List<Drink>> drinks;
    private final DrinkSelector drinkSelector;

    public ChooseDrinkViewModel(@NonNull Application application) {
        super(application);
        repository = new DrinksRepository(application);
        drinks = repository.getDrinks();
        drinkSelector = DrinkSelector.getInstance();
    }

    public LiveData<List<Drink>> getAllDrinks() {
        return drinks;
    } //return all drinks

    public void setSelectedDrink(Drink drink) {
        drinkSelector.setSelectedDrink(drink);
    } //set selected drink

    public void deleteDrink(Drink drink) {
        repository.delete(drink);
    } //delete drink
}