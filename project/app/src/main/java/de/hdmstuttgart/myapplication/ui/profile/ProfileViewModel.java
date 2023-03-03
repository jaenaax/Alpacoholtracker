package de.hdmstuttgart.myapplication.ui.profile;

import static android.content.Context.MODE_PRIVATE;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import de.hdmstuttgart.myapplication.database.DrinksConsumedRepository;

public class ProfileViewModel extends AndroidViewModel {
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final DrinksConsumedRepository repository;

    public ProfileViewModel(@NonNull Application application, Context context) {
        super(application);
        this.context = context;
        repository = new DrinksConsumedRepository(application);
    }

    public void savePerson(int weight, int gender) {
        SharedPreferences prefs = context.getSharedPreferences("Profile", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("weight", weight);
        editor.putInt("gender", gender);
        editor.apply();
    }

    public int getPersonWeight() {
        return repository.getPersonWeight();
    }

    public int getPersonGender() {
        return repository.getPersonGender();
    }
}
