package org.test.tres;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Hello world!
 *
 */
public class App
{
	public static final MediaType XML
    = MediaType.get("text/xml; charset=utf-8");
	static OkHttpClient client = new OkHttpClient().newBuilder().cache(null).build();


	  public static int StrToInt(String str) {
	   if(str==null || str.trim().isEmpty()) {
		   return 0;
	   }

	   char charAt = str.charAt(0);
	   int flag=charAt=='+'?1:charAt=='-'?2:0;
	   char[] charArray = str.toCharArray();
	   int result=0;
	   int i=0;
	   if(flag==1||flag==2) {
		   i=1;
	   }
	   for (; i < charArray.length; i++) {
		   if(Character.isDigit(charArray[i])) {
			   result=10*result+(charArray[i]-'0');
		   }else {
			   return 0;
		   }
	}

	   return (flag==0||flag==1)?result:-result;
	  }

	  public static void main(String[] args) {
	    // TODO Auto-generated method stub
	    String s = "-12312312";
	    System.out.println("使用库函数转换：" + Integer.valueOf(s));
	    int res = App.StrToInt(s);
	    System.out.println("使用自己写的方法转换：" + res);

	  }


}
