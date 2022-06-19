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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.controller.Data;
import br.com.ddlrs.dla.rankfood.controller.User;
import br.com.ddlrs.dla.rankfood.model.Constants;

public class A_A_Login extends AppCompatActivity implements Constants {

    EditText id_edtext_login_edEmail;    // EditText para o nome de usuário
    EditText id_edtext_login_edSenha;   // EditText para a senha
    Button   id_btn_login_enter;        // Botão para entrar
    Data dataInstance;

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
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Data", dataInstance);
        setResult(RESULT_CANCELED, intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_login);

        dataInstance = getIntent().getExtras().getParcelable("Data");

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,68,0)); // Cor da barra de status
        Objects.requireNonNull(getSupportActionBar()).hide(); //esconde a action bar

        // Esconte o teclado quando a tela é iniciada
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm.isActive()) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


        // botão para retornar
        ImageView id_ic_login_back = findViewById(R.id.id_ic_login_back);
        id_ic_login_back.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("Data", dataInstance);
            setResult(RESULT_CANCELED, intent);
            finish();
        });




        // botão/texto de abrir a tela de cadastro do usuário (A_A_Register)
        TextView id_text_login_register = findViewById(R.id.id_text_login_register);
        id_text_login_register.setOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_FIRST_USER, intent);
            finish();
        });


        // pegar id do item
        id_edtext_login_edEmail = findViewById(R.id.id_edtext_login_edEmail);
        id_edtext_login_edSenha = findViewById(R.id.id_edtext_login_edSenha);
        id_btn_login_enter = findViewById(R.id.id_btn_login_enter);


        // função do botão de entrar
        id_btn_login_enter.setOnClickListener(v -> { // Botão para entrar

            String  edEmail = id_edtext_login_edEmail.getText().toString(),     // pegar texto do EditText
                    edSenha = id_edtext_login_edSenha.getText().toString();     // pegar texto do EditText

            User newUser = new User();

            if(newUser.validadeEmail(edEmail)) {


                boolean emailIsUsed = false;
                int currentUser = 0;
                for (int i = 0; i < dataInstance.getDataUser().size(); i++) {
                    if (dataInstance.getDataUser().get(i).UserEmail().equals(edEmail)) {
                        emailIsUsed = true;
                        currentUser = i;
                    }
                }


                boolean currect = emailIsUsed? newUser.validatePassword(dataInstance.getDataUser().get(currentUser).UserPassword(), edSenha) : false;
                if (currect) {
                    Intent intent = new Intent();
                    intent.putExtra("Data", dataInstance);
                    intent.putExtra("UserId", currentUser);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    // Esse email ou senha incorretos
                    alert(getString(R.string.informationIncorrect));
                    Log.d("OperLog" , "Esse email ainda não foi usado");
                    Log.d("OperSerialize" , dataInstance.serialize());
                }

            } else {
                // email invalido
                alert(getString(R.string.invalidEmail));
                Log.d("OperLog" , "email invalido");
                Log.d("OperSerialize" , dataInstance.serialize());
            }

        });

    }
}