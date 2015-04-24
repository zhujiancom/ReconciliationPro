package com.rci.enums;

public final class CommonEnums {
	private CommonEnums(){}
	
	public enum YOrN{
		/*��*/
		Y,
		/*��*/
		N;
		public static YOrN getYN(boolean flag){
			return flag ? Y : N;
		}
		public static YOrN getYN(String flag){
			return Boolean.TRUE.toString().equals(flag) ? Y : N;
		}
		public static boolean isY(YOrN yn){
			return Y.equals(yn);
		}
	}
	
	public static enum CRUDType {
		CREATE,READ,UPDATE,DELTE
	}
}
