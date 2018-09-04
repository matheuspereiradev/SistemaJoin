package com.app.join.sistemajoin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.join.sistemajoin.Model.Agenda;
import com.app.join.sistemajoin.Model.Professor;
import com.app.join.sistemajoin.R;
import java.util.ArrayList;

public class PostagemAdapter extends ArrayAdapter<Agenda> {

    ArrayList<Agenda> agenda;
    Context context;

    public PostagemAdapter(Context c, ArrayList<Agenda> objects) {
        super(c, 0, objects);
        this.context = c;
        this.agenda = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (agenda != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_tabela_exibir_postagens, parent, false);

            TextView textViewNome = view.findViewById(R.id.cmpTituloPost);
            TextView textViewData = view.findViewById(R.id.cmpDataPost);


            Agenda age = agenda.get(position);
            textViewNome.setText(age.getTitulo());
            textViewData.setText(age.getData());
        }
        return view;
    }
}
