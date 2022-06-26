package br.com.ddlrs.dla.rankfood.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.controller.Data;
import br.com.ddlrs.dla.rankfood.model.Adapter.VoteRankingAdapter;

public class A_M02_Vote extends AppCompatActivity {

    TextView            id_ic_voterank_nameVote;
    LinearLayout        id_linear_m02_item01;
    Button              id_btn_m02_savelist;
    Data                dataInstance;
    Integer             rankPosition;
    Integer             rankListPosition;
    VoteRankingAdapter  adapter;

    private void alert(String massege){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss() );
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.item_pop_alert, null);
        TextView titleText = dialogLayout.findViewById(R.id.TitleText);
        titleText.setText(massege);
        android.app.AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_m02_vote);
        dataInstance = getIntent().getExtras().getParcelable("Data");
        rankPosition = Integer.parseInt(getIntent().getStringExtra("RankPosition"));
        rankListPosition = Integer.parseInt(getIntent().getStringExtra("RankListPosition"));


        Log.d("rankPosition" , Integer.toString(rankPosition));
        Log.d("rankListPosition" , Integer.toString(rankListPosition));

        adapter = new VoteRankingAdapter(new ArrayList<>(dataInstance.getDataRanking(rankPosition).getItemsOfRanking()[1] ));

        RecyclerView rv = findViewById(R.id.voterank_lista);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        adapter.setListener(new VoteRankingAdapter.itemActivityListener() {
            @Override
            public void onItemClick(int position) {
                int previousPosition = adapter.setRankingItemsActivity(position);
                adapter.notifyItemChanged(position);
                adapter.notifyItemChanged(previousPosition);
            }
        });

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,100,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar


        // botão para retornar
        ImageView id_ic_voterank_back = findViewById(R.id.id_ic_m02_back);
        id_ic_voterank_back.setOnClickListener(v -> onBackPressed());


        // pegar id do item


        id_linear_m02_item01 = findViewById(R.id.id_linear_m02_item01);

        id_btn_m02_savelist = findViewById(R.id.id_btn_m02_savelist);

        id_btn_m02_savelist.setOnClickListener(v -> {
            int size = adapter.RankingItemsActivity().size();
            int position = adapter.getItemActivity();

            if (position < size){
                if(!dataInstance.guestEnable){
                    dataInstance.getDataRanking(rankPosition).vote(position, dataInstance.log);
                    Log.d("OperSerialize" , "oi meu chapa");
                } else {
                    dataInstance.getDataRanking(rankPosition).vote(position);
                    Log.d("OperSerialize" , "lexa go");
                }

                Log.d("OperSerialize" , dataInstance.serialize());

                Intent intent = new Intent();
                intent.putExtra("Data", dataInstance);
                intent.putExtra("RankPosition", Integer.toString(rankPosition));
                intent.putExtra("RankListPosition", Integer.toString(rankListPosition));
                intent.putExtra("RankVotePosition", Integer.toString(position));
                setResult(RESULT_OK, intent);
                finish();
            } else {
                // nenhum item selecionado
                alert(getString(R.string.selectAnItem));
            }

        });

    }


}