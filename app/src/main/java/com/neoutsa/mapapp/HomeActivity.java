package com.neoutsa.mapapp;

import android.os.Bundle;

public class HomeActivity extends BaseNavActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        wireChrome();
    }
}
