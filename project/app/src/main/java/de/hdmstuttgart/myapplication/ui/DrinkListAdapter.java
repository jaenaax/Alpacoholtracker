package de.hdmstuttgart.myapplication.ui;

import android.app.AlertDialog;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import de.hdmstuttgart.myapplication.R;
import de.hdmstuttgart.myapplication.model.Drink;
import de.hdmstuttgart.myapplication.ui.chooseDrink.ChooseDrinkViewModel;

public class DrinkListAdapter extends RecyclerView.Adapter<DrinkListAdapter.DrinkViewHolder> {

    private final List<Drink> drinkList;
    private final onDrinkClickListener listener;
    private final ChooseDrinkViewModel viewModel;

    public interface onDrinkClickListener {
        void onDrinkClicked(Drink drink, int position);
    }

    public DrinkListAdapter(
            List<Drink> drinkList,
            onDrinkClickListener listener,
            ChooseDrinkViewModel viewModel
    ) {
        this.drinkList = drinkList;
        this.listener = listener;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drink_list_item, parent, false);

        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        Drink drink = drinkList.get(position);

        holder.titleTextView.setText(drink.getName());

        //testet ob Interger oder URI
        try {
            holder.imageDrink.setImageResource(Integer.parseInt(drink.getImage()));
        } catch (NumberFormatException ex) {
            holder.imageDrink.setImageURI(Uri.parse(drink.getImage()));
        }

        holder.itemView.setOnClickListener(v -> listener.onDrinkClicked(drink, position));

        holder.itemView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Delete Drink")
                    .setMessage("Are you sure you want to delete this drink?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        viewModel.deleteDrink(drink);
                        drinkList.remove(position);
                        this.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    static class DrinkViewHolder extends RecyclerView.ViewHolder {

        ImageView imageDrink;
        TextView titleTextView;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.tvNameOfDrink);
            imageDrink = itemView.findViewById(R.id.ivImageDrink);
        }
    }
}
