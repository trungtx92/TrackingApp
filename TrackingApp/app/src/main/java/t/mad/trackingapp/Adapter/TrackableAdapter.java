package t.mad.trackingapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import t.mad.trackingapp.Controller.TrackingActivity;
import t.mad.trackingapp.Model.Trackable;
import t.mad.trackingapp.Model.Tracking;
import t.mad.trackingapp.R;

public class TrackableAdapter extends RecyclerView.Adapter<TrackableAdapter.ViewHolder> {

    private Context context;
    private String[] myArr;
    private String ID;
    String trackableId;
    Intent intent;
    ArrayList<Trackable> trackableList;
    ArrayList<Tracking> trackingList;

    public TrackableAdapter(Context context, String[] arr, ArrayList trackableList, ArrayList trackingList){
        this.context = context;
        this.myArr = arr;
        this.trackableList = trackableList;
        this.trackingList = trackingList;
    }

    @NonNull
    @Override
    public TrackableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.trackable_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrackableAdapter.ViewHolder viewHoder, final int i) {
        String clickID;



        if (i % 2 == 0){
            viewHoder.tv.setBackgroundColor(Color.YELLOW);
        }
        viewHoder.tv.setText(myArr[i]);
        viewHoder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, TrackingActivity.class);

                //Get selected object ID
                for(Trackable trackable: trackableList){
                    if(trackable.getName().compareTo(myArr[i]) == 0){
                        ID = trackable.getId();
                        break;
                    }
                }
                intent.putExtra("trackableID",ID);
                        intent.putExtra("ClassName", "TrackableAdapter");
                        intent.putExtra("trackingList", trackingList);
//                intent.putExtra("trackingList", trackableList);
                        ((Activity)context).startActivityForResult(intent, 1);
                        }
                        });
                        }

    @Override
    public int getItemCount() {
        return myArr.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public ViewHolder(View itemView){
            super(itemView);
            tv = itemView.findViewById(R.id.tvTrackableItem);
        }
    }
}
