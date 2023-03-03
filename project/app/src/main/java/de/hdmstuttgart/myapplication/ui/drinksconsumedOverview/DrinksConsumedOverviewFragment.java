package de.hdmstuttgart.myapplication.ui.drinksconsumedOverview;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdmstuttgart.myapplication.R;
import de.hdmstuttgart.myapplication.databinding.FragmentDrinksconsumedOverviewBinding;
import de.hdmstuttgart.myapplication.ui.DrinkConsumedListAdapter;

public class DrinksConsumedOverviewFragment extends Fragment {
    private FragmentDrinksconsumedOverviewBinding binding;
    private DrinksConsumedOverviewViewModel viewModel;
    private DrinkConsumedListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DrinksConsumedOverviewViewModel.class);

        binding = FragmentDrinksconsumedOverviewBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.rvDrinkDrunkOverview;

        // Initialize a layout manager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        viewModel.getAllDrinks().observe(getViewLifecycleOwner(), drinks -> { // Observe the list of drinks
            if(drinks==null){
                return;
            }
            adapter = new DrinkConsumedListAdapter(drinks, viewModel);
            recyclerView.setAdapter(adapter);
        });
        // Set the adapter on the RecyclerView
        recyclerView.setAdapter(adapter);

        binding.btnDeleteAll.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view1.getContext());
            builder.setTitle("Delete all Drinks")
                    .setMessage("Are you sure you want to delete all drinks?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        viewModel.deleteAll();
                        NavHostFragment.findNavController(DrinksConsumedOverviewFragment.this)
                                .navigate(R.id.action_drinksDrunkOverviewFragment_to_navigation_home);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
