package com.gameon.mycash_carteiradigital.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gameon.mycash_carteiradigital.R;
import com.gameon.mycash_carteiradigital.helper.AdapterListagemDespesas;
import com.gameon.mycash_carteiradigital.helper.DatePickerFragment;
import com.gameon.mycash_carteiradigital.helper.OutputDAO;
import com.gameon.mycash_carteiradigital.helper.RecyclerItemClickListener;
import com.gameon.mycash_carteiradigital.model.Output;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListagemDespesasActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private RecyclerView recyclerView;
    private List<Output> listOutput = new ArrayList<>();
    private Output outputSelected = new Output();
    private AdapterListagemDespesas adapterListagemDespesas;
    private MaterialSearchView searchView;
    private static final String PREFERENCE_2 = "dialog_ON_OFF_2";

    private Button buttonFirstDate;
    private Button buttonLastDate;

    private boolean startOrLastDate = true;

    private Calendar cal = Calendar.getInstance();

    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private Date dtStart = new Date();
    private Date dtLast = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listagem_despesas);
        Toolbar toobar = findViewById(R.id.toolbar);
        setSupportActionBar(toobar);

        //Título
        getSupportActionBar().setTitle("Listagem de despesas");
        //Botão de voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Recuperar o ID do material searchView
        searchView = findViewById(R.id.searchView);

        recyclerView = findViewById(R.id.recyclerListingOutput);

        //Eventos de click para o recyclerView
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                //Deletar item da listagem
                                outputSelected = listOutput.get(position);

                                //Diálogo para confirmar exclusão
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListagemDespesasActivity.this);
                                alertDialog.setTitle("Confirmar exclusão ");
                                alertDialog.setMessage("Deseja escluir este item?");

                                //Botão de positivo
                                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        OutputDAO outputDAO = new OutputDAO(getApplicationContext());

                                        //Condição para excluir
                                        if (outputDAO.delete(outputSelected)){
                                            //Mensagem de confimação para o usuário
                                            Toast.makeText(getApplicationContext(), "Item excluido", Toast.LENGTH_SHORT).show();
                                            //Recarregar lista de itens
                                            loadList();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Erro ao excluir item!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                //Botão de negativo
                                alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Não faz nada
                                    }
                                });

                                alertDialog.create();
                                alertDialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        /** Dialog personalizado **/
        //Salvar a escolha do usuário, se ele quer ou não desativar o Dialog.
        SharedPreferences preferences = getSharedPreferences(PREFERENCE_2, 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("dialogON_2", true);

        if (preferences.contains("dialogON_2") && preferences.getBoolean("dialogON_2", true) == true){
            showDialog();
        }

        //Listener para as funçoes do searchView
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Apresenta algo pro usuário
            }

            @Override
            public void onSearchViewClosed() {
                reloadList();
            }
        });

        //Listeber para caixa de texto
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Executado a pesquisa após o input de pesquisa
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Pesquisa em tempo de execução
                if (newText != null && !newText.isEmpty()){
                    searchItem(newText.toLowerCase());
                }

                //Recarrega toda a lista caso o campo de pesquisa esteja vazio
                if (newText.equals("")){
                    reloadList();
                }

                return true;
            }
        });

    }

    public void loadList(){
        OutputDAO outputDAO = new OutputDAO(ListagemDespesasActivity.this);

        //Recebendo a lista vinda do OutputDao
        listOutput = outputDAO.list();

        //Passando lista para o AdapterListagemDespesas para a listagem
        adapterListagemDespesas = new AdapterListagemDespesas(listOutput);

        //Configurabdo o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapterListagemDespesas);

    }

    //Configurar menu para pesquisa de itens
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        //Configurar o menu para pesquisa de itensd
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        searchView.setMenuItem(menuItem);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuDateFilter) {
            showDialogFilterDate();
        }

        return super.onOptionsItemSelected(item);
    }

    //Carrega a listagem sempre que a activity é iniciada
    /** Dialog personalizado **/
    //Dialog de instrução para ensinar o usuário a excluir itens
    //Função de configuração do Dialog
    public void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ListagemDespesasActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ListagemDespesasActivity.this)
                .inflate(R.layout.layout_dialog, (ConstraintLayout) findViewById(R.id.layoutDialogContainer));
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonDialogYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Salvar a escolha do usuário, se ele quer desativar o Dialog/ instrução de como excluir um item.
                SharedPreferences preferences = getSharedPreferences(PREFERENCE_2, 0);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putBoolean("dialogON_2", false);
                editor.commit();

                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.buttonDialogNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

    @Override
    protected void onStart() {
        loadList();
        super.onStart();
    }

    //Função para pesquisa
    public void searchItem(String text){
        //Log.d("pesquisa", nome);
        List<Output> outPutList = new ArrayList<>();

        for ( Output output : listOutput ){

            String descriptionOutput = output.getDescriptionOutput().toLowerCase();
            if ( descriptionOutput.contains( text ) ){
                outPutList.add(output);
            }
        }

        adapterListagemDespesas = new AdapterListagemDespesas(outPutList);
        recyclerView.setAdapter(adapterListagemDespesas);
        adapterListagemDespesas.notifyDataSetChanged();
    }

    //Função para recarregar a listagem
    public void reloadList(){
        adapterListagemDespesas = new AdapterListagemDespesas(listOutput);
        recyclerView.setAdapter(adapterListagemDespesas);
        adapterListagemDespesas.notifyDataSetChanged();
    }

    //Função para selecionar a data
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //Deixa a data atual selecionada no calendario

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        //Salva a data selecionada no calendário em uma string
        String simpleDate = df.format(cal.getTime());
        String date = DateFormat.getDateInstance().format(cal.getTime());

        /** Estes botões doram substituidos por variaveis globais por questao de bugs **/


        //Dependendo do botão o texto dele muda pra data selecionada
        if(startOrLastDate){
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
        }

    }

    //Função para criar o Dialog para seleção da data para filtrar os itens da lista
    public void showDialogFilterDate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListagemDespesasActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ListagemDespesasActivity.this)
                .inflate(R.layout.layout_dialog_filter_date, (ConstraintLayout) findViewById(R.id.layoutDialogContainer_FilterDate));
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        //Click do botão para data inicial
        buttonFirstDate = view.findViewById(R.id.start_date_filter_btn);
        buttonFirstDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Booleano para verificar se é a primeira data selecionada ou última
                startOrLastDate = true;
                //Vai instanciar o dialogo do calendário baseado na classe de fragment criada
                DialogFragment datePicker = new DatePickerFragment();
                //Mostra o calendário
                datePicker.show(getSupportFragmentManager(),"data start");
            }
        });

        //Click do botão para data final
        buttonLastDate = view.findViewById(R.id.last_date_filter_btn);
        buttonLastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Booleano para verificar se é a primeira data selecionada ou última
                startOrLastDate = false;
                //Vai instanciar o dialogo do calendário baseado na classe de fragment criada
                DialogFragment datePicker = new DatePickerFragment();
                //Mostra o calendário
                datePicker.show(getSupportFragmentManager(),"data last");

            }
        });

        ////Click do botão OK
        view.findViewById(R.id.buttonOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                /** A mágica acontece após esse botão ser precionado **/
                //Verifica se as variáveis são null
                if(dtStart != null) {
                    cal.setTime(dtStart);
                }
                OutputDAO op = new OutputDAO(getApplicationContext());

                //Array com todos os campos do banco
                listOutput = op.list();

                //Array do resultado da consulta
                List<Output> result = new ArrayList<>();


                //Faz a varredura de todas as datas a partir da inicial até a final
                for (Date dt = cal.getTime(); dt.compareTo (dtLast) <= 0; ) {

                    //Avança em um dia no calendário
                    cal.add (Calendar.DATE, +1);

                    /** Aqui é a função que filtra as datas **/
                    for(int i = 0; i<listOutput.size();i++){

                        //Verifica os valores de cada data
                        if(listOutput.get(i).getDateOutput().equals(df.format(dt))){
                            //Caso o valor da data seja igual ao valor da data do período selecionado adiciona no array o objeto
                            result.add(listOutput.get(i));
                        }

                    }

                    //Atribui a nova data a ser tratada e continua o loop
                    dt = cal.getTime();
                    /** Aqui que vai ser colocada a função que filtra as datas **/

                }

                //Atualiza o recyclerview
                adapterListagemDespesas = new AdapterListagemDespesas(result);
                recyclerView.setAdapter(adapterListagemDespesas);
                adapterListagemDespesas.notifyDataSetChanged();

                //Mostra uma notificação caso não haja dados salvos no array
                if(result.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Não há dados para o período selecionado",Toast.LENGTH_SHORT).show();

                }

            }
        });

        ////Click do botão para cancela
        view.findViewById(R.id.buttonCancelDialogFilterDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

}
