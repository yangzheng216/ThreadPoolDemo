package com.parbat.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView)this.findViewById(R.id.mainListView);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,getData()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                if(position == 0){//类别
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this.getApplicationContext(),ExecutorServiceTypeActivity.class);
                    startActivity(intent);
                }else if(position == 1){//生命周期
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this.getApplicationContext(),ExecutorServiceLifeCircleActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public List<String> getData(){
        List<String> data = new ArrayList<String>();
        data.add("ExecutorService类别");
        data.add("ExecutorService生命周期");
        return data;
    }
}
