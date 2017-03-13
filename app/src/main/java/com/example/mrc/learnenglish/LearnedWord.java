package com.example.mrc.learnenglish;

import io.realm.RealmObject;

/**
 * Created by mrT on 23.01.2017.
 */

public class LearnedWord extends RealmObject {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
