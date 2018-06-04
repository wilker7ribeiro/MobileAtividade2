package br.com.wilker.atividade2hospital;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.wilker.atividade2hospital.dao.PacienteDao;
import br.com.wilker.atividade2hospital.entity.Paciente;

/**
 * Created by Wilker on 03/06/2018.
 */

@Database(entities = {Paciente.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PacienteDao pacienteDao();
}
