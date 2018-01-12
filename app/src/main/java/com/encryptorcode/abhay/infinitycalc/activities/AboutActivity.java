package com.encryptorcode.abhay.infinitycalc.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.encryptorcode.abhay.infinitycalc.R;

public class AboutActivity extends BaseActivity {

    View avatarView, websiteView, gitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        avatarView = findViewById(R.id.about_avatar);
        websiteView = findViewById(R.id.about_website);
        gitView = findViewById(R.id.about_git);

        avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://encryptorcode.wordpress.com/contactme/"));
                startActivity(i);
            }
        });

        websiteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://encryptorcode.wordpress.com/"));
                startActivity(i);
            }
        });

        gitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/abhayjatindoshi/infinitycalc"));
                startActivity(i);
            }
        });
    }
}
