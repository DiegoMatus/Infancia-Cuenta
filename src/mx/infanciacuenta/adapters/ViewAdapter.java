package mx.infanciacuenta.adapters;

import mx.infanciacuenta.MainActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ViewAdapter extends PagerAdapter {
	
	private Context context;
	ViewPager view = null;
	TextView label1;
	TextView label2;
	Button startButton;

    View views[];

    public ViewAdapter(Context context) {
    	final Activity a = (Activity)context;
    	this.context = context;
    	
    	label1 = new TextView(context);
    	label2 = new TextView(context);
    	startButton = new Button(context);
    	
    	label1.setText("Los derechos de los ni???os bla bla bla bla bla........");
    	label1.setTextColor(Color.WHITE);
    	label1.setTextSize(24);
    	label2.setText("Los derechos de los ni???os bla bla bla bla bla xsd xd dsx cd xsd........");
    	label2.setTextColor(Color.WHITE);
    	label2.setTextSize(24);
    	startButton.setText("Empezar");
    	startButton.setBackgroundColor(Color.WHITE);
    	startButton.setTextColor(Color.RED);
    	startButton.setTextSize(28);
    	startButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(a, MainActivity.class);
				a.startActivity(i);
				a.finish();
			}
		});
    	
    	views = new View[]{label1, label2, startButton};
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
