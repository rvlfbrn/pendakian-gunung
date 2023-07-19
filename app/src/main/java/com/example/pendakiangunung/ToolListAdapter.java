package com.example.pendakiangunung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ToolListAdapter extends BaseAdapter {

    Context context;
    String titles[];
    String subtitles[];
    int images[];
    LayoutInflater inflater;

    public ToolListAdapter (Context ctx, String[] titles, String[] subtitles, int[] images) {
        this.context = ctx;
        this.titles = titles;
        this.subtitles = subtitles;
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
        convertView = inflater.inflate(R.layout.activity_tool_list, null);
        ImageView image = convertView.findViewById(R.id.toolImage);
        TextView title = convertView.findViewById(R.id.toolTitle);
        TextView subtitle = convertView.findViewById(R.id.toolSubtitle);

        image.setImageResource(images[position]);
        title.setText(titles[position]);
        subtitle.setText(subtitles[position]);

        return convertView;
    }
}
