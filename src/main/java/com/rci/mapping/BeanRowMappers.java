/**
 * 
 */
package com.rci.mapping;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;

import com.rci.annotation.ColumnName;

/**
 * @author zj
 *	
 * 项目名称：BillSystem
 *
 * 类名称：BeanRowMappers
 *
 * 包名称：com.bill.sys
 *
 * Operate Time: 2015年4月15日 下午11:28:43
 *
 * remark (备注):
 *
 * 文件名称：BeanRowMappers.java
 *
 */
public class BeanRowMappers<T> implements RowMapper<T> {

	private Class<T> clazz;

	public BeanRowMappers(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T obj = null;
		try {
			obj = clazz.newInstance();
			PropertyDescriptor[] pdrs = BeanUtils.getPropertyDescriptors(clazz);
			for (PropertyDescriptor pdr : pdrs) {
				if(pdr.getPropertyType() == Class.class){
					continue;
				}
				Method rMethod = pdr.getReadMethod();
				Method wMethod = pdr.getWriteMethod();
				ColumnName columnName = rMethod.getAnnotation(ColumnName.class);
				Class<?> ptype = rMethod.getReturnType();
				if (columnName != null) {
					if (ptype == String.class) {
						wMethod.invoke(obj, rs.getString(columnName.value()));
					}
					if(ptype == Long.class){
						wMethod.invoke(obj, rs.getLong(columnName.value()));
					}
					if(ptype == BigDecimal.class){
						wMethod.invoke(obj, rs.getBigDecimal(columnName.value()));
					}
					if(ptype == Timestamp.class){
						wMethod.invoke(obj, rs.getTimestamp(columnName.value()));
					}
					if(ptype == Integer.class){
						wMethod.invoke(obj, rs.getInt(columnName.value()));
					}
					if(Enum.class.isAssignableFrom(ptype)){
						wMethod.invoke(obj, enumValue(ptype,rs.getString(columnName.value())));
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		return obj;
	}

	/**
	 * 
	 * Describle(描述)：转换成Enum类型
	 *
	 * 方法名称：enumValue
	 *
	 * 所在类名：BeanRowMappers
	 *
	 * Create Time:2015年6月23日 上午8:56:27
	 *  
	 * @param toClass
	 * @param o
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Enum<?> enumValue(Class toClass, Object o) {
		Enum result = null;
		if (o == null)
			result = null;
		else if (o instanceof String[])
			result = Enum.valueOf(toClass, ((String[]) (String[]) o)[0]);
		else if (o instanceof String) {
			result = Enum.valueOf(toClass, (String) o);
		}
		return result;
	}
	
}
