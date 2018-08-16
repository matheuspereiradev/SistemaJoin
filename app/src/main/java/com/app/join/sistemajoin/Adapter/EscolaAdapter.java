package com.app.join.sistemajoin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.R;

import java.util.ArrayList;

public class EscolaAdapter extends ArrayAdapter<Escola> {

    ArrayList<Escola> escolas;
    Context context;

    public EscolaAdapter(Context c, ArrayList<Escola> objects) {
        super(c, 0, objects);
        this.context = c;
        this.escolas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (escolas != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_tabela_exibir_escolas, parent, false);

            TextView textViewNome = view.findViewById(R.id.campoNomeEsc);
            TextView textViewTelefone = view.findViewById(R.id.campoTelEsc);
            TextView textViewCidade = view.findViewById(R.id.campoCidadeEsc);

            Escola esc = escolas.get(position);
            textViewNome.setText(esc.getNome());
            textViewTelefone.setText(esc.getTelefone());
            textViewCidade.setText(esc.getCidade());
        }
        return view;
    }
}
