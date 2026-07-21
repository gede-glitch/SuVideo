package com.su.feature.mediaplayer.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {VideoHistory.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static volatile AppDataBase INSTANCE;
    public abstract VideoHistoryDAO videoHistoryDao();
    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDataBase.class,
                            "Video_history_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
