package kr.paulkim.enjoyyourslide;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String REGISTER_URL = "http://ksmr1102.vps.phps.kr/register_lock.php";
    private EditText emailEditText, usernameEditText, passwordEditText;
    private Button btnRegister;
    private ImageView btnLinkToLoginScreen, btnBackToLoginScreen;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = (EditText) findViewById(R.id.email);
        usernameEditText = (EditText) findViewById(R.id.name);
        passwordEditText = (EditText) findViewById(R.id.password);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLoginScreen = (ImageView) findViewById(R.id.btnLinkToLoginScreen);
        btnBackToLoginScreen = (ImageView) findViewById(R.id.btnBackToLoginScreen);

        emailEditText.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf"));
        usernameEditText.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf"));
        passwordEditText.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf"));
        btnRegister.setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf"));

        btnRegister.setOnClickListener(this);
        btnLinkToLoginScreen.setOnClickListener(this);
        btnBackToLoginScreen.setOnClickListener(this);

        loading = new ProgressDialog(this);
        loading.setMessage("Please Wait!");
        loading.setCancelable(true);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                loading.show();
                registerUser();
                break;

            case R.id.btnLinkToLoginScreen:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;

            case R.id.btnBackToLoginScreen:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim().toLowerCase();
        String username = usernameEditText.getText().toString().trim().toLowerCase();
        String password = passwordEditText.getText().toString().trim().toLowerCase();
        String separator = "2".trim().toLowerCase();

        register(email, username, password, separator);
    }

    private void register(String email, String username, String password, String separator) {

        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                if (s.equals("회원가입에 성공하셨습니다.")) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }


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
                loading.dismiss();

                return result;
            }
        }
        RegisterUser ru = new RegisterUser();
        ru.execute(email, username, password, separator);
    }

}
