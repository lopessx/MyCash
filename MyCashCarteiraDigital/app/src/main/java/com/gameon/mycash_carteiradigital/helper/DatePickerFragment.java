package com.gameon.mycash_carteiradigital.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    /** Classe responsável por fazer a instancia e formatação do Dialog do calendário **/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //É pego o calendário com data atual já salvo no android
        Calendar cal = Calendar.getInstance();

        //Salva os dados em atributos
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //Retorna uma instancia do dialogo formatada de acordo com a lingua e data do celular
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year,month,day);
    }
}
