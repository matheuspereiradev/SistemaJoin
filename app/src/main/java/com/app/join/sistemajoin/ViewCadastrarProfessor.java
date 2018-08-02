package com.app.join.sistemajoin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class ViewCadastrarProfessor extends AppCompatActivity {

    EditText ctEmailProf,ctNomeProf,ctDataNascProf;
    Button btProximoProf1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_professor);

        //==========inicio da conexão java cm xml
        ctNomeProf=(EditText)findViewById(R.id.ctNomeProf);
        ctEmailProf=(EditText)findViewById(R.id.ctEmailProf);
        ctDataNascProf =(EditText)findViewById(R.id.ctDataNascProf);
        btProximoProf1=(Button)findViewById(R.id.btProximoProf1);
        //fim da conexão==========


        //========aplicando mascara na data de nascimento
        SimpleMaskFormatter maskDate = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mascaraData = new MaskTextWatcher(ctDataNascProf, maskDate);
        ctDataNascProf.addTextChangedListener(mascaraData);
        //fim mascara======


        //função do botão prosseguir
        btProximoProf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(),ViewCadastrarProfessor2.class);
                startActivity(i);
            }
        });


    }
}
