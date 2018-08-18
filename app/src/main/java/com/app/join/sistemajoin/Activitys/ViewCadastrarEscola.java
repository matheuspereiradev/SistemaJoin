package com.app.join.sistemajoin.Activitys;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.GeraUsuarioSenha;
import com.app.join.sistemajoin.Tools.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class ViewCadastrarEscola extends AppCompatActivity {

    EditText ctNomeEsc, ctTelEsc, ctEmailEsc, ctCNPJEsc;
    Button btSalvarEsc;
    FirebaseAuth autenticacao;
    GeraUsuarioSenha geraUsuarioSenha;
    Escola escola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_escola);

        //======conexão com o xml
        ctTelEsc = (EditText) findViewById(R.id.ctTelEsc);
        ctEmailEsc = (EditText) findViewById(R.id.ctEmailEsc);
        ctNomeEsc = (EditText) findViewById(R.id.ctNomeEsc);
        ctCNPJEsc = (EditText) findViewById(R.id.ctCNPJEsc);
        btSalvarEsc = (Button) findViewById(R.id.btSalvarEsc);
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

        //Botão salvar tela de Endereço
        btSalvarEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                escola = new Escola();
                escola.setNome(ctNomeEsc.getText().toString());
                escola.setTelefone(ctTelEsc.getText().toString());
                escola.setEmail(ctEmailEsc.getText().toString());
                escola.setCnpj(ctCNPJEsc.getText().toString());
                escola.setCidade("não a campo");

                String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
                String senha = "";
                for (int x = 0; x < 9; x++) {
                    int j = (int) (Math.random() * carct.length);
                    senha += carct[j];
                }

                escola.setSenha(senha);

                cadastrar();

                //Intent i = new Intent(getBaseContext(), ViewListarEscolas.class);
                //startActivity(i);
            }
        });

    }

    public void cadastrar() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(escola.getEmail(), escola.getSenha())
                .addOnCompleteListener(ViewCadastrarEscola.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getBaseContext(), "cadastro ok", Toast.LENGTH_SHORT).show();

                                    String idUsuario = Base64Custon.codificadorBase64(escola.getEmail());
                                    FirebaseUser firebaseUser = task.getResult().getUser();
                                    escola.setId(idUsuario);
                                    escola.salvar();

                                    Preferencias preferencias = new Preferencias(ViewCadastrarEscola.this);
                                    preferencias.salvaUsuarioLogado(idUsuario, escola.getNome());

                                } else {
                                    String erroExcecao = "";
                                    try {
                                        throw task.getException();
                                        //} catch (FirebaseAuthInvalidCredentialsException e) {
                                        //    erroExcecao = "E-mail invalido!";
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

}
