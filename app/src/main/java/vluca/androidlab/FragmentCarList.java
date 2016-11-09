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

        final ArrayList<Car> mCarList = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            mCarList.add(new Car(i, "Mazda","Type" + i, i));
        }
        mPlaceListView = (ListView) view.findViewById(R.id.car_list_view);
        CarListAdapter adapter = new CarListAdapter(getActivity(), mCarList);
        mPlaceListView.setAdapter(adapter);
        mPlaceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(getActivity(), EditCarActivity.class);
                appInfo.putExtra("CAR_MARK", mCarList.get(position).getMark());
                appInfo.putExtra("CAR_MODEL", mCarList.get(position).getModel());
                startActivity(appInfo);
            }
        });

        return view;
    }
}
