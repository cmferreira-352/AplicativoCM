package br.ufmt.rhentrevista;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

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

