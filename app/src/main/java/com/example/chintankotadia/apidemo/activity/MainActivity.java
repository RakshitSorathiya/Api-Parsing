package com.example.chintankotadia.apidemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chintankotadia.apidemo.R;
import com.example.chintankotadia.apidemo.adapter.MainAdapter;
import com.example.chintankotadia.apidemo.model.Example;
import com.example.chintankotadia.apidemo.services.ReqInterface;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private List<Example> list;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_demo);
        progressBar = findViewById(R.id.progress);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,LinearLayout.VERTICAL));
        list = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        loadResponse();
    }

    private void loadResponse() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ReqInterface request = retrofit.create(ReqInterface.class);

        Call<List<Example>> call = request.getMyJSON();

        call.enqueue(new Callback<List<Example>>() {


            @Override
            public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {

                if (response.isSuccessful()) {
                    // Do your success stuff...

                    List<Example> exampleList = response.body();
                    Example example = null;

                    for (int i = 0; i < exampleList.size(); i++) {
                        example = new Example();
                        String userid = String.valueOf(exampleList.get(i).getUserId());
                        String title = exampleList.get(i).getTitle();
                        String body = exampleList.get(i).getBody();
                        example.setUserId(Integer.valueOf(userid));
                        example.setTitle(title);
                        example.setBody(body);
                        list.add(example);
                    }
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "onResponse:" + list);
                    adapter = new MainAdapter(MainActivity.this, list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Example>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error in fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
