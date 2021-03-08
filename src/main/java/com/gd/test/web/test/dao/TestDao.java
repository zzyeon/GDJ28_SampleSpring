package com.gd.test.web.test.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository // Dao에 다는 어노테이션
public class TestDao implements ITestDao {
	@Autowired
	public SqlSession sqlSession;

	@Override
	public List<HashMap<String, String>> getMem(HashMap<String, String> params) throws Throwable {
		// 복수의 row 취득 시 selectList 사용
		// ()에는 쿼리의 위치 지정 : 네임스페이스명.아이디명
		return sqlSession.selectList("test.getMem", params);
	}

	@Override
	public List<HashMap<String, String>> getB(HashMap<String, String> params) throws Throwable {
		
		return sqlSession.selectList("test.getB", params);
	}

	@Override
	public HashMap<String, String> getMemDetail(HashMap<String, String> params) throws Throwable {
		return sqlSession.selectOne("test.getMemDetail", params);
		// 단건 받을 때는 selectOne, 여러건 받을 때는 selectList
	}


	@Override
	public HashMap<String, String> getBDetail(HashMap<String, String> params) throws Throwable {
		return sqlSession.selectOne("test.getBDetail", params);
	}

	@Override
	public void addMem(HashMap<String, String> params) throws Throwable {
		sqlSession.insert("test.addMem", params);
	}

	@Override
	public void addB(HashMap<String, String> params) throws Throwable {
		sqlSession.insert("test.addB", params);
	}

	@Override
	public int updateMem(HashMap<String, String> params) throws Throwable {
		return sqlSession.update("test.updateMem", params);
	}

	@Override
	public int updateB(HashMap<String, String> params) throws Throwable {
		return sqlSession.update("test.updateB", params);
	}

	@Override
	public int deleteMem(HashMap<String, String> params) throws Throwable {
		return sqlSession.delete("test.deleteMem", params);
	}

	@Override
	public int deleteB(HashMap<String, String> params) throws Throwable {
		return sqlSession.delete("test.deleteB", params);
	}

	@Override
	public int getMemCnt(HashMap<String, String> params) throws Throwable {
		return sqlSession.selectOne("test.getMemCnt", params); // 단건 조회는 selectOne, 여러줄 조회는 selectList
	}

	@Override
	public int getBCnt(HashMap<String, String> params) throws Throwable {
		return sqlSession.selectOne("test.getBCnt", params);
	}
}

