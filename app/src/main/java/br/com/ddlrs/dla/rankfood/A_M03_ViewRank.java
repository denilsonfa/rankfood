package br.com.ddlrs.dla.rankfood;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class A_M03_ViewRank extends AppCompatActivity {

    TextView id_ic_m03_nameRank;
    TextView id_text_m03_item01, id_text_m03_item02,
            id_text_m03_item03, id_text_m03_item04,
             id_text_m03_item05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_m03_viewrank);

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,68,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar


        // botão para retornar
        ImageView id_ic_viewrank_back = findViewById(R.id.id_ic_m03_back);
        id_ic_viewrank_back.setOnClickListener(v -> onBackPressed());

        // pegar id do item
        id_ic_m03_nameRank = findViewById(R.id.id_ic_m03_nameRank);

        id_text_m03_item01 = findViewById(R.id.id_text_m03_item01);
        id_text_m03_item02 = findViewById(R.id.id_text_m03_item02);
        id_text_m03_item03 = findViewById(R.id.id_text_m03_item03);
        id_text_m03_item04 = findViewById(R.id.id_text_m03_item04);
        id_text_m03_item05 = findViewById(R.id.id_text_m03_item05);

        // id_ic_m03_nameRank deve receber o nome do rank

        // Aqui vai o codigo onde entra com os dados referente ao ranking
        // Além de que se houver mais de 5 itens, deve-se adicionar na activity

    }
}