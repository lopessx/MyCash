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

import com.gameon.mycash_carteiradigital.model.Output;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterListagemDespesas extends RecyclerView.Adapter<AdapterListagemDespesas.MyOutputViewlHolder> implements Filterable {

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
        Output output = outputList.get(position);;
        holder.textDate.setText(output.getDateOutput());
        holder.textDescription.setText(output.getDescriptionOutput());
        holder.textValue.setText(output.getValueOutput().toString());
        holder.textType.setText(output.getTypeOutput());
    }

    @Override
    public int getItemCount() {
        return outputList.size();
    }

    //Aplicar os filtros da pesquisa
    @Override
    public Filter getFilter() {
        return filter;
    }

    //Configurações do filtro depesquisa
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Output> filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filteredList.addAll(listFilter);
            } else {
                for (Output output : listFilter) {

                    String outputType = output.getTypeOutput();
                    String outputDescription = output.getDescriptionOutput();
                    //Pesuisa pelo tipo
                    if (outputType.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(output);
                    }
                    //Pesuisa pela descrição
                    if (outputDescription.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(output);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            outputList.clear();
            outputList.addAll((Collection<? extends Output>) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyOutputViewlHolder extends RecyclerView.ViewHolder {

        TextView textDate;
        TextView textDescription;
        TextView textValue;
        TextView textType;

        public MyOutputViewlHolder(@NonNull View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.textDateOutput);
            textDescription = itemView.findViewById(R.id.textDescriptionOutput);
            textValue = itemView.findViewById(R.id.textValueOutput);
            textType = itemView.findViewById(R.id.textTypeOutput);
        }
    }
}
