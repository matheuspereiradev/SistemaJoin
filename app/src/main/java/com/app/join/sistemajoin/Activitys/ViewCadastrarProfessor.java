package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.InputMismatchException;

public class ViewCadastrarProfessor extends AppCompatActivity {

    EditText ctEmailProf, ctNomeProf, ctCPFProf, ctTelProf;
    Button btProximoProf1;
    Professor professor;
    String key, idEscola;
    FirebaseAuth autenticacao;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_professor);

        //==========inicio da conexão java cm xml
        ctNomeProf = (EditText) findViewById(R.id.ctNomeProf);
        ctEmailProf = (EditText) findViewById(R.id.ctEmailProf);
        btProximoProf1 = (Button) findViewById(R.id.btSalvarProfessor);
        ctCPFProf = (EditText) findViewById(R.id.ctCPFProf);
        ctTelProf = (EditText) findViewById(R.id.ctTelProf);
        //fim da conexão==========

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
        intent = getIntent();
        key = intent.getStringExtra("key");
        idEscola = intent.getStringExtra("id");
        if (key != null) {
            preencheCampos();
            btProximoProf1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ctNomeProf.getText().equals("") || ctCPFProf.getText().equals("")
                            || ctEmailProf.getText().equals("") || ctTelProf.getText().equals("")) {
                        if(ctNomeProf.getText().equals("")){
                            ctNomeProf.isSelected();
                        }else if(ctCPFProf.getText().equals("")){
                            ctCPFProf.isSelected();
                        }else if(ctTelProf.getText().equals("")){
                            ctTelProf.isSelected();
                        }else if(ctEmailProf.getText().equals("")){
                            ctEmailProf.isSelected();
                        }
                        Toast.makeText(getBaseContext(), "Preemcha todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctTelProf.getText().length() < 13) {
                        ctTelProf.isSelected();
                        Toast.makeText(getBaseContext(), "Telefone Incompleto!", Toast.LENGTH_SHORT).show();

                    } else if (ctCPFProf.getText().length() < 14) {
                        ctCPFProf.isSelected();
                        Toast.makeText(getBaseContext(), "CPF Incompleto!", Toast.LENGTH_SHORT).show();

                    } else if (!validaCpf(ctCPFProf.getText().toString())) {
                        ctCPFProf.isSelected();
                        Toast.makeText(getBaseContext(), "CPF Invalido!", Toast.LENGTH_SHORT).show();

                    } else {
                        editarProfessor(setDadosEditar());
                        chamaTelaListaProfessor();
                        finish();
                    }
                }
            });

        } else {
            btProximoProf1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ctNomeProf.getText().equals("") || ctCPFProf.getText().equals("")
                            || ctEmailProf.getText().equals("") || ctTelProf.getText().equals("")) {
                        Toast.makeText(getBaseContext(), "Favor, preencher todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (ctTelProf.getText().length() < 13) {
                        Toast.makeText(getBaseContext(), "Telefone Incompleto!", Toast.LENGTH_SHORT).show();

                    } else if (ctCPFProf.getText().length() < 14) {
                        Toast.makeText(getBaseContext(), " Favor, preencher os campos corretamente!", Toast.LENGTH_SHORT).show();

                    } else if (!validaCpf(ctCPFProf.getText().toString())) {
                        Toast.makeText(getBaseContext(), "Os dados inseridos são inválidos!", Toast.LENGTH_SHORT).show();

                    } else {
                        professor = setDados();
                        cadastrar();
                        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                        autenticacao.signInWithEmailAndPassword(professor.getEmail(), professor.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    salvarProfessor(professor);
                                }
                            }
                        });
                        chamaTelaListaProfessor();
                        finish();
                    }
                }
            });
        }
    }

    public void cadastrar() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(professor.getEmail(), professor.getSenha())
                .addOnCompleteListener(ViewCadastrarProfessor.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Preferencias preferencias = new Preferencias(ViewCadastrarProfessor.this);
                                    preferencias.salvaUsuarioLogado(professor.getIdProfessor(), professor.getNome());
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

    private void chamaTelaListaProfessor() {
        Intent listProf = new Intent(ViewCadastrarProfessor.this, ViewListaProfessores.class);
        listProf.putExtra("id", intent.getStringExtra("id"));
        startActivity(listProf);
    }

    private void salvarProfessor(Professor p) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("professor");
        data.child(p.getIdProfessor()).setValue(p);
    }

    private Professor setDados() {
        professor = new Professor();
        professor.setNome(ctNomeProf.getText().toString());
        professor.setEmail(ctEmailProf.getText().toString());
        String idUsuario = Base64Custon.codificadorBase64(professor.getEmail());
        professor.setIdProfessor(idUsuario);
        professor.setSenha(geraSenha());
        professor.setStatus("Ativo");
        professor.setCpf(ctCPFProf.getText().toString());
        professor.setTelefone(ctTelProf.getText().toString());
        professor.setKeyTurma("sem Turma");
        professor.setIdEscola(idEscola);
        return professor;
    }

    private Professor setDadosEditar() {
        professor = new Professor();
        professor.setNome(ctNomeProf.getText().toString());
        professor.setEmail(ctEmailProf.getText().toString());
        professor.setIdProfessor(intent.getStringExtra("key"));
        professor.setSenha(intent.getStringExtra("senha"));
        professor.setStatus("Ativo");
        professor.setCpf(ctCPFProf.getText().toString());
        professor.setTelefone(ctTelProf.getText().toString());
        professor.setKeyTurma(intent.getStringExtra("keyTurma"));
        professor.setIdEscola("idEscola");
        return professor;
    }

    public String geraSenha() {
        String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String senha = "";
        for (int x = 0; x < 9; x++) {
            int j = (int) (Math.random() * carct.length);
            senha += carct[j];
        }

        return senha;
    }

    private void editarProfessor(Professor p) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("professor");
        data.child(p.getIdProfessor()).updateChildren(professor.toMap());
    }

    private void preencheCampos() {
        ctNomeProf.setText(intent.getStringExtra("nome"));
        ctEmailProf.setText(intent.getStringExtra("email"));
        ctCPFProf.setText(intent.getStringExtra("cpf"));
        ctTelProf.setText(intent.getStringExtra("tel"));
    }

}
