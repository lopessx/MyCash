package com.gameon.mycash_carteiradigital.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gameon.mycash_carteiradigital.R;
import com.gameon.mycash_carteiradigital.helper.OutputDAO;
import com.gameon.mycash_carteiradigital.model.Output;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraficoDespesasActivity extends AppCompatActivity{

    /** Essse código faz uso da Biblioteca MPAndroidChart **/
    /** Encontrada no link " https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started " **/

    private PieChart pieChart;

    private List<Output> outputsDb;
    private Integer[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grafico_despesas);
        Toolbar toobar = findViewById(R.id.toolbar);
        setSupportActionBar(toobar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Grafico das despesas");

        pieChart = findViewById(R.id.pieChartSpendings);

        //insere a mensagem que aparece caso não tenha dados no chart
        pieChart.setNoDataText("Sem dados disponíveis");


        //recuperar os dados do BD
        OutputDAO outputDAO = new OutputDAO(getApplicationContext());
        outputsDb = outputDAO.list();

        /** cria uma lista do tipo PieChar **/
        List<PieEntry> outputsChar = new ArrayList<>();

        //Valores das despesas
        float value1=0;
        float value2=0;
        float value3=0;
        float value4=0;
        float value5=0;
        float value6=0;
        float value7=0;
        float value8=0;

        //Tipos das despesas
        String type1="", type2="", type3="", type4="", type5="", type6="", type7="", type8="";

        for ( int i=0; i < outputsDb.size(); i++ ) {

            Output out = outputsDb.get(i);
            switch (out.getTypeOutput()){
                case "Alimentação":
                    double v1 = out.getValueOutput();
                    value1 = value1 + (float) v1;
                    type1 = out.getTypeOutput();
                    break;
                case "Aluguel":
                    double v2 = out.getValueOutput();
                    value2 = value2 + (float) v2;
                    type2 = out.getTypeOutput();
                    break;
                case "Água":
                    double v3 = out.getValueOutput();
                    value3 = value3 + (float) v3;
                    type3 = out.getTypeOutput();
                    break;
                case "Energia":
                    double v4 = out.getValueOutput();
                    value4 = value4 + (float) v4;
                    type4 = out.getTypeOutput();
                    break;
                case "Cartão de Crédito":
                    double v5 = out.getValueOutput();
                    value5 = value5 + (float) v5;
                    type5 = out.getTypeOutput().split(" ")[0];
                    break;
                case "Combustível":
                    double v6 = out.getValueOutput();
                    value6 = value6 + (float) v6;
                    type6 = out.getTypeOutput();
                    break;
                case "Lazer":
                    double v7 = out.getValueOutput();
                    value7 = value7 + (float) v7;
                    type7 = out.getTypeOutput();
                    break;
                case "Outras Despesas":
                    double v8 = out.getValueOutput();
                    value8 = value8 + (float) v8;
                    type8 = out.getTypeOutput().split(" ")[0];
                    break;
            }
        }

        float[] values = {value1, value2, value3, value4, value5, value6, value7, value8};
        String[] types = {type1, type2, type3, type4, type5, type6, type7, type8};

        /** Preechendo o gráfico **/
        for (int i=0; i < types.length; i++){
            if(values[i]>0) {
                outputsChar.add(new PieEntry(values[i], types[i]));
            }
        }


        //Array com as cores para o gráfico
        colors = new Integer[]{Color.parseColor("#cf58c2"), Color.parseColor("#28b1ff"),
                Color.parseColor("#ffdb28"), Color.parseColor("#e8423f"),
                Color.parseColor("#b73ae8"), Color.parseColor("#fc952b"),
                Color.parseColor("#80381b"), Color.parseColor("#528c3b")};

        PieDataSet pieDataSet = new PieDataSet(outputsChar, null);
        pieDataSet.setColors(Arrays.asList(colors));

        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);

        //Condição especificando que o gráfico só será criado se houver algum dado cadastrado
        if(outputsChar.size()>0) {
            //Descrição
            Description description = pieChart.getDescription();
            description.setText("Despesas");
            description.setTextSize(14);

            //Configurar legendas
            Legend legend = pieChart.getLegend();
            legend.setMaxSizePercent(0.90f);
            legend.setWordWrapEnabled(true);
            legend.setTextSize(14);

            //Define duração da animação e dados que estarão no grafico
            pieChart.animateY(1000);
            pieChart.setData(pieData);

            //Formata os dados para serem representados por porcentagem
            pieChart.getData().setValueFormatter(new PercentFormatter(pieChart));
            pieChart.setUsePercentValues(true);

            //Esconde os títulos dos valores e define o tamanho
            pieChart.setEntryLabelColor(Color.TRANSPARENT);
            pieChart.getData().setValueTextSize(16);

            pieChart.invalidate();
        }else{

            //Formata o texto da mensagem quando não há dados
            Paint p = pieChart.getPaint(Chart.PAINT_INFO);
            p.setTextSize(60f);
            p.setTextAlign(Paint.Align.CENTER);
            pieChart.invalidate();
        }

    }

}





