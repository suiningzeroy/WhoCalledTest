package com.example.whocalled.test;

import com.example.whocalled.WhoCalledService;

import android.content.Context;
import android.content.Intent;
import android.test.ServiceTestCase;

public class WhoCalledServiceTest extends ServiceTestCase<WhoCalledService> {

	private Context context;
	
	public WhoCalledServiceTest(Class<WhoCalledService> serviceClass) {
		super(serviceClass);
	}

	protected void setUp() throws Exception {
		super.setUp();
		context = getContext();
	}

	protected void tearDown() throws Exception {
		context = null;
		super.tearDown();
	}
	
	public void testStart() {
		Intent intent = new Intent(getContext(),WhoCalledService.class);
		startService(intent);
		WhoCalledService whocalledService = getService();
		assertNotNull(whocalledService);
	}
	
	public void teststop() {
		Intent intent = new Intent();
		startService(intent);
		WhoCalledService service = getService();
		service.stopService(intent);
	}

}
