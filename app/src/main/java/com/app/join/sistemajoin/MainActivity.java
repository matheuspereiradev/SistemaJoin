package com.app.join.sistemajoin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button cadesc,endereco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cadesc=(Button) findViewById(R.id.btcadesc);
        endereco=(Button)findViewById(R.id.endesc);
        cadesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),ViewCadastrarEscola.class));
            }
        });
        endereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),ViewCadastrarEscolaEndereco.class));
            }
        });
    }
}
