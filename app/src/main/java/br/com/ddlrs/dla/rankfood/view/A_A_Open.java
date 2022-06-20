package br.com.ddlrs.dla.rankfood.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.controller.Data;
import br.com.ddlrs.dla.rankfood.controller.User;
import br.com.ddlrs.dla.rankfood.model.Constants;

public class A_A_Open extends AppCompatActivity implements Constants {

    private Data dataInstance = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_open);

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,172,13)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar


        // botão para retornar
        Button id_btn_open_enter = findViewById(R.id.id_btn_open_enter);
        id_btn_open_enter.setOnClickListener(v -> { // Botão para abrir a tela de login
            Intent i = new Intent(A_A_Open.this, A_A_Login.class);
            i.putExtra("Data", dataInstance);
            startActivityForResult(i, LOGIN_ACTIVITY_REQUEST_CODE);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });

        // botão para modo Guest
        Button id_btn_open_guest = findViewById(R.id.id_btn_open_guest);
        id_btn_open_guest.setOnClickListener(v -> {
            dataInstance.setStatusOfinstance(null, true);
            Intent i = new Intent(A_A_Open.this, A_M05_ListaRanks.class);
            i.putExtra("Data", dataInstance);
            i.putExtra("Operation", String.valueOf(GUEST_MODE_ACTIVITY_REQUEST_CODE));
            startActivityForResult(i, GUEST_MODE_ACTIVITY_REQUEST_CODE);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });

        // Texto para abrir a tela de registro do usuário (A_A_Register)
        TextView id_text_open_resgister = findViewById(R.id.id_text_open_resgister);
        id_text_open_resgister.setOnClickListener(v -> { // Botão para abrir a tela de login
            Intent i = new Intent(A_A_Open.this, A_A_Register.class);
            i.putExtra("Data", dataInstance);
            startActivityForResult(i, REGISTER_ACTIVITY_REQUEST_CODE);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });

    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                dataInstance.Update(data.getExtras().getParcelable("Data"));
                dataInstance.setStatusOfinstance(data.getExtras().getInt("UserId"),false);

                Log.d("OperLog" , "Usuario logado");
                Log.d("OperSerialize" , dataInstance.serialize());
                Intent i = new Intent(A_A_Open.this, A_A_Menu.class);
                i.putExtra("Data", dataInstance);
                startActivityForResult(i, MENU_ACTIVITY_REQUEST_CODE);

            } else if(resultCode == RESULT_CANCELED){

                dataInstance.Update(data.getExtras().getParcelable("Data"));
                dataInstance.setStatusOfinstance(null,false);

            } else if(resultCode == RESULT_FIRST_USER){

                Intent i = new Intent(A_A_Open.this, A_A_Register.class);
                i.putExtra("Data", dataInstance);
                startActivityForResult(i, REGISTER_ACTIVITY_REQUEST_CODE);

            }

        }
        else if (requestCode == REGISTER_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_FIRST_USER) {

                User newUser = data.getExtras().getParcelable("NewUser");
                dataInstance.setDataUser(newUser);
                dataInstance.setStatusOfinstance(null,false);

                Intent i = new Intent(A_A_Open.this, A_A_Login.class);
                i.putExtra("Data", dataInstance);
                startActivityForResult(i, LOGIN_ACTIVITY_REQUEST_CODE);

            } else if(resultCode == RESULT_CANCELED){
                dataInstance.Update(data.getExtras().getParcelable("Data"));
                dataInstance.setStatusOfinstance(null,false);
            } else if(resultCode == RESULT_OK){
                Intent i = new Intent(A_A_Open.this, A_A_Login.class);
                i.putExtra("Data", dataInstance);
                startActivityForResult(i, LOGIN_ACTIVITY_REQUEST_CODE);
            }
        }
        else if (requestCode == MENU_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_CANCELED || resultCode== RESULT_OK) {
                dataInstance.Update(data.getExtras().getParcelable("Data"));
                dataInstance.setStatusOfinstance(null, false);
            }
        }
        else if (requestCode == GUEST_MODE_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_CANCELED || resultCode== RESULT_OK) {
                dataInstance.Update(data.getExtras().getParcelable("Data"));
                dataInstance.setStatusOfinstance(null, false);
            }
        }
    }

}