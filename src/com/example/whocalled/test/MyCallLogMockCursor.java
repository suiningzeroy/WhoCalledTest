package com.example.whocalled.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.test.mock.MockCursor;
import android.util.Log;

public class MyCallLogMockCursor extends MockCursor{
	
	private String LOGGING_TAG = "MockCursor";
	private Map<Long, List<String>> cursorList;
	private Long key;
	private Map<String, Integer> columnIndex;
	
	public MyCallLogMockCursor(Map<Long, List<String>> log){
		cursorList = log;
		key = (long)0 ;
		columnIndex = setColumnIndex();
	}

	@Override
	public String getString (int columnIndex){	
		Log.d(LOGGING_TAG, "columnIndex = " + String.valueOf(columnIndex) );
		switch(columnIndex){
			case CallLog.Calls.NUMBER_ID: 
				Log.d(LOGGING_TAG, "NUMBER_ID ");
				return cursorList.get(key).get(1);
			case CallLog.Calls.CACHED_NAME_ID: 
				Log.d(LOGGING_TAG, "CACHED_NAME_ID ");
				return cursorList.get(key).get(0);
			case CallLog.Calls.DURATION_ID: 
				return cursorList.get(key).get(2);
			case CallLog.Calls.DATE_ID: 
				return cursorList.get(key).get(3);
			
			default: return cursorList.get(key).get(1);
		}
	}
	
	@Override
	public long getLong (int columnIndex){	
		switch(columnIndex){
			case CallLog.Calls.DURATION_ID: 
				return Long.valueOf(cursorList.get(key).get(2));
			case CallLog.Calls.DATE_ID: 
				return Long.valueOf(cursorList.get(key).get(3));
		}
		return (long)999;
	}
	
	@Override
	public int  getColumnIndex(String columnName){
		return columnIndex.get(columnName);
	}
	
	@Override
	public boolean moveToNext(){
		return cursorList.containsKey(++key)?true:false;
	}
	
	@Override
	public boolean moveToFirst(){
		key = (long)0;
		return true;
	}
	
	private Map<String, Integer> setColumnIndex(){
		Map<String, Integer> columnIndex = new HashMap<String, Integer>();
		
		columnIndex.put(CallLog.Calls.CACHED_NAME, CallLog.Calls.CACHED_NAME_ID);
		columnIndex.put(CallLog.Calls.NUMBER, CallLog.Calls.NUMBER_ID);
		columnIndex.put(CallLog.Calls.DURATION, CallLog.Calls.DURATION_ID);
		columnIndex.put(CallLog.Calls.DATE, CallLog.Calls.DATE_ID);
		return columnIndex;
	}
	
}
