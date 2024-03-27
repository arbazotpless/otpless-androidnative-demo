package com.androidnative.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.otpless.dto.OtplessRequest;
import com.otpless.dto.OtplessResponse;
import com.otpless.main.OtplessManager;
import com.otpless.main.OtplessView;
import com.otpless.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;


public class MainActivity extends AppCompatActivity {
    OtplessView otplessView;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Button button = findViewById(R.id.button);
        final String token;

        //******************************************************** */
        //This function will tell if WhatsApp is Installed or not.
        // If you are using only whatsapp authentication then you can hide visibility of Login button using this function,
        // if user doesn't have whatsapp installed on device.
        //******************************************************** */

        if (Utility.isWhatsAppInstalled(this)){
            Toast.makeText(this, "whatsapp is installed in device", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Whatsapp is not installed in device", Toast.LENGTH_SHORT).show();
        }


        // Initialise OtplessView
        otplessView = OtplessManager.getInstance().getOtplessView(this);
        OtplessRequest request = new OtplessRequest("RI7MXA25OHAGDHBXMFB1")  // replace app id with your appid provided in documentation
                .setCid("I9HXYP33C1K9Z61ZIF0MI1PY4VZOFX6Q");                       // replace Cid with your Cid provided in documentation
        otplessView.setCallback(request, this::onOtplessCallback);
        otplessView.verifyIntent(getIntent());

        //calling otpless loginpage on button click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otplessView.showOtplessLoginPage(request, response -> onOtplessCallback(response));

            }
        });


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




