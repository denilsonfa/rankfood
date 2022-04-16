package br.com.ddlrs.dla.rankfood;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class A_M02_Vote extends AppCompatActivity {

    TextView id_ic_voterank_nameVote;
    LinearLayout id_linear_m02_item01, id_linear_m02_item02,
                 id_linear_m02_item03, id_linear_m02_item04,
                 id_linear_m02_item05;
    Button id_btn_m02_savelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_m02_vote);

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,68,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar


        // botão para retornar
        ImageView id_ic_voterank_back = findViewById(R.id.id_ic_m02_back);
        id_ic_voterank_back.setOnClickListener(v -> onBackPressed());


        // pegar id do item
        id_ic_voterank_nameVote = findViewById(R.id.id_ic_m02_nameVote);

        id_linear_m02_item01 = findViewById(R.id.id_linear_m02_item01);
        id_linear_m02_item02 = findViewById(R.id.id_linear_m02_item02);
        id_linear_m02_item03 = findViewById(R.id.id_linear_m02_item03);
        id_linear_m02_item04 = findViewById(R.id.id_linear_m02_item04);
        id_linear_m02_item05 = findViewById(R.id.id_linear_m02_item05);

        id_btn_m02_savelist = findViewById(R.id.id_btn_m02_savelist);

        //id_ic_voterank_nameVote deve receber o nome do ranking

        // Deverá haver uma função que adicionar mais itens a lista caso exista mais de 5, a não ser
        // que fique definido que só existe 5 itens para votação.
        // mas fica ao critério de vocês, não serei eu que irá fazer mesmo ;)

        // Deverá também haver a função para selecionar apenas um item e anular os outros.
        // As imagens são R.drawable.id_vote_aprove, R.drawable.id_vote_reprove e R.drawable.id_vote_null


        id_btn_m02_savelist.setOnClickListener(v -> {
            Toast.makeText(this, R.string.voteSuccess, Toast.LENGTH_SHORT).show();
        });

    }
}