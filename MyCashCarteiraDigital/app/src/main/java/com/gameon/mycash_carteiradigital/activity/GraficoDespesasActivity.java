package com.gameon.mycash_carteiradigital.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.gameon.mycash_carteiradigital.R;
import com.gameon.mycash_carteiradigital.helper.InputDAO;
import com.gameon.mycash_carteiradigital.helper.OutputDAO;
import com.gameon.mycash_carteiradigital.model.Input;
import com.gameon.mycash_carteiradigital.model.Output;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraficoDespesasActivity extends AppCompatActivity {

    /** Essse código faz uso da Biblioteca MPAndroidChart **/
    /** Encontrada no link " https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started " **/

    private PieChart pieChart;

    private List<Output> outputsDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grafico_despesas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Resumo de atividades");

        pieChart = findViewById(R.id.pieChartSpendings);

        //recuperar os dados do BD
        OutputDAO outputDAO = new OutputDAO(getApplicationContext());
        outputsDb = outputDAO.list();

        /** cria uma lista do tipo PieChar **/
        List<PieEntry> outputsChar = new ArrayList<>();

        float value1=0;
        float value2=0;
        float value3=0;
        float value4=0;
        float value5=0;
        float value6=0;
        float value7=0;
        float value8=0;

        for ( int i=0; i < outputsDb.size(); i++ ) {

            Output out = outputsDb.get(i);
            switch (out.getTypeOutput()){
                case "Alimentação":
                    double v1 = out.getValueOutput();
                    value1 = value1 + (float) v1;
                    break;
                case "Aluguel":
                    double v2 = out.getValueOutput();
                    value2 = value2 + (float) v2;
                    break;
                case "Água":
                    double v3 = out.getValueOutput();
                    value3 = value3 + (float) v3;
                    break;
                case "Energia":
                    double v4 = out.getValueOutput();
                    value4 = value4 + (float) v4;
                    break;
                case "Cartão de Crédito":
                    double v5 = out.getValueOutput();
                    value5 = value5 + (float) v5;
                    break;
                case "Combustível":
                    double v6 = out.getValueOutput();
                    value6 = value6 + (float) v6;
                    break;
                case "Lazer":
                    double v7 = out.getValueOutput();
                    value7 = value7 + (float) v7;
                    break;
                case "Outras Despesas":
                    double v8 = out.getValueOutput();
                    value8 = value8 + (float) v8;
                    break;
            }
        }

        float[] values = {value1, value2, value3, value4, value5, value6, value7, value8};
        String[] types = {"Alimentação", "Aluguel", "Conta de água","Conta de luz", "Cartão de crédito", "Combustível","Lazer", "Outros"};

        /** Preechendo o gráfico **/
        for (int i=0; i < types.length; i++){
            outputsChar.add(new PieEntry( values[i], types[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(outputsChar, "Gráfico de despesas");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);

        pieChart.animateY(1000);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}





