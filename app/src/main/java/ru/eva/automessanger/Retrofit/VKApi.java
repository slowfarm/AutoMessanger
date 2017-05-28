package ru.eva.automessanger.Retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.eva.automessanger.Models.Message;
import ru.eva.automessanger.Models.ResponseWrapper;

public interface VKApi {
        @POST("messages.send")
        Call<Message> getData(@Query("access_token") String accessToken,
                              @Query("user_id") long userId,
                              @Query("message") String message);
        @POST("likes.add")
        Call<ResponseWrapper> setLike(@Query("access_token") String accessToken,
                                      @Query("type") String type,
                                      @Query("owner_id") long ownerId,
                                      @Query("item_id") String itemId
        );
    }
