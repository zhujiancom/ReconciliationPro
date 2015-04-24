package com.rci.tools;

import java.util.Locale;

public class StringUtils {
	/**
	 * 
	 * Describle(����)��������Spring��
	 * 
	 * �������ƣ�hasText
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����1:41:45
	 *  
	 * @param str
	 * @return
	 */
	public static boolean hasText(String str){
		return hasText((CharSequence)str);
	}
	/**
	 * 
	 * Describle(����)��������Spring��  ��������CharSequence �Ƿ����ʵ�ʵ��ַ��ı�
	 * <pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * <pre>
	 * �������ƣ�hasText
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����1:42:09
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
	 * Describle(����)��������Spring��
	 *
	 * �������ƣ�hasLength
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����1:46:41
	 *  
	 * @param str
	 * @return
	 */
	public static boolean hasLength(final String str) {
		return hasLength((CharSequence) str);
	}
	/**
	 * 
	 * Describle(����)��������Spring�� ��������CharSequence �Ȳ���Null,����Ҳ����0 
	 *
	 * �������ƣ�hasLength
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����1:44:17
	 *  
	 * @param str
	 * @return
	 */
	public static boolean hasLength(final CharSequence str) {
		return (str != null && str.length() > 0);
	}
	/**
	 * 
	 * Describle(����)��������Commons.lang3�� ����ַ���
	 * 
	 * <pre>
     * StringUtils.split(null)       = null
     * StringUtils.split("")         = []
     * StringUtils.split("abc def")  = ["abc", "def"]
     * StringUtils.split("abc  def") = ["abc", "def"]
     * StringUtils.split(" abc ")    = ["abc"]
     * </pre>
	 *
	 * �������ƣ�split
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����2:16:22
	 *  
	 * @param str
	 * @return
	 */
	public static String[] split(final String str){
		return org.apache.commons.lang3.StringUtils.split(str);
	}
	/**
	 * 
	 * Describle(����)��������Commons.lang3�� ��ָ��{�ַ�}����ַ���
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
	 * �������ƣ�split
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����2:18:37
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
	 * Describle(����)��������Commons.lang3�� ��ָ��{�ַ���}����ַ���
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
	 * �������ƣ�split
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����2:21:48
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
	 * Describle(����)��������Commons.lang3�� ��ָ��{�ַ���}����ַ�����ָ��������󳤶�
	 * <pre>
     * StringUtils.split(null, *, *)            = null
     * StringUtils.split("", *, *)              = []
     * StringUtils.split("ab cd ef", null, 0)   = ["ab", "cd", "ef"]
     * StringUtils.split("ab   cd ef", null, 0) = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     * StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     * </pre>
	 * �������ƣ�split
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����2:23:39
	 *  
	 * @param str
	 * @param separatorChars
	 * @param max  ָ����ֺ��������󳤶ȣ�0����û������
	 * @return
	 */
	public static String[] split(final String str, final String separatorChars, final int max){
		return org.apache.commons.lang3.StringUtils.split(str,separatorChars,max);
	}
	/**
	 * 
	 * Describle(����)��ȥ���ַ���ͷ��β�Ŀհ��ַ�
	 *
	 * �������ƣ�trim
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����2:37:15
	 *  
	 * @param str
	 * @return
	 */
	public static String trim(final String str){
		return str == null ? null : str.trim();
	}
	/**
	 * 
	 * Describle(����)��������Commons.lang3�� ȥ���ַ���ͷ��Ϊ�Ŀհ��ַ�������ǿ��ַ������򷵻�null
	 *
	 * �������ƣ�trimToNull
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����2:47:31
	 *  
	 * @param str
	 * @return
	 */
	public static String trimToNull(final String str){
		return org.apache.commons.lang3.StringUtils.trimToNull(str);
	}
	/**
	 * 
	 * Describle(����)��������Commons.lang3�� ȥ���ַ���ͷ��Ϊ�Ŀհ��ַ�������ǿ��ַ������򷵻�""
	 *
	 * �������ƣ�trimToEmpty
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����2:49:23
	 *  
	 * @param str
	 * @return
	 */
	public static String trimToEmpty(final String str){
		return org.apache.commons.lang3.StringUtils.trimToEmpty(str);
	}
	/**
	 * 
	 * Describle(����)��������Spring��  ȥ�������ַ����еĿհ��ַ�
	 *
	 * <pre>
	 * StringUtils.trimAllWhitespace(null)          = null
     * StringUtils.trimAllWhitespace("")            = ""
     * StringUtils.trimAllWhitespace("     ")       = ""
     * StringUtils.trimAllWhitespace(" ab c ")         = "abc"
     * StringUtils.trimAllWhitespace("    abc    ") = "abc"
	 * </pre>
	 *
	 * �������ƣ�trimAllWhitespace
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����2:50:25
	 *  
	 * @param str
	 * @return
	 */
	public static String trimAllWhitespace(final String str){
		return org.springframework.util.StringUtils.trimAllWhitespace(str);
	}
	/**
	 * 
	 * Describle(����)��������Spring��  ȥ���ַ�����ͷָ�����ַ�
	 *
	 * <pre>
	 * StringUtils.trimLeadingCharacter("_a_bc_", '_') = a_bc_
	 * StringUtils.trimLeadingCharacter("a_bc_", '_') = a_bc_
	 * </pre>
	 * �������ƣ�trimLeadingCharacter
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����2:55:22
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
	 * Describle(����)��������Spring��  ȥ���ַ���ĩβָ�����ַ�
	 *
	 * <pre>
	 * StringUtils.trimTrailingCharacter("a_bc_", '_') = "a_bc"
	 * StringUtils.trimTrailingCharacter("_a_bc__", '_') = "_a_bc"
	 * </pre>
	 * �������ƣ�trimTrailingCharacter
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����2:57:45
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
	 * Describle(����)��������Spring��  locale�ַ���ת����Locale����
	 *
	 * �������ƣ�parseLocaleString
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����3:04:09
	 *  
	 * @param localeString
	 * @return
	 */
	public static Locale parseLocaleString(String localeString){
		return org.springframework.util.StringUtils.parseLocaleString(localeString);
	}
	/**
	 * 
	 * Describle(����)��������Spring�� ��ȡ�ļ�����׺
	 *
	 * �������ƣ�getFilenameExtension
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����3:11:41
	 *  
	 * @param path
	 * @return
	 */
	public static String getFilenameExtension(String path) {
		return org.springframework.util.StringUtils.getFilenameExtension(path);
	}
	/**
	 * 
	 * Describle(����)�� ������Spring�� ��ȡ�ļ�����
	 *
	 * �������ƣ�getFilename
	 *
	 * ����������StringUtils
	 *
	 * Create Time:2015��4��22�� ����3:12:33
	 *  
	 * @param path
	 * @return
	 */
	public static String getFilename(String path) {
		return org.springframework.util.StringUtils.getFilename(path);
	}
}
