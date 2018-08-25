package com.app.join.sistemajoin.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Aluno extends Pessoa {

    private String matricola;
    private String cpfResponsavel;
    private String nomeResponsavel;
    private String EmailResponsavel;

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("matricola", getMatricola());
        hashMap.put("nomeResponsavel", getNomeResponsavel());
        hashMap.put("cpfResponsavel", getCpfResponsavel());
        hashMap.put("emailResponsavel", getEmailResponsavel());
        hashMap.put("nome", getNome());
        hashMap.put("telefone", getTelefone());
        hashMap.put("status", getStatus());
        hashMap.put("keyTurma", getKeyTurma());
        hashMap.put("senha", getSenha());
        return hashMap;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public String getCpfResponsavel() {
        return cpfResponsavel;
    }

    public void setCpfResponsavel(String cpfResponsavel) {
        this.cpfResponsavel = cpfResponsavel;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getEmailResponsavel() {
        return EmailResponsavel;
    }

    public void setEmailResponsavel(String emailResponsavel) {
        EmailResponsavel = emailResponsavel;
    }
}
