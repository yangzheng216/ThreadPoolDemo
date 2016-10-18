package com.parbat.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parbat.threadpool.ExecutorLifeCircle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2016/10/18.
 */
public class ExecutorServiceLifeCircleActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,getData()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(ExecutorServiceLifeCircleActivity.this,"请看logcat",Toast.LENGTH_SHORT).show();
                if(position == 0){
                    new ExecutorLifeCircle().run1();
                }else if(position == 1){
                    new  ExecutorLifeCircle().run1();
                }
            }
        });
        this.setContentView(listView);
    }

    public List<String> getData(){
        List<String> data = new ArrayList<String>();
        data.add("run1");
        data.add("run2");
        return data;
    }
}
