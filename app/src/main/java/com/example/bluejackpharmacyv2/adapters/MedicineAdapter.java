package com.example.bluejackpharmacyv2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.models.Medicine;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private final List<Medicine> medicines;
    private Context context;

    public MedicineAdapter(List<Medicine> medicines, Context context) {
        this.medicines = medicines;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView medicineImage;
        private TextView medicineName, medicineManufacturer, medicinePrice;

        public ViewHolder(View view){
            super(view);
            medicineImage = view.findViewById(R.id.medicine_image);
            medicineName = view.findViewById(R.id.medicine_name);
            medicineManufacturer = view.findViewById(R.id.medicine_manufacturer);
            medicinePrice = view.findViewById(R.id.medicine_price);
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
        holder.medicinePrice.setText(medicine.getPrice());
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }
}
