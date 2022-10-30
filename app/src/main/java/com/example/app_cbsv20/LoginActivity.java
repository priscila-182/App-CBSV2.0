package com.example.app_cbsv20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        init();

        showPDialog1();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        },1000);


        ImageView imageAtrasUno = (ImageView) findViewById(R.id.botonAtrasUno);
        imageAtrasUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
            }
        });

        TextView registrarse = (TextView) findViewById(R.id.registrarse);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });
    }

    private void init() {
        this.progressDialog = new ProgressDialog(this);
    }

    private void showPDialog1(){
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Cargando");
        progressDialog.setMessage("Por favor, espere");
        progressDialog.show();
    }
}