package com.app.join.sistemajoin.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Turma {

    private String nome;
    private String id;
    private String nomeProfessor;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nome", getNome());
        hashMap.put("id", getId());
        hashMap.put("nomeprofessor", getId());
        return hashMap;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }
}

