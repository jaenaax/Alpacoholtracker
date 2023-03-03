package de.hdmstuttgart.myapplication.ui.drinksconsumedOverview;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import de.hdmstuttgart.myapplication.database.DrinksConsumedRepository;
import de.hdmstuttgart.myapplication.model.DrinkConsumed;

public class DrinksConsumedOverviewViewModel extends AndroidViewModel {
    private final DrinksConsumedRepository repository;
    private final LiveData<List<DrinkConsumed>> drinksConsumed;

    public DrinksConsumedOverviewViewModel(@NonNull Application application) {
        super(application);
        repository = new DrinksConsumedRepository(application);
        drinksConsumed = repository.getDrinksConsumed();

    }

    //return all drinksConsumed
    public LiveData<List<DrinkConsumed>> getAllDrinks(){return drinksConsumed;}

    //delete drinkConsumed
    public void deleteDrink(DrinkConsumed drinkConsumed){
        repository.deleteDrink(drinkConsumed);
    }

    //delete allDrinks

    public void deleteAll() {
        repository.deleteAll();
    }

    //get impact of drinkDrunk
    public double getImpact(DrinkConsumed drinkConsumed){
        return repository.getImpact(drinkConsumed);
    }
}
