package br.com.ddlrs.dla.rankfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class A_A_Open extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_open);

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,172,13)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar


        // botão de abrir a tela de login do usuário (A_A_Login)
        Button id_btn_open_enter = findViewById(R.id.id_btn_open_enter);
        id_btn_open_enter.setOnClickListener(v -> { // Botão para abrir a tela de login
            Intent i = new Intent(A_A_Open.this, A_A_Login.class);
            startActivity(i); overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });


        // Texto para abrir a tela de registro do usuário (A_A_Register)
        TextView id_text_open_resgister = findViewById(R.id.id_text_open_resgister);
        id_text_open_resgister.setOnClickListener(v -> { // Botão para abrir a tela de login
            Intent i = new Intent(A_A_Open.this, A_A_Register.class);
            startActivity(i); overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });


        // botão de abrir a tela de menu do usuário (A_A_Menu) (temporário)
        Button id_btn_open_home = findViewById(R.id.id_btn_open_home);
        id_btn_open_home.setOnClickListener(v -> { // Botão para abrir a tela de login
            Intent i = new Intent(A_A_Open.this, A_A_Menu.class);
            startActivity(i); overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });
    }

}