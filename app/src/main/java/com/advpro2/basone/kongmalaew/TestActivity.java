package com.advpro2.basone.kongmalaew;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by bason on 14-Nov-17.
 */

public class TestActivity extends AppCompatActivity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TextView tv = new TextView(this);
        tv.setText("ASDASDASDASD");
        setContentView(tv);
    }
}
