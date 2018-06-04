package br.com.wilker.atividade2hospital;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by Wilker on 03/06/2018.
 */

public class DataBaseService {
    static private AppDatabase appDatabase;
    public static AppDatabase getDatabase(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "atividade_hospital").build();
        }
        return appDatabase;
    }
}
