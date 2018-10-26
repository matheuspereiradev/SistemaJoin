package com.app.join.sistemajoin.Model;

import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

public class Turma {

    private String nome;
    private String idTurma;
    private String idEscola;
    private int idadeMax;
    private int idadeMin;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nome", getNome());
        hashMap.put("idTurma", getIdTurma());
        hashMap.put("idEscola", getIdEscola());
        hashMap.put("idadeMax", getIdadeMax());
        hashMap.put("idadeMin", getIdadeMin());
        return hashMap;
    }

    public String getIdEscola() {
        return idEscola;
    }

    public void setIdEscola(String idEscola) {
        this.idEscola = idEscola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(String id) {
        this.idTurma = id;
    }

    public int getIdadeMax() {
        return idadeMax;
    }

    public void setIdadeMax(int idadeMax) {
        this.idadeMax = idadeMax;
    }

    public int getIdadeMin() {
        return idadeMin;
    }

    public void setIdadeMin(int idadeMin) {
        this.idadeMin = idadeMin;
    }
}

