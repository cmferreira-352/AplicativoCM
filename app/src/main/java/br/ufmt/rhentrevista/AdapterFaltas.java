package br.ufmt.rhentrevista;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.List;

public class AdapterFaltas extends BaseAdapter {

    private final List<Falta> faltas;
    private final Activity activity ;

    public AdapterFaltas(List<Falta> faltas, Activity activity) {
        this.faltas = faltas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return faltas.size();
    }

    @Override
    public Object getItem(int position) {
        return faltas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater()
                .inflate(R.layout.lista_falta, parent, false);
        Falta falta = faltas.get(position);
        CheckBox faltaCB = (CheckBox) view.findViewById(R.id.faltaCB);
        faltaCB.setText(falta.getTexto() + " (" + falta.getPontos() + ")");
        faltaCB.setTextColor(Color.parseColor("#000e55"));
        if(position % 2 == 0){
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            view.setBackgroundColor(Color.parseColor("#cfd2d8"));
        }
        return view;
    }
}
