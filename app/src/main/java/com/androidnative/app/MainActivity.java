package com.androidnative.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.otpless.dto.OtplessResponse;
import com.otpless.main.OtplessManager;
import com.otpless.main.OtplessView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    OtplessView otplessView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise OtplessView
        otplessView = OtplessManager.getInstance().getOtplessView(this);
        final JSONObject extras = new JSONObject();
        try {
            extras.put("method", "get");
            final JSONObject params = new JSONObject();
            params.put("cid", "I9HXYP33C1K9Z61ZIF0MI1PY4VZOFX6Q"); // Replace the cid value with your CID value which is provided in the docs
            params.put("crossButtonHidden", true);
            extras.put("params", params);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        otplessView.setCallback(this::onOtplessCallback, extras);
        otplessView.showOtplessLoginPage(extras, this::onOtplessCallback);
        otplessView.verifyIntent(getIntent());
    }

    private void onOtplessCallback(OtplessResponse response) {
        if (response.getErrorMessage() != null) {
// todo error handing
        } else {
            final String token = response.getData().optString("token");
// todo token verification with api
            Log.d("Otpless", "token: " + token);
            Toast.makeText(this, "Token : " + token, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), SecondActivity.class);
            i.putExtra("passing_token", token);
            startActivity(i);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (otplessView != null) {
            otplessView.verifyIntent(intent);
        }
    }
    @Override
    public void onBackPressed() {
        // make sure you call this code before super.onBackPressed();
        if (otplessView.onBackPressed()) return;
        super.onBackPressed();
    }
}