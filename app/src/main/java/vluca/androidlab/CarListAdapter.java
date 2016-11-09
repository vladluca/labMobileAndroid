package vluca.androidlab;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lucav on 11/9/2016.
 */

public class CarListAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<Car> mCars;

    public CarListAdapter(Context context, ArrayList<Car> places) {
        this.context = context;
        this.mCars = places;
    }

    @Override
    public int getCount() {
        return mCars.size();
    }

    @Override
    public Object getItem(int i) {
        return mCars.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mCars.get(i).getFileId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.car_list_item, null);
        }
        TextView carMark = (TextView)view.findViewById(R.id.car_mark);
        carMark.setText(mCars.get(i).getMark());

        TextView carModel = (TextView)view.findViewById(R.id.car_model);
        carModel.setText(mCars.get(i).getModel());

        return view;
    }
}
