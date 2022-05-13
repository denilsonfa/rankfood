package br.com.ddlrs.dla.rankfood;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import controller.Data;
import controller.ViewRankingAdapter;

public class A_M03_ViewRank extends AppCompatActivity {

    Data dataInstance;
    private ViewRankingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_m03_viewrank);
        dataInstance = getIntent().getExtras().getParcelable("Data");

        ArrayList[] dataRanking = dataInstance.getDataRanking((dataInstance.getDataRanking().size() - 1 )).getItemsOfRanking();

        ArrayList<Integer> relativeDataRanking = new ArrayList<Integer>();
        for(int i = 0; i < dataRanking[0].size(); i++)
            relativeDataRanking.add(Integer.parseInt((String) dataRanking[0].get(i)));

        ArrayList<Integer> rankedDataRanking = new ArrayList<Integer>();
        int highestPosition = 0;

        for(int i = 0; i < dataRanking[0].size(); i++){
            highestPosition = 0;
            for(int e = 0; e < relativeDataRanking.size(); e++)
                if(relativeDataRanking.get(e) > relativeDataRanking.get(highestPosition)) highestPosition = e;

            rankedDataRanking.add(highestPosition);
            relativeDataRanking.set(highestPosition,-1);
        }



        adapter = new ViewRankingAdapter(new ArrayList<>(rankedDataRanking),new ArrayList<>(dataRanking[1]));

        RecyclerView rv = findViewById(R.id.viewrank_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);


        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,68,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar


        // botão para retornar
        ImageView id_ic_viewrank_back = findViewById(R.id.id_ic_m03_back);
        id_ic_viewrank_back.setOnClickListener(v -> onBackPressed());



    }
}