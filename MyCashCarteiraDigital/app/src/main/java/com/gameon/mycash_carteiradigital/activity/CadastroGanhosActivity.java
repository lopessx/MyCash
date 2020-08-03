package com.gameon.mycash_carteiradigital.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.gameon.mycash_carteiradigital.R;

import com.gameon.mycash_carteiradigital.helper.InputDAO;
import com.gameon.mycash_carteiradigital.model.Input;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroGanhosActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerCategoryInput;
    private TextInputEditText inputValue;
    private TextInputEditText inputDescription;
    private Button buttonSave;
    private Input idCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro_ganhos);
        Toolbar toobar = findViewById(R.id.toolbar);
        setSupportActionBar(toobar);

        //recuperando IDs dos componentes View
        inputValue       = findViewById(R.id.inputValueInput);
        inputDescription = findViewById(R.id.inputDescriptionInput);
        buttonSave = findViewById(R.id.buttonSalvarGanho);
        spinnerCategoryInput  = findViewById(R.id.spinnerCategoryInput);
        //click do spinner
        spinnerCategoryInput.setOnItemSelectedListener(this);

        //Mudar título da toobar
        getSupportActionBar().setTitle("Adicionar ganho");
        //Botão de voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configurando DropDown das categorias de ganhos
        String[] categorys = getResources().getStringArray(R.array.earning_category);
        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, categorys);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoryInput.setAdapter(arrayAdapter);

        //Clique do botão para salvar
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salveEarnings();
            }
        });

    }

    //Metodo chamado pelo botão para salvar
    public void salveEarnings(){

        InputDAO inputDAO = new InputDAO(getApplicationContext());

        //Valor digitado pelo usuário
        String value1 = inputValue.getText().toString();

        String description = inputDescription.getText().toString();

        //Configurar formato da data e setar seu valor
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
        Date d = new Date();
        String date = simpleDateFormat.format(d);

        //Esse ID é selecionado no Spinner. assim sabemos qual o tipo de ganho o usuário escolhei
        Long idCtg = idCategory.getIdCategory();

        //Recebe a função de validação dos campos
        boolean validateFields = validatedFields(value1, description);

        if (validateFields){
            Input input = new Input();

            //Tratar números negativos
            Double valueFinal = Double.parseDouble(value1);
            if (valueFinal < 0){
                valueFinal = valueFinal * -1;
            }

            input.setValueInput(valueFinal);
            input.setDescriptionInput(description);
            input.setDateInput(date);
            input.setIdCategory(idCtg);

            //salva no bando de dados
            inputDAO.save(input);

            //Mensagem para sinalizar que os dados foram salvos
            Toast.makeText(getApplicationContext(), "Salvo com sucesso!",
                    Toast.LENGTH_SHORT).show();

            //Reinicia a activity
            finish();
            startActivity(new Intent(getApplicationContext(), CadastroGanhosActivity.class));

        }else{
            //Mensagem de aviso casos os campos não tenham sido validados
            Toast.makeText(getApplicationContext(), "Por favor, preencha todos os campos!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    //Métodos de click para o Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Recuperar o ID do item selecionado no Spinner
        if (parent.getId() == R.id.spinnerCategoryInput){
            Long iDposition = parent.getItemIdAtPosition(position);
            String ctg = String.valueOf(iDposition);

            //Intância do objeto que vai receber esse id
            idCategory = new Input();

            //Laço para setar o ID no objeto de acordo com o tipo de gasto escolhido no Spinner
           switch (ctg){
               case "0":
                   long salary = 1;
                   idCategory.setIdCategory(salary);
                   break;
               case "1":
                   long extra = 2;
                   idCategory.setIdCategory(extra);
                   break;
               case "2":
                   long other = 3;
                   idCategory.setIdCategory(other);
                   break;
           }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Função para validar os campos que precisam ser preechidos
    public boolean validatedFields(String value, String description){
        boolean fields = true;

        if(value == null || value.equals("")){fields = false;}
        if (description == null || description.equals("")){fields = false;}
        return fields;
    }
}
