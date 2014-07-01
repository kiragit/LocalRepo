package com.gmail.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class SampleJenkinsActivity extends Activity {
    /** Called when the activity is first created. */

	private Button button1;
	private CheckBox checkBox1;
	private CheckBox checkBox2;
	private CheckBox checkBox3;
	private SeekBar seekBar1;
	private TextView textView1;
	private int seekBar1Prog=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button1= (Button)findViewById(R.id.button1);
        checkBox1 = (CheckBox)findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox)findViewById(R.id.checkBox3);
        seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
        textView1 = (TextView)findViewById(R.id.textView1);
        
        setInitValue();

    }

	private void setInitValue() {

		seekBar1.setMax(255);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				seekBar1Prog = seekBar1Prog + 10;
				if(seekBar1Prog>255)seekBar1Prog=0;
				seekBar1.setProgress(seekBar1Prog);
			}
		});
		
		checkBox1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				textView1.setText(checkBox1.getText());
			}
		});
		
		checkBox2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				textView1.setText(checkBox2.getText());
			}
		});
		
		checkBox3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				textView1.setText(checkBox3.getText());
			}
		});
	}
}