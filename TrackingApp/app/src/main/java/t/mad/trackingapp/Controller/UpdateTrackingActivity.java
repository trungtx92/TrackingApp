package t.mad.trackingapp.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import t.mad.trackingapp.Model.Tracking;
import t.mad.trackingapp.R;

public class UpdateTrackingActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUpdateTrackingTitle;
    TextView tvUpdateTrackingMeetPoint;
    TextView tvUpdateTrackingStartTime;
    TextView tvUpdateTrackingEndTime;
    TextView tvUpdateTrackingCurrentPoint;
    EditText etUpdateTrackingMeetingTime;
    Button btUpdate;
    Button btCancel;

    String id;
    ArrayList<Tracking> trackList;
    public void onCreate(Bundle saveInstancedState){
        super.onCreate(saveInstancedState);
        setContentView(R.layout.update_tracking_view);

        etUpdateTrackingTitle = (EditText) findViewById(R.id.etUpdateTrackingTitle);
        tvUpdateTrackingMeetPoint = (TextView) findViewById(R.id.tvUpdateTrackingMeetPoint);
        tvUpdateTrackingStartTime = (TextView) findViewById(R.id.tvUpdateTrackingStartTime);
        tvUpdateTrackingEndTime = (TextView) findViewById(R.id.tvUpdateTrackingEndTime) ;
        tvUpdateTrackingCurrentPoint = (TextView) findViewById(R.id.tvUpdateTrackingCurrentPoint);
        etUpdateTrackingMeetingTime = (EditText) findViewById(R.id.etUpdateTrackingMeetingTime);
        btUpdate = (Button) findViewById(R.id.btUpdateTrackingSave);
        btCancel = (Button) findViewById(R.id.btUpdateTrackingCancel);
        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);

        id = getIntent().getStringExtra("trackingItemID");
        trackList = (ArrayList<Tracking>) getIntent().getSerializableExtra("trackingList");

        for(Tracking tracking: trackList){
            if(String.valueOf(tracking.getTrackingID()).equals(id)){
                etUpdateTrackingTitle.setText(tracking.getTitle());
                tvUpdateTrackingMeetPoint.setText(tracking.getMeetLocation());
                tvUpdateTrackingStartTime.setText(tracking.getTgStartTime());
                tvUpdateTrackingEndTime.setText(tracking.getTgEndTime());
                tvUpdateTrackingCurrentPoint.setText(tracking.getCurrLocation());
                etUpdateTrackingMeetingTime.setText(tracking.getMeetTime());
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btUpdateTrackingSave){
            String title = etUpdateTrackingTitle.getText().toString();
            String meetingPoint = tvUpdateTrackingMeetPoint.getText().toString();
            String startTime = tvUpdateTrackingStartTime.getText().toString();
            String endTime = tvUpdateTrackingEndTime.getText().toString();
            String currPoint = tvUpdateTrackingCurrentPoint.getText().toString();
            String meetingTime = etUpdateTrackingMeetingTime.getText().toString();


            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date startDate = null;
            Date endDate = null;
            Date meetingDate = null;
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
                for(Tracking tracking: trackList){
                    if(String.valueOf(tracking.getTrackingID()).equals(id)){
                        tracking.setTitle(title);
                        tracking.setMeetLocation(meetingPoint);
                        tracking.setTgStartTime(startTime);
                        tracking.setTgEndTime(endTime);
                        tracking.setCurrLocation(currPoint);
                        tracking.setMeetTime(meetingTime);
                    }
                }
                Intent intent = new Intent(this, TrackingActivity.class);
                intent.putExtra("trackingList", trackList);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }



        }
        if(v.getId() == R.id.btUpdateTrackingCancel){

            finish();
        }

    }
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










