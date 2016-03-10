package com.example.sharuvive.paymentscheduler_sep;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by Sharu Vive on 2/8/2016.
 */
public class List_activity extends ActionBarActivity {

    ListView paymentList;
    SimpleCursorAdapter adapter3;
    DatabaseHelper helper;
    SQLiteDatabase db;
    String selected_id;
    String value, upd, ex;
    Context context;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        context = this;

        helper = new DatabaseHelper(this); //initializing helper

        paymentList = (ListView) findViewById(R.id.list3);

        //assigning adapter,and position
        paymentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> arg0, View arg1, final int position, long arg3) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("Manage Expenses");

                // set the custom dialog components
                Button up = (Button) dialog.findViewById(R.id.Update);

                Button del = (Button) dialog.findViewById(R.id.Delete);
                // if button is clicked, close the custom dialog
                up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor row1 = (Cursor) arg0.getItemAtPosition(position); //get the position of item into the cursor
                        selected_id = row1.getString(0);
                        ex = row1.getString(0);

                        Intent myIntent = new Intent(List_activity.this, updatePayments.class);//redirecting to another activity
                        myIntent.putExtra("data key", ex);// pass your values and retrieve them in the other Activity using keyName
                        startActivity(myIntent);
                        fetchData3();
                    }
                });
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //set up the alert dialog box
                        AlertDialog.Builder builder = new AlertDialog.Builder(List_activity.this);

                        builder.setTitle("Confirm");
                        builder.setMessage("Do you want to delete this selected expenses?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                Cursor row = (Cursor) arg0.getItemAtPosition(position); //Get the position of item
                                selected_id = row.getString(0);

                                db = helper.getWritableDatabase();
                                //db.execSQL("delete ");
                                db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COL_1 + "=?", new String[]{selected_id}); //Delete the particular item
                                db.close();

                                fetchData3(); //fetch data again to listview to refresh
                                Toast.makeText(List_activity.this, "successfully deleted", Toast.LENGTH_LONG).show(); //Success message

                                dialog.cancel();
                            }

                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                                // Redirecting to another activity

                                dialog.cancel();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show(); //show the alert


                    }
                });

                dialog.show();

            }

        });

        Bundle data_from_list = getIntent().getExtras();
        value = data_from_list.getString("passed data key"); // retrieve the data using keyName
        fetchData3();

    }

    private void fetchData3() {
        db = helper.getReadableDatabase();
        Cursor c3 = db.query(DatabaseHelper.TABLE_NAME, null, DatabaseHelper.COL_3 + "='" + value + "'", null, null, null, null);
        adapter3 = new SimpleCursorAdapter(
                this,
                R.layout.row3,
                c3,
                new String[]{DatabaseHelper.COL_2, DatabaseHelper.COL_5, DatabaseHelper.COL_6},
                new int[]{R.id.tv_date, R.id.tv_desc, R.id.tv_amount});
        paymentList.setAdapter(adapter3);
    }

}
