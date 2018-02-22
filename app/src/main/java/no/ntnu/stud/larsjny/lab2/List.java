package no.ntnu.stud.larsjny.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    private ListView list;

    private ArrayList<String> items = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);



        list = findViewById(R.id.listView);

        list.setAdapter(adapter);


        addItem("Test");
    }


    public void addItem(String label){
        items.add(label);
        adapter.notifyDataSetChanged();
    }
}
