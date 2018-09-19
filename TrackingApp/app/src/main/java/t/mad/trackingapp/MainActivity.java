package t.mad.trackingapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import t.mad.trackingapp.Adapter.TrackableAdapter;
import t.mad.trackingapp.Model.Trackable;
import t.mad.trackingapp.Model.Tracking;
import t.mad.trackingapp.Services.TestTrackingService;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spnCategory;
    RecyclerView recyclerView;
    InputStream inputStream;
    BufferedReader br;
    String[] arrTrackableName;
    String[] arrTrackableCategory;
    String line;
    ArrayList<Trackable> trackableList = new ArrayList<Trackable>();
    ArrayList<Tracking> trackingList = new ArrayList<>();
    ArrayList<String> categoryList = new ArrayList<String>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputStream = getResources().openRawResource(R.raw.food_truck_data);
        br = new BufferedReader(new InputStreamReader(inputStream));
        arrTrackableName = new String[countRow()];
        arrTrackableCategory = new String[countRow()];
        setData();
        spnCategory = (Spinner) findViewById(R.id.spCategory);
        adapter = ArrayAdapter.createFromResource(this, R.array.arrCategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
        spnCategory.setOnItemSelectedListener(this);
        String classname = "";

        if(getIntent().getStringExtra("ClassName") != null){
            classname = getIntent().getStringExtra("ClassName");
            trackingList = (ArrayList<Tracking>) getIntent().getSerializableExtra("trackingList");
        }

        recyclerView = (RecyclerView) findViewById(R.id.revTrackableList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TrackableAdapter adapter = new TrackableAdapter(this, arrTrackableName, trackableList, trackingList);
        recyclerView.setAdapter(adapter);
//        TestTrackingService.test(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            trackingList = (ArrayList<Tracking>) data.getSerializableExtra("trackingList");
        }
        TrackableAdapter adapter = new TrackableAdapter(this, arrTrackableName, trackableList, trackingList);
        recyclerView.setAdapter(adapter);
        Log.i("123","");
    }


    void setCategory(){
        categoryList.add("Asian");
        categoryList.add("Italian");
        categoryList.add("African");
        categoryList.add("Argentinian");
        categoryList.add("Thai");
        categoryList.add("Dessert");
        categoryList.add("Vietnamese");
        categoryList.add("Western");
    }

    void setData(){
        int i = 0;
        try {
            while ((line = br.readLine()) != null) {
                String[] str = line.split("-");
                Trackable trackable = new Trackable(str[0], str[1], str[2], str[3], str[4]);
                trackableList.add(trackable);
                arrTrackableName[i] = str[1];
                i++;
            }
            inputStream.reset();
            setCategory();
        }catch (IOException e){

        }
    }

    int countRow(){
        int i = 0;
        try {
            while ((line = br.readLine()) != null) {
                i++;
            }
            inputStream.reset();
        }catch (IOException e){

        }

        return i;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int count = 0;
        int i = 0;
        if(parent.getSelectedItem().toString().equals("All")){
            arrTrackableName = new String[countRow()];
            for(Trackable trackable: trackableList){
                arrTrackableName[i] = trackable.getName();
                i++;
            }
            TrackableAdapter adapter = new TrackableAdapter(this, arrTrackableName,trackableList, trackingList);
            recyclerView.setAdapter(adapter);
        }
        else {
            for(Trackable trackable: trackableList){
                if(trackable.getCategory().equals(parent.getSelectedItem().toString())){
                    count++;
                }
            }
            arrTrackableName = new String[count];
            for(Trackable trackable: trackableList){
                if(trackable.getCategory().equals(parent.getSelectedItem().toString())){
                    arrTrackableName[i] = trackable.getName();
                    i++;
                }
            }

            TrackableAdapter adapter = new TrackableAdapter(this, arrTrackableName,trackableList, trackingList
            );
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onBackPressed() {

    }
}
