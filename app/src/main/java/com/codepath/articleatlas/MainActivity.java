package com.codepath.articleatlas;

import android.app.Activity;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends Activity {

    private final String API_KEY = "11e60a434d9544c2991a82206639d180";
    private final String BASE_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    List<Article> articleList;
    RecyclerView rvArticles;
    RecyclerView.Adapter articlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Networking
    private void fetchArticlesWithSearchQuery(View v) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = BASE_URL;
        RequestParams params = new RequestParams();
        params.put("q", "text from view");
        params.put("api-key", API_KEY);
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here
                Gson gson = new GsonBuilder().create();
                articleList = ArticlesResponse.parseJSON(response).articleList;
//                articlesAdapter = new Articl(getApplicationContext(), articleList);
//                lvMovies.setAdapter(moviesAdapter);
            }

            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject object) {
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable t) {
//                public void onFailure(int statusCode, Header[] headers, String failure, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                System.out.println("failure: " + failure);

            }
        });
    }
}
