package mx.infanciacuenta.fragments;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.infanciacuenta.R;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class MainFragment extends Fragment {
	
	private Spinner states;
	private Spinner domains;
	private Spinner indicators;
	private boolean working = false;
	public MainFragment(){}
	
	String resources[] = {
		"2804dd9b-9d30-4fcd-aee9-70a309013197"	
	};
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        states = (Spinner)rootView.findViewById(R.id.spinner_states);
        domains = (Spinner)rootView.findViewById(R.id.spinner_domains);
        indicators = (Spinner)rootView.findViewById(R.id.spinner_indicators);
        
        domains.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if(position != 0)
					if(!working){
						working = true;
						GetIndicadores e = new GetIndicadores();
						e.execute();
					}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        return rootView;
    }
	
	class GetIndicadores extends AsyncTask<Void, Void, String>{
		@Override
		protected String doInBackground(Void... params) {
			try{
	        	URI uri=new URI("http://192.168.1.65:8000/home/indicadores/");
	            HttpResponse response=null;
		        ArrayList<BasicNameValuePair> parametros= new ArrayList<BasicNameValuePair>();
		         
		        
		        BasicNameValuePair parametro1=new BasicNameValuePair("dominio",resources[(domains.getSelectedItemPosition()-1)]);
		        parametros.add(parametro1);
		         
		        HttpPost post=new HttpPost(uri);
		        UrlEncodedFormEntity entity=new UrlEncodedFormEntity(parametros,HTTP.UTF_8);
		        post.setEntity(entity);
		         
		        HttpClient client= new DefaultHttpClient();
		        response=client.execute(post);
		        HttpEntity e = response.getEntity();
		        String responseString = EntityUtils.toString(e, "UTF-8");
		        return responseString;
	        } catch(Exception entrada){
	            entrada.printStackTrace();
	        }
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			System.out.println(result);
			try{
				JSONArray json = new JSONObject(result).getJSONObject("result").getJSONArray("fields");
				final ArrayList<String> ids = new ArrayList<String>();
				int len = json.length();
				for(int i = 1; i < len; i++)
					ids.add(json.getJSONObject(i).getString("id"));
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line);
						adapter.addAll(ids);
						indicators.setAdapter(adapter);
					}
				});
			}catch(Exception e){
				e.printStackTrace(); 
			}
			working = false;
		}
	}
	
}
