package com.example.bluejackpharmacyv2.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
    MedicineDatabaseHelper medicineDb;
    RecyclerView medicineRV;
    MedicineAdapter adapter;
    List<Medicine> medicines;
    TextView greetingTextview;
    View view;
    UserDatabaseHelper userDb;
    String email;

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

    private void initialize(String email){
        userDb = new UserDatabaseHelper(context);
        greetingTextview = view.findViewById(R.id.greeting_text);
        String name = userDb.getName(email);
        greetingTextview.setText("Hi, " + name + "!");
    }

    private void fetchJson(){
        medicineDb = new MedicineDatabaseHelper(context);
        requestQueue = Volley.newRequestQueue(context);
        String URL = "https://mocki.io/v1/ae13b04b-13df-4023-88a5-7346d5d3c7eb";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("medicines");
                            medicines = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String medicineName = jsonObject.getString("name");
                                String manufacturer = jsonObject.getString("manufacturer");
                                Integer medicinePrice = jsonObject.getInt("price");
                                String medicineImage = jsonObject.getString("image");
                                String medicineDescription = jsonObject.getString("description");

                                medicineDb.insertMedicine(medicineName, manufacturer, medicinePrice, medicineImage, medicineDescription);
                                Medicine medicine = new Medicine(medicineDb.getMedicineId(medicineName), medicinePrice, medicineName, manufacturer, medicineImage, medicineDescription);
                                medicines.add(medicine);

                                adapter = new MedicineAdapter(medicines, context);
                                medicineRV.setAdapter(adapter);

                                medicineRV.setLayoutManager(new LinearLayoutManager(context));
                            }
                        } catch (JSONException e) {
                            Log.i("fetchJson", "nyampe sini");
                            e.printStackTrace();
                        }

                        for(Medicine medicine : medicines){
                            Log.i("ASD", medicine.getMedicineName());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(request);

    }
}