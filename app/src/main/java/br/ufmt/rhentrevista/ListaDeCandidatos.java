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
    private AdapterCanditatos adapter;
    private ListView listaDeCandidatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_candidatos);
        preencheCursos();


        listaDeCandidatos = (ListView) findViewById(R.id.lista);

        adapter = new AdapterCanditatos(candidatos, this);

        listaDeCandidatos.setAdapter(adapter);

        listaDeCandidatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent assinatura = new Intent(getApplicationContext(), AssinaturaActivity.class);
            Intent falta = new Intent(getApplicationContext(), ListaDeFaltas.class);

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Candidato candidato = (Candidato) listaDeCandidatos.getItemAtPosition(position);
                if (candidato.estaAssinado() == StatusEnum.Assinado) {
                    falta.putExtra("posicao", position);
                    startActivityForResult(falta,201);
                } else if (candidato.estaAssinado() == StatusEnum.Registrado){
                    assinatura.putExtra("posicao", position);
                    startActivityForResult(assinatura, 101);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 201 && (data.getIntExtra("posicao", -1) != -1)){
            candidatos.get(data.getIntExtra("posicao", -1)).setEstaAssinado(StatusEnum.Entrevistado);
            candidatos.get(data.getIntExtra("posicao", -1)).setPontosPerdidos(data.getIntExtra("faltas", -1));
            adapter.notifyDataSetChanged();
        } else if (resultCode == 101 && (data.getIntExtra("posicao", -1) != -1)){
            candidatos.get(data.getIntExtra("posicao", -1)).setEstaAssinado(StatusEnum.Assinado);
            adapter.notifyDataSetChanged();
        }
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
