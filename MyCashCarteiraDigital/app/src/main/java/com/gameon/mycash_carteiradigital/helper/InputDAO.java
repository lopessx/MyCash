package com.gameon.mycash_carteiradigital.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gameon.mycash_carteiradigital.model.Input;

import java.util.ArrayList;
import java.util.List;

public class InputDAO implements InputDAOInterface {

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public InputDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean save(Input input) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("value_input", input.getValueInput());
        contentValues.put("description_input", input.getDescriptionInput());
        contentValues.put("date_input", input.getDateInput());
        contentValues.put("id_cat", input.getIdCategory());

        try{
            write.insert(DbHelper.TABLE_INPUT, null, contentValues);
            Log.i("saveInput", "Sucesso ao salvar dados de entrada (ganhos)!");
        }catch (Exception e){
            Log.i("saveInput", "Erro ao salvar dados de entrada (ganhos) : " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Input input) {
        return false;
    }

    @Override
    public boolean delete(Input input) {

        try {
            String[] args = {input.getIdInput().toString()};
            write.delete(DbHelper.TABLE_INPUT, "id_input=?", args);
            Log.i("deleteItem", "Sucesso  ao deletar item");
        }catch (Exception e){
            Log.i("deleteItem", "Erro  ao deletar item :" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Input> list() {
        List<Input> inputList = new ArrayList<>();

        String sqlListInput = " SELECT * FROM "+ DbHelper.TABLE_INPUT +" INNER JOIN "
                                + DbHelper.TABLE_CATEGORY + " ON input.id_cat = category.id_cat ; ";
        Cursor cursor = read.rawQuery(sqlListInput, null);

        while (cursor.moveToNext()){
            Input input = new Input();

            Long id = cursor.getLong(cursor.getColumnIndex("id_input"));
            String date = cursor.getString(cursor.getColumnIndex("date_input"));
            Double value = cursor.getDouble(cursor.getColumnIndex("value_input"));
            String description = cursor.getString(cursor.getColumnIndex("description_input"));
            Long idCat = cursor.getLong(cursor.getColumnIndex("id_cat"));
            String typeInput = cursor.getString(cursor.getColumnIndex("name_cat"));

            input.setIdInput(id);
            input.setDateInput(date);
            input.setValueInput(value);
            input.setDescriptionInput(description);
            input.setIdCategory(idCat);
            input.setTypeInput(typeInput);

            inputList.add(input);
        }

        return inputList;
    }

    //Dados usados pelo gráfico
    public List<Input> typeValue (){
        List<Input> inputList = new ArrayList<>();

        String sqlListInput = " SELECT * FROM "+ DbHelper.TABLE_INPUT +" INNER JOIN "
                + DbHelper.TABLE_CATEGORY + " ON input.id_cat = category.id_cat ; ";
        Cursor cursor = read.rawQuery(sqlListInput, null);

        while (cursor.moveToNext()){
            Input retorno = new Input();

            Double value = cursor.getDouble(cursor.getColumnIndex("value_input"));
            String typeInput = cursor.getString(cursor.getColumnIndex("name_cat"));

            retorno.setValueInput(value);
            retorno.setTypeInput(typeInput);

            inputList.add(retorno);
        }

        return inputList;
    }

    public double totalEarning(){
        double ganhos=0;


        String sqlInput = " SELECT * FROM " + DbHelper.TABLE_INPUT;

        Cursor cursor = read.rawQuery(sqlInput,null);

        while(cursor.moveToNext()){
            //Soma todos os gastos da tabela do db
            ganhos+=cursor.getDouble(cursor.getColumnIndex("value_input"));

        }


        return ganhos;
    }

}
