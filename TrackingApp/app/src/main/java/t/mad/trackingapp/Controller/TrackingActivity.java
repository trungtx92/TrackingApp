package t.mad.trackingapp.Controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import t.mad.trackingapp.Adapter.TrackableAdapter;
import t.mad.trackingapp.Adapter.TrackingAdapter;
import t.mad.trackingapp.MainActivity;
import t.mad.trackingapp.Model.RouteInfo;
import t.mad.trackingapp.Model.Tracking;
import t.mad.trackingapp.R;

public class TrackingActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView reclerViewTracking;
    Button btAddTracking;
    Button btCancelTracking;
    String[] arrTracking = {"Tracking 1", "Tracking 2", "Tracking 3",
                            "Tracking 4","Tracking 5", "Tracking 6",
                            "Tracking 7", "Tracking 8", "Tracking 9"};


    ArrayList<RouteInfo> routeInfoList = new ArrayList<RouteInfo>();
    ArrayList<Tracking> trackingList = new ArrayList<>();
    ArrayList<Tracking> sortedTrackingList = new ArrayList<>();
    HashMap<String, String> mapTracking = new HashMap<String, String>();
    String[] convertStr;
    Tracking[] convertObj;
    Tracking trackingPassing;
    String trackableIDPassing;
    TrackingAdapter adapter;

    InputStream inputStream;
    BufferedReader br;

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.tracking_activity);
        Display display = getWindowManager().getDefaultDisplay();
        String displayName = display.getName();
        // minSdkVersion=17+
        Log.i("OK", "displayName  = " + displayName);

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        loadRouteInfo();
        String classname = getIntent().getStringExtra("ClassName");
        if(classname.equals("TrackableAdapter")){
            getTrackableIDPassing();
            getTrackingPassing();
            Log.i("--", "an");

        }
        else if(classname.equals("AddTrackingActivity")){
            getTrackingPassing();
            getTrackableIDPassing();
        }
        setConvertStr();



        reclerViewTracking = (RecyclerView) findViewById(R.id.reclerViewTracking);
        reclerViewTracking.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrackingAdapter(this, sortedTrackingList);
        reclerViewTracking.setAdapter(adapter);
        registerForContextMenu(reclerViewTracking);


        ViewGroup.LayoutParams params=reclerViewTracking.getLayoutParams();
        params.height= height - 500;
        reclerViewTracking.setLayoutParams(params);

        btAddTracking = (Button) findViewById(R.id.btAddTracking);
        btCancelTracking = (Button) findViewById(R.id.btBackTracking);
        btAddTracking.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        btAddTracking.setOnClickListener(this);
        btCancelTracking.setOnClickListener(this);
        btAddTracking.setHeight(250);
    }

    void setConvertStr(){
        int count = 0;
        int i = 0;

        for(Tracking tracking: trackingList){
            if(tracking.getTrackableID().equals(trackableIDPassing)){
                count++;
            }
        }
        convertStr = new String[count];
        sortedTrackingList = new ArrayList<Tracking>();
        for(Tracking tracking: trackingList){
            if(tracking.getTrackableID().equals(trackableIDPassing)){
                convertStr[i] = "Title: "+tracking.getTitle()+"\nMeeting Point: "+tracking.getMeetLocation()+"\nTrackable ID: "
                        +tracking.getTrackableID() + " TrackingID: " + tracking.getTrackingID();

                sortedTrackingList.add(tracking);

                mapTracking.put(String.valueOf(tracking.getTrackingID()), convertStr[i]);
                i++;
            }
        }

    }

    void getTrackingPassing(){
        trackingList = (ArrayList<Tracking>) getIntent().getSerializableExtra("trackingList");
    }

    void getTrackableIDPassing(){
        trackableIDPassing = getIntent().getStringExtra("trackableID");
    }

    void loadRouteInfo(){
        inputStream = getResources().openRawResource(R.raw.tracking_data);
        br = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        try {
            while ((line = br.readLine()) != null) {
                String[] str = line.split(",");
                RouteInfo routeInfo = new RouteInfo(str[0], str[1], str[2], str[3], str[4], str[5]);
                routeInfoList.add(routeInfo);
            }
            inputStream.reset();
        }catch (IOException e){

        }
        for(RouteInfo route: routeInfoList){
            Log.i("Route Date:",route.getDate());
            Log.i("Route TrackableID:",route.getTrackableID());
            Log.i("Route Stop time:",route.getStopTime());
            Log.i("Route X:",route.getX());
            Log.i("Route Y:",route.getY());
            Log.i("Route Stationary:",route.getStationary());
            Log.i("Route:","---------------------------");
        }
        Log.i("Route:", "----------------Finish Print------------------");
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.reclerViewTracking) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }
    int check =0;
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.editTracking:
                String id = adapter.trackingItemID;
                Intent intent = new Intent(this, UpdateTrackingActivity.class);
                intent.putExtra("trackingItemID", id);
                intent.putExtra("trackingList", trackingList);
                startActivityForResult( intent, 1);
                return true;
            case R.id.deleteTracking:
                int i = 0;
                for(Tracking tracking: trackingList){
                    if(String.valueOf(tracking.getTrackingID()).equals(adapter.trackingItemID)){
                        Log.i("--", "Match");
                        check = i;
                        break;
                    }
                    i++;
                }
                trackingList.remove(i);
                setConvertStr();
                adapter = new TrackingAdapter(this, sortedTrackingList);
                reclerViewTracking.setAdapter(adapter);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    Intent fromAdd;

//    Callback
    @Override
    public void onBackPressed() {
//        Toast.makeText(this, adapter.getCurrentLongClick(), Toast.LENGTH_SHORT).show();
//        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        getTrackingPassing();
        if(data != null)
            trackingList = (ArrayList<Tracking>) data.getSerializableExtra("trackingList");
        setConvertStr();
        adapter = new TrackingAdapter(this, sortedTrackingList);
        reclerViewTracking.setAdapter(adapter);

        registerForContextMenu(reclerViewTracking);
        fromAdd = data;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btAddTracking){
            getTrackableIDPassing();
            Intent i = new Intent(this, AddTrackingActivity.class);
            i.putExtra("routeListPassing", routeInfoList);
            i.putExtra("trackableID", trackableIDPassing);
            i.putExtra("trackingList", trackingList);
            i.putExtra("ClassName", "TrackingActivity");
//            finish();
            startActivityForResult( i, 1);
        }
        if(v.getId() == R.id.btBackTracking){
            if(fromAdd != null){
                setResult(Activity.RESULT_OK, fromAdd );
            }

            finish();
        }
    }
}















