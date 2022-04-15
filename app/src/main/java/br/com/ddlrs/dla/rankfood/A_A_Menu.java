package br.com.ddlrs.dla.rankfood;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Objects;

public class A_A_Menu extends AppCompatActivity {

    LinearLayout id_btn_menu_m01CreateRank;
    LinearLayout id_btn_menu_m02Vote;
    LinearLayout id_btn_menu_m03ViewRank;
    LinearLayout id_btn_menu_m04Sobre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_menu);

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,68,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar

        // pegar id do item
        id_btn_menu_m01CreateRank = findViewById(R.id.id_btn_menu_m01CreateRank);
        id_btn_menu_m02Vote = findViewById(R.id.id_btn_menu_m02Vote);
        id_btn_menu_m03ViewRank = findViewById(R.id.id_btn_menu_m03ViewRank);
        id_btn_menu_m04Sobre = findViewById(R.id.id_btn_menu_m04Sobre);


    }
}