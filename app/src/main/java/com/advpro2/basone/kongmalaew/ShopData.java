package com.advpro2.basone.kongmalaew;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.advpro2.basone.kongmalaew.Model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kanazang on 11/14/2017.
 */

public class ShopData extends BaseAdapter {
    Context mContext;
    String[] strName;
    int[] resId;
    float[] price;
    ArrayList <ProductModel> productModel;

    public ShopData(Context context, ArrayList<ProductModel> productModel) {
        this.mContext= context;
        this.productModel=productModel;

    }

    public ShopData(Context mContext, String[] strName, int[] resId, float[] price) {
        this.mContext = mContext;
        this.strName = strName;
        this.resId = resId;
        this.price = price;
    }


    @Override
    public int getCount() {
        return productModel.size();
    }

    public Object getItem(int position) {
        return productModel.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.row_layout, parent, false);

        TextView textView1 = (TextView)view.findViewById(R.id.textView1);
        TextView textView2 = (TextView)view.findViewById(R.id.textView2);
        Log.i("kuy",productModel.get(position).getProduct_name());
        textView1.setText(productModel.get(position).getProduct_brand()+" "+productModel.get(position).getProduct_name());
        textView2.setText(String.valueOf(productModel.get(position).getProduct_price())+" Baht");
//        textView1.setText(strName[position]);
//        textView2.setText(String.valueOf(price)+" Baht");

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
//        imageView.setBackgroundResource(resId[position]);
        Picasso.with(mContext).load(productModel.get(position).getProduct_img()).placeholder(R.drawable.placeholder).into(imageView);
        Log.i("kuyja",productModel.get(position).getProduct_img());
        return view;
    }
}