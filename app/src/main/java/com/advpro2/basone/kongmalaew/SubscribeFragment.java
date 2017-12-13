package com.advpro2.basone.kongmalaew;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.urbanairship.UAirship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubscribeFragment extends Fragment {
    private String TAG = "Subscribe";
    MyAdapter dataAdapter=null;
    ArrayAdapter sizeAdapter;
    ListView lv;
    Button sizeShoesButton;
    Button sizeShirtButton;
    boolean[] checkedSizeShoes;
    boolean[] checkedSizeShirt;
    TextView tv;
//    Spinner sp;

    public SubscribeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment_subscribe,container,false);
        SharedPreferences subscribePref = getContext().getSharedPreferences("brand_Subscribe_list",Context.MODE_PRIVATE);

        ArrayList<Data> dataList = new ArrayList<Data>();
        Data data  = new Data("Nike",subscribePref.getBoolean("Nike",false));
        dataList.add(data);
        data  = new Data("Adidas",subscribePref.getBoolean("Adidas",false));
        dataList.add(data);
        Log.e(TAG, "onCreateView: "+subscribePref.getBoolean("Nike",false));

        dataAdapter = new MyAdapter(getContext(),
                R.layout.brand_info, dataList);
        ListView listView = (ListView) view.findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
//        sp=view.findViewById(R.id.spinnerSize);


//        sizeAdapter=new ArrayAdapter<Integer>(getContext(),android.R.layout.simple_list_item_checked ,dataSizeList);
//        sp.setAdapter(sizeAdapter);
//
//        lv = (ListView) view.findViewById(R.id.listView1);
//        lv.setAdapter(adapter);

        sizeShoesButton= view.findViewById(R.id.sizeShoesButton);
        sizeShirtButton =  view.findViewById(R.id.sizeShirtButton);
//        tv= (TextView) view.findViewById(R.id.textviewja);
        final String[] sizeShoes=new String[]{
            "35" ,"36","37","38","39","40","41","42","43","44","45","46","47"
        };
        final List<String> dataSizeShoesList = Arrays.asList(sizeShoes);
        checkedSizeShoes = new boolean[sizeShoes.length];
        sizeShoesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                mBuilder.setTitle("Shoes's Size.");
                mBuilder.setMultiChoiceItems(sizeShoes, checkedSizeShoes, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        // Update the current focused item's checked status
                        checkedSizeShoes[which] = isChecked;

                        // Get the current focused item
                        String currentItem = dataSizeShoesList.get(which);

                        // Notify the current action
                        Toast.makeText(getActivity(),
                                currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click positive button
                        String sizeShowShoes = new String();
//                        tv.setText("Your preferred colors..... \n");
                        for (int i = 0; i<checkedSizeShoes.length; i++){
                            boolean checked = checkedSizeShoes[i];
                            if (checked) {
                                sizeShowShoes+="["+dataSizeShoesList.get(i)+ "] ";
//                                tv.setText(tv.getText() + dataSizeList.get(i) + "\n");
                            }


                        }
                        if(!sizeShowShoes.isEmpty()) {
                            Toast.makeText(getActivity(),
                                    sizeShowShoes + "subscribe.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // Set the neutral/cancel button click listener
                mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                        for (int i = 0; i<checkedSizeShoes.length; i++){
                            checkedSizeShoes[i]=false;

                        }
                    }
                });

                AlertDialog dialog = mBuilder.create();
                // Display the alert dialog on interface
                dialog.show();
            }

        });


        final String[] sizeShirt=new String[]{
                "Smaller than XS","XS","M","L","XL","XXL","XXL","3XL","Bigger than 3XL"
        };
        final List<String> dataSizeShirtList = Arrays.asList(sizeShoes);
        checkedSizeShirt = new boolean[sizeShirt.length];
        sizeShirtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                mBuilder.setTitle("Shirt's Size.");
                mBuilder.setMultiChoiceItems(sizeShirt, checkedSizeShirt, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        // Update the current focused item's checked status
                        checkedSizeShirt[which] = isChecked;

                        // Get the current focused item
                        String currentItem = dataSizeShirtList.get(which);

                        // Notify the current action
                        Toast.makeText(getActivity(),
                                currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click positive button
                        String sizeShowShirt = new String();
//                        tv.setText("Your preferred colors..... \n");
                        for (int i = 0; i<checkedSizeShirt.length; i++){
                            boolean checked = checkedSizeShirt[i];
                            if (checked) {
                                sizeShowShirt+="["+dataSizeShirtList.get(i)+ "] ";
//                                tv.setText(tv.getText() + dataSizeList.get(i) + "\n");
                            }


                        }
                        if(!sizeShowShirt.isEmpty()) {
                            Toast.makeText(getActivity(),
                                    sizeShowShirt + "subscibe.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // Set the neutral/cancel button click listener
                mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                        for (int i = 0; i<checkedSizeShirt.length; i++){
                            checkedSizeShirt[i]=false;

                        }
                    }
                });

                AlertDialog dialog = mBuilder.create();
                // Display the alert dialog on interface
                dialog.show();
            }

        });

        SeekBar sb = view.findViewById(R.id.seekbar);
        sb.setMinimumWidth(200);
        sb.setMinimumHeight(500);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged: "+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });








        // Display the alert dialog on interface

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_subscribe, container, false);
        return  view;
    }


    private class ViewHolder {


        CheckBox category;
    }
    public class MyAdapter extends ArrayAdapter<Data>{
        private ArrayList<Data> dataList;
        public MyAdapter(Context context, int textViewResourceId,ArrayList<Data> dataList){
            super(context,textViewResourceId,dataList);
            this.dataList= new ArrayList<Data>();
            this.dataList.addAll(dataList);
        }
        private class ViewHolder {


            CheckBox category;
        }

        @Override
        public View getView (int position,View convertView,ViewGroup parent){
        ViewHolder holder=null;
        Log.v("ConvertView", String.valueOf(position));
            if(convertView==null){
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.brand_info, null);

                holder = new ViewHolder();

                holder.category = convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);
                holder.category.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;

                        Data data = (Data) cb.getTag();

                        Toast.makeText(getActivity().getApplicationContext(),
                                "Subscribe " + cb.getText() +
                                        " now.",
                                Toast.LENGTH_LONG).show();

                        data.setSelected(cb.isChecked());
                        if (data.isSelected()){
                            UAirship.shared().getPushManager().editTags().addTag(cb.getText().toString()).apply();
                            Log.d(TAG, "onClick: Subscribed "+cb.getText());
                            SharedPreferences pref = getContext().getSharedPreferences("brand_Subscribe_list",Context.MODE_PRIVATE);
                            SharedPreferences.Editor prefEdit = pref.edit();
                            prefEdit.putBoolean(cb.getText().toString(),true);
                                prefEdit.apply();
                        }else{
                            UAirship.shared().getPushManager().editTags().removeTag(cb.getText().toString()).apply();
                            Log.d(TAG, "onClick: Unsubscribed "+cb.getText());
                            SharedPreferences pref = getContext().getSharedPreferences("brand_Subscribe_list",Context.MODE_PRIVATE);
                            SharedPreferences.Editor prefEdit = pref.edit();
                            prefEdit.putBoolean(cb.getText().toString(),false);
                            prefEdit.apply();
                            Log.e(TAG, "onClick: "+ pref.getAll().toString());
                        }


                    }
                });
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            Data data = dataList.get(position);
            holder.category.setText(data.getCategory());
            holder.category.setChecked(data.isSelected());
            holder.category.setTag(data);
            return convertView;
        }
    }


}
