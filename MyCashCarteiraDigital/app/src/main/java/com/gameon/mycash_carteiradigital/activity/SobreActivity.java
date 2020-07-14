package com.gameon.mycash_carteiradigital.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.gameon.mycash_carteiradigital.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class SobreActivity extends AppCompatActivity {

    /** Essa Activity faz uso da Biblioteca About Pages **/
    /** Encontrada em https://github.com/medyo/android-about-page **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sobre);

        getSupportActionBar().setTitle("Sobre nós");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String descriition = "Desenvolvedores do App MyCash podem ser encontrados da seguintes maneiras";

        Element versionElement = new Element();
        versionElement.setTitle("1.0");

        View aboutPage = new AboutPage(SobreActivity.this)

                .setDescription(descriition)

                .addGroup("Anthonny K. N.")
                .addGitHub("", "GitHub")
                .addInstagram("", "Instagram")

                .addGroup("José Emanuel")
                .addGitHub("lopessx", "GitHub")
                .addInstagram("", "Instagram")

                .addGroup("Josivânio Marinho")
                .addGitHub("JosivanioMarinho", "GitHub")
                .addInstagram("josivaniomarinho",
                        "Instagram")

                .addGroup("Miguel Barbosa")
                .addGitHub("", "GitHub")
                .addInstagram("", "Instagram")

                .addGroup("Vitor Falcão")
                .addGitHub("VictorGameOn", "GitHub")
                .addInstagram("", "Instagram")

                .addItem(versionElement)

                .create();

        setContentView(aboutPage);

    }
}
