package com.codepath.articleatlas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melissahuang on 7/26/16.
 */
public class ArticlesResponse {

    @SerializedName("docs")
    List<Article> articleList;

    // public constructor is necessary for collections
    public ArticlesResponse() {
        articleList = new ArrayList<Article>();
    }

    public static ArticlesResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        ArticlesResponse articlesResponse = gson.fromJson(response, ArticlesResponse.class);
        return articlesResponse;
    }
}