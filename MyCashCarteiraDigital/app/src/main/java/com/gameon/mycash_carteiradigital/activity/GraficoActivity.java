package com.gameon.mycash_carteiradigital.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gameon.mycash_carteiradigital.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraficoActivity extends AppCompatActivity {

    /** Essse código faz uso da Biblioteca MPAndroidChart **/
    /** Encontrada no link " https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started " **/

    private PieChart pieChart;

    float[] items = {95.5f, 35.4f, 62.56f , 77.7f, 10.3f};

    String[] description = {"Item um", "Item dois", "Item três", "Item quatro", "Item cinco"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grafico);

        pieChart = findViewById(R.id.pieChartEarnings);

        /** cria uma lista do tipo PieChar **/
        List<PieEntry> inputsChar = new ArrayList<>();

        /**Preechendo o gráfico **/
        for (int i=0; i < items.length; i++){
            inputsChar.add(new PieEntry(items[i], description[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(inputsChar, "Gráfico de ganhos");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);

        pieChart.animateY(1300);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
