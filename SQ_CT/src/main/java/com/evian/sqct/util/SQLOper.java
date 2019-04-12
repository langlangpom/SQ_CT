package com.evian.sqct.util;

/**
 * @date   2019年1月18日 下午1:53:18
 * @author XHX
 * @Description sql语句操作
 */
public class SQLOper {

	
	public static Object[] addPaging(StringBuilder sql,Object[] args,Integer PageIndex, Integer PageSize, Boolean IsSelectAll) {
		if(PageIndex!=null&&PageSize!=null) {
			Integer action = (PageIndex-1)*PageSize+1;
			Integer out = PageIndex*PageSize;
			sql.insert(0, "select * from ( ");
			sql.append(" ) as d where row between ? and ? ");
			
			// 重新建立一个新数组
			int b = args.length;
			int d = b+2;
			Object[] c =new  Object[d] ;
			for (int i = 0; i < c.length; i++) {
				if(i==b) {
					c[i]=action;
				}else if(i==b+1) {
					c[i]=out;
				}else {
					c[i]=args[i];
				}
			}
			args = c;
			
		}
		return args;
	}
}
