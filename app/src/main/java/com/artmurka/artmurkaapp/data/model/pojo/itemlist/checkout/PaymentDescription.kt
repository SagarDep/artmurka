package com.artmurka.artmurkaapp.data.model.pojo.itemlist.checkout

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PaymentDescription {

    @SerializedName("pos")
    @Expose
    var pos: String? = null

    @SerializedName("dscr")
    @Expose
    var dscr: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null


}
