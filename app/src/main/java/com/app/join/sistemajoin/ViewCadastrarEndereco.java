package com.app.join.sistemajoin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class ViewCadastrarEndereco extends AppCompatActivity {

    EditText ctEscEndNum,ctEscEndRua,ctEscEndBairro,ctEscEndCEP,ctEscEndCidade;
    Spinner spnUF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_endereco);

        //======inicio conexão xml
        ctEscEndNum=(EditText) findViewById(R.id.ctEscEndNum);
        ctEscEndRua=(EditText) findViewById(R.id.ctEscEndRua);
        ctEscEndBairro=(EditText) findViewById(R.id.ctEscEndBairro);
        ctEscEndCEP=(EditText) findViewById(R.id.ctEscEndCEP);
        ctEscEndCidade=(EditText) findViewById(R.id.ctEscEndCidade);
        spnUF=(Spinner) findViewById(R.id.spnUF);
        //fim conexão xml========

        //========aplicando mascara no cep
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtw = new MaskTextWatcher(ctEscEndCEP, smf);
        ctEscEndCEP.addTextChangedListener(mtw);
        //fim mascara======


        ArrayAdapter adapter=  ArrayAdapter.createFromResource(getBaseContext(),R.array.estados,
                R.layout.support_simple_spinner_dropdown_item);

        spnUF.setAdapter(adapter);
    }
}
