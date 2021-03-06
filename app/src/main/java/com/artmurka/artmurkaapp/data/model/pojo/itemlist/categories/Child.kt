package com.artmurka.artmurkaapp.data.model.pojo.itemlist.categories


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Child {

    @SerializedName("cat_name")
    @Expose
    var catName: String? = null
    @SerializedName("cat_url")
    @Expose
    var catUrl: String? = null
    @SerializedName("goods_count")
    @Expose
    var goodsCount: Int = 0
    @SerializedName("cat_id")
    @Expose
    var catId: String? = null

    @SerializedName("childs")
    @Expose
    var childs: String? = null
    @SerializedName("cat_descr")
    @Expose
    var catDescr: String? = null
    @SerializedName("cat_img")
    @Expose
    var catImg: String? = null

}