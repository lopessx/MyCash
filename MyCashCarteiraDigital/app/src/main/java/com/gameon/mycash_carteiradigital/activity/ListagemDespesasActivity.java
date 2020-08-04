package com.gameon.mycash_carteiradigital.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gameon.mycash_carteiradigital.R;
import com.gameon.mycash_carteiradigital.helper.AdapterListagemDespesas;

import com.gameon.mycash_carteiradigital.helper.DatePickerFragment;
import com.gameon.mycash_carteiradigital.helper.AdapterListagemGanhos;
import com.gameon.mycash_carteiradigital.helper.OutputDAO;
import com.gameon.mycash_carteiradigital.helper.RecyclerItemClickListener;

import com.gameon.mycash_carteiradigital.model.Input;
import com.gameon.mycash_carteiradigital.model.Output;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListagemDespesasActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private RecyclerView recyclerView;
    private List<Output> listOutput = new ArrayList<>();
    private Output outputSelected = new Output();
    AdapterListagemDespesas adapterListagemDespesas;
    private MaterialSearchView searchView;
    private static final String PREFERENCE_2 = "dialog_ON_OFF_2";

    private boolean startOrLastDate = true;

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

        //Botões e eventos
        Button btnStart = (Button) findViewById(R.id.start_date_spendings_btn);
        btnStart.setOnClickListener(new View.OnClickListener() {
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

        Button btnLast = (Button) findViewById(R.id.last_date_spendings_btn);
        btnLast.setOnClickListener(new View.OnClickListener() {
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


        return super.onOptionsItemSelected(item);
    }

    //Carrega a listagem sempre que a activity é iniciada
    @Override
    protected void onStart() {
        loadList();
        super.onStart();
    }

    /** Dialog personalizado **/
    //Dialog de innstrução para ensinar o usuário a excluir itens
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

    //Função para pesquisa
    public void searchItem(String text){
        //Log.d("pesquisa", nome);
        List<Output> outPutList = new ArrayList<>();

        for ( Output output : listOutput ){

            String typeOutput = output.getTypeOutput().toLowerCase();
            if ( typeOutput.contains( text ) ){
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
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        //Salva a data selecionada no calendário em uma string
        String date = DateFormat.getDateInstance().format(cal.getTime());
        Button startDate = findViewById(R.id.start_date_spendings_btn);
        Button lastDate = findViewById(R.id.last_date_spendings_btn);

        //Dependendo do botão o texto dele muda pra data selecionada
        if(startOrLastDate){
            startDate.setText(date);
        }else{
            lastDate.setText(date);
        }

    }


}
