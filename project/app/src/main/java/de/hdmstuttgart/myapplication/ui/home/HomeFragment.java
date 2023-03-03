package de.hdmstuttgart.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import java.util.List;
import de.hdmstuttgart.myapplication.Calculator;
import de.hdmstuttgart.myapplication.R;
import de.hdmstuttgart.myapplication.databinding.FragmentHomeBinding;
import de.hdmstuttgart.myapplication.model.DrinkConsumed;
import de.hdmstuttgart.myapplication.model.Person;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int weight = viewModel.getPersonWeight(); //get weight from Shared Preferences
        int gender = viewModel.getPersonGender(); //get gender from Shared Preferences

        // set onClickListener for plus button
        binding.btnPlus.setOnClickListener(view1 -> {
            if (weight == 0 || gender == 0) { //check if user has inserted his data in the profile
                Toast.makeText(getContext(), "Please insert your data in the profile", Toast.LENGTH_SHORT).show();
            } else { //if user has inserted his data, navigate to chooseDrinkFragment
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_navigation_homeFragment_to_chooseDrinkFragment);
            }
        });


        //set onClickListener for profile
        binding.btnProfile.setOnClickListener(view2 ->
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_navigation_home_to_profileFragment) //navigate to profileFragment
        );

        //set text for alcohol level and time when sober
        TextView tvAlcoholLevel = binding.tvAlcoholLevel;
        TextView tvTimeWhenSober = binding.tvTimeWhenSober;

        //get drinks consumed from database
        LiveData<List<DrinkConsumed>> drinksConsumed = viewModel.getDrinksConsumed();

        //observe drinks consumed
        drinksConsumed.observe(getViewLifecycleOwner(), drinksConsumedList -> {
            //get weight and gender from Shared Preferences
            Person person = new Person(viewModel.getPersonWeight(), viewModel.getPersonGender());
            //calculate alcohol level
            double alcoholLevel = Calculator.getInstance().calculateAlcoholLevel(drinksConsumedList, person);
            //if alcohol level is 0, reset drinks consumed in database
            if (alcoholLevel <= 0.00) {
                viewModel.resetDrinksConsumed();
                binding.tvDescriptionTimeWhenSober.setVisibility(View.INVISIBLE);
            } else {
                binding.tvDescriptionTimeWhenSober.setVisibility(View.VISIBLE);
            }
            //calculate time when sober
            String timeWhenSober = Calculator.getInstance().calculateTimeWhenSober(alcoholLevel);
            //round to 2 decimal places
            alcoholLevel = Math.round(alcoholLevel * 100.0) / 100.0;

            //change size of button
            Button btnPlus = binding.btnPlus;
            if (alcoholLevel >= 0 && alcoholLevel < 0.7) {
                btnPlus.setWidth(250);
                btnPlus.setHeight(100);
                btnPlus.setTextSize(18);
            } else if (alcoholLevel >= 0.7 && alcoholLevel < 1.3) {
                btnPlus.setWidth(350);
                btnPlus.setHeight(230);
                btnPlus.setTextSize(35);
            } else if (alcoholLevel >= 1.3 && alcoholLevel < 2) {
                btnPlus.setWidth(450);
                btnPlus.setHeight(280);
                btnPlus.setTextSize(40);
            } else {
                btnPlus.setWidth(600);
                btnPlus.setHeight(300);
                btnPlus.setTextSize(60);
            }

            //set onClickListener for drinks consumed overview
            double finalAlcoholLevel = alcoholLevel;
            binding.btnShowDrinksConsumed.setOnClickListener(view3 -> {
                if (finalAlcoholLevel == 0.0) {
                    Toast.makeText(getContext(), "Drink something to see or delete it here", Toast.LENGTH_SHORT).show();
                } else {
                    NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_navigation_home_to_drinksDrunkOverviewFragment); //navigate to drinksConsumedOverviewFragment
                }
            });
            //set text for alcohol level and time when sober
            tvAlcoholLevel.setText(alcoholLevel + " ‰");
            tvTimeWhenSober.setText(timeWhenSober);

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}