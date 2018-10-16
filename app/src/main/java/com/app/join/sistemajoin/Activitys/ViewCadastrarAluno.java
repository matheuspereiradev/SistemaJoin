package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.app.join.sistemajoin.Tools.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;

import java.util.InputMismatchException;

public class ViewCadastrarAluno extends AppCompatActivity {

    private Button SalvarAluno;
    private EditText ctNomeAluno, ctTelAluno, ctNomeResponsavel, ctCPFResp, ctEmailResp;
    private String key, idEscola;
    private Aluno aluno;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_aluno);

        ctNomeAluno = (EditText) findViewById(R.id.ctNomeAluno);
        ctTelAluno = (EditText) findViewById(R.id.ctTelAluno);
        ctEmailResp = (EditText) findViewById(R.id.ctEmailResp);
        ctNomeResponsavel = (EditText) findViewById(R.id.ctNomeResponsavel);
        ctCPFResp = (EditText) findViewById(R.id.ctCPFResp);
        SalvarAluno = (Button) findViewById(R.id.SalvarAluno);

        SimpleMaskFormatter simpleMaskTelAl = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mascaraTelAl = new MaskTextWatcher(ctTelAluno, simpleMaskTelAl);
        ctTelAluno.addTextChangedListener(mascaraTelAl);

        SimpleMaskFormatter simpleMaskTelCPFP = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mascaraTelCPFP = new MaskTextWatcher(ctCPFResp, simpleMaskTelCPFP);
        ctCPFResp.addTextChangedListener(mascaraTelCPFP);

        intent = getIntent();
        key = intent.getStringExtra("key");
        idEscola = intent.getStringExtra("idEscola");

        if (key != null) {
            preencheCampos();
            SalvarAluno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ctNomeAluno.getText().equals("") || ctTelAluno.getText().equals("")
                            || ctNomeResponsavel.getText().equals("") || ctCPFResp.getText().equals("") ||
                            ctCPFResp.getText().length() < 14 || ctTelAluno.getText().length() < 15) {
                        if (ctNomeAluno.getText().equals("")) {
                            ctNomeAluno.isSelected();
                            Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                        } else if (ctTelAluno.getText().equals("") || ctTelAluno.getText().length() < 15) {
                            ctTelAluno.isSelected();
                            Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                        } else if (ctNomeResponsavel.getText().equals("")) {
                            ctNomeResponsavel.isSelected();
                            Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                        } else if (ctCPFResp.getText().equals("") || ctCPFResp.getText().length() < 14) {
                            ctCPFResp.isSelected();
                            Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                        }
                    } else if (!validaCpf(ctCPFResp.getText().toString())) {
                        ctCPFResp.isSelected();
                        Toast.makeText(getBaseContext(), "Os dados inseridos são inválidos!", Toast.LENGTH_SHORT).show();
                    } else {
                        editarAluno(setDadosEditar());
                        chamaListarAluno();
                        finish();
                    }
                }
            });

        } else {
            SalvarAluno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ctNomeAluno.getText().equals("") || ctTelAluno.getText().equals("")
                            || ctNomeResponsavel.getText().equals("") || ctCPFResp.getText().equals("") ||
                            ctCPFResp.getText().length() < 14 || ctTelAluno.getText().length() < 15) {
                        if (ctNomeAluno.getText().equals("")) {
                            ctNomeAluno.isSelected();
                            Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                        } else if (ctTelAluno.getText().equals("") || ctTelAluno.getText().length() < 15) {
                            ctTelAluno.isSelected();
                            Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                        } else if (ctNomeResponsavel.getText().equals("")) {
                            ctNomeResponsavel.isSelected();
                            Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                        } else if (ctCPFResp.getText().equals("") || ctCPFResp.getText().length() < 14) {
                            ctCPFResp.isSelected();
                            Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                        }
                    } else if (!validaCpf(ctCPFResp.getText().toString())) {
                        ctCPFResp.isSelected();
                        Toast.makeText(getBaseContext(), "Os dados inseridos são inválidos!", Toast.LENGTH_SHORT).show();
                    } else {
                        salvarAluno(setDadosSalvar());
                        chamaTelaListaTurma();
                        finish();
                    }
                }
            });
        }
    }

    private boolean validaCpf(String CPF) {

        CPF = CPF.replace('.', ' ');
        CPF = CPF.replace('-', ' ');
        CPF = CPF.replaceAll(" ", "");

        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return (false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    private void chamaTelaListaTurma() {
        Intent listProf = new Intent(ViewCadastrarAluno.this, ViewListarTurmas.class);
        listProf.putExtra("idAluno", aluno.getIdAluno());
        listProf.putExtra("nome", aluno.getNome());
        listProf.putExtra("tel", aluno.getTelefone());
        listProf.putExtra("email", aluno.getEmailResponsavel());
        listProf.putExtra("cpf", aluno.getCpfResponsavel());
        listProf.putExtra("status", aluno.getStatus());
        listProf.putExtra("senha", aluno.getSenha());
        listProf.putExtra("keyTurma", aluno.getKeyTurma());
        listProf.putExtra("idEscola", aluno.getIdEscola());
        listProf.putExtra("remetente", "aluno");
        startActivity(listProf);
    }

    private void chamaListarAluno() {
        Intent listaluno = new Intent(ViewCadastrarAluno.this, ViewListarAlunos.class);
        listaluno.putExtra("idEscola", intent.getStringExtra("idEscola"));
        startActivity(listaluno);
    }

    private void salvarAluno(Aluno a) {
        DatabaseReference dataAluno = ConfiguracaoFirebase.getFirebase().child("aluno");
        dataAluno.child(a.getIdAluno()).setValue(a);
    }

    private Aluno setDadosSalvar() {
        aluno = new Aluno();
        aluno.setNome(ctNomeAluno.getText().toString());
        aluno.setEmailResponsavel(ctEmailResp.getText().toString());
        String CPF = ctCPFResp.getText().toString();
        CPF = CPF.replace('.', ' ');
        CPF = CPF.replace('-', ' ');
        CPF = CPF.replaceAll(" ", "");
        aluno.setCpfResponsavel(CPF);
        String idUsuario = Base64Custon.codificadorBase64(aluno.getCpfResponsavel() + aluno.getNome());
        aluno.setIdAluno(idUsuario);
        aluno.setNomeResponsavel(ctNomeResponsavel.getText().toString());
        aluno.setSenha(geraSenha(aluno.getCpfResponsavel()));
        aluno.setStatus("Ativo");
        aluno.setTelefone(ctTelAluno.getText().toString());
        aluno.setKeyTurma("0001");
        aluno.setIdEscola(idEscola);
        return aluno;
    }

    private Aluno setDadosEditar() {
        aluno = new Aluno();
        aluno.setNome(ctNomeAluno.getText().toString());
        aluno.setEmailResponsavel(ctEmailResp.getText().toString());
        aluno.setIdAluno(intent.getStringExtra("key"));
        aluno.setSenha(intent.getStringExtra("senha"));
        aluno.setNomeResponsavel(ctNomeResponsavel.getText().toString());
        aluno.setStatus("Ativo");
        String CPF = ctCPFResp.getText().toString();
        CPF = CPF.replace('.', ' ');
        CPF = CPF.replace('-', ' ');
        CPF = CPF.replaceAll(" ", "");
        aluno.setCpfResponsavel(CPF);
        aluno.setTelefone(ctTelAluno.getText().toString());
        //aluno.setKeyTurma("sem Turma");
        aluno.setIdEscola(intent.getStringExtra("idEscola"));
        return aluno;
    }

    private String geraSenha(String CPF) {
        CPF = CPF.replace('.', ' ');
        CPF = CPF.replace('-', ' ');
        CPF = CPF.replaceAll(" ", "");
        String senha = "";
        senha = CPF.substring(0, 6);

        return senha;
    }

    private void editarAluno(Aluno a) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("aluno");
        data.child(a.getIdAluno()).updateChildren(a.toMap());
    }

    private void preencheCampos() {
        ctNomeAluno.setText(intent.getStringExtra("nome"));
        ctEmailResp.setText(intent.getStringExtra("email"));
        ctCPFResp.setText(intent.getStringExtra("cpf"));
        ctNomeResponsavel.setText(intent.getStringExtra("nomeRes"));
        ctTelAluno.setText(intent.getStringExtra("tel"));
    }
}
