package rss_feed.rssreader.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import rss_feed.rssreader.Data.RssReaderWebService;
import rss_feed.rssreader.Data.User;
import rss_feed.rssreader.R;

/**
 * Created by Phil on 30/04/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int    REQUEST_SIGNUP = 0;

    boolean                     showPassword;

    Button mloginButton;
    TextView msignUpTextView;
    EditText meUsernameText;
    EditText mpasswordText;
    ImageView mVisibilityImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //  NotificationData.getInstance(this).SetNotification(getApplicationContext(), 0, "", "", LoginActivity.class);


        meUsernameText = (EditText)findViewById(R.id.input_email_login);
        mpasswordText = (EditText)findViewById(R.id.input_password);
        mVisibilityImage = (ImageView)findViewById(R.id.visibility_password);
        mVisibilityImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPassword)
                {
                    showPassword = false;
                    mpasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mVisibilityImage.setImageResource(R.drawable.ic_visibility_off_grey);
                }
                else
                {
                    showPassword = true;
                    mpasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mVisibilityImage.setImageResource(R.drawable.ic_visibility_grey);
                }
            }
        });

        mloginButton = (Button)findViewById(R.id.btn_login);
        mloginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        msignUpTextView = (TextView)findViewById(R.id.link_signup);
        msignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        showPassword = false;
        mpasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mVisibilityImage.setImageResource(R.drawable.ic_visibility_off_grey);
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            mloginButton.setEnabled(true);
            return;
        }

        mloginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");
        progressDialog.show();

        String email = meUsernameText.getText().toString();
        String password = mpasswordText.getText().toString();
        User user = new User(password, email);


        RssReaderWebService.getInstance().getUserLogin(user);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (RssReaderWebService.getInstance().isConnected)
                            onLoginSuccess();
                        else
                            onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 8000);

        onLoginSuccess();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        mloginButton.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        //finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Username or Password incorrect", Toast.LENGTH_LONG).show();
        mloginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = meUsernameText.getText().toString();
        String password = mpasswordText.getText().toString();

        if (username.isEmpty()) {
            meUsernameText.setError("Username is missing");
            valid = false;
        } else {
            meUsernameText.setError(null);
        }

        if (password.isEmpty()) {
            mpasswordText.setError("Password is missing");
            valid = false;
        } else {
            mpasswordText.setError(null);
        }

        return valid;
    }
}
