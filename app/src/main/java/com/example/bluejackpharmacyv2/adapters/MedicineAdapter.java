package com.example.bluejackpharmacyv2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.activities.Authentication;
import com.example.bluejackpharmacyv2.activities.Details;
import com.example.bluejackpharmacyv2.models.Medicine;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private final List<Medicine> medicines;
    private final Context context;
    private String email;

    public MedicineAdapter(List<Medicine> medicines, Context context, String email) {
        this.medicines = medicines;
        this.context = context;
        this.email = email;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView medicineImage;
        private TextView medicineName, medicineManufacturer, medicinePrice;
        private CardView medicineCard;
        private LinearLayout medicineContainer;

        public ViewHolder(View view){
            super(view);
            medicineImage = view.findViewById(R.id.medicine_image);
            medicineName = view.findViewById(R.id.medicine_name);
            medicineManufacturer = view.findViewById(R.id.medicine_manufacturer);
            medicinePrice = view.findViewById(R.id.medicine_price);
            medicineCard = view.findViewById(R.id.medicine_card);
            medicineContainer = view.findViewById(R.id.medicine_container);
        }
    }

    @NonNull
    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.ViewHolder holder, int position) {
        Medicine medicine = medicines.get(position);
        holder.medicineName.setText(medicine.getMedicineName());
        holder.medicineManufacturer.setText(medicine.getManufacturer());
        holder.medicinePrice.setText(String.valueOf(medicine.getPrice()));
        Picasso.get().load(medicine.getImage()).into(holder.medicineImage);

        holder.medicineContainer.setForeground(ContextCompat.getDrawable(context, R.drawable.clicked_card));

        holder.itemView.setOnClickListener(v -> {
            String medicineId = String.valueOf(medicine.getMedicineId());
            String medicineName = medicine.getMedicineName();
            String manufacturer = medicine.getManufacturer();
            String medicinePrice = String.valueOf(medicine.getPrice());
            String medicineImage = medicine.getImage();
            String medicineDescription = medicine.getDescription();

            Intent intent = new Intent(context, Details.class);
            intent.putExtra("medicineId", medicineId);
            intent.putExtra("medicineName", medicineName);
            intent.putExtra("manufacturer", manufacturer);
            intent.putExtra("medicinePrice", medicinePrice);
            intent.putExtra("medicineImage", medicineImage);
            intent.putExtra("medicineDescription", medicineDescription);
            intent.putExtra("userEmail", email);
            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return medicines.size();
    }
}
