package com.rci.tools;

import java.util.Locale;

public class StringUtils {
	/**
	 * 
	 * Describle(描述)：【来自Spring】
	 * 
	 * 方法名称：hasText
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午1:41:45
	 *  
	 * @param str
	 * @return
	 */
	public static boolean hasText(String str){
		return hasText((CharSequence)str);
	}
	/**
	 * 
	 * Describle(描述)：【来自Spring】  检查给定的CharSequence 是否存在实际的字符文本
	 * <pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * <pre>
	 * 方法名称：hasText
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午1:42:09
	 *  
	 * @param str
	 * @return
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * Describle(描述)：【来自Spring】
	 *
	 * 方法名称：hasLength
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午1:46:41
	 *  
	 * @param str
	 * @return
	 */
	public static boolean hasLength(final String str) {
		return hasLength((CharSequence) str);
	}
	/**
	 * 
	 * Describle(描述)：【来自Spring】 检查给定的CharSequence 既不是Null,长度也不是0 
	 *
	 * 方法名称：hasLength
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午1:44:17
	 *  
	 * @param str
	 * @return
	 */
	public static boolean hasLength(final CharSequence str) {
		return (str != null && str.length() > 0);
	}
	/**
	 * 
	 * Describle(描述)：【来自Commons.lang3】 拆分字符串
	 * 
	 * <pre>
     * StringUtils.split(null)       = null
     * StringUtils.split("")         = []
     * StringUtils.split("abc def")  = ["abc", "def"]
     * StringUtils.split("abc  def") = ["abc", "def"]
     * StringUtils.split(" abc ")    = ["abc"]
     * </pre>
	 *
	 * 方法名称：split
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午2:16:22
	 *  
	 * @param str
	 * @return
	 */
	public static String[] split(final String str){
		return org.apache.commons.lang3.StringUtils.split(str);
	}
	/**
	 * 
	 * Describle(描述)：【来自Commons.lang3】 按指定{字符}拆分字符串
	 *
	 * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
     * StringUtils.split("a:b:c", '.')    = ["a:b:c"]
     * StringUtils.split("a b c", ' ')    = ["a", "b", "c"]
     * </pre>
	 *
	 * 方法名称：split
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午2:18:37
	 *  
	 * @param str
	 * @param separatorChar
	 * @return
	 */
	public static String[] split(final String str, final char separatorChar){
		return org.apache.commons.lang3.StringUtils.split(str,separatorChar);
	}
	/**
	 * 
	 * Describle(描述)：【来自Commons.lang3】 按指定{字符串}拆分字符串
	 *
	 * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("abc def", null) = ["abc", "def"]
     * StringUtils.split("abc def", " ")  = ["abc", "def"]
     * StringUtils.split("abc  def", " ") = ["abc", "def"]
     * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
	 *
	 * 方法名称：split
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午2:21:48
	 *  
	 * @param str
	 * @param separatorChars
	 * @return
	 */
	public static String[] split(final String str, final String separatorChars){
		return org.apache.commons.lang3.StringUtils.split(str,separatorChars);
	}
	/**
	 * 
	 * Describle(描述)：【来自Commons.lang3】 按指定{字符串}拆分字符串，指定数组最大长度
	 * <pre>
     * StringUtils.split(null, *, *)            = null
     * StringUtils.split("", *, *)              = []
     * StringUtils.split("ab cd ef", null, 0)   = ["ab", "cd", "ef"]
     * StringUtils.split("ab   cd ef", null, 0) = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     * </pre>
	 * 方法名称：split
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午2:23:39
	 *  
	 * @param str
	 * @param separatorChars
	 * @param max  指定拆分后数组的最大长度，0代表没有限制
	 * @return
	 */
	public static String[] split(final String str, final String separatorChars, final int max){
		return org.apache.commons.lang3.StringUtils.split(str,separatorChars,max);
	}
	/**
	 * 
	 * Describle(描述)：去除字符串头和尾的空白字符
	 *
	 * 方法名称：trim
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午2:37:15
	 *  
	 * @param str
	 * @return
	 */
	public static String trim(final String str){
		return str == null ? null : str.trim();
	}
	/**
	 * 
	 * Describle(描述)：【来自Commons.lang3】 去除字符串头和为的空白字符，如果是空字符串，则返回null
	 *
	 * 方法名称：trimToNull
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午2:47:31
	 *  
	 * @param str
	 * @return
	 */
	public static String trimToNull(final String str){
		return org.apache.commons.lang3.StringUtils.trimToNull(str);
	}
	/**
	 * 
	 * Describle(描述)：【来自Commons.lang3】 去除字符串头和为的空白字符，如果是空字符串，则返回""
	 *
	 * 方法名称：trimToEmpty
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午2:49:23
	 *  
	 * @param str
	 * @return
	 */
	public static String trimToEmpty(final Object obj){
		if(obj == null){
			return "";
		}
		return org.apache.commons.lang3.StringUtils.trimToEmpty(obj.toString());
	}
	/**
	 * 
	 * Describle(描述)：【来自Spring】  去除所有字符串中的空白字符
	 *
	 * <pre>
	 * StringUtils.trimAllWhitespace(null)          = null
     * StringUtils.trimAllWhitespace("")            = ""
     * StringUtils.trimAllWhitespace("     ")       = ""
     * StringUtils.trimAllWhitespace(" ab c ")         = "abc"
     * StringUtils.trimAllWhitespace("    abc    ") = "abc"
	 * </pre>
	 *
	 * 方法名称：trimAllWhitespace
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午2:50:25
	 *  
	 * @param str
	 * @return
	 */
	public static String trimAllWhitespace(final String str){
		return org.springframework.util.StringUtils.trimAllWhitespace(str);
	}
	/**
	 * 
	 * Describle(描述)：【来自Spring】  去除字符串开头指定的字符
	 *
	 * <pre>
	 * StringUtils.trimLeadingCharacter("_a_bc_", '_') = a_bc_
	 * StringUtils.trimLeadingCharacter("a_bc_", '_') = a_bc_
	 * </pre>
	 * 方法名称：trimLeadingCharacter
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午2:55:22
	 *  
	 * @param str
	 * @param leadingCharacter
	 * @return
	 */
	public static String trimLeadingCharacter(String str, char leadingCharacter){
		return org.springframework.util.StringUtils.trimLeadingCharacter(str,leadingCharacter);
	}
	/**
	 * 
	 * Describle(描述)：【来自Spring】  去除字符串末尾指定的字符
	 *
	 * <pre>
	 * StringUtils.trimTrailingCharacter("a_bc_", '_') = "a_bc"
	 * StringUtils.trimTrailingCharacter("_a_bc__", '_') = "_a_bc"
	 * </pre>
	 * 方法名称：trimTrailingCharacter
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午2:57:45
	 *  
	 * @param str
	 * @param trailingCharacter
	 * @return
	 */
	public static String trimTrailingCharacter(String str, char trailingCharacter) {
		return org.springframework.util.StringUtils.trimTrailingCharacter(str,trailingCharacter);
	}
	/**
	 * 
	 * Describle(描述)：【来自Spring】  locale字符串转换成Locale对象
	 *
	 * 方法名称：parseLocaleString
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午3:04:09
	 *  
	 * @param localeString
	 * @return
	 */
	public static Locale parseLocaleString(String localeString){
		return org.springframework.util.StringUtils.parseLocaleString(localeString);
	}
	/**
	 * 
	 * Describle(描述)：【来自Spring】 获取文件名后缀
	 *
	 * 方法名称：getFilenameExtension
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午3:11:41
	 *  
	 * @param path
	 * @return
	 */
	public static String getFilenameExtension(String path) {
		return org.springframework.util.StringUtils.getFilenameExtension(path);
	}
	/**
	 * 
	 * Describle(描述)： 【来自Spring】 获取文件名称
	 *
	 * 方法名称：getFilename
	 *
	 * 所在类名：StringUtils
	 *
	 * Create Time:2015年4月22日 下午3:12:33
	 *  
	 * @param path
	 * @return
	 */
	public static String getFilename(String path) {
		return org.springframework.util.StringUtils.getFilename(path);
	}
	
	 public static String leftPad(String str, int size, char padChar) {
		 return org.apache.commons.lang.StringUtils.leftPad(str,size,padChar);
    }
	 
	 public static String join(String[] srcArray,final String separator,String... extraArray){
		 int srclength = srcArray.length;
		 int extralength = extraArray.length;
		 String[] destArray = new String[srclength+extralength];
		 System.arraycopy(srcArray, 0, destArray, 0, srclength);
		 System.arraycopy(extraArray, 0, destArray, srclength, extralength);
		 return org.apache.commons.lang3.StringUtils.join(destArray,separator);
	 }
	 
	 public static String join(final String separator,String... elements){
		 return org.apache.commons.lang3.StringUtils.join(elements,separator);
	 }
}
