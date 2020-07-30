package com.gameon.mycash_carteiradigital.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gameon.mycash_carteiradigital.R;
import com.gameon.mycash_carteiradigital.helper.AdapterListagemGanhos;
import com.gameon.mycash_carteiradigital.helper.InputDAO;
import com.gameon.mycash_carteiradigital.helper.RecyclerItemClickListener;
import com.gameon.mycash_carteiradigital.model.Input;

import java.util.ArrayList;
import java.util.List;

public class ListagemGanhosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Input> listInput = new ArrayList<>();
    private Input inputSelected = new Input();
    AdapterListagemGanhos adapterListagemGanhos;
    private static final String PREFERENCE_1 = "dialog_ON_OFF_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listagem_ganhos);

        //Título
        getSupportActionBar().setTitle("Listagem de ganhos");
        //Botão de voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerListingInput);

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
                                inputSelected = listInput.get(position);

                                //Diálogo para confirmar exclusão
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListagemGanhosActivity.this);
                                alertDialog.setTitle("Confirmar exclusão ");
                                alertDialog.setMessage("Deseja escluir este item?");

                                //Botão de positivo
                                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                       InputDAO inputDAO = new InputDAO(getApplicationContext());

                                       //Condição para excluir
                                        if (inputDAO.delete(inputSelected)){
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
        SharedPreferences preferences = getSharedPreferences(PREFERENCE_1, 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("dialogON_1", true);

        if (preferences.contains("dialogON_1") && preferences.getBoolean("dialogON_1", true) == true){
            showDialog();
        }

    }

    public void loadList(){
        InputDAO inputDAO = new InputDAO(getApplicationContext());

        //Recebendo a lista vinda do InputDao
        listInput = inputDAO.list();

        //Passando lista para o AdapterListagemGanhos para a listagem
        adapterListagemGanhos = new AdapterListagemGanhos(listInput);

        //Configurabdo o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapterListagemGanhos);

    }

    //Configurar menu para pesquisa de itens
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Tudo isso ta no Adapter
                adapterListagemGanhos.getFilter().filter(newText);
                return false;
            }
        });

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
    //Função de configuração do Dialog
    //Dialog de innstrução para ensinar o usuário a excluir itens
    public void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ListagemGanhosActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ListagemGanhosActivity.this)
                .inflate(R.layout.layout_dialog, (ConstraintLayout) findViewById(R.id.layoutDialogContainer));
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonDialogYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Salvar a escolha do usuário, se ele quer desativar o Dialog/ instrução de como excluir um item
                SharedPreferences preferences = getSharedPreferences(PREFERENCE_1, 0);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putBoolean("dialogON_1", false);
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

}
