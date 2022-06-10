package br.com.ddlrs.dla.rankfood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import controller.Data;
import controller.ListaRankingAdapter;
import controller.Ranking;
import model.Constants;

public class A_M05_ListaRanksTemporaria extends AppCompatActivity implements Constants {

    Data dataInstance;
    Integer operation;
    ListaRankingAdapter adapter;
    TextView title;

    private void exite(){
        Intent intent = new Intent();
        intent.putExtra("Data", dataInstance);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onBackPressed() { exite(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_m05_listarankstemporaria);
        dataInstance = getIntent().getExtras().getParcelable("Data");
        operation = Integer.parseInt(getIntent().getStringExtra("Operation"));
        title = findViewById(R.id.id_title_m05);

        if(operation == VOTE_ACTIVITY_REQUEST_CODE){ title.setText(R.string.voteRank); }
        else if(operation == VIEW_RANK_ACTIVITY_REQUEST_CODE){ title.setText(R.string.viewRank); }
        else if(operation == GUEST_MODE_ACTIVITY_REQUEST_CODE){ title.setText(R.string.anonymous); }

        adapter = new ListaRankingAdapter(new ArrayList<Ranking>(dataInstance.getDataRanking()));

        RecyclerView rv = findViewById(R.id.view_list_of_rank);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        adapter.setListener(new ListaRankingAdapter.itemActivityListener() {
            @Override
            public void onItemClick(int position) {
                boolean eventOperation = operation == VOTE_ACTIVITY_REQUEST_CODE;
                
                Intent i = new Intent(A_M05_ListaRanksTemporaria.this, eventOperation?A_M02_Vote.class:A_M03_ViewRank.class);
                i.putExtra("RankPosition", Integer.toString(position));
                i.putExtra("Data", dataInstance);
                startActivityForResult(i, eventOperation?VOTE_ACTIVITY_REQUEST_CODE:VIEW_RANK_ACTIVITY_REQUEST_CODE);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,68,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar

        // botão para retornar
        ImageView id_ic_viewrank_back = findViewById(R.id.id_ic_m05_back);
        id_ic_viewrank_back.setOnClickListener(v -> exite() );
    }
    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == VOTE_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                dataInstance.Update(data.getExtras().getParcelable("Data"));
                Integer rankPosition = Integer.parseInt(data.getStringExtra("RankPosition"));
                Integer rankVotePosition = Integer.parseInt(data.getStringExtra("RankVotePosition"));
                adapter.vote(rankPosition,rankVotePosition);
                adapter.notifyItemChanged(rankPosition);

                Log.d("OperLog" , "A_M05_ListaRanksTemporaria");
                Log.d("OperSerialize" , dataInstance.serialize());
                Log.d("RankPosition" , Integer.toString(rankPosition));
                Log.d("RankVotePosition" , Integer.toString(rankVotePosition));
            }
        }
    }
}