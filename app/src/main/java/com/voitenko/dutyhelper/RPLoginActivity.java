package com.voitenko.dutyhelper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class RPLoginActivity extends Activity  {

    //facebook callbacks manager
    private CallbackManager mCallbackManager;
    private Button mGoToMainButton;
    private LoginButton mFbLoginButton;

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            if (accessToken!=null) {
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

        mFbLoginButton = (LoginButton) findViewById(R.id.login_button);
        mFbLoginButton.setReadPermissions("user_friends");
        mFbLoginButton.registerCallback(mCallbackManager, mCallback);

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
                    "ua.kharkiv.roadpolizei.roadpolizei",
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