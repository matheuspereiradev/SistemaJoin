package com.app.join.sistemajoin.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Agenda {



    private String titulo;
    private String mensagem;
    private String data;
    private String nomeProfessor;
    private String idDestino;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashAgenda = new HashMap<>();
        hashAgenda.put("titulo", getTitulo());
        hashAgenda.put("mensagem", getMensagem());
        hashAgenda.put("data", getData());
        hashAgenda.put("nomeProfessor", getNomeProfessor());
        hashAgenda.put("idDestino", getIdDestino());
        return hashAgenda;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(String idDestino) {
        this.idDestino = idDestino;
    }
}
