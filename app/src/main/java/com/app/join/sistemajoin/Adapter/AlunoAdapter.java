package com.app.join.sistemajoin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.R;

import java.util.ArrayList;

public class AlunoAdapter extends ArrayAdapter<Aluno> {

    ArrayList<Aluno> alunos;
    Context context;

    public AlunoAdapter(Context c, ArrayList<Aluno> objects) {
        super(c, 0, objects);
        this.context = c;
        this.alunos = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (alunos != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_tabela_exibir_alunos, parent, false);

            TextView textViewNome = view.findViewById(R.id.campoNomeAluno);
            TextView textViewTelefone = view.findViewById(R.id.campoMatriculaAluno);
            TextView textViewStatus = view.findViewById(R.id.campoStatusAluno);

            Aluno alu = alunos.get(position);
            textViewNome.setText(alu.getNome());
            textViewTelefone.setText(alu.getIdAluno());
            textViewStatus.setText(alu.getStatus());
        }
        return view;
    }

}
