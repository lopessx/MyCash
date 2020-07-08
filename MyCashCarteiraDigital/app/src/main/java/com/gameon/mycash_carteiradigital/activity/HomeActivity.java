package com.gameon.mycash_carteiradigital.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gameon.mycash_carteiradigital.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

    }

    //Função de mudança de tela que irá ser executada quando o botão for pressionado
    public void ganhoTela(View view){

        Intent intent = new Intent(this, CadastroGanhosActivity.class);
        startActivity(intent);
    }
    //Função de mudança de tela que irá ser executada quando o botão for pressionado
    public void despesaTela(View view){

        Intent intent = new Intent(this, CadastroDespesasActivity.class);
        startActivity(intent);
    }

}
