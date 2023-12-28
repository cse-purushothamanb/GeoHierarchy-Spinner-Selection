package com.example.spinners;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //implements AdapterView.OnItemSelectedListener
    //AdapterView.OnItemSelectedListener
    private Spinner spinnerDistrict, spinnerTaluk, spinnerHobli, spinnerVillage, spinnerSurvey;
    private Button submitButton;
    private TextView textView;
    private DBHandler dbHandler;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("<Base URL>")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ApiService apiService = retrofit.create(ApiService.class);
    List<String> districtlist = new ArrayList<>();
    List<String> taluklist = new ArrayList<>();
    List<String> Hoblilist = new ArrayList<>();

    List<String> Villagelist = new ArrayList<>();
    List<String> Surveylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //isNetworkAvailable();
        //final DatabaseHelper helper = new DatabaseHelper(this);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerTaluk = findViewById(R.id.spinnerTaluk);
        spinnerHobli = findViewById(R.id.spinnerHobli);
        spinnerVillage = findViewById(R.id.spinnerVillage);
        spinnerSurvey = findViewById(R.id.spinnerSurvey);
        submitButton = findViewById(R.id.submitButton);
        textView=findViewById(R.id.textView);
        populateDistrictSpinner();
        setUpSpinnerDependencies();
        dbHandler = new DBHandler(MainActivity.this);
        submitButton.setOnClickListener(v -> {
            String selectedDistrict = spinnerDistrict.getSelectedItem().toString();
            String selectedTaluk = spinnerTaluk.getSelectedItem().toString();
            String selectedHobli = spinnerHobli.getSelectedItem().toString();
            String selectedVillage = spinnerVillage.getSelectedItem().toString();
            String selectedSurvey = spinnerSurvey.getSelectedItem().toString();
            Toast.makeText(MainActivity.this, "Selected: " + selectedDistrict +
                            ", " + selectedTaluk + ", " + selectedHobli +
                            ", " + selectedVillage + ", " + selectedSurvey,
                    Toast.LENGTH_SHORT).show();
            dbHandler.addNewCourse(selectedDistrict, selectedTaluk, selectedHobli, selectedVillage,selectedSurvey);
            String value =dbHandler.renderData();
            textView.setTextSize(20);
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setText(value);
        });
    }

    private void populateDistrictSpinner() {
        GenericModelClass detail = new GenericModelClass();
        detail.setApplicantID("1_GetAHAll");
        detail.setParameter("P1$|$P2");
        detail.setValues("Rural$|$0");
        System.out.println("value inside:" + detail.toString() + "sde,lkn::" + new Gson().toJson(detail));
        Call<List<District>> call = apiService.get_details(detail);
        call.enqueue(new Callback<List<District>>() {
            @Override
            public void onResponse(Call<List<District>> call, Response<List<District>> response) {
                System.out.println("value inside: res:" + response.body());

                if (response.isSuccessful()) {
                    districtlist.clear();
                    if (String.valueOf(response.body()) != null) {
                        if (String.valueOf(response.code()).equals("200")) {
                            for (int i = 0; i < response.body().size(); i++) {
                                Log.d("data111 single", response.body().get(i).toString());

                                districtlist.add(response.body().get(i).getCode() + " " + response.body().get(i).getName());
                            }
                            ArrayAdapter<String> DistrictAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line,
                                    districtlist);
                            spinnerDistrict.setAdapter(DistrictAdapter);
                            //spinnerDistrict.setOnItemSelectedListener(MainActivity.this);
                            //Taluk
                            spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String text = parent.getItemAtPosition(position).toString();
                                    //System.out.println(text);
                                    //textView.setText("District Details: "+text);
                                    String[] split = text.split(" ", 2);
                                    String code = split[0];
                                    String Name = split[1];
                                    System.out.println(code);
                                    GenericModelClass detail = new GenericModelClass();
                                    detail.setApplicantID("1_GetAHAll");
                                    detail.setParameter("P1$|$P2");
                                    detail.setValues("Rural$|$" + code);
                                    System.out.println("value inside:" + detail.toString() + "sde,lkn::" + new Gson().toJson(detail));
                                    Call<List<Taluk>> call = apiService.get_taluk_details(detail);
                                    call.enqueue(new Callback<List<Taluk>>() {
                                        @Override
                                        public void onResponse(Call<List<Taluk>> call, Response<List<Taluk>> response) {
                                            System.out.println("value inside: res:" + response.body());
                                            if (response.isSuccessful()) {
                                                taluklist.clear();
                                                if (String.valueOf(response.body()) != null) {
                                                    if (String.valueOf(response.code()).equals("200")) {
                                                        for (int i = 0; i < response.body().size(); i++) {
                                                            Log.d("data111 single", response.body().get(i).toString());
                                                            taluklist.add(response.body().get(i).getCode() + " " + response.body().get(i).getName());
                                                        }
                                                        ArrayAdapter<String> TalukAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line,
                                                                taluklist);

                                                        spinnerTaluk.setAdapter(TalukAdapter);
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<Taluk>> call, Throwable t) {
                                            Toast.makeText(MainActivity.this, "Failed to fetch Taluk", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            //Hobli
                            spinnerTaluk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String text = parent.getItemAtPosition(position).toString();
                                    //System.out.println(text);
                                    //textView.setText("Taluk Details: "+text);
                                    String[] split = text.split(" ", 2);
                                    String code = split[0];
                                    String Name = split[1];
                                    System.out.println(code);
                                    GenericModelClass detail = new GenericModelClass();
                                    detail.setApplicantID("1_GetAHAll");
                                    detail.setParameter("P1$|$P2");
                                    detail.setValues("Rural$|$" + code);
                                    System.out.println("value inside:" + detail.toString() + "sde,lkn::" + new Gson().toJson(detail));
                                    Call<List<Hobli>> call = apiService.get_Hobli_details(detail);
                                    call.enqueue(new Callback<List<Hobli>>() {
                                        @Override
                                        public void onResponse(Call<List<Hobli>> call, Response<List<Hobli>> response) {
                                            System.out.println("value inside: res:" + response.body());
                                            if (response.isSuccessful()) {
                                                Hoblilist.clear();
                                                if (String.valueOf(response.body()) != null) {
                                                    if (String.valueOf(response.code()).equals("200")) {
                                                        for (int i = 0; i < response.body().size(); i++) {
                                                            Log.d("data111 single", response.body().get(i).toString());
                                                            Hoblilist.add(response.body().get(i).getCode() + " " + response.body().get(i).getName());
                                                        }
                                                        ArrayAdapter<String> HobliAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line,
                                                                Hoblilist);

                                                        spinnerHobli.setAdapter(HobliAdapter);
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<Hobli>> call, Throwable t) {
                                            Toast.makeText(MainActivity.this, "Failed to fetch Hobli", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            //village
                            spinnerHobli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String text = parent.getItemAtPosition(position).toString();
                                    //System.out.println(text);
                                    //textView.setText("Hobli Details: "+text);
                                    String[] split = text.split(" ", 2);
                                    String code = split[0];
                                    String Name = split[1];
                                    System.out.println(code);
                                    GenericModelClass detail = new GenericModelClass();
                                    detail.setApplicantID("1_GetAHAll");
                                    detail.setParameter("P1$|$P2");
                                    detail.setValues("Rural$|$" + code);
                                    System.out.println("value inside:" + detail.toString() + "sde,lkn::" + new Gson().toJson(detail));
                                    Call<List<Village>> call = apiService.get_Village_details(detail);
                                    call.enqueue(new Callback<List<Village>>() {
                                        @Override
                                        public void onResponse(Call<List<Village>> call, Response<List<Village>> response) {
                                            System.out.println("value inside: res:" + response.body());
                                            if (response.isSuccessful()) {
                                                Villagelist.clear();
                                                if (String.valueOf(response.body()) != null) {
                                                    if (String.valueOf(response.code()).equals("200")) {
                                                        for (int i = 0; i < response.body().size(); i++) {
                                                            Log.d("data111 single", response.body().get(i).toString());
                                                            Villagelist.add(response.body().get(i).getKGISVillageCode()+ " " + response.body().get(i).getName());
                                                        }
                                                        ArrayAdapter<String> VillageAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line,
                                                                Villagelist);

                                                        spinnerVillage.setAdapter(VillageAdapter);
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<Village>> call, Throwable t) {
                                            Toast.makeText(MainActivity.this, "Failed to fetch Village", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            // survey
                            spinnerVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String text = parent.getItemAtPosition(position).toString();
                                    //System.out.println(text);
                                    //textView.setText("Village Details: "+text);
                                    String[] split = text.split(" ", 2);
                                    String code = split[0];
                                    String Name = split[1];
                                    System.out.println(code);
                                    GenericModelClass detail = new GenericModelClass();
                                    detail.setApplicantID("1_GetAHAll");
                                    detail.setParameter("P1$|$P2");
                                    detail.setValues("Rural$|$" + code);
                                    System.out.println("value inside:" + detail.toString() + "sde,lkn::" + new Gson().toJson(detail));
                                    Call<List<Survey>> call = apiService.get_Survey_details(detail);
                                    call.enqueue(new Callback<List<Survey>>() {
                                        @Override
                                        public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                                            System.out.println("value inside: res:" + response.body());
                                            if (response.isSuccessful()) {
                                                Surveylist.clear();
                                                if (String.valueOf(response.body()) != null) {
                                                    if (String.valueOf(response.code()).equals("200")) {
                                                        for (int i = 0; i < response.body().size(); i++) {
                                                            Log.d("data111 single", response.body().get(i).toString());
                                                            Surveylist.add(response.body().get(i).getKGISVillageCode() + " " + response.body().get(i).getKGISVillageName());
                                                        }
                                                        ArrayAdapter<String> SurveyAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line,
                                                                Surveylist);

                                                        spinnerSurvey.setAdapter(SurveyAdapter);
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<Survey>> call, Throwable t) {
                                            Toast.makeText(MainActivity.this, "Failed to fetch Village", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<District>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch district", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpSpinnerDependencies() {
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

}
