package com.gameon.mycash_carteiradigital.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.gameon.mycash_carteiradigital.R;
import com.gameon.mycash_carteiradigital.helper.DbHelper;
import com.gameon.mycash_carteiradigital.model.Category;

import java.util.List;

public class CadastroGanhosActivity extends AppCompatActivity {

    private Spinner spinnerCategory;
    private Button buttonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro_ganhos);

        //Teste para verificar se está extraindo dados do DB

        DbHelper db = new DbHelper(this);

        List<Category> ctg = db.categoryAll();
        if(ctg.isEmpty()) {
            Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT).show();
        }


        //Configurar título da toobar
        getSupportActionBar().setTitle("Entrada dos ganhos");

        //Configurando DropDown das categorias de ganhos
        spinnerCategory = findViewById(R.id.spinnerCategoryInput);

        String[] categorys = getResources().getStringArray(R.array.earning_category);
        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, categorys);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);


        //botão para fins de teste
        buttonText = findViewById(R.id.button);
        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroGanhosActivity.this, CadastroDespesasActivity.class);
                startActivity(intent);
            }
        });

    }

    //Botão para salvar, na toobar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_finish, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuFinish){
            //Lógica para salvar os dados após clicar no botão
        }

        return super.onOptionsItemSelected(item);
    }
}
