package com.example.krystiano.asystentremontu.database;

import java.io.Serializable;

/**
 * Created by Beryl
 * on 19.03.2016
 */
public class AssistantUserConfig {
    public String comment;

    public String pathToImg;

    public String date;

   public String price;

    public AssistantUserConfig(String comment,String pathToImg,String date,String price){
        this.comment = comment;
        this.pathToImg = pathToImg;
        this.date = date;
        this.price=price;
    }
}
