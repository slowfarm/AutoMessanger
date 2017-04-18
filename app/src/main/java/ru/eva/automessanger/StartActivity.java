package ru.eva.automessanger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
import ru.eva.automessanger.Models.Girls;
import ru.eva.automessanger.Models.Message;


public class StartActivity extends AppCompatActivity {

    private final static String ACCESS_TOKEN = "accessToken";

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        String accessToken = this.getIntent().getStringExtra(ACCESS_TOKEN);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        VKRequest request = VKApi.users().search(VKParameters.from(
                VKApiConst.FIELDS, "photo_max",
                VKApiConst.CITY, "72",
                VKApiConst.SEX, "1",
                VKApiConst.STATUS, "6",
                VKApiConst.AGE_FROM, "18",
                VKApiConst.AGE_TO, "24",
                VKApiConst.HAS_PHOTO, true,
                VKApiConst.COUNT, "100"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                realm.executeTransactionAsync(realm -> {
                    realm.delete(Girls.class);
                    try {
                        JSONArray jsonArray = response.json.getJSONObject("response").getJSONArray("items");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            realm.createObjectFromJson(Girls.class, jsonArray.getJSONObject(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                RealmResults<Girls> girls = realm.where(Girls.class).findAll();
                girls.addChangeListener(elements -> {
                    for (Girls element : elements) {
                        Application.getMessageId().getData(accessToken, element.getId(), "Привет)")
                                .enqueue(new Callback<Message>() {
                                    @Override
                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                        System.out.println(response.body().getResponse());
                                    }

                                    @Override
                                    public void onFailure(Call<Message> call, Throwable t) {
                                        System.out.println(t.toString());
                                    }
                                });
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                }

            });
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
            realm.close();
        }
    }
