package com.example.bluejackpharmacyv2.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bluejackpharmacyv2.R;
import com.example.bluejackpharmacyv2.adapters.MedicineAdapter;
import com.example.bluejackpharmacyv2.models.Medicine;
import com.example.bluejackpharmacyv2.utils.MedicineDatabaseHelper;
import com.example.bluejackpharmacyv2.utils.UserDatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MedicineFragment extends Fragment {

    private RequestQueue requestQueue;
    private Context context;
    private MedicineDatabaseHelper medicineDb;
    private final String URL = "https://mocki.io/v1/ae13b04b-13df-4023-88a5-7346d5d3c7eb";
    private RecyclerView medicineRV;
    private MedicineAdapter adapter;
    private List<Medicine> medicines;
    private TextView greetingTextview;
    private View view;
    private UserDatabaseHelper userDb;
    private String email;
    private ProgressBar progressBar;
    private CardView videoCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_medicine, container, false);
        this.context = getContext();

        medicineRV = view.findViewById(R.id.medicines_recyclerview);
        Bundle args = getArguments();

        if(args != null){
            email = args.getString("email");
        }

        initialize(email);
        fetchJson();
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void initialize(String email){
        userDb = new UserDatabaseHelper(context);
        greetingTextview = view.findViewById(R.id.greeting_text);
        progressBar = view.findViewById(R.id.progress_bar);
        videoCard = view.findViewById(R.id.video_card);
        String name = userDb.getName(email);
        greetingTextview.setText("Hi, " + name + "!");

        setListener();
    }

    private void setListener(){
        videoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=c06dTj0v0sM")));
            }
        });
    }

    private void fetchJson(){
        medicineDb = new MedicineDatabaseHelper(context);
        requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("medicines");
                        medicines = new ArrayList<>();

                        progressBar.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String medicineName = jsonObject.getString("name");
                            String manufacturer = jsonObject.getString("manufacturer");
                            Integer medicinePrice = jsonObject.getInt("price");
                            String medicineImage = jsonObject.getString("image");
                            String medicineDescription = jsonObject.getString("description");

                            insertMedicineToDatabase(medicineName, manufacturer, medicinePrice, medicineImage, medicineDescription);
                            Medicine medicine = new Medicine(getMedicineIdFromDatabase(medicineName), medicinePrice, medicineName, manufacturer, medicineImage, medicineDescription);
                            medicines.add(medicine);

                            setRecyclerview(medicines, context);
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        Log.i("fetchJson", "nyampe sini");
                        e.printStackTrace();
                    }

                    for(Medicine medicine : medicines){
                        Log.i("ASD", medicine.getMedicineName());
                    }
                },
                Throwable::printStackTrace
        );

        requestQueue.add(request);

    }

    public void insertMedicineToDatabase(String medicineName, String manufacturer, Integer medicinePrice, String medicineImage, String medicineDescription){
        medicineDb.insertMedicine(medicineName, manufacturer, medicinePrice, medicineImage, medicineDescription);
    }

    public int getMedicineIdFromDatabase(String medicineName){
        return medicineDb.getMedicineId(medicineName);
    }

    public void setRecyclerview(List medicines, Context context){
        adapter = new MedicineAdapter(medicines, context);
        medicineRV.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        medicineRV.setLayoutManager(layoutManager);
    }
}