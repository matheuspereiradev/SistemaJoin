package com.app.join.sistemajoin.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.join.sistemajoin.R;

public class ViewExibirInformacoesTurma extends AppCompatActivity {

    TextView cmpNomeTurma,cmpTurnoTurma,cmpProfTurma,cmpQntAlunosTurma;
    ImageButton btEditTurma,btConfigTurma,btExcluirTurma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exibir_informacoes_turma);

        cmpNomeTurma= (TextView) findViewById(R.id.cmpNomeTurma);
        cmpTurnoTurma= (TextView) findViewById(R.id.cmpTurnoTurma);
        cmpProfTurma = (TextView) findViewById(R.id.cmpProfTurma);
        cmpQntAlunosTurma= (TextView) findViewById(R.id.cmpQntAlunosTurma);
        btEditTurma= (ImageButton) findViewById(R.id.btEditTurma);
        btConfigTurma= (ImageButton) findViewById(R.id.btConfigTurma);
        btExcluirTurma= (ImageButton) findViewById(R.id.btExcluirTurma);
    }
}
