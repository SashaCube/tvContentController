package com.oleksandr.havryliuk.tvcontentcontroller.client.weather.room;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {MyWeather.class}, version = 1)
public abstract class WeatherRoomDatabase extends RoomDatabase {

    public abstract MyWeatherDao myWeatherDao();
    private static volatile WeatherRoomDatabase INSTANCE;

    // Singleton
    public static WeatherRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (WeatherRoomDatabase.class){
                if(INSTANCE == null){
                    // Create database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherRoomDatabase.class, "weather_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static Callback sRoomDatabaseCallback =
            new Callback(){

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAnsync(INSTANCE).execute();
                }
            };

    public static class PopulateDbAnsync extends AsyncTask<Void, Void, Void> {
        private final MyWeatherDao mDao;

        PopulateDbAnsync(WeatherRoomDatabase db){
            mDao = db.myWeatherDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
