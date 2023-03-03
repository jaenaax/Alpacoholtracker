package de.hdmstuttgart.myapplication.ui.profile;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import de.hdmstuttgart.myapplication.R;
import de.hdmstuttgart.myapplication.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    public ProfileViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ProfileViewModel(getActivity().getApplication(), getActivity().getApplicationContext());


        binding = FragmentProfileBinding.inflate(inflater, container, false);

        //limit the input of the weight to 3 digits
        binding.inputWeight.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

        // Set the values of the input fields to the values of the person
        binding.inputWeight.setText(String.valueOf(viewModel.getPersonWeight()));
        binding.inputGender.check(viewModel.getPersonGender());

        // On click listener for the save button
        binding.btnSubmitProfile.setOnClickListener(view -> {
            int inputWeight = Integer.parseInt(binding.inputWeight.getText().toString());
            int inputGender = binding.inputGender.getCheckedRadioButtonId();
            if (inputWeight==0 || inputGender==0) { // If no input is given
                Toast.makeText(getContext(), "Insert your data", Toast.LENGTH_SHORT).show();
            } else if (inputWeight <=0 || inputWeight > 601) { // Check if weight is valid
                Toast.makeText(getContext(), "Insert valid weight", Toast.LENGTH_SHORT).show();
            } else { // If input is valid
                viewModel.savePerson(inputWeight, inputGender);
                NavHostFragment.findNavController(ProfileFragment.this)
                        .navigate(R.id.action_profileFragment_to_navigation_home);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
