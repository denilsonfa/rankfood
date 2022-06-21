package br.com.ddlrs.dla.rankfood.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.controller.Data;
import br.com.ddlrs.dla.rankfood.controller.ListaRankingAdapter;
import br.com.ddlrs.dla.rankfood.controller.Ranking;
import br.com.ddlrs.dla.rankfood.model.Constants;

public class A_M05_ListaRanks extends AppCompatActivity implements Constants {

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
        setContentView(R.layout.activity_a_m05_listaranks);
        dataInstance = getIntent().getExtras().getParcelable("Data");
        operation = Integer.parseInt(getIntent().getStringExtra("Operation"));
        title = findViewById(R.id.id_title_m05);

        if(operation == VOTE_ACTIVITY_REQUEST_CODE){ title.setText(R.string.voteRank); }
        else if(operation == VIEW_RANK_ACTIVITY_REQUEST_CODE){ title.setText(R.string.viewRank); }
        else if(operation == GUEST_MODE_ACTIVITY_REQUEST_CODE){ title.setText(R.string.anonymous); }

        adapter = new ListaRankingAdapter(new ArrayList<Ranking>(dataInstance.getDataRanking()), operation, dataInstance.log);

        RecyclerView rv = findViewById(R.id.view_list_of_rank);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        adapter.setListener(new ListaRankingAdapter.itemActivityListener() {
            @Override
            public void onItemClick(int listPosition, int position) {

                if (operation == GUEST_MODE_ACTIVITY_REQUEST_CODE){
                    AlertDialog.Builder builder = new AlertDialog.Builder(A_M05_ListaRanks.this);
                    AlertDialog dialog = builder.create();
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.item_pop_menu, null);
                    TextView item_menu01 = dialogLayout.findViewById(R.id.item_menu01); //Perfil
                    TextView item_menu02 = dialogLayout.findViewById(R.id.item_menu02); //Sair

                    item_menu01.setText(R.string.voteRank);
                    item_menu02.setText(R.string.viewRank);

                    //Função do botão ver perfil
                    item_menu01.setOnClickListener(x -> {
                        Intent i = new Intent(A_M05_ListaRanks.this, A_M02_Vote.class);
                        i.putExtra("RankPosition", Integer.toString(listPosition));
                        i.putExtra("RankListPosition", Integer.toString(position));
                        i.putExtra("Data", dataInstance);
                        startActivityForResult(i, VOTE_ACTIVITY_REQUEST_CODE);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    });

                    //Função do botão sair
                    item_menu02.setOnClickListener(y -> {
                        Intent i = new Intent(A_M05_ListaRanks.this, A_M03_ViewRank.class);
                        i.putExtra("RankPosition", Integer.toString(listPosition));
                        i.putExtra("RankListPosition", Integer.toString(position));
                        i.putExtra("Data", dataInstance);
                        startActivityForResult(i, VIEW_RANK_ACTIVITY_REQUEST_CODE);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    });

                    dialog.setView(dialogLayout);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                }
                else {
                    boolean eventOperation = operation == VOTE_ACTIVITY_REQUEST_CODE;

                    Intent i = new Intent(A_M05_ListaRanks.this, eventOperation ? A_M02_Vote.class : A_M03_ViewRank.class);
                    i.putExtra("RankPosition", Integer.toString(listPosition));
                    i.putExtra("RankListPosition", Integer.toString(position));
                    i.putExtra("Data", dataInstance);
                    startActivityForResult(i, eventOperation ? VOTE_ACTIVITY_REQUEST_CODE : VIEW_RANK_ACTIVITY_REQUEST_CODE);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        });

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,100,0)); // Cor da barra de status
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
                Integer rankListPosition = Integer.parseInt(data.getStringExtra("RankListPosition"));

                if (operation == GUEST_MODE_ACTIVITY_REQUEST_CODE){
                    adapter.vote(rankListPosition,rankVotePosition);
                    adapter.notifyItemChanged(rankListPosition);
                } else {
                    adapter.getRankingItems().remove(rankListPosition);
                    adapter.remove();
                    adapter.notifyItemRemoved(rankListPosition);
                }

                Log.d("OperSerialize" , dataInstance.serialize());
                Log.d("RankPosition" , Integer.toString(rankPosition));
                Log.d("RankVotePosition" , Integer.toString(rankVotePosition));
            }
        }
    }
}