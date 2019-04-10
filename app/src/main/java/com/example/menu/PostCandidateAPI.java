package com.example.menu;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class PostCandidateAPI {


    private static final String url = "http://piyushn88.pythonanywhere.com/categoryrest/v1/";


    public static PostCandidateAPI.PostCandidateService postcandidateservice = null;


    public static PostCandidateAPI.PostCandidateService getservice() {
        if (postcandidateservice == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            postcandidateservice = retrofit.create(PostCandidateAPI.PostCandidateService.class);
        }
        return postcandidateservice;
    }


    public interface PostCandidateService {
        @Multipart
        @POST("interviews/")
        Call<Feedback> uploadImage_feedback(@Part("round") RequestBody can_round,
                                            @Part("description") RequestBody can_description,
                                            @Part MultipartBody.Part image,
                                            @Part("interviewer") RequestBody can_interviewer,
                                            @Part("status") RequestBody can_status,
                                            @Part("candidate") RequestBody can_candidate);


    }
}
