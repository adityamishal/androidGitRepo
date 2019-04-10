package com.example.menu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

public class CustomListView extends ArrayAdapter<String> {

    private String[] candidate;
    private String[] date;
    private String[] round;
    private String[] interviewer;
    private String[] status;
    private URL[] image;
    private Activity context;

    public CustomListView(Activity context, String[] candidate, String[] round, String[] date, String[] interviewer, String[] status, URL[] image) {
        super(context, R.layout.listview_layout, candidate);
        this.context = context;
        this.candidate = candidate;
        this.date = date;
        this.interviewer = interviewer;
        this.round = round;
        this.status = status;
        this.image = image;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View r = convertView;
        ViewHolder viewHolder = null;

        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_layout, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }

        viewHolder.candidate.setText(candidate[position]);
        viewHolder.status.setText(status[position]);
        viewHolder.date.setText(date[position]);
        viewHolder.interviewer.setText(interviewer[position]);
        viewHolder.round.setText(round[position]);
        viewHolder.image.setText(image[position].toString());

        return r;
    }


    class ViewHolder {
        TextView candidate;
        TextView status;
        TextView date;
        TextView interviewer;
        TextView round;
        TextView image;

        ViewHolder(View view) {
            candidate = view.findViewById(R.id.candidate);
            status = view.findViewById(R.id.status);
            date = view.findViewById(R.id.date);
            interviewer = view.findViewById(R.id.interviewer);
            round = view.findViewById(R.id.round);
            image = view.findViewById(R.id.image);
        }
    }


}
