package com.example.whocalled.test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.whocalled.WhoCalledActivity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

public class WhoCalledActivityTest extends ActivityInstrumentationTestCase2<WhoCalledActivity> {
	private Activity mActivity;
	private ListView mList;
	
	public WhoCalledActivityTest(){
		this("WhoCalledActivityTest");
	}
	
	public WhoCalledActivityTest(String name){
		super(WhoCalledActivity.class);
		setName(name);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		mList = (ListView) mActivity.findViewById(com.example.whocalled.R.id.WhoCalledList);
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testPreconditions(){		
		assertNotNull(mActivity);
	}

	public final void testHasDisplayFields(){
		assertNotNull(mList);
	}

}
