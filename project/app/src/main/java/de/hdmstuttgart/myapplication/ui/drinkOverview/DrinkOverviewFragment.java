package de.hdmstuttgart.myapplication.ui.drinkOverview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import de.hdmstuttgart.myapplication.R;
import de.hdmstuttgart.myapplication.databinding.FragmentDrinkOverviewBinding;
import de.hdmstuttgart.myapplication.model.Drink;

public class DrinkOverviewFragment extends Fragment {
    private FragmentDrinkOverviewBinding binding;
    private DrinkOverviewViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDrinkOverviewBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set overview for the selected drink
        viewModel = new ViewModelProvider(this).get(DrinkOverviewViewModel.class);
        Drink selectedDrink = viewModel.getSelectedDrink(); //get selected drink
        try {
            int i = Integer.parseInt(selectedDrink.getImage());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), i);
            binding.imageDrinkOverview.setImageBitmap(bitmap); //set the image
        } catch (NumberFormatException nfe) {
            binding.imageDrinkOverview.setImageURI(Uri.parse(selectedDrink.getImage())); //set the image
        }

        binding.tvName.setText(selectedDrink.getName()); //set the title
        binding.inputAmount.setText(String.valueOf(selectedDrink.getAmount())); //set the amount
        binding.inputVolume.setText(String.valueOf(selectedDrink.getVolume())); //set the volume

        // limit the input fields to 4 and 5 characters
        binding.inputAmount.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        binding.inputVolume.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});


        binding.btnSubmitDrinkOverview.setOnClickListener(view1 -> { // set the onClickListener for the submit button
            //get the values from the input fields
            int amount = Integer.parseInt(binding.inputAmount.getText().toString());
            double volume = Double.parseDouble(binding.inputVolume.getText().toString());

            if (amount == 0 || volume == 0) { //check if the input fields are empty
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (amount < 0 || volume < 0) { //check if the input fields are negative
                Toast.makeText(getContext(), "Please fill in positive values", Toast.LENGTH_SHORT).show();
            } else if (volume > 100) { //check if the volume is bigger than 100
                Toast.makeText(getContext(), "Please fill in a volume below 100%", Toast.LENGTH_SHORT).show();
            } else { //if everything is fine, save the drink
                //set the values to the selected drink
                selectedDrink.setAmount(amount);
                selectedDrink.setVolume(volume);
                //save the drink
                viewModel.insert(selectedDrink.convertToDrinkConsumed());
                //navigate to home fragment
                NavHostFragment.findNavController(DrinkOverviewFragment.this)
                        .navigate(R.id.action_drinkOverviewFragment_to_navigation_home);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
