package kr.paulkim.enjoyyourslide;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import kr.paulkim.enjoyyourslide.Tutorial.TutorialActivity;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final String TAG = "SignInActivity";
    private static final String REGISTER_URL = "http://ksmr1102.vps.phps.kr/register_google.php";
    private static final int RC_SIGN_IN = 9001;
    private ProgressDialog mProgressDialog;
    private GoogleApiClient mGoogleApiClient;
    private EditText emailEditText, passwordEditText;
    private Button googleLogin, btnLogin;
    private ImageView btnLinkToRegisterScreen;
    private boolean loggedIn = false;
    private MyApplication app;
    private boolean googleLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        app = (MyApplication) getApplicationContext();
        /*
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(this);
        */

        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        googleLogin = (Button) findViewById(R.id.googleLogin);
        btnLinkToRegisterScreen = (ImageView) findViewById(R.id.btnLinkToRegisterScreen);

        emailEditText.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf"));
        passwordEditText.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf"));
        btnLogin.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf"));
        googleLogin.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf"));

        btnLogin.setOnClickListener(this);
        googleLogin.setOnClickListener(this);
        btnLinkToRegisterScreen.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mPref = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = mPref.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
        String email = mPref.getString(Config.EMAIL_SHARED_PREF, "Not available");
        if (loggedIn) {
            app.setEmail(email);
            app.setSeparator("2");
            dismissProgressDialog();
            startActivity(new Intent(LoginActivity.this, SettingsActivity.class));
            finish();
        }

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    dismissProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void login() {
        final String email = emailEditText.getText().toString().trim().toLowerCase();
        final String password = passwordEditText.getText().toString().trim().toLowerCase();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase(Config.LOGIN_SUCCESS)) {
                    SharedPreferences.Editor editor = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                    editor.putString(Config.EMAIL_SHARED_PREF, email);
                    editor.apply();

                    startActivity(new Intent(LoginActivity.this, TutorialActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "이메일 혹은 비밀번호가 틀립니다", Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.KEY_EMAIL, email);
                params.put(Config.KEY_PASSWORD, password);
                params.put(Config.KEY_SEPARATOR, "2");

                return params;
            }
        };
        app.setEmail(email);
        app.setSeparator("2");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            googleLoggedIn = true;
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            MyApplication app = (MyApplication) getApplicationContext();
            app.setEmail(acct.getEmail());
            app.setUsername(acct.getDisplayName());
            app.setSeparator("1");

            String email = acct.getEmail().trim().toLowerCase();
            String username = acct.getDisplayName().trim().toLowerCase();
            String password = "".trim().toLowerCase();
            String separator = "1".trim().toLowerCase();

            insetIntoDatabase(email, username, password, separator);

            //Log.d("GoogleLogin", String.valueOf(app.isLogin()));
            Log.d("GoogleLogin", String.valueOf(googleLoggedIn));
            if (googleLoggedIn) {
                startActivity(new Intent(LoginActivity.this, TutorialActivity.class));
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
            dismissProgressDialog();

        } else {
            // Signed out, show unauthenticated UI.
            //Toast.makeText(getApplicationContext(), "Failed to Login", Toast.LENGTH_SHORT).show();
        }
    }

    private void insetIntoDatabase(String email, String username, String password, String separator) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("email", params[0]);
                data.put("username", params[1]);
                data.put("password", params[2]);
                data.put("separator", params[3]);

                Log.d("tag", params[0] + params[1] + params[2] + params[3]);

                //Toast.makeText(getApplicationContext(), params[0] + params[1] + params[2] + params[3], Toast.LENGTH_LONG).show();

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }
        RegisterUser ru = new RegisterUser();
        ru.execute(email, username, password, separator);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();
                break;


            case R.id.googleLogin:
                signIn();
                break;

            case R.id.btnLinkToRegisterScreen:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
        }

    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
