package com.advpro2.basone.kongmalaew;

/**
 * Created by kanazang on 11/14/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.advpro2.basone.kongmalaew.Model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.OrderViewHolder>{
    private List<ProductModel> list;
    private  Context context;

    public ProductAdapter(List<ProductModel> list){
        this.list = list;
    }

    public ProductAdapter(Context c ,List<ProductModel> list){
        this.list = list;
        this.context=c;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {
        ProductModel product = list.get(position);

        holder.product.setText(product.getProduct_name());
        holder.price.setText(String.valueOf(product.getProduct_price()));

        //Integer.parseInt("1234");
        Picasso.with(context).load(product.getProduct_img()).placeholder(R.drawable.placeholder).into(holder.img);

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                //menu.add(holder.getAdapterPosition(), R.layout.order_que , 0,"menu");
                menu.add(holder.getAdapterPosition(), 1 , 0 , "bbb");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView product, price;
        ImageView img;

        public OrderViewHolder(View itemView){
            super(itemView);

            product = (TextView) itemView.findViewById(R.id.textView1);
            price = (TextView) itemView.findViewById(R.id.textView2);
            img = (ImageView) itemView.findViewById(R.id.imageView1);
        }

    }
}