package com.example.mrc.learnenglish;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mrT on 08.03.2017.
 */

public class Updates extends RealmObject {

    private Date date;

    private Integer version=0;
    public Date getDate(){
        return date;
    }
    public Integer getVersion(){
        return version;
    }
    public  void setVersion(Integer version){
        this.version=version;
    }
    public void setDate(Date date){
        this.date=date;
    }

}
