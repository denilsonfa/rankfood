package br.com.ddlrs.dla.rankfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Objects;

public class A_A_Open extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_open);

        getWindow().setStatusBarColor(Color.rgb(255,172,13)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar

        Button id_btn_a_open = findViewById(R.id.id_btn_a_open); // botão de abrir a tela de login do usuário (A_A_Login)

        id_btn_a_open.setOnClickListener(v -> { // Botão para abrir a tela de login
            Intent i = new Intent(A_A_Open.this, A_A_Login.class);
            startActivity(i); overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });
    }

}