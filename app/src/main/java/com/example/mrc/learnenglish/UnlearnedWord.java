package com.example.mrc.learnenglish;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mrT on 23.01.2017.
 */

public class UnlearnedWord extends RealmObject {
    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
