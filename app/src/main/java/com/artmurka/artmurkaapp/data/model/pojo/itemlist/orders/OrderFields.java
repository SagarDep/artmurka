
package com.artmurka.artmurkaapp.data.model.pojo.itemlist.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderFields {

    @SerializedName("2")
    @Expose
    private com.artmurka.artmurkaapp.data.model.pojo.itemlist.orders._2 _2;
    @SerializedName("1")
    @Expose
    private com.artmurka.artmurkaapp.data.model.pojo.itemlist.orders._1 _1;

    public com.artmurka.artmurkaapp.data.model.pojo.itemlist.orders._2 get2() {
        return _2;
    }

    public void set2(com.artmurka.artmurkaapp.data.model.pojo.itemlist.orders._2 _2) {
        this._2 = _2;
    }

    public com.artmurka.artmurkaapp.data.model.pojo.itemlist.orders._1 get1() {
        return _1;
    }

    public void set1(com.artmurka.artmurkaapp.data.model.pojo.itemlist.orders._1 _1) {
        this._1 = _1;
    }

}