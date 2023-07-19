package com.example.pendakiangunung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MountainListAdapter extends BaseAdapter {

    Context context;
    String titles[];
    int images[];
    LayoutInflater inflater;

    public MountainListAdapter (Context ctx, String[] titles, int[] images) {
        this.context = ctx;
        this.titles = titles;
        this.images = images;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_mountain_list, null);
        ImageView image = convertView.findViewById(R.id.mountainImage);
        TextView title = convertView.findViewById(R.id.mountainName);

        image.setImageResource(images[position]);
        title.setText(titles[position]);

        return convertView;
    }
}
