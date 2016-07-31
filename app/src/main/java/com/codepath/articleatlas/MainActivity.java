package com.codepath.articleatlas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends Activity {

    private final String API_KEY = "11e60a434d9544c2991a82206639d180";
    private final String BASE_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    private int pageCount = 0;
    private final int SETTINGS_RESULT = 10;
    List<Article> articleList;
    RecyclerView rvArticles;
    RecyclerView.Adapter articlesAdapter;
    MenuItem miActionProgressItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        articleList = new ArrayList<Article>();
        articlesAdapter = new ArticlesAdapter(this, articleList);
        rvArticles.setAdapter(articlesAdapter);
        rvArticles.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (item.getItemId() == R.id.action_settings) {
            openSettings();
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchTextAndSearch(View v) {
        EditText searchField = (EditText) findViewById(R.id.etSearchQuery);
        fetchArticlesWithSearchQuery(searchField.getText().toString(), null);
    }

    // Networking
    private void fetchArticlesWithSearchQuery(String query, HashMap<String, Object> extraParams) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = BASE_URL;
        RequestParams params = new RequestParams();
        params.put("q", query);
        params.put("page", pageCount);
        params.put("api-key", API_KEY);
        if (extraParams != null)  {
            for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
                String key = entry.getKey();
                Object object = entry.getValue();
                params.put(key, object);
            }
        }
        System.out.println("params: " + params);
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here
                Gson gson = new GsonBuilder().create();
                articleList.clear();
                articleList.addAll(ArticlesResponse.parseJSON(response).articleResponse.getArticleList());
                articlesAdapter.notifyDataSetChanged();
//                articlesAdapter.notifyItemRangeInserted(0, articleList.size());
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

    public Article getArticleAtPosition(int position) {
        return articleList.get(position);
    }

    public void openSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivityForResult(i, SETTINGS_RESULT);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HashMap<String, Object> params = new HashMap<>();
        if (resultCode == Activity.RESULT_OK) {
            String beginDate = data.getStringExtra("beginDate");
            String sortString = data.getStringExtra("sort");
            String[] newsArray = data.getStringArrayExtra("news");
            if (beginDate != null) {
                params.put("begin_date", beginDate);
            }
            if (sortString != null) {
                params.put("sort", sortString);
            }
            System.out.println("newsArray: " + newsArray);

            if (newsArray != null && newsArray.length > 0) {
                String filters = android.text.TextUtils.join(" ", newsArray);
                String newsString = "news_desk:(" + filters + ")";
                System.out.println("newsString: " + newsString);
                params.put("fq", newsString);
            }
            EditText searchField = (EditText) findViewById(R.id.etSearchQuery);

            fetchArticlesWithSearchQuery(searchField.getText().toString(), params);
        }
    }
}
