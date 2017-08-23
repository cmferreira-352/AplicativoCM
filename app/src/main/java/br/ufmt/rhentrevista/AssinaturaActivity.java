package br.ufmt.rhentrevista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.gcacace.signaturepad.views.SignaturePad;

public class AssinaturaActivity extends AppCompatActivity {
    private SignaturePad  assinaturaPad;
    private Button salvar;
    private Button limpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinatura);
        salvar.setEnabled(false);
        limpar.setEnabled(false);
        assinaturaPad = (SignaturePad) findViewById(R.id.assinaturaPad);
        assinaturaPad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Faz nada
            }

            @Override
            public void onSigned() {
                salvar.setEnabled(true);
                limpar.setEnabled(true);
            }

            @Override
            public void onClear() {
                salvar.setEnabled(false);
                limpar.setEnabled(false);
            }
        });
        salvar = (Button) findViewById(R.id.save_button);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int posicao = intent.getIntExtra("posicao",-1);
                ListaDeCandidatos candidatos = new ListaDeCandidatos();
                Log.d("POSCICAO", "A POSICAO QUE CHEGA É: "+posicao);
                if (posicao != -1) candidatos.getCandidatos().get(posicao).setEstaAssinado(true);
                Intent lista = new Intent(getApplicationContext(), ListaDeCandidatos.class);

                startActivity(lista);
            }
        });
        limpar = (Button) findViewById(R.id.clear_button);
        limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assinaturaPad.clear();
            }
        });
    }
}
