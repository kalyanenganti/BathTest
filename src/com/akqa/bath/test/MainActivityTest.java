package com.akqa.bath.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.TextView;

import com.akqa.bath.BathView;
import com.akqa.bath.MainActivity;
import com.akqa.bath.NetworkUtility;
import com.akqa.bath.R;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	MainActivity mainActivity;
    ImageView imageView1, imageView2;
    TextView textView1,textView2;
    BathView bathView;
    NetworkUtility netUtil;

	public MainActivityTest() {
		super(MainActivity.class);

	}

	@Override
	public void setUp() throws Exception {
		// setUp() is run before a test case is started.
		super.setUp();
		mainActivity = getActivity();
		bathView=new BathView(mainActivity.getApplicationContext());
		netUtil=new NetworkUtility(mainActivity.getApplicationContext());
		imageView1 = (ImageView) mainActivity.findViewById(R.id.coldButton);
		imageView2 = (ImageView) mainActivity.findViewById(R.id.hotButton);
		textView1=(TextView)mainActivity.findViewById(R.id.hotButtonText);
		textView2=(TextView)mainActivity.findViewById(R.id.coldButtonText);

	}

	@Override
	public void tearDown() throws Exception {
		// tearDown() is run after a test case has finished.
		super.tearDown();
	}

	public void testPreConditions() throws Exception {
		//the two taps should be off
		boolean expected = false;
		assertEquals(expected, imageView1.isPressed());
		assertEquals(expected, imageView2.isPressed());
		
		//background images are showing for the imageView
		boolean isImageShowing= true;
		assertEquals(isImageShowing, imageView1.getBackground()!=null);
		assertEquals(isImageShowing, imageView2.getBackground()!=null);
		
		//check if Hot and Cold text under the images is shown
		boolean expected_text1=true;
		boolean expected_text2=true;
		boolean actual_text1=textView1.getText().toString().equalsIgnoreCase("HOT");
		boolean actual_text2=textView2.getText().toString().equalsIgnoreCase("COLD");
		assertEquals(expected_text1, actual_text1);
		assertEquals(expected_text2, actual_text2);
		
		//check if our custom bath view is enabled and showing
		boolean expected_view=true;
		boolean actual_view=bathView.isEnabled();
		assertEquals(expected_view, actual_view);
		
		//check if a network connection is available to fetch data from json file
		boolean expected_network=true;
		boolean actual_network=netUtil.isMobileDataEnabled()|netUtil.isWifiConnected();
		assertEquals(expected_network, actual_network);
		
	}
	
	public void testColdWaterTemperature() throws Exception {
		//check if the cold tap sets the right temperature
		boolean expected = true;
		imageView1.setPressed(true);
		TextView tv=(TextView)mainActivity.findViewById(R.id.textView1);
		String data=tv.getText().toString();
		boolean actual=data.contains("10.0");
		assertEquals(expected, actual);
 
	}
	public void testHotWaterTemperature() throws Exception {
		//check if the hot tap sets the right temperature
		boolean expected = true;
		imageView2.setPressed(true);
		TextView tv=(TextView)mainActivity.findViewById(R.id.textView1);
		String data=tv.getText().toString();
		boolean actual=data.contains("50.0");
		assertEquals(expected, actual);
 
	}
	//test to check if the image view'state is same across screen rotation
	public void testHotWaterTapImageStateOnScreenOrientationChanged(){
		float angle_before = 0;
		float angle_after=0;
		imageView1.setPressed(true);
		angle_before=imageView1.getRotation();
        mainActivity.finish();
        mainActivity=getActivity();
        if(imageView1!=null){
		angle_after= imageView1.getRotation();
		}
		assertEquals("HOtTap ImageView is restored after rotation",angle_before, angle_after);
		
	}
	
	//test to check if the image view'state is same across screen rotation
	public void testColdWaterTapImageStateOnScreenOrientationChanged(){
		float angle_before = 0;
		float angle_after=0;
		imageView2.setPressed(true);
		angle_before=imageView2.getRotation();
        mainActivity.finish();
        mainActivity=getActivity();
        if(imageView2!=null){
		angle_after= imageView2.getRotation();
		}
		assertEquals("ColdTap ImageView is restored after rotation",angle_before, angle_after);
		
	}


}
