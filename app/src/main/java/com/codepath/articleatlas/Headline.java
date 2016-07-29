package com.codepath.articleatlas;

/**
 * Created by melissahuang on 7/26/16.
 */
public class Headline {
    String main;
    String kicker;

    public String getMain() {
        return main;
    }

    public String getKicker() {
        return kicker;
    }

    public Headline(String main, String kicker) {
        this.main = main;
        this.kicker = kicker;
    }
}
