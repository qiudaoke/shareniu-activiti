package com.shareniu.chapter15;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface ShareniuMapper {
	@Select("SELECT ID_ as id, NAME_ as name FROM ACT_RU_TASK")
	//@Select("SELECT * FROM a")
	  List<Map<String, Object>> selectTasks();
	
	@Insert("insert into a(a, b) values(#{id}, #{name})")  
	public int insertShareni(ShareniuEntiy shareniuEntiy);
}	
