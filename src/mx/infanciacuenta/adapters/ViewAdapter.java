package mx.infanciacuenta.adapters;

import com.example.infanciacuenta.R;

import mx.infanciacuenta.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewAdapter extends PagerAdapter {
	
	private Context context;
	ViewPager view = null;
	TextView label1;
	TextView label2;
	TextView label3;
	TextView label4;
	Button startButton;

    View views[];

    public ViewAdapter(Context context) {
    	final Activity a = (Activity)context;
    	this.context = context;
    	
    	label1 = new TextView(context);
    	label2 = new TextView(context);
    	label3 = new TextView(context);
    	label4 = new TextView(context);
    	startButton = new Button(context);
    	
    	label1.setText(R.string.page1);
    	label1.setTextColor(Color.WHITE);
    	label1.setTextSize(20);
    	label1.setGravity(Gravity.CENTER);
    	
    	label2.setText(R.string.page2);
    	label2.setTextColor(Color.WHITE);
    	label2.setTextSize(20);
    	label2.setGravity(Gravity.CENTER);
    	
    	label3.setText(R.string.page3);
    	label3.setTextColor(Color.WHITE);
    	label3.setTextSize(20);
    	label3.setGravity(Gravity.CENTER);
    	
    	label4.setText(R.string.page4);
    	label4.setTextColor(Color.WHITE);
    	label4.setTextSize(20);
    	label4.setGravity(Gravity.CENTER);
    	
    	startButton.setText("Empezar");
    	startButton.setBackgroundColor(Color.argb(255, 222, 70, 59));
    	startButton.setTextColor(Color.WHITE);
    	startButton.setTextSize(28);
    
    	startButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(a, MainActivity.class);
				a.startActivity(i);
				a.finish();
			}
		});
    	
    	views = new View[]{label1, label2, label3, label4, startButton};
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.length;
	}

	@Override
	public Object instantiateItem(View container, int position){
		View view = new  View(context);
		view = views[position];
		
		((ViewPager) container).addView(view, 0);
		return view;
	}
	
	@Override
    public void destroyItem(View container, int position, Object obj) {
           ((ViewPager) container).removeView((View) obj);
    }

    @Override
    public boolean isViewFromObject(View container, Object obj) {
           return container == obj;
    }

}
