package de.hdmstuttgart.myapplication.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import de.hdmstuttgart.myapplication.R;
import de.hdmstuttgart.myapplication.model.Drink;
import de.hdmstuttgart.myapplication.model.DrinkConsumed;

@Database(entities = {Drink.class, DrinkConsumed.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DrinkDao drinkDao();

    public abstract DrinksConsumedDao drinksConsumedDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "drinksDb").addCallback(roomDBCallabck)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomDBCallabck = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                DrinkDao dao = INSTANCE.drinkDao();

                dao.insert(new Drink("Beer", 5.0, 500, String.valueOf(R.drawable.beer)));
                dao.insert(new Drink("Wine", 12.0, 250, String.valueOf(R.drawable.wine)));
                dao.insert(new Drink("Vodka", 37.5, 20, String.valueOf(R.drawable.vodka)));
                dao.insert(new Drink("Jägermeister", 37.5, 20, String.valueOf(R.drawable.jaegermeister)));
                dao.insert(new Drink("Shot", 40.0, 20, String.valueOf(R.drawable.shot)));
            });
        }
    };

}
