package com.example.infanciacuenta;

import com.actionbarsherlock.app.SherlockActivity;
import com.example.adapters.ViewAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

 
public class WalkthroughActivity extends SherlockActivity {

	ViewPager viewPager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);
		getSupportActionBar().hide();
		viewPager = (ViewPager)findViewById(R.id.viewPager);
		viewPager.setAdapter(new ViewAdapter(this));
	}
 
}