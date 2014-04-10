package mx.infanciacuenta;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import mx.infanciacuenta.adapters.SymbolsAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.infanciacuenta.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

public class GraphActivity extends Activity{

	private WebView webview;
	private ProgressDialog dialog;
	private String dominio;
	private int estado;
	private String indicador;
	private String anio;
	private ListView list_symbols;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		getActionBar().setTitle("Resultados");
		dominio = getIntent().getStringExtra("dominio")!= null? getIntent().getStringExtra("dominio") : "";
		estado = getIntent().getIntExtra("estado",0);
		indicador = getIntent().getStringExtra("indicador")!= null ? getIntent().getStringExtra("indicador") : "";
		list_symbols = (ListView)findViewById(R.id.simbology);
		dialog = new ProgressDialog(this);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setTitle("Por favor espere...");
		dialog.setMessage("Estamos graficando su consulta");
		dialog.show();
		webview = (WebView)findViewById(R.id.result_webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            view.loadUrl(url);
	            return true;
	        }
			@Override
			public void onPageFinished(WebView view, String url) {
				dialog.dismiss();
				super.onPageFinished(view, url);
			}
		});
		String post = "estado="+estado+"&dominio="+dominio+"&indicador="+indicador+"&tipo=html";
		webview.postUrl("http://mobileapps.dragonflylabs.com.mx/infanciacuenta/indicadores_2/", EncodingUtils.getBytes(post, "base64"));
		LoadSimbols simbols = new LoadSimbols();
		simbols.execute();
	}
	
	class LoadSimbols extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			try{
				URI uri=new URI("http://mobileapps.dragonflylabs.com.mx/infanciacuenta/indicadores_2/");
	            HttpResponse response=null;
		        ArrayList<BasicNameValuePair> parametros= new ArrayList<BasicNameValuePair>();
		         
		        
		        BasicNameValuePair parametro1=new BasicNameValuePair("dominio",dominio);
		        BasicNameValuePair parametro2=new BasicNameValuePair("estado",String.valueOf(estado));
		        BasicNameValuePair parametro3=new BasicNameValuePair("indicador",indicador);
		        
		        parametros.add(parametro1);
		        parametros.add(parametro2);
		        parametros.add(parametro3);
		         
		        HttpPost post=new HttpPost(uri);
		        UrlEncodedFormEntity entity=new UrlEncodedFormEntity(parametros,HTTP.UTF_8);
		        post.setEntity(entity);
		         
		        HttpClient client= new DefaultHttpClient();
		        response=client.execute(post);
		        HttpEntity e = response.getEntity();
		        String responseString = EntityUtils.toString(e, "UTF-8");
		        return responseString;
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			System.out.println(result);
			try{
				JSONArray json = new JSONArray(result);
				ArrayList<JSONObject> jsons = new ArrayList<JSONObject>();
				for(int i = 0; i < json.length(); i++)
					jsons.add(json.getJSONObject(i));
				SymbolsAdapter adapter = new SymbolsAdapter(getApplicationContext(), R.layout.symbols_item, jsons);
				list_symbols.setAdapter(adapter);
			}catch(Exception e){
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}
	}
}
