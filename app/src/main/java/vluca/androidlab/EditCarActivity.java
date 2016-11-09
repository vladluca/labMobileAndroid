package vluca.androidlab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by lucav on 11/9/2016.
 */

public class EditCarActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_edit_activity);
        TextView carMarkField = (TextView) findViewById(R.id.car_mark_field);
        TextView carModelField = (TextView) findViewById(R.id.car_model_field);

        Bundle extras = getIntent().getExtras();
        carMarkField.setText(extras.getString("CAR_MARK"));
        carModelField.setText(extras.getString("CAR_MODEL"));
    }
}
