package com.tubing.client.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kornfeld on 17/12/2015.
 */
public class ShareActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.share);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        final String stringExtra = intent.getStringExtra(Intent.EXTRA_TEXT);

        // You would then use these three variable to figure out who has sent you an Intent and what they want you to do!
        // See here for further instruction: http://developer.android.com/training/sharing/receive.html
        // http://developer.android.com/guide/topics/manifest/action-element.html
        // http://developer.android.com/reference/android/content/Intent.html#ACTION_GET_CONTENT

    }
}
