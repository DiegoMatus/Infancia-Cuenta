package mx.infanciacuenta;

import com.example.infanciacuenta.R;

import android.app.Activity;
import android.os.Bundle;

public class GraphActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		getActionBar().setTitle(getIntent().getStringExtra("title"));
	}
}