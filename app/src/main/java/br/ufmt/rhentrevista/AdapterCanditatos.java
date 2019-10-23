package br.ufmt.rhentrevista;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterCanditatos extends BaseAdapter {

    private final List<Candidato> candidatos;
    private final Activity activity;

    public AdapterCanditatos(List<Candidato> candidatos, Activity activity) {
        this.candidatos = candidatos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return candidatos.size();
    }

    @Override
    public Object getItem(int position) {
        return candidatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater()
                .inflate(R.layout.lista_candidato, parent, false);
        Candidato candidato = candidatos.get(position);
        TextView cpfTV = (TextView) view.findViewById(R.id.cpfTV);
        TextView nomeTV = (TextView) view.findViewById(R.id.nomeTV);
        TextView assinadoTV = (TextView) view.findViewById(R.id.assinadoTV);

        cpfTV.setText(candidato.getCpf());
        nomeTV.setText(candidato.getNome());
        assinadoTV.setText(String.valueOf(candidato.estaAssinado()));
        if (position % 2 == 0) {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            view.setBackgroundColor(Color.parseColor("#cfd2d8"));
        }
        return view;
    }
}
