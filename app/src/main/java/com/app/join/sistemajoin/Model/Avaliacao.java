package com.app.join.sistemajoin.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Avaliacao {

    private int av;
    private String idAluno;
    private String dataAv;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapAvaliacao = new HashMap<>();
        hashMapAvaliacao.put("idAluno", getIdAluno());
        hashMapAvaliacao.put("av", getAv());
        hashMapAvaliacao.put("dataAv", getDataAv());
        return hashMapAvaliacao;
    }

    public String getDataAv() {
        return dataAv;
    }

    public void setDataAv(String dataAv) {
        this.dataAv = dataAv;
    }

    public int getAv() {
        return av;
    }

    public void setAv(int av) {
        this.av = av;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }
}
