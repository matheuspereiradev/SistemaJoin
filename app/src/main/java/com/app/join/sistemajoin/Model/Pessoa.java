package com.app.join.sistemajoin.Model;

public class Pessoa {

    private String nome;
    private String telefone;
    private String keyTurma;
    private String senha;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getKeyTurma() {
        return keyTurma;
    }

    public void setKeyTurma(String keyTurma) {
        this.keyTurma = keyTurma;
    }
}
