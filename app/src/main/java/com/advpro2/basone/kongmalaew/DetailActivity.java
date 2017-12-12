package com.advpro2.basone.kongmalaew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView tvProductName,tvProductPrice,tvProductUploadby,tvProductDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
//        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b3e5fc")));
        Intent intent = getIntent();

        String product_name=intent.getStringExtra("product_name");
        Double product_price=intent.getDoubleExtra("product_price",0);
        String product_uploadby=intent.getStringExtra("product_uploadby");
        String product_img=intent.getStringExtra("product_img");
        String product_brand=intent.getStringExtra("product_brand");
        String product_detail=intent.getStringExtra("product_detail");
        Log.i("product_price",product_price.toString());
        ImageView imageView = findViewById(R.id.product_img);
        tvProductName = findViewById(R.id.product_name) ;
        tvProductPrice= findViewById(R.id.product_price);
        tvProductUploadby = findViewById(R.id.product_uploadby);
        tvProductDetail = findViewById(R.id.product_detail);
        Picasso.with(this).load(product_img).placeholder(R.drawable.placeholder).into(imageView);
        if(product_detail.isEmpty()){
            tvProductDetail.setText("Product Detail : " +"ไม่มีเน้ออออออ");
        }else{
        tvProductDetail.setText("Product Detail : "+product_detail);}
        tvProductName.setText("ชื่อสินค้า : "+product_brand +" "+product_name);

        tvProductPrice.setText("ราคา "+product_price+" บาท" );
        tvProductUploadby.setText("จัดจำหน่ายโดย "+product_uploadby);
    }
}
