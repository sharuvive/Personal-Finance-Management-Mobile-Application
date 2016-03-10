package com.example.sharuvive.paymentscheduler_sep;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class addPayment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    Spinner spinner2;

    DatabaseHelper myDb;
    EditText editDescription, editRs;
    Button btnAddData;
    String date;

    Button btnSetDate;
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;

    Button btnViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);



        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.subcategory, android.R.layout.simple_spinner_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        myDb = new DatabaseHelper(this);
        editDescription = (EditText)findViewById(R.id.editText_desc);
        editRs = (EditText)findViewById(R.id.editText_rs);
        btnAddData = (Button)findViewById(R.id.button_add);


        final Calendar calendar = Calendar.getInstance();
        year_x = calendar.get(Calendar.YEAR);
        month_x = calendar.get(Calendar.MONTH);
        day_x = calendar.get(Calendar.DAY_OF_MONTH);

        showDateDialog();

        addPayment();

        btnViewData = (Button)findViewById(R.id.button_view);

        viewData();
    }

    public void showDateDialog()
    {
        btnSetDate = (Button)findViewById(R.id.btn_setDate);

        btnSetDate.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;

            date = year_x+"/"+month_x+"/"+day_x;

        }
    };

    public void addPayment()
    {


        btnAddData.setOnClickListener(


                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String category = spinner.getSelectedItem().toString();
                        String subcategory = spinner2.getSelectedItem().toString();
                        String desc = editDescription.getText().toString();
                        String amt = editRs.getText().toString();

                        boolean isInserted = myDb.insertData(date, category, subcategory, desc, amt);
                        if(isInserted = true)
                            Toast.makeText(addPayment.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(addPayment.this, "Not Added..!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void viewData()
{
        btnViewData.setOnClickListener(
        new View.OnClickListener(){
@Override
public void onClick(View v){

        Intent intent = new Intent(addPayment.this, testList.class);
        startActivity(intent);


                       /* Cursor res = myDb.getAllData();
                        if(res.getCount() == 0){
                            showMessage("Error", "No Payments found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Id : "+ res.getString(0)+"\n");
                            buffer.append("Date : "+ res.getString(1)+"\n");
                            buffer.append("Category : "+ res.getString(2)+"\n");
                            buffer.append("SubCategory : "+ res.getString(3)+"\n");
                            buffer.append("Description : "+ res.getString(4)+"\n");
                            buffer.append("Amount : "+ res.getString(5)+"\n");

                            buffer.append("Status : "+ res.getString(6)+"\n");
                        }

                        showMessage("Payments", buffer.toString());*/
        }
        }
        );
        }

public void showMessage(String title, String Message)
        {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
        }

@Override
public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

@Override
public void onNothingSelected(AdapterView<?> parent) {

        }
        }
