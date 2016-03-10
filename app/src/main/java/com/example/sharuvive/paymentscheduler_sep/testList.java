package com.example.sharuvive.paymentscheduler_sep;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Sharu Vive on 2/9/2016.
 */
public class testList extends AppCompatActivity {
    ListView listView ;

    SimpleCursorAdapter adapter;
    DatabaseHelper helper;
    //SQLiteDatabase db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_list);

        helper = new DatabaseHelper(this); //initialize the helper

        listView = (ListView)findViewById(R.id.list_test);

        fetchData();

    }

    private void fetchData() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = helper.getAllData();
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.row2,
                c,
                new String[]{DatabaseHelper.COL_3},
                new int[]{R.id.lbl});
        listView.setAdapter(adapter); //set the adapter to listview
    }
}
