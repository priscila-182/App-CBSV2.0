package com.example.app_cbsv20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class RegistroActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        init();

        showPDialog1();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        },1000);


        ImageView imageAtrasUno = (ImageView) findViewById(R.id.imageAtrasUno);
        imageAtrasUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
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