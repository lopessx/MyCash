package com.gameon.mycash_carteiradigital.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gameon.mycash_carteiradigital.R;
import com.gameon.mycash_carteiradigital.model.Input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterListagemGanhos extends RecyclerView.Adapter<AdapterListagemGanhos.MyInputViewlHolder> {

    //Lista padrão
    private List<Input> inputList = new ArrayList<>();
    //Lista para o filtro
    private List<Input> listFilter;

    public AdapterListagemGanhos(List<Input> inputList) {
        this.inputList = inputList;
        //Lista de filtro recebe os dados da lista padrão
        this.listFilter = new ArrayList<>(inputList);
    }

    @NonNull
    @Override
    public MyInputViewlHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_listagem_ganhos, parent, false);
        return new MyInputViewlHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyInputViewlHolder holder, int position) {
        Input input = inputList.get(position);;
        holder.textDate.setText(input.getDateInput());
        holder.textDescription.setText(input.getDescriptionInput());
        holder.textValue.setText("R$ " + String.format("%.2f", input.getValueInput()));
        holder.textType.setText(input.getTypeInput());
    }

    @Override
    public int getItemCount() {
        return inputList.size();
    }


    public class MyInputViewlHolder extends RecyclerView.ViewHolder {

        TextView textDate;
        TextView textDescription;
        TextView textValue;
        TextView textType;

        public MyInputViewlHolder(@NonNull View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.textDateInput);
            textDescription = itemView.findViewById(R.id.textInput);
            textValue = itemView.findViewById(R.id.textValueInput);
            textType = itemView.findViewById(R.id.textTypeInput);
        }
    }

}
