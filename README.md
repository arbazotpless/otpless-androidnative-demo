# Android Native

Integrating One Tap OTPLESS Sign In into your React Native Application using our SDK is a streamlined process. This guide offers a comprehensive walkthrough, detailing the steps to install the SDK and seamlessly retrieve user information.

1. Install **OTPless SDK** Dependency
- In your app's build.gradle file, insert the following line into the dependencies section and sync your gradle
```gradle
implementation 'io.github.otpless-tech:otpless-android-sdk:2.1.8'
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
	android:scheme= "com.androidnative.app.otpless"/> //remove the application id and add the login uri which you are passing
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
            params.put("cid", "I9HXYP33C1K9Z61ZIF0MI1PY4VZOFX6Q");

            //parameter to add updated package name and also makes changes in manifest.xml file
            params.put("login_uri","com.androidnative.app");
            //parameter to add updated package name and also makes changes in manifest.xml file

            extras.put("params", params);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        otplessView.setCallback(this::onOtplessCallback, extras);
        otplessView.showOtplessLoginPage(extras, this::onOtplessCallback);
        otplessView.verifyIntent(getIntent());

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialise OtplessView
        otplessView = OtplessManager.getInstance().getOtplessView(this)
        val extras = JSONObject()
        try {
            extras.put("method", "get")
            val params = JSONObject()
            params.put("cid", "I9HXYP33C1K9Z61ZIF0MI1PY4VZOFX6Q")

            // parameter to add updated package name and also makes changes in manifest.xml file
            params.put("login_uri", "com.androidnative.app")
            // parameter to add updated package name and also makes changes in manifest.xml file

            extras.put("params", params)
        } catch (e: JSONException) {
            throw RuntimeException(e)
        }
        otplessView.setCallback(this::onOtplessCallback, extras)
        otplessView.showOtplessLoginPage(extras) { result -> onOtplessCallback(result, extras) }
        otplessView.verifyIntent(intent)
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
