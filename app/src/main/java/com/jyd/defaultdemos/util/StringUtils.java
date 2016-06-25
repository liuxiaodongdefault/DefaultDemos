package com.jyd.defaultdemos.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串处理工具类: <br>
 * 1、boolean 判断字符串长度是否大于0或null isEmpty (string != null && string.length > 0) <br>
 * 2、boolean 判断string == null || 除去空格后 size > 0 <br>
 * 3、boolean邮箱格式匹配 <br>
 * 3、void 除去String中的空格 <br>
 * 4、boolean 判断两个字符串是否相同 <br>
 * 5、string 以utf-8格式编码 <br>
 * 6、string 输出字符串，如果str==null return ""; <br>
 * 7、string 首字母大写:如果params != null && size > 0 && 第一个字符为小写字符 则第一个字符大写
 * 
 * @author fengmiao
 * 
 */
public class StringUtils {
	
	/**
	 * 复制到剪贴板
	 * @param context
	 * @param content
	 */
	public static void copyToClipboard(Context context,String content){
		// 得到剪贴板管理器  
		try{
			ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
			// setText()方法被丢弃
			//cmb.setText(text).setText(content.trim());
			cmb.setPrimaryClip(ClipData.newPlainText(null, content.trim())); 
		}catch(Exception e){
		}
	}

	public static boolean checkEmail(String email) {// 验证邮箱的正则表达式
		String format = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*"
				+ "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (!TextUtils.isEmpty(email) && email.matches(format)) {
			return true;// 邮箱名合法，返回true
		} else {
			return false;// 邮箱名不合法，返回false
		}
	}

	/**
	 * 判断 str == null || 出去空格后size > 0
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (TextUtils.isEmpty(deleteBlank(str)))
			return true;
		return false;

	}

	/**
	 * 删除空格、空白、等
	 * 
	 * @param str
	 * @return
	 */
	public static String deleteBlank(String str) {
		if (str == null)
			str = "";
		return str.replaceAll("\\s*", "");
	}

	/**
	 * null string -->> "" string
	 * 
	 * @param str
	 * @return
	 */
	public static String nullToStr(String str) {
		return str == null ? "" : str;
	}

	/**
	 * String 以 以utf-8格式编码
	 * 
	 * @param str
	 * @return
	 */
	public static String utf8Encode(String str) {
		if (!TextUtils.isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(
						"UnsupportedEncodingException occurred. ", e);
			}
		}
		return str;
	}

	/**
	 * 把string进行utf-8格式转码,转码失败则返回默认string
	 * 
	 * @param str
	 * @param defultReturn
	 * @return
	 */
	public static String utf8Encode(String str, String defultReturn) {
		if (!TextUtils.isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return defultReturn;
			}
		}
		return str;
	}

	/**
	 * 第一个字母大写 capitalize first letter
	 * 
	 * <pre>
	 * capitalizeFirstLetter(null)     =   null;
	 * capitalizeFirstLetter("")       =   "";
	 * capitalizeFirstLetter("2ab")    =   "2ab"
	 * capitalizeFirstLetter("a")      =   "A"
	 * capitalizeFirstLetter("ab")     =   "Ab"
	 * capitalizeFirstLetter("Abc")    =   "Abc"
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalizeFirstLetter(String str) {
		if (TextUtils.isEmpty(str)) {
			return str;
		}

		char c = str.charAt(0);
		return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
				: new StringBuilder(str.length())
						.append(Character.toUpperCase(c))
						.append(str.substring(1)).toString();
	}

	/**
	 * 删除html标签 有待验证
	 */
	public static String delHtml(String str) {
		String info = str.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		info = info.replaceAll("[(/>)<]", "");
		return info;
	}

	
	/**
	 * 验证电话号码
	 * @param mobiles
	 * @return
	 */
	public static boolean checkPhoneNum(String mobiles){
		Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
		
//		if(mobiles == null)
//			return false;
//
//		if(StringUtils.isBlank(mobiles) || mobiles.length() != 11 || !mobiles.startsWith("1"))
//			return false;
//
//		return true;
	}

	/**
	 * 判断字符是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(CharSequence str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 判断手机号格式
	 * @param mobiles
	 * @return
	 */
	public static boolean isPhoneNO(String mobiles) {
		Pattern p = Pattern
				//.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
				.compile("^((13[0-9])|(15[^4,\\D])|(17[0,6-8])|(18[0-9])|(14[5,7]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	/**
	 * 手机号中间4位转密文
	 * @param mobile 手机号
	 * @return
	 */
	public static String mobileMiddleEncrypt(String mobile){
		if(StringUtils.isEmpty(mobile)||mobile.length()!=11){
			return null;
		}
		return mobile.substring(0,mobile.length()-(mobile.substring(3)).length())+"****"+mobile.substring(7);
	}
}
