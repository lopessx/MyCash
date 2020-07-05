package com.gameon.mycash_carteiradigital.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gameon.mycash_carteiradigital.model.Input;

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
        return false;
    }

    @Override
    public List<Input> list() {
        return null;
    }
}
