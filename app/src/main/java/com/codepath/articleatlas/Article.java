package com.codepath.articleatlas;

import java.util.List;

/**
 * Created by melissahuang on 7/26/16.
 */
public class Article {

    String web_url;
    Headline headline;
    List<Multimedia> multimedia;

    public String getWebUrl() { return web_url; }

    public Headline getHeadline() {
        return headline;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public Article(String web_url, Headline headline, List<Multimedia> multimedia) {
        this.web_url = web_url;
        this.headline = headline;
        this.multimedia = multimedia;
    }
}
