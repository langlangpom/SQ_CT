package com.evian.sqct.util;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**数据库ResultSet反射List类*/
public class ResultSetToBeanHelper implements ResultSetExtractor<List<Map<String, Object>>>{

	
	@SuppressWarnings("unchecked")
	public static List resultSetToList(ResultSet rs, Class cls) throws Exception {

		//
		Method[] methods = cls.getDeclaredMethods();
		List lst = new ArrayList();
		
		ResultSetMetaData meta = rs.getMetaData();
		
		if(Map.class.isAssignableFrom(cls)){// 当Class是Map.class时  返回List<Map<Object,Object>>
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					String colName = meta.getColumnName(i);
					Object value = rs.getObject(colName);
					map.put(colName, value);
				}
				lst.add(map);
			}
			return lst;
		}else{ // 当Class不是Map.class时  返回List<Object>
			Object obj = null;
			while (rs.next()) {
				obj = cls.newInstance(); 
				for (int i = 1; i <= meta.getColumnCount(); i++) {
					String colName = meta.getColumnName(i);
					String setMethodName = "set" + colName;
	
					for (int j = 0; j < methods.length; j++) {
						if (methods[j].getName().equalsIgnoreCase(setMethodName)) {
							setMethodName = methods[j].getName();
							Object value = rs.getObject(colName);
							if (value == null) {
								continue;
							}
							try {
								Method setMethod = obj.getClass().getMethod(setMethodName, value.getClass());
								setMethod.invoke(obj, value);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				lst.add(obj);
			}
	
			return lst;
		}

	}
	

	/**
	 * 2018.3.14
	 * 谢海鑫
	 * @return ResultSetExtractor<List<Map<String, Object>>>
	 */
	public static ResultSetToBeanHelper resultSetToListMap() {
		return new ResultSetToBeanHelper();
	}

	
	/**
	 * 2018.3.14
	 * 谢海鑫
	 * jdbcTemplate 返回集合 不用建立实体类 省事
	 */
	@Override
	public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
		ResultSetMetaData meta = rs.getMetaData();
		List lst = new ArrayList();
		
		while (rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String colName = meta.getColumnName(i);
					Object value = rs.getObject(colName);
					map.put(colName, value);
			}
			lst.add(map);
		}
		return lst;
	}
}
