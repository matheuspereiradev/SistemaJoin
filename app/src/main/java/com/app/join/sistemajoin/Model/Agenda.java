package com.app.join.sistemajoin.Model;

import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

public class Agenda {


    private int id;
    private String titulo;
    private String mensagem;
    private String data;
    private String nomeCriador;
    private String idDestino;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashAgenda = new HashMap<>();
        hashAgenda.put("id", getId());
        hashAgenda.put("titulo", getTitulo());
        hashAgenda.put("mensagem", getMensagem());
        hashAgenda.put("data", getData());
        hashAgenda.put("nomeCriador", getNomeCriador());
        hashAgenda.put("idDestino", getIdDestino());
        return hashAgenda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNomeCriador() {
        return nomeCriador;
    }

    public void setNomeCriador(String nomeCriador) {
        this.nomeCriador = nomeCriador;
    }

    public String getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(String idDestino) {
        this.idDestino = idDestino;
    }
}
