package com.example.tamz2project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelSelectActivity extends Activity {

    String[] levelFileNames;
    Context context;
    ListView levelList;
    protected List<Level> levels = new ArrayList<Level>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_level_select);

        getLevels();

        levelList = (ListView) findViewById(R.id.levelList);
        ListItem arrayAdapter = new ListItem(this, R.layout.list_item_layout, levels);

        levelList.setAdapter(arrayAdapter);

    }




    private void getLevels(){
        try {
            levelFileNames = getAssets().list("json");
            assert levelFileNames != null;
            for(int i = 0; i<levelFileNames.length; i++){
                Level newLevel = new Level(levelFileNames[i],"Level " + i);
                levels.add(newLevel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}