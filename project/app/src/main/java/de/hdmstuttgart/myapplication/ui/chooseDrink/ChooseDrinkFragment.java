package de.hdmstuttgart.myapplication.ui.chooseDrink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import de.hdmstuttgart.myapplication.R;
import de.hdmstuttgart.myapplication.databinding.FragmentChooseDrinkBinding;
import de.hdmstuttgart.myapplication.ui.DrinkListAdapter;

public class ChooseDrinkFragment extends Fragment {

    private FragmentChooseDrinkBinding binding;
    private ChooseDrinkViewModel viewModel;
    private DrinkListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ChooseDrinkViewModel.class);

        binding = FragmentChooseDrinkBinding.inflate(inflater, container, false); // Inflate the layout for this fragment

        RecyclerView recyclerView = binding.rvChooseDrink;

        // Initialize a layout manager for the RecyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2); // 2 columns
        recyclerView.setLayoutManager(layoutManager); // Set the layout manager on the RecyclerView

        // Initialize the adapter and attach it to the RecyclerView
        adapter = new DrinkListAdapter(new ArrayList<>(), (drink, position) -> {
            viewModel.setSelectedDrink(drink); // Set the selected drink
            NavHostFragment.findNavController(ChooseDrinkFragment.this)
                    .navigate(R.id.action_chooseDrinkFragment_to_drinkOverviewFragment); // Navigate to DrinkOverviewFragment
        }, viewModel);

        viewModel.getAllDrinks().observe(getViewLifecycleOwner(), drinks -> { // Observe the list of drinks
            if (drinks == null) {
                return;
            }
            adapter = new DrinkListAdapter(drinks, (drink, position) -> { // Initialize the adapter
                viewModel.setSelectedDrink(drink); // Set the selected drink
                NavHostFragment.findNavController(ChooseDrinkFragment.this)
                        .navigate(R.id.action_chooseDrinkFragment_to_drinkOverviewFragment); // Navigate to DrinkOverviewFragment
            }, viewModel);
            recyclerView.setAdapter(adapter);
        });
        // Set the adapter on the RecyclerView
        recyclerView.setAdapter(adapter);


        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Click listener on fab to navigate to CreateNewDrinkFragment
        binding.fabCreateNewDrink.setOnClickListener(view1 -> NavHostFragment.findNavController(ChooseDrinkFragment.this)
                .navigate(R.id.action_chooseDrinkFragment_to_createNewDrinkFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}