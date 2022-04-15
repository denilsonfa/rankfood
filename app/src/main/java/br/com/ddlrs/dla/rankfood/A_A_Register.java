package br.com.ddlrs.dla.rankfood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class A_A_Register extends AppCompatActivity {

    EditText    id_edtext_register_edName;          // Nome do usuário
    EditText    id_edtext_register_edEmail;         // Email do usuário
    EditText    id_edtext_register_edSenha;         // Senha do usuário
    EditText    id_edtext_register_edConfirmSenha;  // Confirmação da senha
    Button      id_btn_register_login;              // Botão de login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_register);

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,68,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar

        // Esconte o teclado quando a tela é iniciada
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm.isActive()) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


        // botão de abrir a tela de login do usuário (A_A_Login)
        ImageView id_ic_register_back = findViewById(R.id.id_ic_register_back);
        id_ic_register_back.setOnClickListener(v -> onBackPressed());


        // botão/texto de abrir a tela de cadastro do usuário (A_A_Register)
        TextView id_text_register_login = findViewById(R.id.id_text_register_login);
        id_text_register_login.setOnClickListener(v -> { // Botão para abrir a tela de login
            Intent i = new Intent(A_A_Register.this, A_A_Login.class);
            startActivity(i); overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });


        // pegar id do item
        id_edtext_register_edName = findViewById(R.id.id_edtext_register_edName);
        id_edtext_register_edEmail = findViewById(R.id.id_edtext_register_edEmail);
        id_edtext_register_edSenha = findViewById(R.id.id_edtext_register_edSenha);
        id_edtext_register_edConfirmSenha = findViewById(R.id.id_edtext_register_edConfirmSenha);
        id_btn_register_login = findViewById(R.id.id_btn_register_login);


        // função do botão de entrar
        id_btn_register_login.setOnClickListener(v -> { // Botão para entrar

            id_edtext_register_edName.getText();            // pegar texto do EditText
            id_edtext_register_edEmail.getText();           // pegar texto do EditText
            id_edtext_register_edSenha.getText();           // pegar texto do EditText
            id_edtext_register_edConfirmSenha.getText();    // pegar texto do EditText

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

    }
}