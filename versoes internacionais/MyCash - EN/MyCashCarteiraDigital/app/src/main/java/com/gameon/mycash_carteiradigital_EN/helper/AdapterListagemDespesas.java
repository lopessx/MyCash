package com.gameon.mycash_carteiradigital_EN.helper;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gameon.mycash_carteiradigital_EN.R;

import com.gameon.mycash_carteiradigital_EN.model.Output;

import java.util.ArrayList;
import java.util.List;


public class AdapterListagemDespesas extends RecyclerView.Adapter<AdapterListagemDespesas.MyOutputViewlHolder>{

    //Lista padrão
    private List<Output> outputList = new ArrayList<>();
    //Lista para o filtro
    private List<Output> listFilter;

    public AdapterListagemDespesas(List<Output> outputList) {
        this.outputList = outputList;
        //Lista de filtro recebe os dados da lista padrão
        this.listFilter = new ArrayList<>(outputList);
    }

    @NonNull
    @Override
    public MyOutputViewlHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_listagem_despesas, parent, false);
        return new MyOutputViewlHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOutputViewlHolder holder, int position) {
        Output output = outputList.get(position);
        holder.textDateOutput.setText(output.getDateOutput());
        holder.textDescriptionOutPut.setText(output.getDescriptionOutput());
        holder.textValueOutput.setText("$ " + String.format("%.2f", output.getValueOutput()));
        String tipo="";
        switch (output.getTypeOutput()){
            case "Alimentação":
                tipo = "Feeding";
                break;
            case "Aluguel":
                tipo = "Rent";
                break;
            case "Água":
                tipo = "Water";
                break;
            case "Energia":
                tipo = "Electricity";
                break;
            case "Cartão de Crédito":
                tipo = "Credit Card";
                break;
            case "Combustível":
                tipo = "Gas";
                break;
            case "Lazer":
                tipo = "Leisure";
                break;
            case "Outras Despesas":
                tipo = "Other";
                break;
        }
        holder.textTypeOutput.setText(tipo);
    }

    @Override
    public int getItemCount() {
        return outputList.size();
    }

    public class MyOutputViewlHolder extends RecyclerView.ViewHolder {

        TextView textDateOutput;
        TextView textDescriptionOutPut;
        TextView textValueOutput;
        TextView textTypeOutput;

        public MyOutputViewlHolder(@NonNull View itemView) {
            super(itemView);

            textDateOutput = itemView.findViewById(R.id.textDateOutput);
            textDescriptionOutPut = itemView.findViewById(R.id.textDescriptionOutput);
            textValueOutput = itemView.findViewById(R.id.textValueOutput);
            textTypeOutput = itemView.findViewById(R.id.textTypeOutput);
        }
    }
}
