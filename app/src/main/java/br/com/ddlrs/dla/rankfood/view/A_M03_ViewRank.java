package br.com.ddlrs.dla.rankfood.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.controller.Data;
import br.com.ddlrs.dla.rankfood.controller.ViewRankingAdapter;
import br.com.ddlrs.dla.rankfood.model.SortList.RankTree;

public class A_M03_ViewRank extends AppCompatActivity {

    Data                dataInstance;
    Integer             rankPosition;
    Integer             rankListPosition;
    ViewRankingAdapter  adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_m03_viewrank);
        dataInstance = getIntent().getExtras().getParcelable("Data");
        rankPosition = Integer.parseInt(getIntent().getStringExtra("RankPosition"));
        rankListPosition = Integer.parseInt(getIntent().getStringExtra("RankListPosition"));


        Log.d("rankPosition" , Integer.toString(rankPosition));
        Log.d("rankListPosition" , Integer.toString(rankListPosition));

        ArrayList[] dataRanking = dataInstance.getDataRanking(rankPosition).getItemsOfRanking();
        RankTree rankTree = new RankTree();

        for (int i = 0; i < dataRanking[0].size(); i++)
            rankTree.add(i, Integer.parseInt((String) dataRanking[0].get(i)));

        ArrayList<Integer> sortDataRanking = new ArrayList<>(rankTree.getSort());

        adapter = new ViewRankingAdapter(new ArrayList<>(sortDataRanking),new ArrayList<>(dataRanking[1]));

        RecyclerView rv = findViewById(R.id.viewrank_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);


        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,100,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar


        // botão para retornar
        ImageView id_ic_viewrank_back = findViewById(R.id.id_ic_m03_back);
        id_ic_viewrank_back.setOnClickListener(v -> onBackPressed());



    }
}