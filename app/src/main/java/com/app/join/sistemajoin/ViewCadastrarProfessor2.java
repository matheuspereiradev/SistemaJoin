package com.app.join.sistemajoin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class ViewCadastrarProfessor2 extends AppCompatActivity {

    EditText ctCPFProf,ctRGProf,ctTelProf,ctFormProf;
    Button btProxPEndereco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_professor2);

        //===========Inicio da conexão java com xml
        ctCPFProf=(EditText)findViewById(R.id.ctCPFProf);
        ctRGProf=(EditText)findViewById(R.id.ctRGProf);
        ctTelProf=(EditText)findViewById(R.id.ctTelProf);
        ctFormProf=(EditText)findViewById(R.id.ctFormProf);
        btProxPEndereco=(Button)findViewById(R.id.btProxPEndereco);
        //fim da conexão java com xml==============

        //========aplicando mascara CPF
        SimpleMaskFormatter maskCPF = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mascaraCPF = new MaskTextWatcher(ctCPFProf, maskCPF);
        ctCPFProf.addTextChangedListener(mascaraCPF);
        //fim mascara======
        
        //========aplicando mascara telefone
        SimpleMaskFormatter maskTel = new SimpleMaskFormatter("(NN) N NNNN-NNNN");
        MaskTextWatcher mascaraTel = new MaskTextWatcher(ctTelProf, maskTel);
        ctTelProf.addTextChangedListener(mascaraTel);
        //fim mascara======


    }
}
