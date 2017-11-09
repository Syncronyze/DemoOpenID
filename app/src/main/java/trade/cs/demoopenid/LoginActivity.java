package trade.cs.demoopenid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends Activity {

    // this will be the return_to portion of the constructed OpenID URL.
    // it doesn't have to exist, we're just using it to catch the returned login page.
    private final String MISC_PARAM = "RandomUrl.com";
    private WebView wv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // remember to give internet permissions to the app!
        wv = findViewById(R.id.wv_login);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                setTitle(url);
                pb.setVisibility(View.INVISIBLE);
                Uri Url = Uri.parse(url);

                if( Url.getAuthority().equals( MISC_PARAM.toLowerCase() ) ){
                    // if once the URL matches the redirect parameters, we can stop the loading
                    // and just get the identity from there.
                    wv.stopLoading();
                    // the URL will come back with the identity of the user on a successful login.
                    // we only know their PUBLIC ID, and anything that's public that comes with it.
                    // we will not find anything else about them that you can't already find public.
                    Uri userAccountUrl = Uri.parse(Url.getQueryParameter("openid.identity"));
                    String userID = userAccountUrl.getLastPathSegment();
                    Intent i = new Intent();
                    i.putExtra("steamID", userID);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });

        pb = findViewById(R.id.pb_loginLoad);
        pb.setVisibility(View.VISIBLE);
        // Constructing openid url request
        String URL = "https://steamcommunity.com/openid/login?" +
                "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
                "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
                "openid.mode=checkid_setup&" +
                "openid.ns=http://specs.openid.net/auth/2.0&" +
                "openid.realm=https://" + MISC_PARAM + "&" +
                "openid.return_to=https://" + MISC_PARAM + "/signin/";

        wv.loadUrl(URL);

    }

    @Override
    public void onBackPressed(){
        setResult(RESULT_CANCELED);
        finish();
    }
}
