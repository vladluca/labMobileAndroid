package vluca.androidlab;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by lucav on 11/9/2016.
 */

public class EditCarActivity extends Activity {
    DatePickerDialog dateStartPicker;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
    DBWr dbWrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbWrapper = new DBWr(getApplicationContext(), null, null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_edit_activity);
        final TextView carIDField = (TextView) findViewById(R.id.car_id_field);
        final TextView carMarkField = (TextView) findViewById(R.id.car_mark_field);
        final TextView carModelField = (TextView) findViewById(R.id.car_model_field);
        final TextView carYear = (TextView) findViewById(R.id.carYearTextField);
        final TextView carFuelField = (TextView) findViewById(R.id.fuel_text);
        carYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                dateStartPicker = new DatePickerDialog(EditCarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar c = Calendar.getInstance();
                        c.set(i, i1, i2);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
                        carYear.setText(df.format(c.getTime()));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                //c.add(Calendar.DAY_OF_MONTH, 1);
                dateStartPicker.getDatePicker().setMinDate(c.getTimeInMillis());
                dateStartPicker.show();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("CAR_ID")) {
            Button deleteCarBtn = (Button) findViewById(R.id.car_delete_btn);
            final Car car = dbWrapper.getCar(Integer.parseInt(extras.getString("CAR_ID")));
            deleteCarBtn.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbWrapper.deleteCar(car.getId());
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), "Deleted", duration);
                    toast.show();
                    finish();
                }
            });
            carIDField.setText(String.valueOf(car.getId()));
            carMarkField.setText(car.getMark());
            carModelField.setText(car.getModel());
            carYear.setText(df.format(car.getYear()));
            carFuelField.setText(DBWr.implode(" ", car.getFuel()));
            LineChart chart = (LineChart) findViewById(R.id.chart);
            int[] performance = car.getFuel();

            List<Entry> entries = new ArrayList<Entry>();
            int i = 0;
            for (int data : performance) {
                // turn your data into Entry objects
                entries.add(new Entry(i, data));
                i++;
            }
            LineDataSet dataSet = new LineDataSet(entries, "Fuel");
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.invalidate(); // refresh
        }
        Button saveBtn = (Button) findViewById(R.id.carSave);
        saveBtn.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBWr dbWrapper = new DBWr(getApplicationContext(), null, null, 1);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
                try{
                    String[] parts = carFuelField.getText().toString().split(" ");
                    Log.e("PARTS", Arrays.toString(parts));
                    int[] fuel = new int[parts.length];
                    for(int n = 0; n < parts.length; n++) {
                        fuel[n] = Integer.parseInt(parts[n]);
                    }
                    Car c = new Car(
                            Integer.parseInt(carIDField.getText().toString()),
                            carMarkField.getText().toString(),
                            carModelField.getText().toString(),
                            df.parse(carYear.getText().toString()),
                            fuel
                    );
                    int duration = Toast.LENGTH_SHORT;
                    String text = dbWrapper.saveCar(c) ? "Saved" : "An error has occured";
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
