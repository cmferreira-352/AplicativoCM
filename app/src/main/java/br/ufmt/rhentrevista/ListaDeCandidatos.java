package br.ufmt.rhentrevista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaDeCandidatos extends AppCompatActivity {
    private static List<Candidato> candidatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_candidatos);
        preencheCursos();


        final ListView listaDeCandidatos = (ListView) findViewById(R.id.lista);

        AdapterCanditatos adapter =
                new AdapterCanditatos(candidatos, this);

        listaDeCandidatos.setAdapter(adapter);

        listaDeCandidatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent test = new Intent(getApplicationContext(), AssinaturaActivity.class);

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Candidato candidato = (Candidato) listaDeCandidatos.getItemAtPosition(position);
                Log.d("POSICAO", "A posição é: "+position);
                test.putExtra("posicao", position);
                startActivityForResult(test, 101);
            }
        });
    }

    public void preencheCursos() {
        List<Candidato> lista = new ArrayList<>();
        lista.add(new Candidato("111.111.111-11", "Abadio Amancio de Araújo"));
        lista.add(new Candidato("222.222.222-22", "Acledima Ribeiro Pinto"));
        lista.add(new Candidato("333.333.333-33", "Adair Arantes de Palma"));
        lista.add(new Candidato("444.444.444-44", "Edilene Rodrigues Gomes"));
        candidatos = lista;
    }

    public List<Candidato> getCandidatos() {
        return candidatos;
    }
}
