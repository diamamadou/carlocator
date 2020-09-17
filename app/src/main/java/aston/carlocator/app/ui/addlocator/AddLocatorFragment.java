package aston.carlocator.app.ui.addlocator;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import aston.carlocator.app.API;
import aston.carlocator.app.Locator;
import aston.carlocator.app.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;

public class AddLocatorFragment extends Fragment implements LocationListener {

    private API api;
    LocationManager locationManager;
    Location location;
    private EditText car_name;
    private Button add_btn;
    private TextView message;
    private String date;
    private Double lng;
    private Double lat;
    private View root;

    public AddLocatorFragment() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        api = retrofit.create(API.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add_locator, container, false);

        message = (TextView)root.findViewById(R.id.message);
        add_btn = (Button) root.findViewById(R.id.add_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isGranted();
            }
        });
        return root;
    }

    public void addLocator(View view) {
        car_name = (EditText)view.findViewById(R.id.car_name);

        String tCarName = car_name.getText().toString();

        if(tCarName.length() > 0) {
            Date d = new Date();
            SimpleDateFormat ft =
                    new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
            date = ft.format(d);
            Locator locator = new Locator(tCarName, date, lng, lat);

            Call<Locator> call = api.addLocator(locator);

            call.enqueue(new Callback<Locator>() {
                @Override
                public void onResponse(Call<Locator> call, Response<Locator> response) {
                    car_name.setText("");
                    int status = response.code();
                    System.out.println("STATUS ===> "+ status);
                    if(status == 200) {
                        message.setText("Locator added successfully");
                        message.setTextColor(Color.parseColor("#08873f"));
                    }
                }

                @Override
                public void onFailure(Call<Locator> call, Throwable t) {
                    System.out.println("ERROORRR ===> " + t);
                    message.setText("Error");
                    message.setTextColor(Color.parseColor("#87081f"));
                }
            });
        } else {
            message.setText("Fill Car name input");
            message.setTextColor(Color.parseColor("#87081f"));
        }
    }

    private void isGranted() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("PERMISSION ==> GRANTED");
            locationManager = (LocationManager) getContext()
                .getSystemService(LOCATION_SERVICE);

            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000 * 60 * 1,
                10, this);
            Log.d("GPS Enabled", "GPS Enabled");
            if (locationManager != null) {
                location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    addLocator(root);
                } else {
                    message.setText("No location founded");
                    message.setTextColor(Color.parseColor("#87081f"));
                }
            }
        } else {
            System.out.println("PERMISSION ==> NOT GRANTED");
            ActivityCompat.requestPermissions(getActivity(), new String[] {ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}