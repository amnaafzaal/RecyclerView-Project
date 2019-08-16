package com.example.testproject.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testproject.Handler.DatabaseHelper;
import com.example.testproject.R;
import com.example.testproject.Utils.AVLoadingIndicatorDialog;
import com.example.testproject.Utils.Constants;
import com.example.testproject.adapter.AlbumAdapter;
import com.example.testproject.model.AlbumData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<AlbumData> dataList;
    AlbumAdapter albumAdapter;
    DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        dataList = new ArrayList<>();

        db = new DatabaseHelper(getApplicationContext());

        dataList.addAll(db.getAllAlbums());

        albumAdapter = new AlbumAdapter(getApplicationContext(), dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(albumAdapter);

        StringRequest request = new StringRequest(Constants.getServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("onResponse: ", response);


                try {
                    if (response != null) {

                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray != null)
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.optJSONObject(i);
                                AlbumData albumData = new AlbumData(obj);
                                dataList.add(albumData);
                            }

                            albumAdapter.replaceAlbums(dataList);

                    } else {
                        Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error in Response", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    private void createAlbum(String data) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertAlbumData(data);

        // get the newly inserted note from db
        AlbumData n = db.getAlbumData(id);

        if (n != null) {
            // adding new note to array list at 0 position
            dataList.add(0, n);

            // refreshing the list
            albumAdapter.notifyDataSetChanged();

        }
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0){
           getFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
    }
}
