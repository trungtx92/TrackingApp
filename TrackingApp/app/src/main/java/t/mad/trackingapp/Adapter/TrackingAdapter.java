package t.mad.trackingapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import t.mad.trackingapp.Model.Tracking;
import t.mad.trackingapp.R;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.ViewHolder> {

    Context context;
    String[] myArr;
    public String trackingItemID;
    HashMap<String, String> map;
    ArrayList<Tracking> sortedTrackingList;
    public TrackingAdapter(Context c, ArrayList<Tracking> list){
        context = c;
//        myArr = arr;
//        this.map = map;
        sortedTrackingList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.tracking_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHoder, final int i) {

        String str = "Title: "+ sortedTrackingList.get(i).getTitle()+"\nMeeting Point: "+sortedTrackingList.get(i).getMeetLocation()+"\nTrackable ID: "
                +sortedTrackingList.get(i).getTrackableID() + " TrackingID: " + sortedTrackingList.get(i).getTrackingID() + "\nMeeting Time: " + sortedTrackingList.get(i).getMeetTime();
        viewHoder.tv.setText(str);
        viewHoder.tvID.setText(String.valueOf(sortedTrackingList.get(i).getTrackingID()));
        Log.i("","");
        viewHoder.tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "OK fine", Toast.LENGTH_SHORT);
                trackingItemID = String.valueOf(sortedTrackingList.get(i).getTrackingID());
                return false;
            }
        });
    }

    public String getCurrentLongClick(){
        return trackingItemID;
    }
    @Override
    public int getItemCount() {
//        return myArr.length;
//        return map.size();
        return sortedTrackingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv;
        public TextView tvID;
        public ViewHolder(View itemView){
            super(itemView);
            tv = itemView.findViewById(R.id.tvTrackingItem);
            tvID = itemView.findViewById(R.id.tvTrackingItemID);
        }
    }
}
