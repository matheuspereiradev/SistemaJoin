package com.app.join.sistemajoin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.app.join.sistemajoin.Model.Turma;
import com.app.join.sistemajoin.R;
import java.util.ArrayList;

public class TurmaAdapter extends ArrayAdapter<Turma> {

    ArrayList<Turma> turmas;
    Context context;

    public TurmaAdapter(Context c, ArrayList<Turma> objects) {
        super(c, 0, objects);
        this.context = c;
        this.turmas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (turmas != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_tabela_exibir_turmas, parent, false);

            TextView textViewNome = view.findViewById(R.id.tvNomeTurma);
            TextView textViewTelefone = view.findViewById(R.id.tvTurnoTurma);

            Turma tur = turmas.get(position);
            textViewNome.setText(tur.getNome());
            textViewTelefone.setText(tur.getNomeProfessor());
        }
        return view;
    }

}

