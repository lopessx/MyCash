package com.gameon.mycash_carteiradigital.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.gameon.mycash_carteiradigital.R;
import com.gameon.mycash_carteiradigital.helper.InputDAO;
import com.gameon.mycash_carteiradigital.helper.OutputDAO;

public class HomeActivity extends AppCompatActivity {

    private double saldo=0;

    private TextView saldoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        //Elevação da toobar
        getSupportActionBar().setElevation(0);

    }

    //Atualizar o saldo sempre que a tela Home for chamada
    @Override
    protected void onStart() {
        super.onStart();

        //Inicialização dos DAOs para tratar dos valores de entrada e saída
        OutputDAO output = new OutputDAO(this);
        InputDAO input = new InputDAO(this);

        //Pega a view do layout
        saldoText = findViewById(R.id.numSaldoTotal);

        //Calculo do saldo
        saldo = input.totalEarning() -  output.totalSpending();

        //Visualização do saldo no textview
        saldoText.setText("R$ " + String.format("%.2f", saldo));
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

    public void graficoTelaDespesas (View view){

        Intent intent = new Intent(this, GraficoDespesasActivity.class);
        startActivity(intent);

    }

    //Vai pra tela de SOBRE NÓS
    public void sobreTela(View view){

        Intent intent = new Intent(this, SobreActivity.class);
        startActivity(intent);
    }

}
