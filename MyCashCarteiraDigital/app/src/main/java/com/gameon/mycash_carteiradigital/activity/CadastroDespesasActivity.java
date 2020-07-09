package com.gameon.mycash_carteiradigital.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.gameon.mycash_carteiradigital.helper.OutputDAO;
import com.gameon.mycash_carteiradigital.model.Input;
import com.gameon.mycash_carteiradigital.model.Output;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroDespesasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinnerCategoryOutput;
    private TextInputEditText outputValue;
    private TextInputEditText outputDescription;
    private Input idCategory;

    private Button buttonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro_despesas);

        //recuperando IDs dos componentes View
        outputValue = findViewById(R.id.inputValueOutput);
        outputDescription = findViewById(R.id.inputDescriptionOutput);
        spinnerCategoryOutput = findViewById(R.id.spinnerCategoryOutput);
        //click do spinner
        spinnerCategoryOutput.setOnItemSelectedListener(this);

        //Mudar título da toobar
        getSupportActionBar().setTitle("Saída dos ganhos");
        //Botão de voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configurando DropDown das categorias de ganhos
        String[] categorys = getResources().getStringArray(R.array.expenses_category);
        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, categorys);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoryOutput.setAdapter(arrayAdapter);

        //botão para fins de teste
        buttonText = findViewById(R.id.buttontexte2);
        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroDespesasActivity.this, ListagemGanhosActivity.class);
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

    //Click do botão para salvas os dados
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        OutputDAO outputDAO = new OutputDAO(getApplicationContext());

        int id = item.getItemId();

        if (id == R.id.menuFinish) {
            String value = outputValue.getText().toString();
            String description = outputDescription.getText().toString();

            //Configurar formato da data e setar seu valor
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
            Date d = new Date();
            String date = simpleDateFormat.format(d);

            Long idCtg = idCategory.getIdCategory();

            //Recebe a função de validação dos campos
            boolean validateFields = validatedFields(value, description);

            if (validateFields) {
                Output output = new Output();

                output.setValueOutput(Double.parseDouble(value));
                output.setDescriptionOutput(description);
                output.setDateOutput(date);
                output.setIdCategory(idCtg);

                //salva no banco de dados
                outputDAO.save(output);

                //Mensagem para sinalizar que os dados foram salvos
                Toast.makeText(getApplicationContext(), "Salvo com sucesso!",
                        Toast.LENGTH_SHORT).show();

            } else {
                //Mensagem de aviso casos os campos não tenham sido validados
                Toast.makeText(getApplicationContext(), "Por favor, preencha todos os campos!",
                        Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    //Métodos de click para o Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.spinnerCategoryInput) {
            Long iDposition = parent.getItemIdAtPosition(position);
            String ctg = String.valueOf(iDposition);

            idCategory = new Input();

            switch (ctg) {
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
    public boolean validatedFields(String value, String description) {
        boolean fields = true;

        if (value == null || value.equals("")) {
            fields = false;
        }
        if (description == null || description.equals("")) {
            fields = false;
        }
        return fields;
    }
}
