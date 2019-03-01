package com.cao.nang.myapplication;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ActivityFb extends AppCompatActivity {
private LoginButton loginButton;
private CallbackManager callbackManager;
private ShareButton btnShares;
private Button btnShare;
private ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb);
        getKeyHash();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        callbackManager = CallbackManager.Factory.create();
        btnShare = findViewById(R.id.btn_share);
        shareDialog = new ShareDialog(this);
        // this part is optional
     btnShare.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                 @Override
                 public void onSuccess(Sharer.Result result) {

                 }

                 @Override
                 public void onCancel() {

                 }

                 @Override
                 public void onError(FacebookException error) {

                 }
             });
             if (ShareDialog.canShow(ShareLinkContent.class)) {
                 ShareLinkContent linkContent = new ShareLinkContent.Builder()
                         .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                         .build();
                 shareDialog.show(linkContent);
             }
             btnShare.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     getPhotos();
                 }
             });
             LoginManager.getInstance().registerCallback(callbackManager,
                     new FacebookCallback<LoginResult>() {
                         @Override
                         public void onSuccess(LoginResult loginResult) {
                             // App code
                         }

                         @Override
                         public void onCancel() {
                             // App code
                         }

                         @Override
                         public void onError(FacebookException exception) {
                             // App code
                         }
                     });
             getPhotos();
             getShareLink();
         }
     });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void getKeyHash(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.myapplication",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {
        }
    }
    private void getShareLink(){
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
    }
    private void getPhotos(){
        Bitmap image = BitmapFactory.decodeResource(getResources(),R.id.img);

        SharePhoto photo = new SharePhoto.Builder().setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
    }
}
