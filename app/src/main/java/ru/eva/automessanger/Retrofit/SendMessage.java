package ru.eva.automessanger.Retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.eva.automessanger.Models.Message;

public interface SendMessage {
        @POST("messages.send")
        Call<Message> getData(@Query("access_token") String accessToken,
                              @Query("user_id") int userId,
                              @Query("message") String message);
    }
