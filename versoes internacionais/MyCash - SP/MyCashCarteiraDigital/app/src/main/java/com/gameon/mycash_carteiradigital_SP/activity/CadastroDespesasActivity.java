package com.gameon.mycash_carteiradigital_SP.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.gameon.mycash_carteiradigital_SP.R;


import com.gameon.mycash_carteiradigital_SP.helper.OutputDAO;

import com.gameon.mycash_carteiradigital_SP.model.Output;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroDespesasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinnerCategoryOutput;
    private TextInputEditText outputValue;
    private TextInputEditText outputDescription;
    private Button buttonSalve;
    private Output idCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cadastro_despesas);
        Toolbar toobar = findViewById(R.id.toolbar);
        setSupportActionBar(toobar);

        //recuperando IDs dos componentes View
        outputValue = findViewById(R.id.inputValueOutput);
        outputDescription = findViewById(R.id.inputDescriptionOutput);
        buttonSalve = findViewById(R.id.buttonSalvarGasto);
        spinnerCategoryOutput = findViewById(R.id.spinnerCategoryOutput);
        //click do spinner
        spinnerCategoryOutput.setOnItemSelectedListener(this);

        //Mudar título da toobar
        getSupportActionBar().setTitle(getString(R.string.text_toobar_tela_add_despesas_espanhol));
        //Botão de voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configurando Spinner das categorias de ganhos
        String[] categorys = getResources().getStringArray(R.array.expenses_category);
        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, categorys);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoryOutput.setAdapter(arrayAdapter);

        //Click do botãp pra salvar os gastos
        buttonSalve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSpences();
            }
        });


    }

    public void saveSpences(){

        OutputDAO outputDAO = new OutputDAO(getApplicationContext());

        //Valor digitado pelo usuário
        String value1 = outputValue.getText().toString();

        String description = outputDescription.getText().toString();

        //Configurar formato da data e setar seu valor
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
        Date d = new Date();
        String date = simpleDateFormat.format(d);

        //Esse ID é selecionado no Spinner. assim sabemos qual o tipo de gasto o usuário escolhei
        Long idCtg = idCategory.getIdCategory();

        //Recebe a função de validação dos campos
        boolean validateFields = validatedFields(value1, description);

        if (validateFields) {
            Output output = new Output();

            //Tratar campo nulo
            if (value1.isEmpty() || value1.contentEquals(" ") || value1.contentEquals(".")){
                //Mensagem para sinalizar que os dados foram salvos
                Toast.makeText(getApplicationContext(), "Valor no válido",
                        Toast.LENGTH_SHORT).show();
                value1 = "0";
                validateFields = false;
            }

            //Tratar número igual a zero
            if (Double.parseDouble(value1) == 0){
                //Mensagem para sinalizar que os dados foram salvos
                Toast.makeText(getApplicationContext(), "Valor no válido",
                        Toast.LENGTH_SHORT).show();
                validateFields = false;

            }

            //Tratar números negativos
            Double valueFinal = Double.parseDouble(value1);
            if (valueFinal < 0){
                valueFinal = valueFinal * -1;
            }

            output.setValueOutput(valueFinal);
            output.setDescriptionOutput(description);
            output.setDateOutput(date);
            output.setIdCategory(idCtg);
            if(validateFields) {
                //salva no banco de dados
                outputDAO.save(output);

                //Mensagem para sinalizar que os dados foram salvos
                Toast.makeText(getApplicationContext(), getString(R.string.toast_salvar),
                        Toast.LENGTH_SHORT).show();

                //Reiniciar a activity
                finish();
                startActivity(new Intent(getApplicationContext(), CadastroDespesasActivity.class));
            }
        } else {
            //Mensagem de aviso casos os campos não tenham sido validados
            Toast.makeText(getApplicationContext(), "Llena todos los campos",
                    Toast.LENGTH_SHORT).show();
        }


    }

    //Métodos de click para o Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Recuperar o ID do item selecionado no Spinner
        if (parent.getId() == R.id.spinnerCategoryOutput) {
            Long iDposition = parent.getItemIdAtPosition(position);
            String ctg = String.valueOf(iDposition);

            //Intância do objeto que vai receber esse id
            idCategory = new Output();

            //Laço para setar o ID no objeto de acordo com o tipo de gasto escolhido no Spinner
            switch (ctg) {
                case "0":
                    long food = 4;
                    idCategory.setIdCategory(food);

                    break;
                case "1":
                    long rent = 5;
                    idCategory.setIdCategory(rent);

                    break;
                case "2":
                    long water = 6;
                    idCategory.setIdCategory(water);

                    break;
                case "3":
                    long energy = 7;
                    idCategory.setIdCategory(energy);

                    break;
                case "4":
                    long creditcard = 8;
                    idCategory.setIdCategory(creditcard);

                    break;
                case "5":
                    long fuel = 9;
                    idCategory.setIdCategory(fuel);

                    break;
                case "6":
                    long fun = 10;
                    idCategory.setIdCategory(fun);

                    break;
                case "7":
                    long other = 11;
                    idCategory.setIdCategory(other);

                    break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Função para validar os campos que precisam ser preenchidos
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
