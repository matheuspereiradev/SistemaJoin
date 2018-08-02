package com.app.join.sistemajoin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class ViewCadastrarEscola extends AppCompatActivity {

    EditText ctNomeEsc, ctTelEsc, ctEmailEsc, ctCNPJEsc;
    Button btProsseguir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_escola);

        //======conexão com o xml
        ctTelEsc=(EditText)findViewById(R.id.ctTelEsc);
        ctEmailEsc=(EditText) findViewById(R.id.ctEmailEsc);
        ctNomeEsc=(EditText)findViewById(R.id.ctNomeEsc);
        ctCNPJEsc=(EditText)findViewById(R.id.ctCNPJEsc);
        btProsseguir = (Button)findViewById(R.id.btProsseguir);
        //fim da conexão com o xml=========

        //=====criar mascara no campo telefone escola
        SimpleMaskFormatter simpleMaskTel = new SimpleMaskFormatter("(NN) N NNNN NNNN");
        MaskTextWatcher mascaraTel = new MaskTextWatcher(ctTelEsc, simpleMaskTel);
        ctTelEsc.addTextChangedListener(mascaraTel);
        //FIM MASCARA==========
        //=====criar mascara no campo CNPJ escola
        SimpleMaskFormatter simpleMaskCNPJ = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        MaskTextWatcher mascaraCNPJ = new MaskTextWatcher(ctCNPJEsc, simpleMaskCNPJ);
        ctCNPJEsc.addTextChangedListener(mascaraCNPJ);
        //FIM MASCARA==========

        //Botão chamar tela de Endereço
        btProsseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ViewCadastrarEndereco.class);
                startActivity(i);
            }
        });

    }
}
