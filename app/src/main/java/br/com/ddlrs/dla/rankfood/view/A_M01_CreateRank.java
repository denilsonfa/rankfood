package br.com.ddlrs.dla.rankfood.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.controller.Data;
import br.com.ddlrs.dla.rankfood.controller.Ranking;
import br.com.ddlrs.dla.rankfood.controller.CreateRankingAdapter;

public class A_M01_CreateRank extends AppCompatActivity {

    EditText id_edtext_m01_edName, id_edtext_m01_namefood05, id_edtext_m01_dateRank;
    TextView id_text_m01_addExtra;
    LinearLayout id_checkbox_public, id_linear_m01_dateRank;
    ImageView option_public, option_date;
    Button id_btn_m01_savelist;
    Data dataInstance;
    int added = 0;
    int position;

    boolean option_date_check = false;

    private CreateRankingAdapter adapter;

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
        setContentView(R.layout.activity_a_m01_createrank);
        dataInstance = getIntent().getExtras().getParcelable("Data");

        adapter = new CreateRankingAdapter(new ArrayList<>());

        RecyclerView rv = findViewById(R.id.createrank_lista);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,68,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar

        // Esconte o teclado quando a tela é iniciada
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm.isActive()) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


        // botão para retornar
        ImageView id_ic_createrank_back = findViewById(R.id.id_ic_m01_back);
        id_ic_createrank_back.setOnClickListener(v -> onBackPressed());

        // pegar id do item
        id_edtext_m01_edName = findViewById(R.id.id_edtext_m01_edName);

        id_edtext_m01_namefood05 = findViewById(R.id.id_edtext_m01_namefood05);

        id_text_m01_addExtra = findViewById(R.id.id_text_m01_addExtra);
        id_btn_m01_savelist = findViewById(R.id.id_btn_m01_savelist);

        id_checkbox_public = findViewById(R.id.id_checkbox_public);
        option_public = findViewById(R.id.option_public);

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHandler(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
        ));

        helper.attachToRecyclerView(rv);

        // Função visibilidade de rank
        id_checkbox_public.setOnClickListener(v -> {

            //Demonstração de função -> de bobeira mesmo
            int a = 0;
            if(a == 0){
                option_public.setImageResource(R.drawable.ic_vote_aprove);
                a = 1;
            } else {
                option_public.setImageResource(R.drawable.ic_vote_null);
                a = 0;
            }
        });

        // Função dos botões addExtra
        id_text_m01_addExtra.setOnClickListener(v -> { // Adicionar mais um item

            String newItem = id_edtext_m01_namefood05.getText().toString();

            if(newItem.isEmpty()){
                alert(getString(R.string.alert_empty_item));
            } else {

                if (added == 0){
                    position = dataInstance.getDataRanking().size();
                    Ranking newRanking = new Ranking(dataInstance.log);
                    dataInstance.setDataRanking(newRanking);
                }

                adapter.getRankingItems().add(newItem);
                adapter.notifyItemInserted((adapter.getRankingItems().size() - 1));
                dataInstance.getDataRanking(position)
                        .setItemOfRanking(newItem);
                added++;

                //Limpa o campo de texto
                id_edtext_m01_namefood05.setText("");

                //Enconder teclado ao clicar em botão
                if(imm.isActive()) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                //Logs
                Log.d("OperLog" , "um item foi criado");
                Log.d("OperSerialize" , dataInstance.serialize());
            }
        });

        id_btn_m01_savelist.setOnClickListener(v -> { // Salvar lista

            String nameText = String.valueOf(id_edtext_m01_edName.getText());         // pegar texto do EditText

            if(added >= 2){
                if(!nameText.equals("")){
                    dataInstance.getDataRanking(position)
                            .setName(nameText);
                    Intent intent = new Intent();
                    intent.putExtra("Data", dataInstance);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    // é necessario colocar um nome para o Ranking
                    alert(getString(R.string.rankingNameIsRequired));
                }
            } else {
                // é necessario colocar mais de um item para votação
                alert(getString(R.string.moreItemsIsRequired));
            }
        });

    }

    private class ItemTouchHandler extends ItemTouchHelper.SimpleCallback{

        public ItemTouchHandler(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            Collections.swap(adapter.getRankingItems(), from, to);
            adapter.notifyItemMoved(from, to);

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (dataInstance.getDataRanking(position).removeItemOfRanking(viewHolder.getAdapterPosition())){
                adapter.getRankingItems().remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
            Log.d("OperLog" , "um item foi removido");
            Log.d("OperSerialize" , dataInstance.getDataRanking().get(dataInstance.log).serialize());
        }
    }

}