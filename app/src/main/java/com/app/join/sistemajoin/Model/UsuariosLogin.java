package com.app.join.sistemajoin.Model;

public class UsuariosLogin {

    private int id;
    private String usuario;
    private String senha;
    private String tipo;
    private int status;

    public UsuariosLogin(String usuario, String senha, String tipo, int status) {
        this.usuario = usuario;
        this.senha = senha;
        this.tipo = tipo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
