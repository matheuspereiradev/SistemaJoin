package com.app.join.sistemajoin.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Avaliacao {

    private int avComportamento;
    private int avAtividade;
    private int avParticipacao;
    private String idAluno;
    private String dataAv;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapAvaliacao = new HashMap<>();
        hashMapAvaliacao.put("idAluno", getIdAluno());
        hashMapAvaliacao.put("avParticipacao", getAvParticipacao());
        hashMapAvaliacao.put("avAtividade", getAvAtividade());
        hashMapAvaliacao.put("avComportamento", getAvComportamento());
        hashMapAvaliacao.put("dataAv", getDataAv());
        return hashMapAvaliacao;
    }

    public String getDataAv() {
        return dataAv;
    }

    public void setDataAv(String dataAv) {
        this.dataAv = dataAv;
    }

    public int getAvComportamento() {
        return avComportamento;
    }

    public void setAvComportamento(int avComportamento) {
        this.avComportamento = avComportamento;
    }

    public int getAvAtividade() {
        return avAtividade;
    }

    public void setAvAtividade(int avAtividade) {
        this.avAtividade = avAtividade;
    }

    public int getAvParticipacao() {
        return avParticipacao;
    }

    public void setAvParticipacao(int avParticipacao) {
        this.avParticipacao = avParticipacao;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }
}
