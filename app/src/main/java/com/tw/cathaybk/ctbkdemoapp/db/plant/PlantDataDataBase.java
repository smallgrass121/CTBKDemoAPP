package com.tw.cathaybk.ctbkdemoapp.db.plant;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PlantData.class}, version = 1, exportSchema = false)
public abstract  class PlantDataDataBase extends RoomDatabase {
    /**
     * 單例模式
     * volatile 確保線程安全
     * 線程安全意味著改對象會被許多線程使用
     * 可以被看作是一種 “程度較輕的 synchronized”
     */
    private static volatile PlantDataDataBase INSTANCE;
    /**
     * 該方法由於獲得 DataBase 對象
     * abstract
     * @return
     */
    public abstract PlantDataDao getPlantDataDao();

    public static PlantDataDataBase getInstance(Context context) {
        // 為空則進行實例化, 否則直接返回
        if (null == INSTANCE) {
            synchronized (PlantDataDataBase.class) {
                if (null == INSTANCE){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlantDataDataBase.class, "PlantData.db")
//                            .addMigrations(MIGRATION_1to2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    public static final Migration MIGRATION_1to2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE plantdata ADD COLUMN image TEXT");
//        }
//    };
}
