
package com.artmurka.artmurkaapp.Model.Pojo.ItemList.Good;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Good {

    @SerializedName("success")
    @Expose
    private Success success;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

}
