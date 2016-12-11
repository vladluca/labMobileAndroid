package vluca.androidlab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by lucav on 11/9/2016.
 */

public class FragmentCarList extends Fragment {
    View view;
    private ListView mPlaceListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.car_list_layout, container, false);
        } catch (InflateException e) {
            Log.e("ERR", e.toString());
        }

        final DBWr dbWrapper = new DBWr(getContext(), null, null, 1);
        Car[] cars = dbWrapper.getCars();

        final ArrayList<Car> mCarList = new ArrayList<>();

        for (Car c: cars) {
            mCarList.add(c);
        }

        mPlaceListView = (ListView) view.findViewById(R.id.car_list_view);
        CarListAdapter adapter = new CarListAdapter(getActivity(), mCarList);
        mPlaceListView.setAdapter(adapter);
        mPlaceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(getActivity(), EditCarActivity.class);

                appInfo.putExtra("CAR_ID", ""+mCarList.get(position).getId());
                startActivity(appInfo);

                startActivity(appInfo);
            }
        });

        Button addCarBtn = (Button) view.findViewById(R.id.addCarBtn);
        addCarBtn.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appInfo = new Intent(getActivity(), EditCarActivity.class);
                startActivity(appInfo);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DBWr dbWrapper = new DBWr(getContext(), null, null, 1);
        Car[] cars = dbWrapper.getCars();
        final ArrayList<Car> mCarList = new ArrayList<>();
        for (Car c : cars) {
            mCarList.add(c);
        }
        mPlaceListView = (ListView) view.findViewById(R.id.car_list_view);
        CarListAdapter adapter = new CarListAdapter(getActivity(), mCarList);
        mPlaceListView.setAdapter(adapter);
        mPlaceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(getActivity(), EditCarActivity.class);
                appInfo.putExtra("CAR_ID", ""+mCarList.get(position).getId());
                startActivity(appInfo);
            }
        });
    }
}
