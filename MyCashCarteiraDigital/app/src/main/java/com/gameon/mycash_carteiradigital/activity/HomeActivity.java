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

        //Elevação da toobar
        getSupportActionBar().setElevation(0);

    }

    //Função de mudança de tela que irá ser executada quando o botão for pressionado
    public void ganhoTela(View view){

        Intent intent = new Intent(this, CadastroGanhosActivity.class);
        startActivity(intent);
    }

    //Tela de listagem de ganhos
    public  void consultarGanhos (View view){

        Intent intent = new Intent(this, ListagemGanhosActivity.class);
        startActivity(intent);
    }

    //Tela de listagem de despesas
    public void consultarDespesas (View view){

        Intent intent = new Intent(this, ListagemDespesasActivity.class);
        startActivity(intent);
    }

    //Função de mudança de tela que irá ser executada quando o botão for pressionado
    public void despesaTela(View view){

        Intent intent = new Intent(this, CadastroDespesasActivity.class);
        startActivity(intent);
    }

    public void graficoTela (View view){

        Intent intent = new Intent(this, GraficoActivity.class);
        startActivity(intent);

    }

    //Vai pra tela de SOBRE NÓS
    public void sobreTela(View view){

        Intent intent = new Intent(this, SobreActivity.class);
        startActivity(intent);
    }

}
