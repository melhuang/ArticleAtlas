package com.codepath.articleatlas;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by melissahuang on 7/28/16.
 */
public class ArticleResponse {

    @SerializedName("docs")
    ArrayList<Article> articleList;

    public ArrayList<Article> getArticleList() {
        return articleList;
    }

    // public constructor is necessary for collections
    public ArticleResponse() {
        articleList = new ArrayList<Article>();
    }
}
