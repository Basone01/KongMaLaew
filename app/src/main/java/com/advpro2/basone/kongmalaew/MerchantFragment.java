package com.advpro2.basone.kongmalaew;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.advpro2.basone.kongmalaew.Model.Singleton;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantFragment extends Fragment {
    private static final int SELECT_PICTURE = 0;
    private ImageView imageView;
    private Spinner categorySpinner;
    private Spinner clothesSpinner;
    private EditText priceEdit;
    private EditText productBrand;
    private EditText productId;
    private EditText productModel;
    private EditText productDetails;
    private Button submitButton;
    private Button galleryButton;
    private RequestQueue q;
    private StorageReference mStorageRef;
    private static final int GALLERY_INTENT=2;
    private ProgressDialog progressDialog;
    private Uri uri;
    private Uri downloadUrl;
    public MerchantFragment() {


        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_merchant,container,false);
        categorySpinner= (Spinner) view.findViewById(R.id.category);
        progressDialog = new ProgressDialog(getActivity());
        final ArrayList<String> categoryList = new ArrayList<String>();
        categoryList.add("shoes");
        categoryList.add("clothes");
        categoryList.add("others");
        final ArrayList<String> clothesSizeList = new ArrayList<String>();


        final String price ;
        galleryButton= (Button) view.findViewById(R.id.galleryButton);
        priceEdit = (EditText) view.findViewById(R.id.priceedit);
        productBrand = (EditText)view.findViewById(R.id.brandEdit);
        submitButton= (Button) view.findViewById(R.id.submit);


        productId = (EditText) view.findViewById(R.id.productIdEdit);
        productModel = (EditText) view.findViewById(R.id.modelEdit);
        productDetails =(EditText) view.findViewById(R.id.detailEdit);


        clothesSpinner= (Spinner) view.findViewById(R.id.size);
        final ArrayAdapter<String> adapterClothesSize = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, clothesSizeList);

        for(int i=0;i<categoryList.size();i++){
            Log.i("Text", categoryList.get(i));
        }
        clothesSpinner.setAdapter(adapterClothesSize);
        final ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, categoryList);
        categorySpinner.setAdapter(adapterCategory);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(),
                        "Select : " + categoryList.get(position),
                        Toast.LENGTH_SHORT).show();
                if(categoryList.get(position).equals("clothes")){
//

                    clothesSizeList.clear();
                    clothesSizeList.add("XS");
                    clothesSizeList.add("S");
                    clothesSizeList.add("M");
                    clothesSizeList.add("L");
                    clothesSizeList.add("XL");
                    clothesSizeList.add("XXL");
                    clothesSizeList.add("Free Size");
                    adapterClothesSize.notifyDataSetChanged();


                }
                else if(categoryList.get(position).equals("shoes")){
//
                    clothesSizeList.clear();
                    for(int i=36;i<48;i++)
                    {

                        clothesSizeList.add(i+" eur");
                    }


                    adapterClothesSize.notifyDataSetChanged();



                }else if(categoryList.get(position).equals("others")){
//
                    clothesSizeList.clear();
                    clothesSizeList.add("No Size");


                    adapterClothesSize.notifyDataSetChanged();

                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setSelectPicture();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
//                Toast.makeText(getActivity(),
//                        "มึงอัพ "+categorySpinner.getSelectedItem()+" ไซส์ "+clothesSpinner.getSelectedItem() +
//                                " ที่ชื่อยี่ห้อ "+productBrand.getText()+
//                                " ราคา "+priceEdit.getText() +" บาท เรียบร้อยแล้ว",
//                        Toast.LENGTH_SHORT).show();
//                CallAPI callAPI = new CallAPI();
//                callAPI.execute();
                q = Volley.newRequestQueue(getActivity().getApplicationContext());

                Log.i("uri",uri.toString());
                mStorageRef = FirebaseStorage.getInstance().getReference();
                StorageReference filePath = mStorageRef.child("images/"+uri.getLastPathSegment());

                progressDialog.setMessage("Uploading...");
                progressDialog.show();


//                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        downloadUrl = taskSnapshot.getDownloadUrl();
//                        Log.d("URL", "onSuccess: "+downloadUrl);
//                        Toast.makeText(getActivity(),
//                                " Upload Done",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e("ERRR", "onFailure: "+e.getMessage() );
//                    }
//                });





                UploadTask uploadTask = filePath.putFile(uri);

// Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Log.i("UPPPP", "onSuccess: ");
                        downloadUrl = taskSnapshot.getDownloadUrl();
                        progressDialog.dismiss();
                        postData();
                    }
                });


                // currentContext.startActivity(activityChangeIntent);


            }
        });

        // Inflate the layout for this fragment
        return view;




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog.setMessage("Uploading...");
        if(requestCode==GALLERY_INTENT&&resultCode==RESULT_OK){
            uri = data.getData();

            Toast.makeText(getActivity(),
                    uri.toString(),
                    Toast.LENGTH_SHORT).show();
            galleryButton.setText(uri.toString());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        q = Volley.newRequestQueue(getActivity().getApplicationContext());
//        postData();
    }


    void setSelectPicture(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);
    }


    void postData(){
        String url = "https://us-central1-kongmalaew-e33a2.cloudfunctions.net/posttest";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("POSTED", "onResponse: "+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("POSTED", "onResponse: "+error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("product_name",productModel.getText().toString());
                params.put("product_price",priceEdit.getText().toString());
                params.put ("product_id",productId.getText().toString());
                params.put ("product_detail",productDetails.getText().toString());
                params.put ("product_brand",productBrand.getText().toString());
                params.put("product_uploadby", Singleton.getInstance().getFirstName()+" "+Singleton.getInstance().getLastName() );
                params.put("product_img",downloadUrl.toString());

                return params;
            }
        };
        q.add(postRequest);
    }





}
