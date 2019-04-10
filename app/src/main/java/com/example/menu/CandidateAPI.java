package com.example.menu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class CandidateAPI {
    private static final String url = "http://piyushn88.pythonanywhere.com/categoryrest/v1/";

    public static CandidateAPI.CandidateService candidateservice = null;

    public static CandidateAPI.CandidateService getservice() {
        if (candidateservice == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            candidateservice = retrofit.create(CandidateAPI.CandidateService.class);
        }
        return candidateservice;
    }


    public interface CandidateService {
        @GET("candidates_inprocess")
        Call<List<Candidate>> getcandidateList();
    }
}
