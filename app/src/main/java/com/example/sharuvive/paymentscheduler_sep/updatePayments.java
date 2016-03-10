package com.example.sharuvive.paymentscheduler_sep;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sharu Vive on 2/8/2016.
 */
public class updatePayments extends Activity implements View.OnClickListener {

    public static String SELECTED_DATE = null;

    String data2, data3, data4, data5;
    DatabaseHelper helper;
    SQLiteDatabase db;
    EditText rs, disc;
    TextView txr, tx5;
    static TextView date;
    Button btn66, btn;
    TextView ed, date2;
    String value_in_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatepayment_activity);

        btn = (Button) findViewById(R.id.btn_save);
        btn.setOnClickListener(this);

        helper = new DatabaseHelper(updatePayments.this);


        Bundle data_from_list = getIntent().getExtras();
        value_in_tv = data_from_list.getString("data key"); // retrieve the data using keyName

        ed = (TextView) findViewById(R.id.u_spinner);
        disc = (EditText) findViewById(R.id.u_description);
        rs = (EditText) findViewById(R.id.u_rs);
        date = (TextView) findViewById(R.id.et_date1);

        fetchData4(); //call the fetch method

    }

    private void fetchData4() {
        db = helper.getReadableDatabase();
        Cursor c = db.query(DatabaseHelper.TABLE_NAME, null, DatabaseHelper.COL_1 + "='" + value_in_tv + "'", null, null, null, null); //get the items from the database for the particular id
        if (c.moveToFirst()) {
            data2 = c.getString(c.getColumnIndex(DatabaseHelper.COL_3)); //get the values from the database
            data3 = c.getString(c.getColumnIndex(DatabaseHelper.COL_5));
            data4 = c.getString(c.getColumnIndex(DatabaseHelper.COL_6));
            data5 = c.getString(c.getColumnIndex(DatabaseHelper.COL_2));

            ed.setText(data2); //assign the values to the textfields
            disc.setText(data3);
            rs.setText(data4);
            date.setText(data5);



        }
    }
    @Override
    public void onClick(View v) {

        String desc = disc.getText().toString();
        String Rs = rs.getText().toString();
        String dat = date.getText().toString();

        ContentValues value = new ContentValues();

        value.put(DatabaseHelper.COL_5, disc.getText().toString());  //get data from the textfields to database
        value.put(DatabaseHelper.COL_6, rs.getText().toString());
        value.put(DatabaseHelper.COL_1, date.getText().toString());
        db = helper.getWritableDatabase();
        db.update(DatabaseHelper.TABLE_NAME, value, " " + DatabaseHelper.COL_1 + "='" + value_in_tv + "'", null); //update the values to the database
        db.close();
        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_LONG).show(); //success message

        Intent i = new Intent(updatePayments.this, addPayment.class); //redirecting another activity
        startActivity(i);

    }
}
