package com.gameon.mycash_carteiradigital.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.GetChars;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.gameon.mycash_carteiradigital.R;
import com.gameon.mycash_carteiradigital.helper.DatePickerFragment;
import com.gameon.mycash_carteiradigital.helper.InputDAO;
import com.gameon.mycash_carteiradigital.model.Input;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GraficoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    /** Essse código faz uso da Biblioteca MPAndroidChart **/
    /** Encontrada no link " https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started " **/

    private PieChart pieChart;

    private List<Input> inputsDb;

    //Variável que controla se a seleção é do começo ou do final
    private boolean startOrLastDate = true;

    private Calendar cal = Calendar.getInstance();

    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private Date dtStart;
    private Date dtLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grafico);
        Toolbar toobar = findViewById(R.id.toolbar);
        setSupportActionBar(toobar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Grafico dos ganhos");

        pieChart = findViewById(R.id.pieChartEarnings);

        //Ações dos botões da tela
        //Todo Implementar botões na tela


        //insere a mensagem que aparece caso não tenha dados no chart
        pieChart.setNoDataText("Sem dados disponíveis");


        //recuperar os dados do BD
        InputDAO inputDAO = new InputDAO(getApplicationContext());
        inputsDb = inputDAO.typeValue();

        /** cria uma lista do tipo PieChar **/
        List<PieEntry> inputsChar = new ArrayList<>();

        //Valores das categorias
        float value1=0;
        float value2=0;
        float value3=0;

        //Nome de cada categoria
        String type1="";
        String type2="";
        String type3="";

        /** extrai todos os valores do banco de dados das entradas de ganhos **/
        for ( int i=0; i < inputsDb.size(); i++ ) {

            Input inp = inputsDb.get(i);
            switch (inp.getTypeInput()){
                case "Salário":
                    double v1 = inp.getValueInput();
                    value1 = value1 + (float) v1;
                    type1 = inp.getTypeInput();
                    break;
                case "Extra":
                    double v2 = inp.getValueInput();
                    value2 = value2 + (float) v2;
                    type2 = inp.getTypeInput();
                    break;
                case "Outros Ganhos":
                    double v3 = inp.getValueInput();
                    value3 = value3 + (float) v3;
                    type3 = inp.getTypeInput().split(" ")[0];
                    break;
            }
        }

        float[] values = {value1, value2, value3};
        String[] types = {type1, type2, type3};



        /** Preechendo o gráfico **/
        for (int i=0; i < types.length; i++){
            //Condição que verifica se há valores positivos sendo adicionados no array
            if(values[i]>0){
                inputsChar.add(new PieEntry( values[i], types[i]));
            }


        }



        PieDataSet pieDataSet = new PieDataSet(inputsChar, null);

        //Define as cores do gráfico
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);

        //Condição especificando que o gráfico só será criado se houver algum dado cadastrado
        if(inputsChar.size()>0) {
            //Descrição
            Description description = pieChart.getDescription();
            description.setText("Ganhos");
            description.setTextSize(14);

            //Configurar legendas
            Legend legend = pieChart.getLegend();
            legend.setTextSize(14);

            //Define a duração da animação e os dados que estarão no gráfico
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

    //Função para selecionar a data
    //Todo Botão para selecionar data
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //Deixa a data atual selecionada no calendario

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        //Salva a data selecionada no calendário em uma string
        String simpleDate = df.format(cal.getTime());
        String date = DateFormat.getDateInstance().format(cal.getTime());
        //Button startDate = findViewById(R.id.start_date_btn);
        //Button lastDate = findViewById(R.id.close_date_btn);

        //Dependendo do botão o texto dele muda pra data selecionada
        /*if(startOrLastDate){
            try {
                dtStart = df.parse(simpleDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            buttonFirstDate.setText(date);
        }else{
            try {
                dtLast = df.parse(simpleDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            buttonLastDate.setText(date);
        }*/

    }
}

