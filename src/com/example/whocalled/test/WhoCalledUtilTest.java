package com.example.whocalled.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.example.whocalled.WhoCalledOrmLiteHelper;
import com.example.whocalled.WhoCalledUtil;
import com.example.whocalled.model.Contact;
import com.example.whocalled.model.Statistic;


public class WhoCalledUtilTest extends AndroidTestCase {

	private static WhoCalledOrmLiteHelper ormLiteHelper;
	private static RenamingDelegatingContext context;

	protected void setUp() throws Exception {
		context = new RenamingDelegatingContext(getContext(), "test_");
		super.setUp();
	}

	protected void tearDown() throws Exception {
		WhoCalledUtil.releaseOrmLiteHelper();
		super.tearDown();
	}

	private void insertTestDataInStatisticTable(Context context){
		Statistic testStatistic = new Statistic();
		testStatistic.setPhonenumber("114");
		testStatistic.setUsername("test");
		testStatistic.setCallcounts(10);
		testStatistic.setCallduration(100);
		testStatistic.setStatisticdate(Long.valueOf("1379321410851"));
		testStatistic.setCallaverage(10);
		WhoCalledUtil.clearStatisticTable(context);
		WhoCalledUtil.insertToStatistics(context, testStatistic);
		
	}
	
	private Map<Long, List<String>> setTestCaseLogList(){
		Map<Long, List<String>> testCase = new HashMap<Long, List<String>>();
		Long key = (long) 0;
		List<String> logList = setValueForTestCase("test","114","5","2222222");
		testCase.put(key,logList);
		
		return testCase;
	}
	
	private List<String> setValueForTestCase(String name,String num,String du,String date){
		List<String> logList = new ArrayList<String>();
		logList.add(name);
		logList.add(num);
		logList.add(du);
		logList.add(date);
		return logList;
	}

	public final void testUpdateStatisticUseAddedCallLog() {
		insertTestDataInStatisticTable(context);
		Map<Long, List<String>> testCase = setTestCaseLogList();
		MyCallLogMockCursor mCursor = new MyCallLogMockCursor(testCase);
		WhoCalledUtil.updateStatisticUseAddedCallLog(context,mCursor);
		Statistic result = WhoCalledUtil.getTestDataFromStatisticTable(context);
		WhoCalledUtil.clearStatisticTable(context);
		
		if(result == null){
			fail("result is null");
		}else{
			assertEquals("test",result.getUsername());
			assertEquals("114",result.getPhonenumber());
			assertEquals((long) 11,result.getCallcounts());
			assertEquals((long) 105,result.getCallduration());
			assertEquals((long)2222222,result.getStatisticdate());
		}
	}

	public final void testIsTodaysDataPrepare() {
		insertTestDataInStatisticTable(context);
		boolean result = WhoCalledUtil.isTodaysDataPrepare(context);
		assertTrue(result);
	}
	
	public final void testCompareTheLastSeveralFiguresBetweenTwoInputNumber(){
		String contactNumber = "13909067302"; 
		String callRecordNumber= "23909067302"; 
		String anotherCallRecordNumber= "12909067302"; 
		int figrueNumber = 10;
		Boolean result = WhoCalledUtil.compareTheLastSeveralFiguresBetweenTwoInputNumber(contactNumber, callRecordNumber, figrueNumber);
		Boolean anotherResult = WhoCalledUtil.compareTheLastSeveralFiguresBetweenTwoInputNumber(contactNumber, anotherCallRecordNumber, figrueNumber);

		assertTrue(result);
		assertFalse(anotherResult);
	}
	

	public final void testCompareFiguresBetweenTwoInputNumber(){
		String contactNumber = "114";  
		String callRecordNumber = "888888888114";
		
		String anotherContactNumber = "2638888";  
		String anotherCallRecordNumber = "08252638881";
		
		Boolean anotherResult = WhoCalledUtil.compareFiguresBetweenTwoInputNumber(anotherContactNumber, anotherCallRecordNumber);
		Boolean result = WhoCalledUtil.compareFiguresBetweenTwoInputNumber(contactNumber, callRecordNumber);
		assertTrue(result);
		assertFalse(anotherResult);
	}

	private void insertTestDataInContactTable(Context context){
		Contact testContact = new Contact();
		testContact.setPhonenumber("114");
		testContact.setContactname("test");
		WhoCalledUtil.clearContactTable(context);
		WhoCalledUtil.insertToContact(context, testContact);
		
	}
	
	private String getTestDataFromContactTable(Context context){
		List<Contact> result  = null;
		try {
			result = ormLiteHelper.getContactDao().queryBuilder().where().eq("contactname", "test").query();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (result == null){
			return null;
		}else{
			return result.get(0).getPhonenumber();
		}
	}
	
	public final void testModifyNumberInCallRecordIfUnrcognized() {
		String phoneNumber = "0825114";
		insertTestDataInContactTable(context);
		String result = WhoCalledUtil.modifyNumberInCallRecordIfUnrcognized(context, phoneNumber);
		WhoCalledUtil.clearContactTable(context);
		
		assertEquals("114",result);
	}

}
