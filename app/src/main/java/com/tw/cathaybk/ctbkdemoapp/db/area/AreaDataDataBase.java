package com.tw.cathaybk.ctbkdemoapp.db.area;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {AreaData.class}, version = 2, exportSchema = false)
public abstract  class AreaDataDataBase extends RoomDatabase {
    /**
     * 單例模式
     * volatile 確保線程安全
     * 線程安全意味著改對象會被許多線程使用
     * 可以被看作是一種 “程度較輕的 synchronized”
     */
    private static volatile AreaDataDataBase INSTANCE;
    /**
     * 該方法由於獲得 DataBase 對象
     * abstract
     * @return
     */
    public abstract AreaDataDao getAreaDataDao();

    public static AreaDataDataBase getInstance(Context context) {
        // 為空則進行實例化, 否則直接返回
        if (null == INSTANCE) {
            synchronized (AreaDataDataBase.class) {
                if (null == INSTANCE){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AreaDataDataBase.class, "AreaData.db")
                            .addMigrations(MIGRATION_1to2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static final Migration MIGRATION_1to2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE areadata ADD COLUMN image TEXT");
        }
    };
}
