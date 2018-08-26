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
import com.app.join.sistemajoin.Model.Professor;
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
import com.google.firebase.database.Query;

import java.util.InputMismatchException;

public class ViewCadastrarAluno extends AppCompatActivity {

    Button btSalvarAluno;
    EditText ctNomeAluno,ctMatricAluno,ctTelAluno,ctNomeResponsavel,ctCPFResp;

    Aluno aluno;
    String key = "";
    private DatabaseReference firebase;
    FirebaseAuth autenticacao;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_aluno);

        ctNomeAluno=(EditText)findViewById(R.id.ctNomeAluno);
        ctMatricAluno=(EditText)findViewById(R.id.ctMatricAluno);
        ctTelAluno =(EditText)findViewById(R.id.ctTelAluno);
        ctNomeResponsavel=(EditText)findViewById(R.id.ctNomeResponsavel);
        ctCPFResp=(EditText)findViewById(R.id.ctCPFResp);
        btSalvarAluno=(Button)findViewById(R.id.btSalvarAluno);

        //=====criar mascara no campo telefone
        SimpleMaskFormatter simpleMaskTelAl = new SimpleMaskFormatter("(NN) N NNNN NNNN");
        MaskTextWatcher mascaraTelAl = new MaskTextWatcher(ctTelAluno, simpleMaskTelAl);
        ctTelAluno.addTextChangedListener(mascaraTelAl);
        //FIM MASCARA==========

        //=====criar mascara no campo CPF
        SimpleMaskFormatter simpleMaskTelCPFP = new SimpleMaskFormatter("(NN) N NNNN NNNN");
        MaskTextWatcher mascaraTelCPFP = new MaskTextWatcher(ctCPFResp, simpleMaskTelCPFP);
        ctCPFResp.addTextChangedListener(mascaraTelCPFP);
        //FIM MASCARA==========

        intent = getIntent();
        key = intent.getStringExtra("key");

        if (key != null) {
            preencheCampos();
            btSalvarAluno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ctNomeAluno.getText().equals("") || ctMatricAluno.getText().equals("") || ctTelAluno.getText().equals("")
                            || ctNomeResponsavel.getText().equals("") || ctCPFResp.getText().equals("")) {
                        Toast.makeText(getBaseContext(), "Preemcha todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctTelAluno.getText().length() < 13) {
                        Toast.makeText(getBaseContext(), "Telefone Incompleto!", Toast.LENGTH_SHORT).show();

                    } else if (ctCPFResp.getText().length() < 14) {
                        Toast.makeText(getBaseContext(), "CPF Incompleto!", Toast.LENGTH_SHORT).show();

                    } else if (!validaCpf(ctCPFResp.getText().toString())) {
                        Toast.makeText(getBaseContext(), "CPF Invalido!", Toast.LENGTH_SHORT).show();

                    } else {
                        editar(setDados());
                        chamaTelaListaEscola();
                        finish();
                    }
                }
            });

        } else {
            btSalvarAluno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ctNomeAluno.getText().equals("") || ctMatricAluno.getText().equals("") || ctTelAluno.getText().equals("")
                            || ctNomeResponsavel.getText().equals("") || ctCPFResp.getText().equals("")) {
                        Toast.makeText(getBaseContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctTelAluno.getText().length() < 13) {
                        Toast.makeText(getBaseContext(), "Telefone Incompleto!", Toast.LENGTH_SHORT).show();

                    } else if (ctCPFResp.getText().length() < 14) {
                        Toast.makeText(getBaseContext(), "CPF Incompleto!", Toast.LENGTH_SHORT).show();

                    } else if (!validaCpf(ctCPFResp.getText().toString())) {
                        Toast.makeText(getBaseContext(), "CPF Invalido!", Toast.LENGTH_SHORT).show();

                    } else {
                        cadastrar();
                        chamaTelaListaEscola();
                        finish();
                    }
                }
            });
        }
    }

    public void cadastrar() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(aluno.getEmailResponsavel(), aluno.getSenha())
                .addOnCompleteListener(ViewCadastrarAluno.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    salvar(setDados());
                                    Preferencias preferencias = new Preferencias(ViewCadastrarAluno.this);
                                    preferencias.salvaUsuarioLogado(aluno.getMatricola(), aluno.getNome());

                                } else {
                                    String erroExcecao = "";
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        erroExcecao = "E-mail invalido!";
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        erroExcecao = "E-mail ja esta cadastrado em outro usuario!";
                                    } catch (Exception e) {
                                        erroExcecao = "Erro no cadastro!";
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getBaseContext(), "ERRO! " + erroExcecao, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
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

    private void chamaTelaListaEscola() {
        Intent i = new Intent(getBaseContext(), ViewListarAlunos.class);
        startActivity(i);
    }

    private void salvar(Aluno a) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("aluno");
        data.child(a.getMatricola()).setValue(a);

    }

    private Aluno setDados() {
        aluno = new Aluno();
        aluno.setNome(ctNomeAluno.getText().toString());
        //aluno.setEmailResponsavel(ct.getText().toString());
        String idUsuario = Base64Custon.codificadorBase64(aluno.getCpfResponsavel());
        aluno.setMatricola(idUsuario);
        aluno.setStatus("Ativo");
        aluno.setSenha(geraSenha());
        aluno.setCpfResponsavel(ctCPFResp.getText().toString());
        aluno.setTelefone(ctNomeResponsavel.getText().toString());
        aluno.setKeyTurma("sem Turma");
        return aluno;
    }

    public String geraSenha() {
        String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String senha = "";
        for (int x = 0; x < 9; x++) {
            int j = (int) (Math.random() * carct.length);
            senha += carct[j];
        }

        return senha;
    }

    private void editar(Aluno a) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("aluno");
        data.child(a.getMatricola()).updateChildren(aluno.toMap());
    }

    private void preencheCampos() {
        ctNomeAluno.setText(intent.getStringExtra("nome"));
        //ctEmailProf.setText(intent.getStringExtra("email"));
        ctCPFResp.setText(intent.getStringExtra("cpf"));
        ctNomeResponsavel.setText(intent.getStringExtra("nomeRes"));
        ctMatricAluno.setText(intent.getStringExtra("rg"));
        ctTelAluno.setText(intent.getStringExtra("tel"));
    }
}
