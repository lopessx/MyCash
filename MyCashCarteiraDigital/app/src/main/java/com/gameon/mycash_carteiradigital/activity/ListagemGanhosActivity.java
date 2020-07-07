package com.gameon.mycash_carteiradigital.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    }

    public void loadList(){
        InputDAO inputDAO = new InputDAO(getApplicationContext());

        //Recebendo a lista vinda do InputDao
        listInput = inputDAO.list();

        //Passando lista para o AdapterListagemGanhos para a listagem
        AdapterListagemGanhos adapterListagemGanhos = new AdapterListagemGanhos(listInput);

        //Configurabdo o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapterListagemGanhos);

    }

    @Override
    protected void onStart() {
        loadList();
        super.onStart();
    }

}
