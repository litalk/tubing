package com.tubing.client.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.tubing.client.android.http.AsyncHttpClientPost;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kornfeld on 17/12/2015.
 */
public class ShareActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "ShareActivity";
    private static final int RC_GET_AUTH_CODE = 9003;

    private GoogleApiClient mGoogleApiClient;
    private String mQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.share);

        Intent intent = getIntent();
        mQuery = intent.getStringExtra(Intent.EXTRA_TEXT);

        initGoogleAPI();
        getAuthCode();
    }

    private void initGoogleAPI() {

        String serverClientId = getString(R.string.server_client_id);
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(serverClientId)
                        .requestServerAuthCode(serverClientId).requestScopes(
                        new Scope("https://www.googleapis.com/auth/youtube"),
                        new Scope("https://www.googleapis.com/auth/youtube.upload")).build();

        // Build GoogleAPIClient with the Google Sign-In API and the above options.
        mGoogleApiClient =
                new GoogleApiClient.Builder(this).enableAutoManage(
                        this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */).addApi(
                        Auth.GOOGLE_SIGN_IN_API,
                        gso).build();
    }

    private void getAuthCode() {
        // Start the retrieval process for a server auth code.  If requested, ask for a refresh
        // token.  Otherwise, only get an access token if a refresh token has been previously
        // retrieved.  Getting a new access token for an existing grant does not require
        // user consent.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GET_AUTH_CODE);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GET_AUTH_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "onActivityResult:GET_AUTH_CODE:status:" + result.getStatus());
            if (result.isSuccess()) {
                final GoogleSignInAccount signInAccount = result.getSignInAccount();
                Log.d(
                        TAG,
                        "onActivityResult:GET_AUTH_CODE:account:"
                                + signInAccount.getDisplayName());
                // Send code to server and exchange for access/refresh/ID tokens.
                try {
                    Map<String, String> cookies = new HashMap<String, String>() {{
                        put("tubing_user-id", signInAccount.getIdToken());
                        put("tubing_auth-code", signInAccount.getServerAuthCode());
                    }};
                    new AsyncHttpClientPost(mQuery, cookies).
                            execute(new URL(getString(R.string.tubing_server) + "/tubing/playlist"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
