package com.gameon.mycash_carteiradigital.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gameon.mycash_carteiradigital.model.Input;
import com.gameon.mycash_carteiradigital.model.Output;

import java.security.cert.CertificateParsingException;
import java.util.ArrayList;
import java.util.List;

public class
OutputDAO implements OutputDAOInterface {

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public OutputDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean save(Output output) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("value_output", output.getValueOutput());
        contentValues.put("description_output", output.getDescriptionOutput());
        contentValues.put("date_output", output.getDateOutput());
        contentValues.put("id_cat", output.getIdCategory());

        try{
            write.insert(DbHelper.TABLE_OUTPUT, null, contentValues);
            Log.i("saveOutput", "Sucesso ao salvar output (gastos)!");
        }catch (Exception e){
            Log.i("saveOutput", "Erro ao salvar output (gastos) : " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Output output) {
        return false;
    }

    @Override
    public boolean delete(Output output) {
        try {
            String[] args = {output.getIdOutput().toString()};
            write.delete(DbHelper.TABLE_OUTPUT, "id_output=?", args);
            Log.i("deleteItem", "Sucesso  ao deletar item");
        }catch (Exception e){
            Log.i("deleteItem", "Erro  ao deletar item :" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Output> list() {
        List<Output> outputList = new ArrayList<>();

        String sqlListInput = " SELECT * FROM "+ DbHelper.TABLE_OUTPUT +" INNER JOIN "
                + DbHelper.TABLE_CATEGORY + " ON output.id_cat = category.id_cat ; ";

        Cursor cursor = read.rawQuery(sqlListInput, null);

        while (cursor.moveToNext()){
            Output output = new Output();

            Long id = cursor.getLong(cursor.getColumnIndex("id_output"));
            String date = cursor.getString(cursor.getColumnIndex("date_output"));
            Double value = cursor.getDouble(cursor.getColumnIndex("value_output"));
            String description = cursor.getString(cursor.getColumnIndex("description_output"));
            Long idCat = cursor.getLong(cursor.getColumnIndex("id_cat"));
            String typeOutput = cursor.getString(cursor.getColumnIndex("name_cat"));

            output.setIdOutput(id);
            output.setDateOutput(date);
            output.setValueOutput(value);
            output.setDescriptionOutput(description);
            output.setIdCategory(idCat);
            output.setTypeOutput(typeOutput);

            outputList.add(output);
        }

        return outputList;
    }
    //Função que calcula o total de gastos
    public double totalSpending(){
        double gastos=0;

        String sqlOutput = " SELECT * FROM " + DbHelper.TABLE_OUTPUT;

        Cursor cursor = read.rawQuery(sqlOutput,null);

        while(cursor.moveToNext()){
        //Soma todos os gastos da tabela do db
            gastos+=cursor.getDouble(cursor.getColumnIndex("value_output"));

        }

        return gastos;
    }

}
