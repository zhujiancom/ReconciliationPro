package com.rci.enums;

public final class CommonEnums {
	private CommonEnums(){}
	
	public enum YOrN{
		/*是*/
		Y,
		/*否*/
		N;
		public static YOrN getYN(boolean flag){
			return flag ? Y : N;
		}
		public static YOrN getYN(String flag){
			return Boolean.TRUE.toString().equals(flag) ? Y : N;
		}
		public static boolean isY(YOrN yn){
			if(yn == null){
				return false;
			}
			return Y.equals(yn);
		}
	}
	
	public static enum CRUDType {
		CREATE,READ,UPDATE,DELTE
	}
}
