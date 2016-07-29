package com.codepath.articleatlas;

import java.util.List;

/**
 * Created by melissahuang on 7/26/16.
 */
public class Article {
    Headline headline;
    List<Multimedia> multimedia;

    public Headline getHeadline() {
        return headline;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public Article(Headline headline, List<Multimedia> multimedia) {
        this.headline = headline;
        this.multimedia = multimedia;
    }
}
