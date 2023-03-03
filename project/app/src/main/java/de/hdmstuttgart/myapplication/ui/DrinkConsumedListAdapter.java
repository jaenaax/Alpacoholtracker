package de.hdmstuttgart.myapplication.ui;

import android.annotation.SuppressLint;
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
import de.hdmstuttgart.myapplication.model.DrinkConsumed;
import de.hdmstuttgart.myapplication.ui.drinksconsumedOverview.DrinksConsumedOverviewViewModel;

public class DrinkConsumedListAdapter extends RecyclerView.Adapter<DrinkConsumedListAdapter.DrinkConsumedViewHolder> {

    private final List<DrinkConsumed> drinkList;
    private final DrinksConsumedOverviewViewModel viewModel;

    public DrinkConsumedListAdapter(
            List<DrinkConsumed> drinkList,
            DrinksConsumedOverviewViewModel viewModel
    ) {
        this.drinkList = drinkList;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public DrinkConsumedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drinksconsumed_list_item, parent, false);

        return new DrinkConsumedViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull DrinkConsumedViewHolder holder, int position) {
        DrinkConsumed drinkConsumed = drinkList.get(position);

        holder.tvNameOfDrink.setText(drinkConsumed.getName());
        holder.tvAmount.setText((drinkConsumed.getAmount()) + " ml");
        holder.tvVolume.setText((drinkConsumed.getVolume()) + " %");
        holder.tvImpact.setText((Math.round(viewModel.getImpact(drinkConsumed) * 100.0) / 100.0 + " ‰"));

        //testet ob Interger oder URI
        try {
            holder.imageDrink.setImageResource(Integer.parseInt(drinkConsumed.getImage()));
        } catch (NumberFormatException ex) {
            holder.imageDrink.setImageURI(Uri.parse(drinkConsumed.getImage()));
        }

        holder.itemView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Delete Drink")
                    .setMessage("Are you sure you want to delete " + drinkConsumed.getName() + "?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        viewModel.deleteDrink(drinkConsumed);
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

    static class DrinkConsumedViewHolder extends RecyclerView.ViewHolder {

        ImageView imageDrink;
        TextView tvNameOfDrink;
        TextView tvAmount;
        TextView tvVolume;
        TextView tvImpact;

        public DrinkConsumedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameOfDrink = itemView.findViewById(R.id.tvNameOfDrink);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvVolume = itemView.findViewById(R.id.tvVolume);
            tvImpact = itemView.findViewById(R.id.tvImpact);
            imageDrink = itemView.findViewById(R.id.ivImageDrink);

        }
    }
}
