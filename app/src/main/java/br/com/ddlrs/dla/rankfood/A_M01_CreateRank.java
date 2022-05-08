package br.com.ddlrs.dla.rankfood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import android.widget.Toast;

import java.util.Objects;

public class A_M01_CreateRank extends AppCompatActivity {

    EditText id_edtext_m01_edName;
    EditText id_edtext_m01_namefood01, id_edtext_m01_namefood02,
             id_edtext_m01_namefood03, id_edtext_m01_namefood04,
             id_edtext_m01_namefood05;
    TextView id_text_m01_addExtra;
    Button id_btn_m01_savelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_m01_createrank);

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

        id_edtext_m01_namefood01 = findViewById(R.id.id_edtext_m01_namefood01);
        id_edtext_m01_namefood02 = findViewById(R.id.id_edtext_m01_namefood02);
        id_edtext_m01_namefood03 = findViewById(R.id.id_edtext_m01_namefood03);
        id_edtext_m01_namefood04 = findViewById(R.id.id_edtext_m01_namefood04);
        id_edtext_m01_namefood05 = findViewById(R.id.id_edtext_m01_namefood05);

        id_text_m01_addExtra = findViewById(R.id.id_text_m01_addExtra);
        id_btn_m01_savelist = findViewById(R.id.id_btn_m01_savelist);

        // Função dos botões
        id_text_m01_addExtra.setOnClickListener(v -> { // Adicionar mais um item
            // Mensagem de Teste
            Toast.makeText(this, "Quero ver vocês fazerem isso aqui ;)", Toast.LENGTH_SHORT).show();
        });

        id_btn_m01_savelist.setOnClickListener(v -> { // Salvar lista

            id_edtext_m01_edName.getText();         // pegar texto do EditText
            id_edtext_m01_namefood01.getText();     // pegar texto do EditText
            id_edtext_m01_namefood02.getText();     // pegar texto do EditText
            id_edtext_m01_namefood03.getText();     // pegar texto do EditText
            id_edtext_m01_namefood04.getText();     // pegar texto do EditText
            id_edtext_m01_namefood05.getText();     // pegar texto do EditText

            //Lembrado que tem que ter um controlador que impeça o usuário de deixar o campo em branco

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