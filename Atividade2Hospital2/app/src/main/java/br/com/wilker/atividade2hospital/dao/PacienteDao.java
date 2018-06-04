package br.com.wilker.atividade2hospital.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.content.Context;

import java.util.List;

import br.com.wilker.atividade2hospital.DataBaseService;
import br.com.wilker.atividade2hospital.entity.Paciente;

/**
 * Created by Wilker on 03/06/2018.
 */

@Dao
public interface PacienteDao {

    @Query("SELECT * FROM paciente")
    List<Paciente> getAll();

    @Query("SELECT * FROM paciente WHERE id IN (:pacientesId)")
    List<Paciente> loadAllByIds(int[] pacientesId);

    @Query("SELECT * FROM paciente WHERE nome LIKE :nome LIMIT 1")
    Paciente findByName(String nome);

    @Query("SELECT * FROM paciente WHERE id = :id LIMIT 1")
    Paciente findById(int id);

    @Update
    void updatePaciente(Paciente paciente);

    @Insert
    void insertAll(Paciente... users);

    @Delete
    void delete(Paciente user);
}
