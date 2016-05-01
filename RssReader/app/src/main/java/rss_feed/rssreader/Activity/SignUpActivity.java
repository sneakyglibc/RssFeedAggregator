package rss_feed.rssreader.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rss_feed.rssreader.Data.RssReaderWebService;
import rss_feed.rssreader.Data.User;
import rss_feed.rssreader.R;

/**
 * Created by Phil on 30/04/2016.
 */
public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    EditText mnameText;
    EditText                    memailText;
    EditText                    mpasswordText;
    Button msignupButton;
    TextView mloginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mnameText = (EditText)findViewById(R.id.input_username);
        memailText = (EditText)findViewById(R.id.input_email);
        mpasswordText = (EditText)findViewById(R.id.input_password);
        msignupButton = (Button)findViewById(R.id.btn_signup);
        mloginLink = (TextView)findViewById(R.id.link_login);

        msignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        mloginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            msignupButton.setEnabled(true);
            return;
        }

        msignupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = mnameText.getText().toString();
        String email = memailText.getText().toString();
        String password = mpasswordText.getText().toString();

        User user = new User(password, email);

        RssReaderWebService.getInstance().postUser(user);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (RssReaderWebService.getInstance().isConnected)
                            onSignupSuccess();
                        else
                            onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

    }


    public void onSignupSuccess() {
        msignupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent in = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(in);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Inscriptions Closed", Toast.LENGTH_LONG).show();

        msignupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = mnameText.getText().toString();
        String email = memailText.getText().toString();
        String password = mpasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            mnameText.setError("at least 3 characters");
            valid = false;
        } else {
            mnameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            memailText.setError("enter a valid email address");
            valid = false;
        } else {
            memailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mpasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mpasswordText.setError(null);
        }

        return valid;
    }
}
