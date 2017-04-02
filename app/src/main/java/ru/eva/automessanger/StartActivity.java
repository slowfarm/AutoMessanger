package ru.eva.automessanger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class StartActivity extends AppCompatActivity {

    List<String> text = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "id,first_name,last_name,sex,bdate,city"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONArray items = response.json.getJSONObject("response").getJSONArray("items");
                    for(int i=0; i< items.length(); i++) {
                        text.add(items.getJSONObject(i).getString("first_name")
                                + " "
                                +items.getJSONObject(i).getString("last_name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(StartActivity.this));
                MainFragmentAdapter adapter = new MainFragmentAdapter(text);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
