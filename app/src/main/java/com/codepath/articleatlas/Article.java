package com.codepath.articleatlas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melissahuang on 7/26/16.
 */
public class Article {
    Headline headline;
    List<Multimedia> multimedia;

    public Article(Headline headline, List<Multimedia> multimedia) {
        this.headline = headline;
        this.multimedia = multimedia;
    }
}
