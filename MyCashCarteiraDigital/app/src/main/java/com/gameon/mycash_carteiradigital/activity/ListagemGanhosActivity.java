package com.gameon.mycash_carteiradigital.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.gameon.mycash_carteiradigital.R;
import com.gameon.mycash_carteiradigital.helper.AdapterListagemGanhos;
import com.gameon.mycash_carteiradigital.helper.InputDAO;
import com.gameon.mycash_carteiradigital.model.Input;

import java.util.ArrayList;
import java.util.List;

public class ListagemGanhosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Input> listInput = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listagem_ganhos);

        //Título
        getSupportActionBar().setTitle("Listagem de ganhos");
        //Botão de voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerListingInput);

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
