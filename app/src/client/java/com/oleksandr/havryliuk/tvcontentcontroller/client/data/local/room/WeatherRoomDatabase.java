package com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {MyWeather.class}, version = 2)
public abstract class WeatherRoomDatabase extends RoomDatabase {

    public abstract MyWeatherDao myWeatherDao();

    private static volatile WeatherRoomDatabase INSTANCE;

    // Singleton
    public static WeatherRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WeatherRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherRoomDatabase.class, "weather_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static Callback sRoomDatabaseCallback =
            new Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAnsync(INSTANCE).execute();
                }
            };

    public static class PopulateDbAnsync extends AsyncTask<Void, Void, Void> {
        private final MyWeatherDao mDao;

        PopulateDbAnsync(WeatherRoomDatabase db) {
            mDao = db.myWeatherDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
