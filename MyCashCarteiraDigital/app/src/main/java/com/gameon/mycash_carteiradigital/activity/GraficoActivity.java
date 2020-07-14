package com.gameon.mycash_carteiradigital.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.gameon.mycash_carteiradigital.R;
import com.gameon.mycash_carteiradigital.helper.InputDAO;
import com.gameon.mycash_carteiradigital.model.Input;
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

    private List<Input> inputsDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grafico);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Resumo de atividades");

        pieChart = findViewById(R.id.pieChartEarnings);

        //recuperar os dados do BD
        InputDAO inputDAO = new InputDAO(getApplicationContext());
        inputsDb = inputDAO.typeValue();

        /** cria uma lista do tipo PieChar **/
        List<PieEntry> inputsChar = new ArrayList<>();

        float value1=0;
        float value2=0;
        float value3=0;

        for ( int i=0; i < inputsDb.size(); i++ ) {

            Input inp = inputsDb.get(i);
            switch (inp.getTypeInput()){
                case "Salário":
                    double v1 = inp.getValueInput();
                    value1 = value1 + (float) v1;
                    break;
                case "Extra":
                    double v2 = inp.getValueInput();
                    value2 = value2 + (float) v2;
                    break;
                case "Outros Ganhos":
                    double v3 = inp.getValueInput();
                    value3 = value3 + (float) v3;
                    break;
            }
        }

        float[] values = {value1, value2, value3};
        String[] types = {"Salário", "Extras", "Outros"};

        /** Preechendo o gráfico **/
        for (int i=0; i < types.length; i++){
            inputsChar.add(new PieEntry( values[i], types[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(inputsChar, "Gráfico de ganhos");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);

        pieChart.animateY(1000);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}

