package com.examples.animalsappmvvm.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.examples.animalsappmvvm.R;
import com.examples.animalsappmvvm.databinding.ItemAnimalBinding;
import com.examples.animalsappmvvm.model.AnimalModel;
import com.examples.animalsappmvvm.view.fragments.ListFragmentDirections;
import com.examples.animalsappmvvm.view.listeners.AnimalClickListener;

import java.util.ArrayList;
import java.util.List;

public class AnimalListAdapter extends RecyclerView.Adapter<AnimalListAdapter.ViewHolder> implements AnimalClickListener {

    private ArrayList<AnimalModel> animalList = new ArrayList<>();

    public void updateAnimalList(List<AnimalModel> animalList){
        this.animalList.clear();
        this.animalList.addAll(animalList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAnimalBinding view = DataBindingUtil.inflate(inflater, R.layout.item_animal, parent, false);
        return new AnimalListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setAnimal(animalList.get(position));
        holder.itemView.setListener(this);
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    @Override
    public void onClick(View v) {
        for(AnimalModel animal : animalList){
            if(v.getTag().equals(animal.getName())){
                NavDirections action = ListFragmentDirections.actionGoToDetails(animal);
                Navigation.findNavController(v).navigate(action);
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ItemAnimalBinding itemView;

        public ViewHolder(@NonNull ItemAnimalBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
