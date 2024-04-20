package com.androidnative.app;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.otpless.main.OtplessManager;
import com.otpless.main.OtplessView;
import com.otpless.dto.HeadlessRequest;
import com.otpless.dto.HeadlessResponse;
import com.otpless.dto.HeadlessChannelType;
import com.otpless.utils.Utility;


import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    OtplessView otplessView;

    private EditText inputEditText, otpEditText;

    private HeadlessChannelType channelType;
    private TextView headlessResponseTv;

    private Button otpverify,whatsappButton,gmailButton,twitterButton,slackButton,facebookButton,linkedinButton,microsoftButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inputEditText = findViewById(R.id.input_text_layout);
        otpEditText = findViewById(R.id.otp_et);
        otpverify = findViewById(R.id.otpverify);
        whatsappButton = findViewById(R.id.whatsapp_btn);
        gmailButton = findViewById(R.id.gmail_btn);
        twitterButton = findViewById(R.id.twitter_btn);
        slackButton = findViewById(R.id.slack_btn);
        facebookButton = findViewById(R.id.facebook_btn);
        linkedinButton = findViewById(R.id.linkedin_btn);
        microsoftButton = findViewById(R.id.microsoft_btn);
        headlessResponseTv = findViewById(R.id.headless_response_tv);


        // copy this code in onCreate of your Login Activity
        otplessView = OtplessManager.getInstance().getOtplessView(this);
        otplessView.initHeadless("OQKCVGB7H42IZPVUMX7P", savedInstanceState); //replace with your appid provided in documentation
        otplessView.setHeadlessCallback(this::onHeadlessCallback);
        otplessView.verifyIntent(getIntent());


        initTestingView();


    }

    private HeadlessRequest getHeadlessRequest() {
        final String input = inputEditText.getText().toString();
        final HeadlessRequest request = new HeadlessRequest();
        if (this.channelType != null) {
            request.setChannelType(this.channelType);
        }else {
            if (!input.trim().isEmpty()) {
                try {
                    // parse phone number
                    Long.parseLong(input);
                    request.setPhoneNumber("+91", input);
                } catch (Exception ex) {
                    request.setEmail(input);
                }

            }
            final String otp = otpEditText.getText().toString();
            if (!otp.trim().isEmpty()){
                request.setOtp(otp);
            }
        }
        return request;
    }


    private void initTestingView() {
        otpverify.setOnClickListener(view -> {
            otplessView.startHeadless(getHeadlessRequest(), this::onHeadlessCallback);
        });
        // This code will be used to detect the WhatsApp installed status on the user's device.
        if (Utility.isWhatsAppInstalled(this)) {
            whatsappButton.setVisibility(View.VISIBLE);
        } else {
            whatsappButton.setVisibility(View.GONE);
        }
        //end
        whatsappButton.setOnClickListener(view -> {
            channelType = HeadlessChannelType.WHATSAPP;
            otplessView.startHeadless(getHeadlessRequest(), this::onHeadlessCallback);
        });

        gmailButton.setOnClickListener(view -> {
            channelType = HeadlessChannelType.GMAIL;
            otplessView.startHeadless(getHeadlessRequest(), this::onHeadlessCallback);
        });

        twitterButton.setOnClickListener(view -> {
            channelType = HeadlessChannelType.TWITTER;
            otplessView.startHeadless(getHeadlessRequest(), this::onHeadlessCallback);
        });

        slackButton.setOnClickListener(view -> {
            channelType = HeadlessChannelType.SLACK;
            otplessView.startHeadless(getHeadlessRequest(), this::onHeadlessCallback);
        });

        facebookButton.setOnClickListener(view -> {
            channelType = HeadlessChannelType.FACEBOOK;
            otplessView.startHeadless(getHeadlessRequest(), this::onHeadlessCallback);
        });

        linkedinButton.setOnClickListener(view -> {
            channelType = HeadlessChannelType.LINKEDIN;
            otplessView.startHeadless(getHeadlessRequest(), this::onHeadlessCallback);
        });

        microsoftButton.setOnClickListener(view -> {
            channelType = HeadlessChannelType.MICROSOFT;
            otplessView.startHeadless(getHeadlessRequest(), this::onHeadlessCallback);

        });


    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        otplessView.verifyIntent(intent);
    }

    private void onHeadlessCallback(@NonNull final HeadlessResponse response) {
        if (response.getStatusCode() == 200) {
            JSONObject successResponse = response.getResponse();
        } else {
            String error = response.getResponse().optString("errorMessage");
        }
        headlessResponseTv.setText(response.toString());
    }


    @Override
    public void onBackPressed() {
        if (otplessView.onBackPressed()) return;
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}