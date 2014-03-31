package com.example.infanciacuenta;

import com.example.adapters.ViewAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;

 
public class WalkthroughActivity extends Activity {

	ViewPager viewPager = null;
	Button start;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);
		getActionBar().hide();
		viewPager = (ViewPager)findViewById(R.id.viewPager);
		viewPager.setAdapter(new ViewAdapter(this));
	}
 
}