package br.com.wilker.atividade2hospital.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Wilker on 03/06/2018.
 */
@Entity
public class Paciente {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "numero_leito")
    private int numeroLeito;

    @ColumnInfo(name = "pressao_arterial_sistolica")
    private Integer pressaoArterialSistolica;

    @ColumnInfo(name = "pressao_arterial_diastolica")
    private Integer pressaoArterialDiastolica;

    @ColumnInfo(name = "temperatura")
    private Integer temperatura;

    @ColumnInfo(name = "batimentos")
    private Integer batimentosCardiacos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumeroLeito() {
        return numeroLeito;
    }

    public void setNumeroLeito(Integer numeroLeito) {
        this.numeroLeito = numeroLeito;
    }

    public Integer getPressaoArterialSistolica() {
        return pressaoArterialSistolica;
    }

    public void setPressaoArterialSistolica(Integer pressaoArterialSistolica) {
        this.pressaoArterialSistolica = pressaoArterialSistolica;
    }

    public Integer getPressaoArterialDiastolica() {
        return pressaoArterialDiastolica;
    }

    public void setPressaoArterialDiastolica(Integer pressaoArterialDiastolica) {
        this.pressaoArterialDiastolica = pressaoArterialDiastolica;
    }

    public Integer getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Integer temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getBatimentosCardiacos() {
        return batimentosCardiacos;
    }

    public void setBatimentosCardiacos(Integer batimentosCardiacos) {
        this.batimentosCardiacos = batimentosCardiacos;
    }


}
