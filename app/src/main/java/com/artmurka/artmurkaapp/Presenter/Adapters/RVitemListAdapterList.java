package com.artmurka.artmurkaapp.Presenter.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artmurka.artmurkaapp.Model.InterfacesModel.IBasket;
import com.artmurka.artmurkaapp.Model.InterfacesModel.ICheckoutRequest;
import com.artmurka.artmurkaapp.Model.InterfacesModel.IWishList;
import com.artmurka.artmurkaapp.Model.Modules.BasketRequest;
import com.artmurka.artmurkaapp.Model.Modules.CheckoutRequest;
import com.artmurka.artmurkaapp.Model.Modules.WishListRequest;
import com.artmurka.artmurkaapp.Model.Pojo.ItemList.Checkout.CheckoutAllGoods;
import com.artmurka.artmurkaapp.Model.Pojo.ItemList.GoodsProperties;
import com.artmurka.artmurkaapp.Model.Pojo.ItemList.ItemBasket.BasketItems;
import com.artmurka.artmurkaapp.Model.Pojo.ItemList.WishList.WishList;
import com.artmurka.artmurkaapp.R;
import com.artmurka.artmurkaapp.Views.Activities.SelectedGood;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RVitemListAdapterList extends RecyclerView.Adapter<RVitemListAdapterList.ViewHolder> {
    private ArrayList<GoodsProperties> successList;
    Context ctx;

    public RVitemListAdapterList(Context context) {
        this.ctx = context;
        successList = new ArrayList<>();
    }

    public void setData(ArrayList<GoodsProperties> list) {
        if (list != null && list.size() > 0) {
            this.successList.clear();
            this.successList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_list, parent, false);
        final ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvItemName.setText(successList.get(position).getEntryTitle());
        holder.tvPrice.setText(successList.get(position).getEntryPrice().getPrice() + "");
        Picasso.with(ctx).load(successList.get(position).getEntryPhoto().getDefPhoto().getThumb()).into(holder.ivItemPhoto);
        Log.d("Log.d", successList.get(position).getEntryIsInBasket()+"");
        holder.ivToBasket.setImageResource(R.drawable.basketfill_small_grey);

        holder.ivToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RotateAnimation anim = new RotateAnimation(-10, 10, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(Animation.RELATIVE_TO_PARENT);
                anim.setDuration(200);
                holder.ivToBasket.startAnimation(anim);

                if (successList.get(position).getEntryIsInBasket() == 0) {
                    //в корзину
                    IBasket basket = new BasketRequest();
                    Observable<BasketItems> observable = basket.toBasket(successList.get(position).getEntryId());

                    observable.subscribe(new Observer<BasketItems>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(BasketItems value) {
                            Log.d("Log.d", new Gson().toJson(value.getSuccess().getBasket()));
                            successList.get(position).setEntryIsInBasket(1);
                            Toast.makeText(ctx, successList.get(position).getEntryTitle() + " успішно додано до кошика. Id=" + successList.get(position).getEntryId(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("Log.d", "onError " + e.toString());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
                }else {

                    ICheckoutRequest request = new CheckoutRequest();
                    Call<CheckoutAllGoods> call = request.recountCheckoutData(successList.get(position).getEntryId(), "0");
                    call.enqueue(new Callback<CheckoutAllGoods>() {
                        @Override
                        public void onResponse(Call<CheckoutAllGoods> call, Response<CheckoutAllGoods> response) {
                            successList.get(position).setEntryIsInBasket(0);
                            Toast.makeText(view.getContext(), "Видалено з корзини", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFailure(Call<CheckoutAllGoods> call, Throwable t) {}
                    });

                }
            }
        });

        holder.ivToWish.setImageResource(
                successList.get(position).getEntryIsInWishlist() == 1 ?
                        R.drawable.heart_small_orange :
                        R.drawable.heart_small);

        holder.ivToWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, successList.get(position).getEntryTitle() + " додано до бажань", Toast.LENGTH_SHORT).show();
                //animation
                RotateAnimation anim = new RotateAnimation(-10, 10, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(Animation.RELATIVE_TO_PARENT);
                anim.setDuration(200);
                holder.ivToWish.startAnimation(anim);


                IWishList iWishList = new WishListRequest();
                Call<WishList> obs = iWishList.toWishList(successList.get(position).getEntryId());
                obs.enqueue(new Callback<WishList>() {
                    @Override
                    public void onResponse(Call<WishList> call, Response<WishList> response) {
                        if (successList.get(position).getEntryIsInWishlist() == 1) {
                            successList.get(position).setEntryIsInWishlist(0);
                            holder.ivToWish.setImageResource(R.drawable.heart_small);
                        } else {
                            successList.get(position).setEntryIsInWishlist(1);
                            holder.ivToWish.setImageResource(R.drawable.heart_small_orange);
                        }
                    }

                    @Override
                    public void onFailure(Call<WishList> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return successList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvItemName, tvPrice;
        public ImageView ivItemPhoto, ivToBasket, ivToWish;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.item_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            ivItemPhoto = (ImageView) itemView.findViewById(R.id.ivItemPhoto);
            ivToBasket = (ImageView) itemView.findViewById(R.id.ivToBasket);
            ivToWish = (ImageView) itemView.findViewById(R.id.ivToWish);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //to aboutGoods activity
                    Toast.makeText(v.getContext(), successList.get(getAdapterPosition()) + "\n", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(itemView.getContext(), SelectedGood.class);
                    String id = successList.get(getAdapterPosition()).getEntryId();
                    intent.putExtra("id", id);
                    intent.putExtra("inWish", successList.get(getAdapterPosition()).getEntryIsInWishlist());
                    intent.putExtra("inBasket", successList.get(getAdapterPosition()).getEntryIsInBasket());
                    itemView.getContext().startActivity(intent);

                }
            });
        }
    }
}