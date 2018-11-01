package com.example.android.foodtinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.loginBtn)
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null && !accessToken.isExpired()){
            navigateToMainRecipesActivity(accessToken);
        }else{
            login();
        }
    }

    private void login(){
        String EMAIL = "email";
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        //loginbutton register callbackmanager and facebookcallback handles the event
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                navigateToMainRecipesActivity(accessToken);
                Toast.makeText(MainActivity.this,"successful login",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"The user cancelled login");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG,"login facebook error: "+error.toString());
            }
        });
    }

    private void navigateToMainRecipesActivity(AccessToken accessToken){
        Intent intent = new Intent(this,FoodRecipesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("access_token",accessToken);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //this is a startactivityforresult call from Facebook's login page
        //this onactivityresult of callbackmanager will actually call loginbutton's callback
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


}



















