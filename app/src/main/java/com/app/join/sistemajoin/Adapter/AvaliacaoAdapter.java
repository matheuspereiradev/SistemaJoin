package com.app.join.sistemajoin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.Model.Avaliacao;
import com.app.join.sistemajoin.R;

import java.util.ArrayList;

public class AvaliacaoAdapter extends ArrayAdapter<Avaliacao> {

    ArrayList<Avaliacao> avaliacaos;
    Context context;

    public AvaliacaoAdapter(Context c, ArrayList<Avaliacao> objects) {
        super(c, 0, objects);
        this.context = c;
        this.avaliacaos = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (avaliacaos != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_exibir_avaliacoes, parent, false);

            TextView mes = view.findViewById(R.id.tvMes);
            TextView media = view.findViewById(R.id.tvMedia);

            Avaliacao av = avaliacaos.get(position);
            mes.setText(av.getDataAv());
            media.setText(String.valueOf(av.getAv()));

        }
        return view;
    }

}

