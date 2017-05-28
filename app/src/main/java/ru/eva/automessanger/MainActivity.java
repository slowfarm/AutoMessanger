package ru.eva.automessanger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.eva.automessanger.Adapters.MainFragmentAdapter;
import ru.eva.automessanger.Models.Girl;
import ru.eva.automessanger.Models.ResponseWrapper;


public class MainActivity extends AppCompatActivity {

    private final String ACCESS_TOKEN = "accessToken";
    private final String TAG = "MainActivity";

    Realm realm;
    MainFragmentAdapter adapter;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        String accessToken = this.getIntent().getStringExtra(ACCESS_TOKEN);
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MainFragmentAdapter(realm.where(Girl.class).findAll());
        recyclerView.setAdapter(adapter);

        gson = new GsonBuilder().create();

        VKRequest request = VKApi.users().search(VKParameters.from(
                VKApiConst.FIELDS, "photo_max, photo_id",
                VKApiConst.CITY, "72",
                VKApiConst.SEX, "1",
                VKApiConst.STATUS, "6",
                VKApiConst.AGE_FROM, "18",
                VKApiConst.AGE_TO, "24",
                VKApiConst.HAS_PHOTO, true,
                VKApiConst.COUNT, "20"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                realm.executeTransactionAsync(realm -> {
                    try {
                        JSONArray jsonArray = response.json.getJSONObject("response").getJSONArray("items");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Girl girl = gson.fromJson(jsonArray.getJSONObject(i).toString(), Girl.class);
                            if (isNotExist(girl, realm)) {
                                realm.copyToRealm(girl);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        });


        RealmResults<Girl> girls = realm.where(Girl.class).findAll();
        girls.addChangeListener(elements -> {
            adapter.notifyDataSetChanged();
            for (Girl element : elements) {
                String photoId = element.getPhotoId().split("_")[1];
                Application.getApi().setLike(accessToken, "photo", element.getId(), photoId)
                        .enqueue(new Callback<ResponseWrapper>() {
                            @Override
                            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                                try {
                                    Log.d(TAG, "onResponse: " + response.body().getResponse().getLikes());
                                }
                                catch(NullPointerException ex) {
                                    ex.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                try {
                    Thread.sleep(350);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isNotExist(Girl girl, Realm realm) {
        return realm.where(Girl.class).equalTo("id", girl.getId()).findFirst() == null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
