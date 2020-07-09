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

public class AdapterListagemGanhos extends RecyclerView.Adapter<AdapterListagemGanhos.MyInputViewlHolder> implements Filterable {

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
        holder.textValue.setText(input.getValueInput().toString());
        holder.textType.setText(input.getTypeInput());
    }

    @Override
    public int getItemCount() {
        return inputList.size();
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
            List<Input> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredList.addAll(listFilter);
            }else {
                for (Input input : listFilter){

                    String inputType = input.getTypeInput();
                    String inputDescription = input.getDescriptionInput();
                    //Pesuisa pelo tipo
                    if (inputType.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(input);
                    }
                    //Pesuisa pela descrição
                    if (inputDescription.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(input);
                    }
                }
            }
            FilterResults  filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            inputList.clear();
            inputList.addAll((Collection<? extends Input>) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyInputViewlHolder extends RecyclerView.ViewHolder {

        TextView textDate;
        TextView textDescription;
        TextView textValue;
        TextView textType;

        public MyInputViewlHolder(@NonNull View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.textDateInput);
            textDescription = itemView.findViewById(R.id.textDescriptionInput);
            textValue = itemView.findViewById(R.id.textValueInput);
            textType = itemView.findViewById(R.id.textTypeInput);
        }
    }

}
