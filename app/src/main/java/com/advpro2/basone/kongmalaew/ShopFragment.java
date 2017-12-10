package com.advpro2.basone.kongmalaew;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.advpro2.basone.kongmalaew.Model.ProductModel;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {
    ShopData adapter;
    private ArrayList<ProductModel> productList;
    ProductModel productModel;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_shop,container,false);



        Log.d("JSONNNNNNNN", "onResponse: gfgdfgdfgdfgdfgdf");
        RequestQueue q = Volley.newRequestQueue(getActivity().getApplicationContext());
        fetch(q);


//        int[] resId = { R.drawable.flexexpe
//                , R.drawable.maxmotion, R.drawable.maxtavas,R.drawable.freern
//                , R.drawable.maxzero,R.drawable.max17,R.drawable.hua,R.drawable.roshe
//                , R.drawable.uncage
//        };
//
//        String[] list = { "Nike Flex Experience", "Nike Air Max Motion", "Nike Air Max Tavas","Nike Free rn","Nike Air Max Zero"
//                ,"Nike Air Max 2017","Nike Air Huarache","Nike Roshe Run" ,"Adidas Ultraboost Uncage"
//        };
//        float[] priceList= {2500,3500,3500,4200,5000,6400,4200,2900,8990};

        adapter = new ShopData(getActivity().getApplicationContext(), productList);
//        ShopData adapter = new ShopData(getActivity().getApplicationContext(), list,resId,priceList);

        final ListView listView = (ListView)view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(getActivity().getApplicationContext()," Click!!!!" , Toast.LENGTH_SHORT).show();
//                String Info = listView.getItemAtPosition(position).toString();

                Log.i("listviewClicking",productList.get(position).getProduct_id());


                Intent intent = new Intent(getActivity().getApplicationContext(),
                        DetailActivity.class);
//
                Bundle bundle = new Bundle();
//                bundle.putString("product_id",productList.get(position).getProduct_id());
//                intent.putExtras(bundle);
//                Bundle bundle = new Bundle();
                bundle.putString("product_id", productList.get(position).getProduct_id());
                bundle.putString("product_name",productList.get(position).getProduct_name());
                bundle.putDouble("product_price",productList.get(position).getProduct_price());
                bundle.putString("product_detail",productList.get(position).getProduct_detail());
                bundle.putString("product_img",productList.get(position).getProduct_img());
                bundle.putString("product_uploadby",productList.get(position).getProduct_uploadby());
                bundle.putString("product_brand",productList.get(position).getProduct_brand());
                bundle.putString("product_model",productList.get(position).getProduct_img());

//                intent.putExtra("product_id",productList.get(position).getProduct_id());
                intent.putExtras(bundle);
                Log.i("kja","jaa");
                startActivity(intent);

            }
        });

        return view;



    }

    // Fetch method
    private void fetch(RequestQueue requestQueue) {


        JsonArrayRequest request = new JsonArrayRequest("https://us-central1-kongmalaew-e33a2.cloudfunctions.net/getProducts",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for(int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Log.i("K",jsonObject.toString());

                                    String imgurl;
                                    if (!jsonObject.has("product_img")){
                                        imgurl = "https://firebasestorage.googleapis.com/v0/b/kongmalaew-e33a2.appspot.com/o/anonymous_mask1600.png?alt=media&token=c900d233-d226-4f63-8679-f794fa95d94c";
                                    }else{
                                        imgurl =jsonObject.getString("product_img");
                                    }
                                    ProductModel model = new ProductModel(jsonObject.getString("product_id"),
                                            jsonObject.getString("product_name"),
                                            jsonObject.getString("product_detail"),
                                            jsonObject.getDouble("product_price") ,
                                            imgurl,
                                            jsonObject.getString("product_uploadby"),
                                            jsonObject.getString("product_brand"));

                                    productList.add(model);
                                    Log.i("model",jsonObject.toString());
                                    Log.i("model",model.getProduct_name());
                                    Log.i("model",model.getProduct_detail());
//                                Log.i("model",model.getProduct_brand());
                                    Log.d("JS", "onResponse: "+jsonObject.toString());


                            }
                            catch(JSONException e) {
//                                productList.add("Error: " + e.getLocalizedMessage());
                                Log.d("JSONN", "onResponse: gfgdfgdfgdfgdfgdf");
                            }
                        }
                        adapter.notifyDataSetChanged();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("JSONNNNNNNN", "onResponse: ERRRRRRRRRRRRR");
                    }
                });

        productList = new ArrayList<>();
        requestQueue.add(request);
    }




}
