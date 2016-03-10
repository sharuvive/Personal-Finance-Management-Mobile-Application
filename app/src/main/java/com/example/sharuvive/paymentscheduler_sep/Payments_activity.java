package com.example.sharuvive.paymentscheduler_sep;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Sharu Vive on 2/8/2016.
 */
public class Payments_activity extends Activity {

    ListView paymentslist;
    SimpleCursorAdapter adapter;
    DatabaseHelper helper;
    SQLiteDatabase db;
    String selected_did;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments_activity);

        helper = new DatabaseHelper(this); //initialize the helper

        paymentslist = (ListView) findViewById(R.id.lv_payments);

        fetchData();

        paymentslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                String payment;

                Cursor row = (Cursor) adapter.getItemAtPosition(position);
                selected_did = row.getString(0);
                payment = row.getString(2); //get the name of the selected item


                Intent myIntent = new Intent(Payments_activity.this, List_activity.class);
                myIntent.putExtra("passed data key", payment);// pass your values and retrieve them in the other Activity using keyName
                startActivity(myIntent);

            }
        });
    }

    //fetch data from database and load it to database
    private void fetchData() {
        db = helper.getReadableDatabase();
        Cursor c = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null); //cursor have set of data related to query

        adapter = new SimpleCursorAdapter(
                this,
                R.layout.row2,
                c,
                new String[]{DatabaseHelper.COL_3},
                new int[]{R.id.lbl});
        paymentslist.setAdapter(adapter); //set the adapter to listview
    }
}
