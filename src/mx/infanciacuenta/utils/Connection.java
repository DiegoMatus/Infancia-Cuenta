package mx.infanciacuenta.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connection {

	public static boolean checkInternetConnection(Activity activity){
		ConnectivityManager conexion= (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=conexion.getActiveNetworkInfo();
        if(net!=null&&net.getState()== NetworkInfo.State.CONNECTED)
            return true;
        return false;
	}
}
