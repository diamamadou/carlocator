package aston.carlocator.app.ui.locators;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import aston.carlocator.app.API;
import aston.carlocator.app.Locator;
import aston.carlocator.app.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class locatorsList extends Fragment {
    private API api;
    private TextView locator;

    public locatorsList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_locators, container, false);

        getList(root);

        return root;
    }

    public void getList(View view) {
        locator = (TextView)view.findViewById(R.id.locator);
        Call<List<Locator>> call = api.getLocators();

        call.enqueue(new Callback<List<Locator>>() {
            @Override
            public void onResponse(Call<List<Locator>> call, Response<List<Locator>> response) {
                int status = response.code();
                if(status == 200) {
                    List<Locator> locators = response.body();
                    String content = "";
                    if(locators.size() > 0) {
                        for (Locator locator : locators) {
                            content += "En date du " + locator.getDate() + ", ma " +
                                    locator.getCarName() + " se situe à " + locator.getLng() + ", " +
                                    locator.getLat() + "\n\n";
                            System.out.println(locator);
                        }
                    } else {
                        content = "0 locators";
                    }
                    locator.setText(content);
                }
            }

            @Override
            public void onFailure(Call<List<Locator>> call, Throwable t) {
                System.out.println("ERROORRR ===> " + t);
                locator.setText("Error");
                locator.setTextColor(Color.parseColor("#87081f"));
            }
        });
    }
}