package de.hdmstuttgart.myapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import de.hdmstuttgart.myapplication.model.DrinkConsumed;

@Dao
public interface DrinksConsumedDao {

    @Query("SELECT * FROM drinkconsumed")
    LiveData<List<DrinkConsumed>> getAll();

    @Query("DELETE FROM drinkconsumed")
        //reset the database
    void deleteAll();

    @Insert
    void insert(DrinkConsumed drinkConsumed);

    @Delete
    void delete(DrinkConsumed drink);
}
