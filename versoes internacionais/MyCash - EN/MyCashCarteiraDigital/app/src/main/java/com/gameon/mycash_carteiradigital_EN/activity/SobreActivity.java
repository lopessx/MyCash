package com.gameon.mycash_carteiradigital_EN.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.gameon.mycash_carteiradigital_EN.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class SobreActivity extends AppCompatActivity {

    /** Essa Activity faz uso da Biblioteca About Pages **/
    /** Encontrada em https://github.com/medyo/android-about-page **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sobre);

        getSupportActionBar().setTitle(getString(R.string.text_toobar_sobre_nos_espanhol));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Descrição
        String descriition = getString(R.string.descricao_tela_sobre_espanhol);

        //Versao do APP
        Element versionElement = new Element();
        versionElement.setTitle("1.0");

        View aboutPage = new AboutPage(SobreActivity.this)

                .setDescription(descriition)

                .addGroup("Anthonny K. N.")
                .addGitHub("kyllaweya2000", "GitHub")
                .addInstagram("kyllaweya", "Instagram")

                .addGroup("Emanuel Lopes")
                .addGitHub("lopessx", "GitHub")
                .addInstagram("emanuellopesss", "Instagram")

                .addGroup("Josivânio Marinho")
                .addGitHub("JosivanioMarinho", "GitHub")
                .addInstagram("josivaniomarinho", "Instagram")

                .addGroup("Miguel Barbosa")
                .addGitHub("miguel8000", "GitHub")
                .addEmail("miguel.neto8000@gmail.com", "E-mail")

                .addGroup("Vitor Falcão")
                .addGitHub("VictorGameOn", "GitHub")
                .addInstagram("victor_falcao33", "Instagram")

                .addItem(versionElement)

                .create();

        setContentView(aboutPage);

    }
}
