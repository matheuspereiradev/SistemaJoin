package com.app.join.sistemajoin.Model;

import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Escola {

    private String id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private String senha;
    private String cidade;
    private int status = 0;

    public Escola() {
    }

    public void salvar() {

        DatabaseReference data = ConfiguracaoFirebase.getFirebase();
        data.child("escola").child(String.valueOf(getId())).setValue(this);
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapUsuarios = new HashMap<>();

        hashMapUsuarios.put("id", getId());
        hashMapUsuarios.put("nome", getNome());
        hashMapUsuarios.put("cnpj", getCnpj());
        hashMapUsuarios.put("telefone", getTelefone());
        hashMapUsuarios.put("email", getEmail());
        hashMapUsuarios.put("senha", getSenha());
        hashMapUsuarios.put("cidade", getCidade());
        hashMapUsuarios.put("status", getStatus());

        return hashMapUsuarios;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
