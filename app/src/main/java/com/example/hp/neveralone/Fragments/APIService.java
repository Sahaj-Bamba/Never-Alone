package com.example.hp.neveralone.Fragments;

import com.example.hp.neveralone.Notifications.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:appication/json",
                    "Authorization:key=AAAAI52Eu1E:APA91bGTztWjli5oSnlNw1ve3By1VgwHUXgH-g-YIaBDgxXJvpWoOzjYs_JqfoZ26thvyatxaYoNzBvOyE4awLTsM0mC4e36UkyHpjvDR6rO2GHAAwXYFNmFn_aNN8cH6L0TQqawDauW"
            }
    )


    @POST("fcm/send")
    Call<MyResponse> sendNotification(Body Sender_body);
}
