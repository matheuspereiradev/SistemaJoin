package com.app.join.sistemajoin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class ViewCadastrarEscola extends AppCompatActivity {

    EditText ctNomeEsc, ctTelEsc, ctEmailEsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_escola);

        //======conexão com o xml
        ctTelEsc=(EditText)findViewById(R.id.ctTelEsc);
        ctEmailEsc=(EditText) findViewById(R.id.ctEmailEsc);
        ctNomeEsc=(EditText)findViewById(R.id.ctNomeEsc);
        //fim da conexão com o xml=========

        //=====criar mascara no campo telefone escola
        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN) N NNNN NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(ctTelEsc, smf);
        ctTelEsc.addTextChangedListener(mtw);
        //FIM MASCARA==========


    }
}
