package com.jyd.defaultdemos.util;

import android.util.Log;

/**
 * @description 自定义Log
 * @author   zh
 * @version  1.0
 */
public class MyLog {
	public static void i(String tag, String msg) {
		if (Constants.ISLOG)
			Log.i(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (Constants.ISLOG)
			Log.v(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (Constants.ISLOG)
			Log.d(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (Constants.ISLOG)
			Log.w(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (Constants.ISLOG)
			Log.e(tag, msg);
	}
}
