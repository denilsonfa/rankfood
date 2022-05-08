package br.com.ddlrs.dla.rankfood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import controller.*;

public class A_A_Open extends AppCompatActivity {
    private static final int LOGIN_ACTIVITY_REQUEST_CODE = 0;
    private static final int REGISTER_ACTIVITY_REQUEST_CODE = 1;

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
            i.putExtra("data", dataInstance);
            startActivityForResult(i, LOGIN_ACTIVITY_REQUEST_CODE);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });


        // Texto para abrir a tela de registro do usuário (A_A_Register)
        TextView id_text_open_resgister = findViewById(R.id.id_text_open_resgister);
        id_text_open_resgister.setOnClickListener(v -> { // Botão para abrir a tela de login
            Intent i = new Intent(A_A_Open.this, A_A_Register.class);
            i.putExtra("data", dataInstance);
            startActivityForResult(i, REGISTER_ACTIVITY_REQUEST_CODE);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });


        // botão de abrir a tela de menu do usuário (A_A_Menu) (temporário)
        Button id_btn_open_home = findViewById(R.id.id_btn_open_home);
        id_btn_open_home.setOnClickListener(v -> { // Botão para abrir a tela de login
            Intent i = new Intent(A_A_Open.this, A_A_Menu.class);
            startActivity(i); overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            finish(); // Finaliza a tela de abertura
        });
    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == LOGIN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

            }if(resultCode == RESULT_CANCELED){
                dataInstance.Update(data.getExtras().getParcelable("data"));
            }
            dataInstance.setStatusOfinstance(null,false,false);

        }else if (requestCode == REGISTER_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {

                User newUser = data.getExtras().getParcelable("NewUser");
                dataInstance.setDataUser(newUser);

                Intent i = new Intent(A_A_Open.this, A_A_Login.class);
                i.putExtra("data", dataInstance);
                startActivityForResult(i, LOGIN_ACTIVITY_REQUEST_CODE);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }if(resultCode == RESULT_CANCELED){
                dataInstance.Update(data.getExtras().getParcelable("data"));
            }

            dataInstance.setStatusOfinstance(null,false,false);
        }
    }

}