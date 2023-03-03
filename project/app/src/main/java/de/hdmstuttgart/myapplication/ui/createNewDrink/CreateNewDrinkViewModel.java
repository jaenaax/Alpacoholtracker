package de.hdmstuttgart.myapplication.ui.createNewDrink;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import de.hdmstuttgart.myapplication.database.DrinksRepository;
import de.hdmstuttgart.myapplication.model.Drink;

public class CreateNewDrinkViewModel extends AndroidViewModel {
    private final DrinksRepository repository;

    public CreateNewDrinkViewModel(@NonNull Application application) {
        super(application);
        repository = new DrinksRepository(application);
    }

    public void saveDrink(Drink drink) {
        repository.insert(drink);
    }
}
