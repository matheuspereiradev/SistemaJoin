package com.app.join.sistemajoin.Tools;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {
    private Context context;
    private SharedPreferences preferences;
    private String nomeAquivo = "Join.preferencias";
    private int mode = 0;
    private SharedPreferences.Editor editor;

    private final String ChaveIndentificador = "IdentificadorUsuarioLogado";
    private final String ChaveNome = "nomeUsuarioLogado";

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(nomeAquivo, mode);
        editor = preferences.edit();
    }
    public void salvaUsuarioLogado(String identificador, String nome){
        editor.putString(ChaveIndentificador, identificador);
        editor.putString(ChaveNome, nome);
        editor.commit();
    }
    public String getIdentificador(){
        return preferences.getString(ChaveIndentificador, null);
    }
    public String getNome(){
        return preferences.getString(ChaveNome, null);
    }
}
