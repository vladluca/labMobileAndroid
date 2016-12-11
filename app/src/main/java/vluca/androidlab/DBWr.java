package vluca.androidlab;

/**
 * Created by lucav on 12/11/2016.
 */


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class DBWr extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Cars.db";
    //PLAYERS TABLE
    private static final String TABLE_CARS = "cars";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_MARK = "mark";
    private static final String COLUMN_MODEL = "model";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_FUEL = "fuel";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

    public DBWr(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String steps_query = "CREATE TABLE " + TABLE_CARS + " ( "+
                COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                COLUMN_MARK + " INTEGER, " +
                COLUMN_MODEL + " TEXT, " +
                COLUMN_YEAR + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                COLUMN_FUEL + " TEXT" +
                " );";
        db.execSQL(steps_query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
        onCreate(db);
    }

    public static String implode(String separator, int... foo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < foo.length ; i++) {
            sb.append(foo[i]);
            if (i != foo.length - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    boolean saveCar(Car car){
        if (car != null)
        {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, car.getId());
            values.put(COLUMN_MARK, car.getMark());
            values.put(COLUMN_MODEL, car.getModel());
            values.put(COLUMN_YEAR, dateFormat.format(car.getYear()));
            values.put(COLUMN_FUEL, implode(" ", car.getFuel()));
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_CARS + " WHERE " + COLUMN_ID + " ='" + car.getId() + "';");
            db.insert(TABLE_CARS, null, values);

            return true;
        }
        return false;
    }

    public Car getCar(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CARS + " WHERE " + COLUMN_ID + "='" + id + "';", null);
        if (c.moveToFirst())
        {
            try {
                String[] parts = c.getString(4).split(" ");
                int[] fuel = new int[parts.length];
                for(int n = 0; n < parts.length; n++) {
                    fuel[n] = Integer.parseInt(parts[n]);
                }
                Car p = new Car(
                        Integer.parseInt(c.getString(0)),
                        c.getString(1),
                        c.getString(2),
                        dateFormat.parse(c.getString(3)),
                        fuel
                );
                c.close();

                return p;
            } catch (Exception ignored) {
                c.close();

                return null;
            }
        }
        c.close();


        return null;
    }

    private int getCarCount() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CARS;
        Cursor c = db.rawQuery(sql, null);
        int count = c.getCount();
        c.close();
        return count;
    }

    Car[] getCars(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CARS + ";", null);
        int carCount = this.getCarCount();
        Car[] cars = new Car[carCount];
        if (c.moveToFirst())
        {
            for (int i = 0 ; i < carCount ; i++) {
                try {
                    Log.e("CURSOR", c.getString(0));
                    Log.e("CURSOR", c.getString(1));
                    Log.e("CURSOR", c.getString(2));
                    Log.e("CURSOR", c.getString(3));
                    Log.e("CURSOR", c.getString(4));
                    String[] parts = c.getString(4).split(" ");
                    int[] fuel = new int[parts.length];
                    for(int n = 0; n < parts.length; n++) {
                        fuel[n] = Integer.parseInt(parts[n]);
                    }
                    cars[i] = new Car(
                            Integer.parseInt(c.getString(0)),
                            c.getString(1),
                            c.getString(2),
                            dateFormat.parse(c.getString(3)),
                            fuel
                    );
                } catch (Exception e) {e.printStackTrace();}
                c.moveToNext();
            }
        }
        c.close();

        return cars;
    }

    public void deleteCar(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CARS + " WHERE " + COLUMN_ID + " ='" + id + "';");
    }
}
