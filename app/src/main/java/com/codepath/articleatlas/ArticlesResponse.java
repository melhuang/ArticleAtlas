package com.codepath.articleatlas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Created by melissahuang on 7/26/16.
 */
public class ArticlesResponse {

    @SerializedName("response")
    ArticleResponse articleResponse;

    public ArticleResponse getArticleResponse() {
        return articleResponse;
    }

    public static ArticlesResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        ArticlesResponse articlesResponse = gson.fromJson(response, ArticlesResponse.class);
        return articlesResponse;
    }
}