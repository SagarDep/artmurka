package com.artmurka.artmurkaapp.Views.Fragments.Interfaces;


import com.artmurka.artmurkaapp.Model.Pojo.ItemList.Checkout.OrderDesc;

import java.util.List;

public interface ICheckoutFragment {
    void showCheckout(List<OrderDesc> list);

}
