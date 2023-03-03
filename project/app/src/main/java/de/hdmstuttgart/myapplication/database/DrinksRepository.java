package de.hdmstuttgart.myapplication.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import de.hdmstuttgart.myapplication.model.Drink;

public class DrinksRepository {
    private final DrinkDao drinkDao;
    private final LiveData<List<Drink>> drinksLiveData;

    public DrinksRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        drinkDao = db.drinkDao();
        drinksLiveData = drinkDao.getAll();
    }

    public LiveData<List<Drink>> getDrinks() {
        return drinksLiveData;
    }

    public void insert(Drink drink) {
        AppDatabase.databaseWriteExecutor.execute(() -> drinkDao.insert(drink));
    }

    public void delete(Drink drink) {
        AppDatabase.databaseWriteExecutor.execute(() -> drinkDao.delete(drink));
    }
}
