package br.ufmt.rhentrevista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity{


    private AutoCompleteTextView loginACTV;
    private EditText senhaET;
    private Button entrarBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login();


    }

    public void login(){
        entrarBTN = (Button) findViewById(R.id.entrarBTN);
        entrarBTN.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginACTV = (AutoCompleteTextView) findViewById(R.id.loginACTV);
                senhaET = (EditText) findViewById(R.id.senhaET);
                String login = loginACTV.getText().toString();
                String senha = senhaET.getText().toString();
                if(login.equalsIgnoreCase("")){
                    loginACTV.setError("Insira um usuário");
                }else if(senha.equalsIgnoreCase("")){
                    senhaET.setError("Insira uma senha");
                } else {
                    Intent intent = new Intent(getApplicationContext(), ListaDeCandidatos.class);
                    startActivity(intent);
                }
            }
        });


    }


}

