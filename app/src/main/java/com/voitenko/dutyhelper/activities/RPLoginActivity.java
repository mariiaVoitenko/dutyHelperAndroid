package com.voitenko.dutyhelper.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.voitenko.dutyhelper.R;
import com.voitenko.dutyhelper.BL.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class RPLoginActivity extends Activity {

    //facebook callbacks manager
    private CallbackManager mCallbackManager;
    private Button mGoToMainButton;
    private LoginButton mFbLoginButton;
    private TextView mForgottenPasswordTextView;
    private TextView mRegistrationTextView;

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            if (accessToken != null) {
                Intent main = new Intent(RPLoginActivity.this, MainActivity.class);
                main.putExtra("login", "noapp");
                startActivity(main);
            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        mRegistrationTextView=(TextView)findViewById(R.id.txtRegistrate);
//        mRegistrationTextView.setText(Html.fromHtml(
//                "<b>If you lost your password please contact </b>" +
//                        "<a href=\"http://10.0.3.2:8080/#/register\">DutyHelper on web</a> "));
//        mRegistrationTextView.setMovementMethod(LinkMovementMethod.getInstance());

        mFbLoginButton = (LoginButton) findViewById(R.id.login_button);
        mFbLoginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        mFbLoginButton.registerCallback(mCallbackManager, mCallback);
        // Callback registration
        mFbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                String email = null;
                                try {
                                    email = object.getString("email");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.v("LoginActivity", response.toString());
                                Log.v("LoginActivity", object.toString());

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields",  "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });

        mGoToMainButton = (Button) findViewById(R.id.gotomain_button);
        mGoToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserManager.isProfileValid(RPLoginActivity.this)) {
                    Toast msg = Toast.makeText(RPLoginActivity.this, "Log in first", Toast.LENGTH_SHORT);
                    msg.show();
                } else {
                    startActivity(new Intent(RPLoginActivity.this, MainActivity.class));
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getHashInfo() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.voitenko.dutyhelper",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
}