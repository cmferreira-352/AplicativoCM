package br.ufmt.rhentrevista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListaDeFaltas extends AppCompatActivity {

    private List<Falta> faltas;
    private AdapterFaltas adapter;
    private ListView listaDeFaltas;
    private int contadorFaltas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_faltas);
        preencheFaltas();
        listaDeFaltas = (ListView) findViewById(R.id.faltaLV);
        adapter = new AdapterFaltas(faltas, this);
        listaDeFaltas.setAdapter(adapter);
        contadorFaltas = 0;
        listaDeFaltas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Falta falta = faltas.get(position);
                CheckBox faltaCB = (CheckBox) view.findViewById(R.id.faltaCB);
                if (faltaCB.isChecked()){
                    faltaCB.setChecked(false);
                    contadorFaltas -= falta.getPontos();
                } else {
                    faltaCB.setChecked(true);
                    contadorFaltas += falta.getPontos();
                    if (contadorFaltas > 3){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (!isFinishing()){
                                    new AlertDialog.Builder(ListaDeFaltas.this)
                                            .setTitle("Candidato Eliminado")
                                            .setMessage("O candidato excedeu o limite de faltas")
                                            .setCancelable(false)
                                            .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = getIntent();
                                                    int posicao = intent.getIntExtra("posicao",-1);
                                                    Intent resultadoIntent = new Intent();
                                                    resultadoIntent.putExtra("posicao", posicao);
                                                    resultadoIntent.putExtra("faltas", contadorFaltas);
                                                    setResult(201,resultadoIntent);
                                                    finish();
                                                }
                                            }).show();
                                }
                            }
                        });
                    }
                }
            }
        });
        Button finalizarBTN = (Button) findViewById(R.id.finalizarBTN);
        finalizarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int posicao = intent.getIntExtra("posicao",-1);
                Intent resultadoIntent = new Intent();
                resultadoIntent.putExtra("posicao", posicao);
                resultadoIntent.putExtra("faltas", contadorFaltas);
                setResult(201,resultadoIntent);
                finish();
            }
        });
    }

    public void preencheFaltas() {
        List<Falta> lista = new ArrayList<>();

        // Faltas Leves
        lista.add(new Falta("Cruzou os braços", NivelFalta.LEVE, 1));
        lista.add(new Falta("Bocejou", NivelFalta.LEVE, 1));
        lista.add(new Falta("Não se vestiu adequadamente", NivelFalta.LEVE, 1));
        lista.add(new Falta("Demonstrou-se desconfortável durante a entrevista", NivelFalta.LEVE, 1));
        lista.add(new Falta("Foi pro lixo durante as respostas", NivelFalta.LEVE, 1));
        lista.add(new Falta("Foi monossilábico durante as respostas", NivelFalta.LEVE, 1));
        lista.add(new Falta("Falou mal do emprego anterior", NivelFalta.LEVE, 1));
        lista.add(new Falta("Expôs sua vida pessoal em excesso", NivelFalta.LEVE, 1));
        lista.add(new Falta("Apresentou dificuldade em articular suas respostas", NivelFalta.LEVE, 1));
        lista.add(new Falta("Perguntou sobre salário no início da entrevista.", NivelFalta.LEVE, 1));

        // Faltas Médias
        lista.add(new Falta("Chegou até 15 minutos atrasado", NivelFalta.MEDIA, 2));
        lista.add(new Falta("Não fez pergunta sobre a vaga ao entrevistador", NivelFalta.MEDIA, 2));
        lista.add(new Falta("Não trouxe currículo impresso", NivelFalta.MEDIA, 2));
        lista.add(new Falta("Não tem conhecimento da língua espanhola", NivelFalta.MEDIA, 2));
        lista.add(new Falta("Estava desinformado sobre a vaga", NivelFalta.MEDIA, 2));
        lista.add(new Falta("Estava desinformado sobre a empresa", NivelFalta.MEDIA, 2));

        // Faltas Graves
        lista.add(new Falta("Chegou até 30 minutos atrasado", NivelFalta.GRAVE, 3));
        lista.add(new Falta("Cometeu erro de concordância verbal na entrevista", NivelFalta.GRAVE, 3));
        lista.add(new Falta("Não tem disponibilidade para viajar", NivelFalta.GRAVE, 3));
        lista.add(new Falta("Respondeu erroneamente a uma pergunta técnica.", NivelFalta.GRAVE, 3));

        // Faltas Eliminatórias
        lista.add(new Falta("Chegou mais do que 30 minutos atrasado", NivelFalta.ELIMINATORIA, 4));
        lista.add(new Falta("Usou termo de baixo calão", NivelFalta.ELIMINATORIA, 4));
        lista.add(new Falta("Não tem conhecimento da língua inglesa", NivelFalta.ELIMINATORIA, 4));
        lista.add(new Falta("Sair da entrevista antes do seu término", NivelFalta.ELIMINATORIA, 4));

        Collections.sort(lista, new Comparator<Falta>() {
            @Override
            public int compare(Falta o1, Falta o2) {
                Integer x1 = o1.getPontos();
                Integer x2 = o2.getPontos();
                int iComp = x1.compareTo(x2);
                if (iComp != 0) {
                    return iComp;
                } else {
                    String y1 = o1.getTexto();
                    String y2 = o2.getTexto();
                    return y1.compareTo(y2);
                }
            }
        });
        faltas = lista;

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent resultadoIntent = new Intent();
        resultadoIntent.putExtra("posicao", -1);
        resultadoIntent.putExtra("faltas", contadorFaltas);
        setResult(201,resultadoIntent);
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        contadorFaltas = 0;
    }
}

