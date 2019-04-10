package com.example.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedbackList extends AppCompatActivity {

    String[] candidate;
    String[] date;
    String[] round;
    String[] interviewer;
    String[] status;
    URL[] image;
    ListView listView;
    CustomListView customListView;
    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView =  findViewById(R.id.list_view);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.URL).addConverterFactory(GsonConverterFactory.create()).build();

        Api api = retrofit.create(Api.class);
        Call<List<Data>> call = api.getData();
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

                List<Data> data = response.body();
                Data[] datalist = new Data[data.size()];
                candidate = new String[data.size()];
                date = new String[data.size()];
                status = new String[data.size()];
                round = new String[data.size()];
                interviewer = new String[data.size()];
                image = new URL[data.size()];

                for (int i = 0; i < data.size(); i++) {
                    datalist[i] = data.get(i);
                    candidate[i] = data.get(i).getCandidate();
                    status[i] = data.get(i).getStatus();
                    date[i] = data.get(i).getDate();
                    round[i] = data.get(i).getRound();
                    interviewer[i] = data.get(i).getInterviewer();
                    try {
                        image[i] = new URL(data.get(i).getImage());
                    } catch (MalformedURLException e) {

                    }
                }

                for (Data d : data) {
                    Log.d("name", d.getCandidate());
                    Log.d("date", d.getDate());
                    Log.d("description", d.getDescription());
                    Log.d("id", d.getId());
                    Log.d("image", d.getImage());
                    Log.d("status", d.getStatus());
                }

                customListView = new CustomListView(FeedbackList.this, candidate, round, date, interviewer, status, image);

               /* ArrayList<String> arrayList = new ArrayList();
                arrayList.addAll(Arrays.asList(status));
*/
               /* mAdapter = new ArrayAdapter<String>(FeedbackList.this,
                        android.R.layout.simple_list_item_1, arrayList);*/

                listView.setAdapter(customListView);
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customListView.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
