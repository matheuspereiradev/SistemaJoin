package com.app.join.sistemajoin.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Professor extends Pessoa {

    private String idProfessor;
    private String email;
    private String rg;
    private String cpf;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapUsuarios = new HashMap<>();

        hashMapUsuarios.put("idProfessor", getIdProfessor());
        hashMapUsuarios.put("email", getNome());
        hashMapUsuarios.put("rg", getRg());
        hashMapUsuarios.put("cpf", getCpf());
        hashMapUsuarios.put("nome", getNome());
        hashMapUsuarios.put("dataNacimento", getDataNacimento());
        hashMapUsuarios.put("telefone", getTelefone());
        hashMapUsuarios.put("tipo", getTipo());
        hashMapUsuarios.put("status", getStatus());
        hashMapUsuarios.put("keyTurma", getKeyTurma());
        hashMapUsuarios.put("senha", getSenha());


        return hashMapUsuarios;
    }

    public String getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(String idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
