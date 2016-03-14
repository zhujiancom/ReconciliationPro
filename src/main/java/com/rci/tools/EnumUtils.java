package com.rci.tools;

import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.CollectionUtils;

import com.rci.bean.LabelValueBean;
import com.rci.contants.BusinessConstant;

public class EnumUtils {
	public static String getEnumMessage(Enum<?> e) {
		if (e == null) {
			return null;
		}
		return PropertyUtils.getStringValue(getEnumResourceKey(e));
	}

	public static String getEnumResourceKey(Enum<?> e) {
		if (e == null) {
			return null;
		}

		Class<?> declaringClass = e.getDeclaringClass();
		Class<?> enclosingClass = declaringClass.getEnclosingClass();

		StringBuilder key = new StringBuilder();
		if (enclosingClass != null) {
			key.append(enclosingClass.getName()).append(BusinessConstant.DOT);
			key.append(declaringClass.getSimpleName());
		} else {
			key.append(declaringClass.getName());
		}
		key.append(BusinessConstant.DOT).append(e.name());
		return key.toString();
	}

	public static <E extends Enum<E>> List<LabelValueBean<String>> getEnumLabelValueBeanWithSelectList(Class<E> enumClass) {
		 List<LabelValueBean<String>> labelValueBeans =getEnumLabelValueBeanList(enumClass,true);
		 if (CollectionUtils.isEmpty(labelValueBeans)) {
			 return Collections.emptyList();
		 }
		 return labelValueBeans;
	}
	
	public static <E extends Enum<E>> List<LabelValueBean<String>> getEnumLabelValueBeanList(Class<E> enumClass,boolean withSelect){
		return getEnumLabelValueBeans(enumClass,String.class,withSelect,new Converter<E, String>() {

			@Override
			public String convert(E e) {
				return e.name();
			}
		},new ElementFilter<E>() {

			@Override
			public boolean accept(E e) {
				return true;
			}
		});
	}

	/**
	 * 
	 * Describle(描述)：
	 *
	 * 方法名称：getEnumLabelValueBeans
	 *
	 * 所在类名：EnumUtils
	 *
	 * Create Time:2015年9月30日 上午10:34:37
	 *  
	 * @param enumClass  枚举类型
	 * @param valueClass	需要展示的值类型
	 * @param withSelect   是否有 '请选择' 选项
	 * @param converter    转换器
	 * @param filter	过滤器
	 * @return
	 */
	public static <E extends Enum<E>, V> List<LabelValueBean<V>> getEnumLabelValueBeans(
			Class<E> enumClass, Class<V> valueClass, boolean withSelect,
			Converter<E, V> converter, ElementFilter<E> filter) {
		List<LabelValueBean<V>> list = withSelect ? emptyListWithSelect(valueClass,null) : emptyListWithoutSelect(valueClass);
		EnumSet<E> enumSet = EnumSet.allOf(enumClass);
		for (E e : enumSet) {
		    if (filter.accept(e)) {
				list.add(new LabelValueBean<V>(EnumUtils.getEnumMessage(e), converter.convert(e)));
		    }
		}
		return list;
	}
	
	public static <V> List<LabelValueBean<V>> emptyListWithSelect(Class<V> valueClass, V selectValue){
		List<LabelValueBean<V>> list = emptyListWithoutSelect(valueClass);
		list.add(new LabelValueBean<V>(PropertyUtils.getStringValue("combobox.default.list.select"), selectValue));
		return list;
	}
	
	/**
	 * 
	 * Describle(描述)：创建一个不带 "请选择" 选项的下拉列表
	 *
	 * 方法名称：emptyListWithoutSelect
	 *
	 * 所在类名：EnumUtils
	 *
	 * Create Time:2015年9月30日 上午10:51:51
	 *  
	 * @param valueClass
	 * @return
	 */
	public static <V> List<LabelValueBean<V>> emptyListWithoutSelect(Class<V> valueClass){
		List<LabelValueBean<V>> list = new LinkedList<LabelValueBean<V>>();
		return list;
	}
}
