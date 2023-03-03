package de.hdmstuttgart.myapplication.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import de.hdmstuttgart.myapplication.database.DrinksConsumedRepository;
import de.hdmstuttgart.myapplication.model.DrinkConsumed;

public class HomeViewModel extends AndroidViewModel {

    private final DrinksConsumedRepository repository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new DrinksConsumedRepository(application);
    }

    public LiveData<List<DrinkConsumed>> getDrinksConsumed() {
        return repository.getDrinksConsumed();
    }


    public int getPersonWeight() {
        return repository.getPersonWeight();
    }

    public int getPersonGender() {
        return repository.getPersonGender();
    }

    public void resetDrinksConsumed() {
        repository.deleteAll();
    }
}