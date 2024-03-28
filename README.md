# Android Native

Integrating One Tap OTPLESS Sign In into your React Native Application using our SDK is a streamlined process. This guide offers a comprehensive walkthrough, detailing the steps to install the SDK and seamlessly retrieve user information.

1. Install **OTPless SDK** Dependency
- In your app's build.gradle file, insert the following line into the dependencies section and sync your gradle
```gradle
implementation 'io.github.otpless-tech:otpless-android-sdk:2.2.4'
```

2. Configure **AndroidManifest.xml**

`Android`

- Add an intent filter inside your Main Activity code block.

```xml
<intent-filter>
<action android:name="android.intent.action.VIEW" />
<category android:name="android.intent.category.DEFAULT" />
<category android:name="android.intent.category.BROWSABLE" />
<data
	android:host="otpless"
	android:scheme= "${applicationId}.otpless"/>
</intent-filter>
```

- Change your activity launchMode to singleTop and exported true for your Main Activity.

```xml
android:launchMode="singleTop"
android:exported="true"
```

3. **Configure Sign up/Sign in**

- Import the following classes.

`Java`
```java
import com.otpless.dto.OtplessResponse;
import com.otpless.main.OtplessManager;
import com.otpless.main.OtplessView;
```
- Add this code to your onCreate() method to initialize and load OTPLESS Sign in.
```java
    OtplessView otplessView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Button button = findViewById(R.id.button);

        // Initialise OtplessView
        otplessView = OtplessManager.getInstance().getOtplessView(this);
        OtplessRequest request = new OtplessRequest("RI7MXA25OHAGDHBXMFB1")  // replace app id with your appid provided in documentation
                .setCid("I9HXYP33C1K9Z61ZIF0MI1PY4VZOFX6Q");                 // replace Cid with your Cid provided in documentation
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
```

- This code will be used to detect the WhatsApp installed status on the user's device.

```java
// If you are using WHATSAPP login, it's required to add this code to hide the OTPless functionality

if (Utility.isWhatsAppInstalled(this)) {
    Toast.makeText(this, "WhatsApp is installed", Toast.LENGTH_SHORT).show();
} else {
    Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
}
```


`Kotlin`
```kotlin
import com.otpless.dto.OtplessResponse;
import com.otpless.main.OtplessManager;
import com.otpless.main.OtplessView;
```
- Add this code to your onCreate() method to initialize and load OTPLESS Sign in.
```kotlin
    private lateinit var otplessView: OtplessView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        // Initialise OtplessView
	otplessView = OtplessManager.getInstance().getOtplessView(this)
	OtplessRequest request =  OtplessRequest("YOUR_APPID")  // replace with your appid provided in documentation
			.setCid("YOUR_CID")                    // replace with your cid provided in documentation
	otplessView.setCallback(request, this::onOtplessCallback)
	otplessView.verifyIntent(getIntent())

        // calling otpless login page on button click
        button.setOnClickListener {
            otplessView.showOtplessLoginPage(request) { response ->
    onOtplessCallback(response)
}
        }
    }
}
```

- This code will be used to detect the WhatsApp installed status on the user's device.

```kotlin
// If you are using WHATSAPP login, it's required to add this code to hide the OTPless functionality

if (Utility.isWhatsAppInstalled(this)) {
    Toast.makeText(this, "WhatsApp is installed", Toast.LENGTH_SHORT).show();
} else {
    Toast.makeText(this, "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
}
```

4. **Handle Callback**

- Add the code to handle callback from OTPLESS sdk.

`Java`

```java
private void onOtplessCallback(OtplessResponse response) {
if (response.getErrorMessage() != null) {
// todo error handing
} else {
final String token = response.getData().optString("token");
// todo token verification with api
Log.d("Otpless", "token: " + token);
  }
}
```

`Kotlin`

```kotlin
private fun onOtplessCallback(response: OtplessResponse) {
if (response.errorMessage != null) {
// todo error handing
} else {
val token = response.data.optString("token")
// todo token verification with api
Log.d("Otpless", "token: $token")
	}
}
```
- Add this code to your onNewIntent() method.

`Java`

```java
if (otplessView != null) {
  otplessView.verifyIntent(intent);
}
```

`Kotlin`

```kotlin
otplessView.verifyIntent(intent)
```

5. **Handle Backpress**

- Add this code to your onBackPressed() method to handle backpress.

`Java`

```java
// make sure you call this code before super.onBackPressed();
if (otplessView.onBackPressed()) return;
```

`Kotlin`

```kotlin
// make sure you call this code before super.onBackPressed()
if (otplessView.onBackPressed()) return
```




# Thank You

# [Visit OTPless](https://otpless.com/platforms/android)
