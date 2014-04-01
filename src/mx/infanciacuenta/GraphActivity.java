package mx.infanciacuenta;

import org.apache.http.util.EncodingUtils;

import com.example.infanciacuenta.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GraphActivity extends Activity{

	private WebView webview;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		getActionBar().setTitle("Resultados");
		webview = (WebView)findViewById(R.id.result_webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient(){
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            view.loadUrl(url);
	            return true;
	        }
		});
		String post = "estado="+getIntent().getStringExtra("estado")+"&dominio="+getIntent().getStringExtra("dominio")+"&indicador="+getIntent().getStringExtra("indicador")+"&tipo=html";
		webview.postUrl("http://192.168.1.66:8000/home/indicadores_2/", EncodingUtils.getBytes(post, "base64"));
	}
}
