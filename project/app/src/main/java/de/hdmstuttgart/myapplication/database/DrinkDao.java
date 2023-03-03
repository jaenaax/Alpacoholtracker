package de.hdmstuttgart.myapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import de.hdmstuttgart.myapplication.model.Drink;

@Dao
public interface DrinkDao {

    @Query("SELECT * FROM drink")
    LiveData<List<Drink>> getAll();

    @Insert
    void insert(Drink drink);

    @Delete
    void delete(Drink drink);
}
