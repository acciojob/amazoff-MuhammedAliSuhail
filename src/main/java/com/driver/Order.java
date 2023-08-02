package com.driver;

import org.springframework.beans.factory.annotation.Autowired;

public class Order {

    private String id;
    private int deliveryTime;
@Autowired
Services ser;
    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM/
this.id=id;

this.deliveryTime=StringTimetoInteger(deliveryTime);
    }
    private Integer StringTimetoInteger(String id){
        String ans="";
        for(int i=0;i<id.length();i++){
            if(id.charAt(i)!=':')ans+=id.charAt(i);
        }
        return Integer.parseInt(ans);
    }
    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
