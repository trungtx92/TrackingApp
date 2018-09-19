package t.mad.trackingapp.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import t.mad.trackingapp.Model.RouteInfo;
import t.mad.trackingapp.Model.Tracking;
import t.mad.trackingapp.R;

public class AddTrackingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    ArrayList<RouteInfo> routeInfoList;
    String trackableIDPassing;
    String[] stationaryArr;
    Spinner spMeetingPoint;
    ArrayAdapter<String> adapter;
    TextView tvTrackingStartTime;
    TextView tvTrackingEndTime;
    TextView tvTrackingCurrentPoint;
    EditText etTrackingMeetingTime;
    Button btTrackingSave;
    Button btTrackingCancel;
    EditText etTrackingTitle;
    ArrayList<Tracking> trackingList = new ArrayList<>();
    int check = 0;

    public void onCreate(Bundle saveInstancedState){
        super.onCreate(saveInstancedState);
        setContentView(R.layout.add_tracking_view);
        tvTrackingStartTime = (TextView) findViewById(R.id.tvTrackingStartTime);
        btTrackingSave = (Button) findViewById(R.id.btTrackingSave);
        btTrackingCancel = (Button) findViewById(R.id.btTrackingCancel);
        tvTrackingEndTime = (TextView) findViewById(R.id.tvTrackingEndTime);
        tvTrackingCurrentPoint = (TextView) findViewById(R.id.tvTrackingCurrentPoint);
        etTrackingTitle = (EditText) findViewById(R.id.etTrackingTitle) ;
        etTrackingMeetingTime = (EditText) findViewById(R.id.etTrackingMeetingTime);
        routeInfoList = (ArrayList<RouteInfo>) getIntent().getSerializableExtra("routeListPassing");

        btTrackingCancel.setOnClickListener(this);
        btTrackingSave.setOnClickListener(this);
        trackableIDPassing = getIntent().getStringExtra("trackableID");
        for(RouteInfo route: routeInfoList){
            Log.i("--", route.getDate());
        }

        trackingList = (ArrayList<Tracking>) getIntent().getSerializableExtra("trackingList");
        int count = 0;

        for(RouteInfo route: routeInfoList){
            if(!route.getStopTime().equals("0") && route.getTrackableID().equals(trackableIDPassing)){
                count++;
            }
        }
        stationaryArr = new String[count];
        check = count;
        int i = 0;
        for(RouteInfo route: routeInfoList){
            if(!route.getStopTime().equals("0") && route.getTrackableID().equals(trackableIDPassing)){
                stationaryArr[i] = route.getStationary();
                i++;
            }
        }
        spMeetingPoint = (Spinner) findViewById(R.id.spTrackingMeetPoint);
        spMeetingPoint.setOnItemSelectedListener(this);
        tvTrackingCurrentPoint.setText(getCurrLocation(trackableIDPassing));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stationaryArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMeetingPoint.setAdapter(adapter);
    }

    public String getCurrLocation(String trackableID){
        String currLoc = "";
        for (RouteInfo route: routeInfoList){
            if(route.getTrackableID().equals(trackableID) && getCurrTime(trackableID).equals(route.getDate())){
                currLoc = route.getX() + "; " + route.getY();
                break;
            }
        }
        Log.i("--getCurrRealTime", getCurrRealTime());
        Log.i("--getCurrTime", getCurrTime(trackableID));
        Log.i("--getLocation", currLoc);
        return currLoc;
    }

    public String getCurrTime(String trackableID){
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        String currLoc = "";
        String currTime = getCurrRealTime();
        String tg = currTime;
        for(RouteInfo route: routeInfoList){
            if(route.getTrackableID().equals(trackableID)){
                Date dateFromFile = null;
                Date dateTg = null;
                Date dateCurr = null;
                try {
                    dateFromFile = dateformat.parse(route.getDate().toString());
                    dateTg = dateformat.parse(tg);
                    dateCurr = dateformat.parse(currTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(dateFromFile.before(dateCurr)){
                    tg = route.getDate();
                }
                else
                    break;
            }
        }
        currTime = tg;
        return currTime;
    }

    public String getCurrRealTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String startTime;
        String endTime;
        String addingValue;

        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aa");

        for(RouteInfo route: routeInfoList){
            if(route.getStationary().equals(parent.getSelectedItem().toString()) && route.getTrackableID().equals(trackableIDPassing)){
                tvTrackingStartTime.setText(route.getDate());

                startTime = route.getDate();
                Date startTimeDate;
                try{
                    startTimeDate = formatter.parse(startTime);
                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(startTimeDate);
                    startCal.add(Calendar.MINUTE, Integer.parseInt(route.getStopTime()));
                    startTimeDate = startCal.getTime();
                    endTime = formatter.format(startTimeDate);
                    tvTrackingEndTime.setText(endTime);
                }catch (Exception ex){
                    System.err.print(ex);
                }


            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

//        if(v.getId() == R.id.tvTrackingMeetingTime){
//            showDialog(TIME_DIALOG_ID);
//        }



        if(v.getId() == R.id.btTrackingSave){
            Tracking tracking;
            int trackingID = 1;
            String trackableID = trackableIDPassing;
            String title = etTrackingTitle.getText().toString();
            String meetingPoint = "";
            if(spMeetingPoint.getSelectedItem() != null)
                meetingPoint = spMeetingPoint.getSelectedItem().toString();
            String startTime = tvTrackingStartTime.getText().toString();
            String endTime = tvTrackingEndTime.getText().toString();
            String currentLocation = tvTrackingCurrentPoint.getText().toString();
            String meetingTime = etTrackingMeetingTime.getText().toString();
            if(trackingList.size() == 0){
                trackingID = 1;
            }
            else {
                int max = trackingList.get(0).getTrackingID();
                for(Tracking tk: trackingList){
                    if(max < tk.getTrackingID()){
                        max = tk.getTrackingID();
                    }
                }
                max += 1;
                trackingID = max;
            }

            SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date startDate;
            Date endDate;
            Date meetingDate;
            boolean validChecker = false;
            try{
                startDate = formatter.parse(startTime);
                endDate = formatter.parse(endTime);
                meetingDate = formatter.parse(meetingTime);

                if(meetingDate.before(endDate) && meetingDate.after(startDate) && !title.equals("")){
                    validChecker = true;
                }
                else showDialog(0);
            }catch (ParseException ex){
//                System.err.print(ex);
                showDialog(0);
            }


            if(validChecker == true){
                tracking = new Tracking(trackingID, trackableID, title, startTime, endTime, meetingTime, currentLocation, meetingPoint);
                trackingList.add(tracking);
                for(Tracking tk: trackingList){
                    Log.i("--", String.valueOf(tk.getTrackingID()));
                }

                Intent i = new Intent();
                i.putExtra("trackingList", trackingList);
                i.putExtra("test", "lon");
                i.putExtra("trackableID", trackableIDPassing);
                i.putExtra("ClassName", "AddTrackingActivity");
                setResult(Activity.RESULT_OK, i);
                finish();
            }

//            startActivity(i);
        }
        if(v.getId() == R.id.btTrackingCancel){
            finish();
        }


    }
    int hour, min;
    static final int TIME_DIALOG_ID = 0;
//    protected Dialog onCreateDialog(int id){
//        switch (id){
//            case TIME_DIALOG_ID:
//                return new TimePickerDialog(this, mTimeSetListener, hour, min, false);
//        }
//        return null;
//    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
        }
    };

    protected Dialog onCreateDialog(int id){
        switch (id){
            case 0:
                return new AlertDialog.Builder(this)
                        .setTitle("InValid Input")
                        .setMessage("Please check TITLE and MEETING TIME carefully!!!")
                        .create();
        }
        return null;
    }
    public void onBackPressed() {

    }
}












