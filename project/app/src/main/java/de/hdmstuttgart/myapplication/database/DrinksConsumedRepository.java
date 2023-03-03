package de.hdmstuttgart.myapplication.database;

import static android.content.Context.MODE_PRIVATE;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.LiveData;
import java.util.List;
import de.hdmstuttgart.myapplication.Calculator;
import de.hdmstuttgart.myapplication.model.DrinkConsumed;

public class DrinksConsumedRepository {
    private final DrinksConsumedDao drinksConsumedDao;
    private final LiveData<List<DrinkConsumed>> drinksConsumedLiveData;
    private final Context context;

    private final Calculator calculator;

    public DrinksConsumedRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        drinksConsumedDao = db.drinksConsumedDao();
        drinksConsumedLiveData = drinksConsumedDao.getAll();
        context = application.getApplicationContext();
        calculator = Calculator.getInstance();
    }

    public LiveData<List<DrinkConsumed>> getDrinksConsumed() {
        return drinksConsumedLiveData;
    }

    public void insert(DrinkConsumed drinkConsumed) {
        AppDatabase.databaseWriteExecutor.execute(() -> drinksConsumedDao.insert(drinkConsumed));
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(drinksConsumedDao::deleteAll);
    }

    public int getPersonWeight() {
        SharedPreferences prefs = context.getSharedPreferences("Profile", MODE_PRIVATE);
        return prefs.getInt("weight", 0);
    }

    public int getPersonGender() {
        SharedPreferences prefs = context.getSharedPreferences("Profile", MODE_PRIVATE);
        return prefs.getInt("gender", 0);
    }

    public void deleteDrink(DrinkConsumed drinkConsumed) {
        AppDatabase.databaseWriteExecutor.execute(() -> drinksConsumedDao.delete(drinkConsumed));
    }

    public double getImpact(DrinkConsumed drinkConsumed) {
        return calculator.calculateImpactOfDrink(drinkConsumed, getPersonWeight(), getPersonGender());
    }
}
