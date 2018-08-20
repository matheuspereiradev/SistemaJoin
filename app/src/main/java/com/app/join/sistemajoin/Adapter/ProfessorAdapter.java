package com.app.join.sistemajoin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.Model.Professor;
import com.app.join.sistemajoin.R;

import java.util.ArrayList;

public class ProfessorAdapter extends ArrayAdapter<Professor> {

    ArrayList<Professor> professor;
    Context context;

    public ProfessorAdapter(Context c, ArrayList<Professor> objects) {
        super(c, 0, objects);
        this.context = c;
        this.professor = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (professor != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_tabela_exibir_professores, parent, false);

            TextView textViewNome = view.findViewById(R.id.campoNomeProf);
            TextView textViewTelefone = view.findViewById(R.id.campoTelProf);
            TextView textViewStatus = view.findViewById(R.id.campoStatusProf);

            Professor pro = professor.get(position);
            textViewNome.setText(pro.getNome());
            textViewTelefone.setText(pro.getTelefone());
            textViewStatus.setText(pro.getStatus());
        }
        return view;
    }
}
