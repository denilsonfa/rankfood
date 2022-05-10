package br.com.ddlrs.dla.rankfood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import controller.Data;
import model.Constants;

public class A_A_Menu extends AppCompatActivity implements Constants {

    LinearLayout id_btn_menu_m01CreateRank;
    LinearLayout id_btn_menu_m02Vote;
    LinearLayout id_btn_menu_m03ViewRank;
    LinearLayout id_btn_menu_m04Sobre;
    ImageView id_ic_menu_exit;
    Data dataInstance;

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Data", dataInstance);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_menu);

        dataInstance = getIntent().getExtras().getParcelable("Data");

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,68,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar


        // pegar id do item
        id_btn_menu_m01CreateRank = findViewById(R.id.id_btn_menu_m01CreateRank);
        id_btn_menu_m02Vote = findViewById(R.id.id_btn_menu_m02Vote);
        id_btn_menu_m03ViewRank = findViewById(R.id.id_btn_menu_m03ViewRank);
        id_btn_menu_m04Sobre = findViewById(R.id.id_btn_menu_m04Sobre);
        id_ic_menu_exit = findViewById(R.id.id_ic_menu_exit);


        // função do botão de sair
        id_ic_menu_exit.setOnClickListener(v -> { // Botão para entrar

            // Zoeira mesmo
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton(R.string.ok, (dialog, which) -> {
                dialog.dismiss();   // fechar o dialog
            });
            AlertDialog dialog = builder.create();
            LayoutInflater inflater = getLayoutInflater();
            View dialogLayout = inflater.inflate(R.layout.item_pop_zoeira, null);
            dialog.setView(dialogLayout);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();

        });


        // Botão para abrir a tela de criação de rank
        id_btn_menu_m01CreateRank.setOnClickListener(v -> {
            Intent i = new Intent(A_A_Menu.this, A_M01_CreateRank.class);
            startActivity(i); overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });


        // Botão para abrir tela de votação de rank
        id_btn_menu_m02Vote.setOnClickListener(v -> {
            Intent i = new Intent(A_A_Menu.this, A_M02_Vote.class);
            startActivity(i); overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });


        // Botão para abrir tela de visualização de rank
        id_btn_menu_m03ViewRank.setOnClickListener(v -> {
            Intent i = new Intent(A_A_Menu.this, A_M03_ViewRank.class);
            startActivity(i); overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });


        // Botão para abrir tela Sobre
        id_btn_menu_m04Sobre.setOnClickListener(v -> {
            Intent i = new Intent(A_A_Menu.this, A_M04_Sobre.class);
            startActivity(i); overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });


    }

//    @Override
//    public void onBackPressed(){
//
//        finish();
//    }
}