package br.com.ddlrs.dla.rankfood.view;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.controller.Data;
import br.com.ddlrs.dla.rankfood.controller.User;
import br.com.ddlrs.dla.rankfood.model.Constants;

public class A_A_Register extends AppCompatActivity implements Constants {

    Data        dataInstance;
    EditText    id_edtext_register_edName;          // Nome do usuário
    EditText    id_edtext_register_edEmail;         // Email do usuário
    EditText    id_edtext_register_edSenha;         // Senha do usuário
    EditText    id_edtext_register_edConfirmSenha;  // Confirmação da senha
    ImageView   id_profile_picture;                 // Foto de perfil
    ImageView   id_change_btn;                      // Botão de troca
    Button      id_btn_register_login;              // Botão de login
    int         pictureOption = 0;

    private void alert(String massege){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss() );
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.item_pop_alert, null);
        TextView titleText = dialogLayout.findViewById(R.id.TitleText);
        titleText.setText(massege);
        AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Data", dataInstance);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_register);

        dataInstance = getIntent().getExtras().getParcelable("Data");

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,68,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar

        // Esconte o teclado quando a tela é iniciada
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm.isActive()) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


        // botão para retornar
        ImageView id_ic_register_back = findViewById(R.id.id_ic_register_back);
        id_ic_register_back.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("Data", dataInstance);
            setResult(RESULT_CANCELED, intent);
            finish();
        });


        // botão/texto de abrir a tela de cadastro do usuário (A_A_Register)
        TextView id_text_register_login = findViewById(R.id.id_text_register_login);
        id_text_register_login.setOnClickListener(v -> { // Botão para abrir a tela de login
            Intent intent = new Intent();
            intent.putExtra("Data", dataInstance);
            setResult(RESULT_OK, intent);
            finish();
        });


        // pegar id do item
        id_edtext_register_edName = findViewById(R.id.id_edtext_register_edName);
        id_edtext_register_edEmail = findViewById(R.id.id_edtext_register_edEmail);
        id_edtext_register_edSenha = findViewById(R.id.id_edtext_register_edSenha);
        id_edtext_register_edConfirmSenha = findViewById(R.id.id_edtext_register_edConfirmSenha);
        id_profile_picture = findViewById(R.id.id_profile_picture);
        id_change_btn = findViewById(R.id.id_change_btn);
        id_btn_register_login = findViewById(R.id.id_btn_register_login);


        id_change_btn.setOnClickListener(v -> {
            pictureOption = (pictureOption == 5)?0:pictureOption + 1;

            id_profile_picture.setImageResource(IMAGE_OPTIONS[pictureOption]);
        });

        // função do botão de entrar
        id_btn_register_login.setOnClickListener(v -> { // Botão para entrar

            String  edName = String.valueOf(id_edtext_register_edName.getText()),                   // pegar texto do EditText
                    edEmail  = String.valueOf(id_edtext_register_edEmail.getText()),                // pegar texto do EditText
                    edSenha = String.valueOf(id_edtext_register_edSenha.getText()),                 // pegar texto do EditText
                    edConfirmSenha = String.valueOf(id_edtext_register_edConfirmSenha.getText());   // pegar texto do EditText

            User newUser = new User();

            if (newUser.validadeEmail(edEmail)){

                boolean emailIsUsed = false;
                for (int i = 0; i < dataInstance.getDataUser().size(); i++){
                    if (dataInstance.getDataUser().get(i).UserEmail().equals(edEmail)) emailIsUsed = true;
                }

                if(!emailIsUsed){
                    if(newUser.validatePassword(edSenha,edConfirmSenha)){
                        newUser.createUser(edName, edEmail, edSenha, pictureOption);
                        dataInstance.setDataUser(newUser);
                        Intent intent = new Intent();
                        intent.putExtra("NewUser", newUser);
                        setResult(RESULT_FIRST_USER, intent);
                        finish();
                    }

                    else {
                        // senha e senha de confirmação são diferentes
                        alert(getString(R.string.incompatiblePass));
                        Log.d("OperLog" , "senha e senha de confirmação são diferentes");
                        Log.d("OperSerialize" , dataInstance.serialize());

                    }
                }

                else {
                    // O email já foi usado
                    alert(getString(R.string.usedEmail));
                    Log.d("OperLog" , "O email já foi usado");
                    Log.d("OperSerialize" , dataInstance.serialize());
                }
            }

            else{
                // email invalido
                alert(getString(R.string.invalidEmail));
                Log.d("OperLog" , "email invalido");
                Log.d("OperSerialize" , dataInstance.serialize());
            }


        });

    }
}