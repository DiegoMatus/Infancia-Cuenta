package mx.infanciacuenta.adapters;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.infanciacuenta.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SymbolsAdapter extends ArrayAdapter<JSONObject>{

	private JSONArray objects;
	@SuppressLint("NewApi")
	public SymbolsAdapter(Context context, int resource, List<JSONObject> objects) throws JSONException {
		super(context, resource, objects);
		this.objects = new JSONArray(objects);
	}
	
	@Override
	public int getCount() {
		return this.objects.length();
	}
	
	static class ViewHolder{
		public static View color;
		public static TextView title;
		public static TextView amount;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView ==  null){
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.symbols_item, null);
			ViewHolder.color = (View)convertView.findViewById(R.id.color_item);
			ViewHolder.title = (TextView)convertView.findViewById(R.id.title_item);
			ViewHolder.amount = (TextView)convertView.findViewById(R.id.amount_item);
		}
		try{
			JSONObject j = getItem(position);
			ViewHolder.color.setBackgroundColor(Color.parseColor(j.getString("hex")));
			ViewHolder.title.setText(j.getString("f"));
			ViewHolder.amount.setText(j.getString("v"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return convertView;
	}

}
