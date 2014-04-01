package mx.infanciacuenta.adapters;

import java.util.List;

import com.example.infanciacuenta.R;

import twitter4j.Status;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TweetsAdapter extends ArrayAdapter<Status>{

	private List<Status> tweets;
	
	public TweetsAdapter(Context context, int resource, List<Status> objects) {
		super(context, resource, objects);
		this.tweets = objects;
	}
	
	@Override
	public int getCount() {
		return this.tweets.size();
	}
	
	static class ViewHolder{
		public static TextView name_tweet;
		public static TextView date_tweet;
		public static TextView tweet;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.tweet_item, null);
			ViewHolder.name_tweet = (TextView)convertView.findViewById(R.id.name_tweet);
			ViewHolder.date_tweet = (TextView)convertView.findViewById(R.id.date_tweet);
			ViewHolder.tweet = (TextView)convertView.findViewById(R.id.text_tweet);
		}
		Status s = getItem(position);
		ViewHolder.name_tweet.setText(s.getUser().getScreenName());
		ViewHolder.date_tweet.setText(s.getCreatedAt().toGMTString());
		ViewHolder.tweet.setText(s.getText());
		return convertView;
	}

}
