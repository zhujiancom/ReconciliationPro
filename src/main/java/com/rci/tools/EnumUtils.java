package com.rci.tools;

import com.rci.contants.BusinessConstant;
import com.rci.tools.properties.PropertyUtils;

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
}
