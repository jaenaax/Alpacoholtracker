package de.hdmstuttgart.myapplication.ui.createNewDrink;

import static android.app.Activity.RESULT_OK;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import de.hdmstuttgart.myapplication.R;
import de.hdmstuttgart.myapplication.databinding.FragmentCreateNewDrinkBinding;
import de.hdmstuttgart.myapplication.model.Drink;


public class CreateNewDrinkFragment extends Fragment {

    private FragmentCreateNewDrinkBinding binding;
    private final int GALLERY_REQ_CODE = 1000;
    ImageView imageView;
    private CreateNewDrinkViewModel viewModel;
    int REQUESTCODE_PERMISSIONS = 1;
    int WHERE_FROM;
    private Uri CameraImg;
    private Uri GalleryImg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            CameraImg = Uri.parse(getArguments().getString("ImgUri")); // get the image from the camera
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCreateNewDrinkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CreateNewDrinkViewModel.class);

        if (CameraImg != null) {
            binding.ivImageOwnDrink.setImageURI(CameraImg); // set image to the imageView
        }

        //check if the permission to read from the storage is granted
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //permission not granted
            binding.btnAddImageFromGallery.setEnabled(false);
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUESTCODE_PERMISSIONS);
            Toast.makeText(getActivity(), "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
        } else { //permission granted
            Log.w("Permissions: ", "says: " + PackageManager.PERMISSION_GRANTED);
            onRequestPermissionResultStorage(REQUESTCODE_PERMISSIONS, new int[]{0});
        }

        imageView = binding.ivImageOwnDrink;
        Button btnGallery = binding.btnAddImageFromGallery;
        btnGallery.setOnClickListener(view1 -> { //set OnClickListener for the button to open the gallery
            WHERE_FROM = 0;
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_REQ_CODE);
        });

        binding.btnOpenCamera.setOnClickListener(view1 -> { //set OnClickListener for the button to open the camera
            WHERE_FROM = 1;
            NavHostFragment.findNavController(CreateNewDrinkFragment.this)
                    .navigate(R.id.action_createNewDrinkFragment_to_newCameraFragment);
        });

        //limit the input to specific length
        binding.inputNameOwnDrink.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        binding.inputAmountOwnDrink.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        binding.inputVolumeOwnDrink.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});

        //set OnClickListener for the confirm button to navigate to the ChooseDrinkFragment and save the new drink
        binding.btnConfirmNewDrink.setOnClickListener(view1 -> {
            String nameOwnDrink = "";
            int amountOwnDrink = 0;
            double volumeOwnDrink = 0;

            //check if the input fields are empty
            if (binding.inputNameOwnDrink.getText().toString().isEmpty() || binding.inputAmountOwnDrink.getText().toString().isEmpty() ||
                    binding.inputVolumeOwnDrink.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Insert valid data", Toast.LENGTH_SHORT).show();
            } else { //if not empty, get the input data
                nameOwnDrink = binding.inputNameOwnDrink.getText().toString();
                amountOwnDrink = Integer.parseInt(binding.inputAmountOwnDrink.getText().toString());
                volumeOwnDrink = Double.parseDouble(binding.inputVolumeOwnDrink.getText().toString());
            }


            if (amountOwnDrink <= 0 || volumeOwnDrink < 0 || volumeOwnDrink > 100 || nameOwnDrink.isEmpty()) { //check if the input data is valid
                Toast.makeText(getContext(), "Insert valid data", Toast.LENGTH_SHORT).show();
            } else {
                Uri uri;
                if (CameraImg != null) { //if the image is from the camera, save it to the storage
                    uri = CameraImg;
                } else if (GalleryImg != null) { //if the image is from the gallery, save it to the storage
                    uri = GalleryImg;
                } else { //if no image is selected, set the default image
                    uri = Uri.parse("android.resource://de.hdmstuttgart.myapplication/drawable/own_drink");
                }
                viewModel.saveDrink(new Drink(nameOwnDrink, volumeOwnDrink, amountOwnDrink, uri.toString()));
                NavHostFragment.findNavController(CreateNewDrinkFragment.this).navigate(R.id.action_createNewDrinkFragment_to_chooseDrinkFragment);
            }
        });
    }

    public void onRequestPermissionResultStorage(int requestCode, int[] grantResults) {
        if (requestCode == REQUESTCODE_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            binding.btnAddImageFromGallery.setEnabled(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (WHERE_FROM == 0) {
            if (resultCode == RESULT_OK && requestCode == GALLERY_REQ_CODE) {
                try {
                    imageView.setImageURI(data.getData());
                    GalleryImg = data.getData();
                } catch (Exception e) {
                    Log.e("Error", "NullPointerExeption");
                    Toast.makeText(getContext(), "App can not open gallery", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
