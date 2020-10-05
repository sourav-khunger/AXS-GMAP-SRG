package com.doozycod.axs.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.doozycod.axs.Database.DAO.DriverInfoDAO;
import com.doozycod.axs.Database.DAO.ReasonDAO;
import com.doozycod.axs.Database.DAO.RunInfoDAO;
import com.doozycod.axs.Database.DAO.StatusDAO;
import com.doozycod.axs.Database.DAO.TaskInfoDAO;
import com.doozycod.axs.Database.Entities.DriverInfoEntity;
import com.doozycod.axs.Database.Entities.ReasonEntity;
import com.doozycod.axs.Database.Entities.RunInfoEntity;
import com.doozycod.axs.Database.Entities.StatusEntity;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {DriverInfoEntity.class, TaskInfoEntity.class, RunInfoEntity.class, StatusEntity.class, ReasonEntity.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract DriverInfoDAO driverInfoDAO();

    public abstract TaskInfoDAO taskInfoDAO();

    public abstract RunInfoDAO runInfoDAO();

    public abstract StatusDAO statusDAO();

    public abstract ReasonDAO reasonDAO();

    public static volatile Database INSTANCE;
    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static RoomDatabase.Callback sDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    DriverInfoDAO driverInfoDAO = INSTANCE.driverInfoDAO();
                    TaskInfoDAO taskInfoDAO = INSTANCE.taskInfoDAO();
                    RunInfoDAO runInfoDAO = INSTANCE.runInfoDAO();
                    StatusDAO statusDAO = INSTANCE.statusDAO();
                    ReasonDAO reasonDAO = INSTANCE.reasonDAO();
                    driverInfoDAO.deleteDriverInfos();
                    statusDAO.deleteAllStatuses();
                    reasonDAO.deleteAllReasons();
                }
            });
        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
        }
    };

    public static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "driver_detail_database").addCallback(sDatabaseCallback).allowMainThreadQueries().build();
                }

            }
        }

        return INSTANCE;

    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
