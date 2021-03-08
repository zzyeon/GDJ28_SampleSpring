package com.gd.test.web.test.dao;

import java.util.HashMap;
import java.util.List;

public interface ITestDao {
	 List<HashMap<String, String>> getMem(HashMap<String, String> params) throws Throwable;

	List<HashMap<String, String>> getB(HashMap<String, String> params) throws Throwable;

	HashMap<String, String> getMemDetail(HashMap<String, String> params) throws Throwable;

	HashMap<String, String> getBDetail(HashMap<String, String> params) throws Throwable;

	void addMem(HashMap<String, String> params) throws Throwable;

	void addB(HashMap<String, String> params) throws Throwable;

	int updateMem(HashMap<String, String> params) throws Throwable;

	int updateB(HashMap<String, String> params) throws Throwable;

	int deleteMem(HashMap<String, String> params) throws Throwable;

	int deleteB(HashMap<String, String> params) throws Throwable;

	int getMemCnt(HashMap<String, String> params) throws Throwable;

	int getBCnt(HashMap<String, String> params) throws Throwable;

}
