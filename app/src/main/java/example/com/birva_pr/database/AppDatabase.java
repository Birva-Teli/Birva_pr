package example.com.birva_pr.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * -------------------Room DB--------------------
 * Created by android3 on 12/12/17.
 */
@Database(entities = {UserDetailsBean.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
private static AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "app_db")
//Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                           // recreate the database if necessary
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}