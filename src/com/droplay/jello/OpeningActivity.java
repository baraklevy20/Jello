package com.droplay.jello;

import com.droplay.jello.menu.JelloMenuActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class OpeningActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageView image = new ImageView(this);
		image.setBackgroundResource(R.drawable.openscreen);
		setContentView(image);
	}
	
	@Override
	protected void onStart() {
		super.onStart();

		new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(OpeningActivity.this, JelloMenuActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2500);
	}
}
