package com.examples.animalsappmvvm.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examples.animalsappmvvm.databinding.FragmentListBinding;
import com.examples.animalsappmvvm.view.adapters.AnimalListAdapter;
import com.examples.animalsappmvvm.viewmodel.ListViewModel;

public class ListFragment extends Fragment {

    private ListViewModel viewModel;
    private AnimalListAdapter listAdapter = new AnimalListAdapter();
    private FragmentListBinding binding;

    public ListFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.animals.observe(getViewLifecycleOwner(), list -> {
            if(list != null){
                binding.recyclerView.setVisibility(View.VISIBLE);
                listAdapter.updateAnimalList(list);
            }
        });
        viewModel.loading.observe(getViewLifecycleOwner(), loading -> {
            binding.loadingProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
            if(loading){
                binding.errorMaterialTextView.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.GONE);
            }
        });
        viewModel.loadError.observe(getViewLifecycleOwner(), error -> binding.errorMaterialTextView.setVisibility(error ? View.VISIBLE : View.GONE));

        viewModel.refresh();

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerView.setAdapter(listAdapter);

        binding.animalsRefreshLayout.setOnRefreshListener(() -> {
            binding.recyclerView.setVisibility(View.GONE);
            binding.errorMaterialTextView.setVisibility(View.GONE);
            binding.loadingProgressBar.setVisibility(View.VISIBLE);
            viewModel.hardRefresh();
            binding.animalsRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}