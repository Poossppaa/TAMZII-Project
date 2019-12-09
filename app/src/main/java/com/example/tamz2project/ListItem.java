package com.example.tamz2project;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.List;

public class ListItem extends ArrayAdapter<Level> {

    Context context;
    List<Level> levelData = null;
    int layoutResourceId;

    public ListItem(@NonNull Context context, int layoutResourceId, @NonNull List<Level> levelData) {
        super(context, layoutResourceId, levelData);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.levelData = levelData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LevelDataHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new LevelDataHolder();
            holder.levelName = (TextView)row.findViewById(R.id.levelName);
            holder.levelFileName = (TextView)row.findViewById(R.id.fileName);

            row.setTag(holder);
        }
        else
        {
            holder = (LevelDataHolder)row.getTag();
        }

        final Level entry = levelData.get(position);
        holder.levelName.setText(entry.levelName);
        holder.levelFileName.setText(entry.fileName);

        row.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    v.setBackgroundColor(Color.argb(100,100,100,100));

                    String levelFileName = entry.fileName;

                    Context appContext = context.getApplicationContext();
                    Intent intent = new Intent(appContext,MainActivity.class);
                    intent.putExtra("level",levelFileName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    appContext.startActivity(intent);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_DOWN){

                    v.setBackgroundColor(Color.argb(100,0,0,255));
                    return true;
                }
                else{
                    v.setBackgroundColor(Color.argb(100,100,100,100));
                }
                return false;
            }
        });

        return row;
    }

    static class LevelDataHolder
    {
        TextView levelName;
        TextView levelFileName;
    }
}
