package com.gmail.sample;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AndroidWithJenkinsActivity extends Activity {

	private Button mButton;
	private TextView mTtestPaint_1;
	private TextView mTtestPaint_2;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mButton = (Button)findViewById(R.id.mButton);
        mTtestPaint_1 = (TextView)findViewById(R.id.mTextPaint_1);
        mTtestPaint_2 = (TextView)findViewById(R.id.mTextPaint_2);

        mButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		mButton.setText("push");
        	}
        });
    }
}