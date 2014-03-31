package mx.infanciacuenta.fragments;


import com.example.infanciacuenta.R;
import com.example.infanciacuenta.TwitterStatics;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class TwitterFragment extends Fragment{
	private Twitter mTwitter;
    private RequestToken mRequestToken;
    private SharedPreferences mPrefs;
    private Activity context;
    
    public TwitterFragment(){
    	context = getActivity();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.twitter_feed, null);
		mPrefs = getActivity().getSharedPreferences("twitterPrefs", Context.MODE_PRIVATE);
        mTwitter = new TwitterFactory().getInstance();
        mTwitter.setOAuthConsumer(TwitterStatics.TWITTER_CONSUMER_KEY, TwitterStatics.TWITTER_CONSUMER_SECRET);
        Button login = (Button)view.findViewById(R.id.login_twitter);
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClick_Login(v);
			}
		});
        return view;
	}
	
	private void onClick_Login(View button){
        if (mPrefs.contains(TwitterStatics.PREF_ACCESS_TOKEN) && !TextUtils.isEmpty(mPrefs.getString(TwitterStatics.PREF_ACCESS_TOKEN, null))) {
            loginAuthorisedUser();
        } else {
            loginNewUser();
        }
	}
	
	private void loginNewUser() {
        final boolean[] prevendDoubleCallbackEvent = {false};
        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    mRequestToken = mTwitter.getOAuthRequestToken(TwitterStatics.TWITTER_CALLBACK_URL);
                    return true;
                } catch (TwitterException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (result){
                    String url = mRequestToken.getAuthenticationURL();
                    WebView wv = new WebView(getActivity());
                    wv.loadUrl(url);
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    dialog.setContentView(wv);

                    wv.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return true;
                        }
                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            if (!prevendDoubleCallbackEvent[0] && url.contains(TwitterStatics.TWITTER_CALLBACK_URL)){
                                prevendDoubleCallbackEvent[0] = true;
                                String verifier = Uri.parse(url).getQueryParameter("oauth_verifier");

                                new AsyncTask<String, Void, AccessToken>(){
                                    @Override
                                    protected AccessToken doInBackground(String... params) {
                                        try {
                                            return mTwitter.getOAuthAccessToken(mRequestToken, params[0]);
                                        } catch (TwitterException e) {
                                            e.printStackTrace();
                                            return null;
                                        }
                                    }
                                    @Override
                                    protected void onPostExecute(AccessToken result) {
                                        super.onPostExecute(result);
                                        if (result != null){
                                            mTwitter.setOAuthAccessToken(result);
                                            saveAccessToken(result);
                                            Toast.makeText(getActivity(), "usuario logueado", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }.execute(verifier);
                                dialog.dismiss();
                            } else
                                super.onPageStarted(view, url, favicon);
                        }
                    });
                    dialog.show();
                }
            }
        }.execute();
    }

    private void loginAuthorisedUser() {
        String token = mPrefs.getString(TwitterStatics.PREF_ACCESS_TOKEN, null);
        String secret = mPrefs.getString(TwitterStatics.PREF_ACCESS_TOKEN_SECRET, null);
        AccessToken at = new AccessToken(token, secret);
        mTwitter.setOAuthAccessToken(at);
        Toast.makeText(context, "usuario logueado", Toast.LENGTH_SHORT).show();
    }
    
    private void saveAccessToken(AccessToken at) {
        String token = at.getToken();
        String secret = at.getTokenSecret(); 
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(TwitterStatics.PREF_ACCESS_TOKEN, token);
        editor.putString(TwitterStatics.PREF_ACCESS_TOKEN_SECRET, secret);
        editor.commit();
    }

}
