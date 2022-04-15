package br.com.ddlrs.dla.rankfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Objects;

public class A_A_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_login);

        getWindow().setStatusBarColor(Color.rgb(255,68,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar

        ImageView id_ic_a_back_open = findViewById(R.id.id_ic_a_back_open); // botão de abrir a tela de login do usuário (A_A_Login)
        id_ic_a_back_open.setOnClickListener(v -> onBackPressed());

    }
}