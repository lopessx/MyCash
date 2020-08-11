package com.gameon.mycash_carteiradigital.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.gameon.mycash_carteiradigital.model.Category;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static int VERSION = 3;
    private static String NAME_BD = "MYCASH";
    public static String TABLE_CATEGORY = "category";
    public static String TABLE_INPUT = "input";
    public static String TABLE_OUTPUT = "output";

    //String para popular a tabela de categorias
    private String populateCategory = "INSERT INTO "+ TABLE_CATEGORY +" (id_cat,name_cat,type_cat) VALUES (1,'Salário','Entrada de dinheiro'),(2,'Extra','Entrada de dinheiro'),(3,'Outros Ganhos','Entrada de dinheiro'), " +
            "(4,'Alimentação','Saida de dinheiro'),(5,'Aluguel','Saida de dinheiro'),(6,'Água','Saida de dinheiro'),(7,'Energia','Saida de dinheiro'),(8,'Cartão de Crédito','Saida de dinheiro'),(9,'Combustível','Saida de dinheiro'),(10,'Lazer','Saida de dinheiro'),(11,'Outras Despesas','Saida de dinheiro');";


    public DbHelper(@Nullable Context context) {
        super(context, NAME_BD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tableCategorySQL = " CREATE TABLE IF NOT EXISTS "+ TABLE_CATEGORY
                +" (id_cat INTEGER PRIMARY KEY,"
                +" name_cat TEXT NOT NULL,"
                +" type_cat TEXT NOT NULL ); ";

        String tableInputSQL = " CREATE TABLE IF NOT EXISTS "+  TABLE_INPUT
                +" (id_input INTEGER PRIMARY KEY AUTOINCREMENT,"
                +" date_input TEXT NOT NULL,"
                +" value_input FLOAT NOT NULL,"
                +" description_input TEXT NOT NULL,"
                +" id_cat INTEGER NOT NULL,"
                +" FOREIGN KEY (id_cat) REFERENCES "+ TABLE_CATEGORY +" (id_cat) ); ";

        String tableOutputSQL = " CREATE TABLE IF NOT EXISTS "+  TABLE_OUTPUT
                +" (id_output INTEGER PRIMARY KEY AUTOINCREMENT,"
                +" date_output TEXT NOT NULL,"
                +" value_output FLOAT NOT NULL,"
                +" description_output TEXT NOT NULL,"
                +" id_cat INTEGER NOT NULL,"
                +" FOREIGN KEY (id_cat) REFERENCES "+ TABLE_CATEGORY +" (id_cat) ); ";

        try{
            db.execSQL(tableCategorySQL);
            db.execSQL(tableInputSQL);
            db.execSQL(tableOutputSQL);

            db.execSQL(populateCategory);

            Log.i("infotables", "onCreate: Sucesso ao criar tabelas!");
        }catch (Exception e){
            Log.i("infotables", "onCreate: Erro ao criar tabelas! " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //String para excluir os dados na tabela category
        String resetCategory = " DELETE FROM  " + TABLE_CATEGORY ;

        //Vai tentar executar o SQL da String
        try{
            db.execSQL(resetCategory);
            db.execSQL(populateCategory);
            Log.i("infotables", "onUpgrade: Sucesso ao popular tabela category!");
        }catch (Exception e){
            Log.i("infotables", "onUpgrade: Erro ao popular tabela category! " + e.getMessage());
        }
    }

    //Listagem da tabela category retorna uma lista contendo as linahs do DB
    public List<Category> categoryAll(){
        //Instaciação da lista
        List<Category> listcategory = new ArrayList<Category>();
        //Faz a leitura do banco
        SQLiteDatabase db = getReadableDatabase();
        //Comando SQL para selecionar todas as linhas da tabela category
        String sqlcategoryall = "SELECT * FROM category";
        //Ponteiro para percorrer as linhas extraídas do DB
        Cursor cursor = db.rawQuery(sqlcategoryall,null);
        if(cursor.moveToFirst()){
            do{
                //Instaciação do model
                Category ctg = new Category(cursor.getLong(0),cursor.getString(1),cursor.getString(2));
                //Adição do model a lista
                listcategory.add(ctg);
            }while(cursor.moveToNext());
        }
        //Fechamento da conexão
        db.close();
        //Retorna uma lista com o conteúdo da tabela category
        return listcategory;
    }

}
