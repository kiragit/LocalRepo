package com.gmail.sample.test;

import com.gmail.sample.R;
import com.gmail.sample.SampleJenkinsActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class TestCase extends ActivityInstrumentationTestCase2<SampleJenkinsActivity > {

	private SampleJenkinsActivity sampleJenkinsActivity;
	private Button button1;
	private CheckBox checkBox1;
	private CheckBox checkBox2;
	private CheckBox checkBox3;
	private SeekBar seekBar1;
	private TextView textView1;

	public TestCase(Class<SampleJenkinsActivity> activityClass) {
		super(activityClass);
	}

	@SuppressWarnings("deprecation")
	public TestCase(String pkg, Class<SampleJenkinsActivity> activityClass) {
		super(pkg,activityClass);
	}

	@Override
	protected void setUp(){
		sampleJenkinsActivity = getActivity();
		button1 = (Button) sampleJenkinsActivity.findViewById(R.id.button1);
		checkBox1 = (CheckBox) sampleJenkinsActivity.findViewById(R.id.checkBox1);
		checkBox2 = (CheckBox) sampleJenkinsActivity.findViewById(R.id.checkBox2);
		checkBox3 = (CheckBox) sampleJenkinsActivity.findViewById(R.id.checkBox3);
	}

	public void testButton1(){
		assertEquals(button1.getText(),"");
	}
	public void testCheckBox1(){
		assertEquals(checkBox1.getText(),"");
	}
	public void testCheckBox2(){
		assertEquals(checkBox2.getText(),"");
	}
	public void testCheckBox3(){
		assertEquals(checkBox3.getText(),"");
	}
}
