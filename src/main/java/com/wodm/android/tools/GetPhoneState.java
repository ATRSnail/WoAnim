package com.wodm.android.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.wodm.android.utils.PermissionInfoTools;
import com.wodm.android.utils.Preferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

/**
 * �?��手机状�?
 * 
 * @author Administrator
 * 
 */
public class GetPhoneState {
	private static ConnectivityManager connManager = null;
	private static TelephonyManager telephonyManager = null;
	public static DisplayMetrics dm;


	/**
	 * �?��SDCard是否可用
	 * 
	 * @return
	 */
	public static boolean isSDCardAvailable() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �?��网络是否可用
	 * 
	 * @return
	 */
	public static boolean isNetworkAvailable(Context appContext) {
		Context context = appContext.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 获取当前操作系统的语�?
	 * 
	 * @return String 系统语言
	 */
	public static String getSysLanguage() {
		return Locale.getDefault().getLanguage();
	}

	/**
	 * 获取手机品牌
	 * 
	 * @return String 手机型号
	 */
	public static String getBrand() {
		return android.os.Build.BRAND;
	}
	/**
	 * 获取手机型号
	 * 
	 * @return String 手机型号
	 */
	public static String getModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取操作系统的版本号
	 * 
	 * @return String 系统版本�?
	 */
	public static String getSysRelease() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 读取手机串号imei
	 * 
	 * @param con
	 *            上下�?
	 * @return String 手机串号
	 */
	public static String readTelephoneSerialNum(final Context con) {
		final String[] imei = {"00000000"};
		PermissionInfoTools.getReadPhoneStatePermission((Activity) con, new PermissionInfoTools.SetPermissionCallBack() {
			@Override
			public void IPsermission(boolean isPermsion) {
				if (isPermsion){
					TelephonyManager telephonyManager = (TelephonyManager) con
							.getSystemService(Context.TELEPHONY_SERVICE);
					imei[0] =telephonyManager.getDeviceId();
					if(TextUtils.isEmpty(imei[0])){
						imei[0] =getLocalMacAddress(con);
					}
				}
			}
		});
		return imei[0];
	}
	
	
	public static String getLocalMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		String mac = info.getMacAddress();
		if (TextUtils.isEmpty(mac)) {
			mac = "00:00:00:00:00:00";
		}
		return mac;
	}

	/**
	 * 读取sim卡序列号imsi
	 */
	@SuppressLint("NewApi")
	public static String hasIccCard(Context con) {
		if (con == null) {
			return "";
		}
		PermissionInfoTools.getReadPhoneStatePermission((Activity) con, new PermissionInfoTools.SetPermissionCallBack() {
			@Override
			public void IPsermission(boolean isPermsion) {

			}
		});
		if (telephonyManager == null) {
			telephonyManager = (TelephonyManager) con
					.getSystemService(Context.TELEPHONY_SERVICE);
		}
		if (telephonyManager.hasIccCard())
			return "true";
		return "false";
	}
	/**
	 * 读取sim卡序列号imsi
	 */
	public static String readSimSerialNum(final Context con) {
		final String[] subscriberIe = {""};
		if (con == null) {
			return "";
		}
		PermissionInfoTools.getReadPhoneStatePermission((Activity) con, new PermissionInfoTools.SetPermissionCallBack() {
			@Override
			public void IPsermission(boolean isPermsion) {
				if (isPermsion){
					if (telephonyManager == null) {
						telephonyManager = (TelephonyManager) con
								.getSystemService(Context.TELEPHONY_SERVICE);
					}
					if(telephonyManager.getSubscriberId()==null)
						return;
					subscriberIe[0] =telephonyManager.getSubscriberId();
				}
			}
		});

		return subscriberIe[0];
	}
	/**
	 * 读取sim卡序列号
	 */
	public static String readSimICCID(final Context con) {
		final String[] Iccid = {""};
		if (con == null) {
			return "";
		}
		PermissionInfoTools.getReadPhoneStatePermission((Activity) con, new PermissionInfoTools.SetPermissionCallBack() {
			@Override
			public void IPsermission(boolean isPermsion) {
				if (isPermsion){
					if (telephonyManager == null) {
						telephonyManager = (TelephonyManager) con
								.getSystemService(Context.TELEPHONY_SERVICE);
					}
					if(telephonyManager.getSimSerialNumber()==null)
						return;
					Iccid[0] =telephonyManager.getSimSerialNumber();
				}
			}
		});

		return Iccid[0];
	}
	
	
	/**
	 * Role:获取当前设置的电话号码
	 * <BR>Date:2012-3-12
	 * <BR>@author CODYY)peijiangping
	 */
	
	public static String getNativePhoneNumber() {
		String NativePhoneNumber=null;
		NativePhoneNumber=telephonyManager.getLine1Number();
		if(TextUtils.isEmpty(NativePhoneNumber))
			return "0";
		return NativePhoneNumber;
	}

	public static String GetNetIp(final Context context) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				URL infoUrl = null;
				InputStream inStream = null;
				try {
					// http://iframe.ip138.com/ic.asp
					// infoUrl = new URL("http://city.ip138.com/city0.asp");
					infoUrl = new URL("http://1212.ip138.com/ic.asp");
					URLConnection connection = infoUrl.openConnection();
					HttpURLConnection httpConnection = (HttpURLConnection) connection;
					int responseCode = httpConnection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						inStream = httpConnection.getInputStream();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(inStream, "gb2312"));
						StringBuilder strber = new StringBuilder();
						String line = null;
						while ((line = reader.readLine()) != null)
							strber.append(line + "\n");
						inStream.close();

//						// 从反馈的结果中提取出IP地址
						int start = strber.indexOf("<center>");
						int end = strber.indexOf("</center>", start + 1);
						String line_address = strber.substring(start + 7, end).replace(" ","&");
						int start1 = line_address.indexOf("来自：");
						int end1 = line_address.lastIndexOf("&");
						String getAddress=line_address.substring(start1+3,end1);
						Preferences.getInstance(context).setPreference("getAddress", getAddress);
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		return "0.0.0.0";

	}

	  
	    
	/**
	 * 获取运营商信�?
	 * 
	 * @param con
	 *            上下�?
	 * @return String 运营商信�?
	 */
	public static String getCarrier(final Context con) {
		final String[] carrier = {""};
		PermissionInfoTools.getReadPhoneStatePermission((Activity) con, new PermissionInfoTools.SetPermissionCallBack() {
			@Override
			public void IPsermission(boolean isPermsion) {
				if (isPermsion){
					if (telephonyManager == null) {
						telephonyManager = (TelephonyManager) con
								.getSystemService(Context.TELEPHONY_SERVICE);
						String imsi = telephonyManager.getSubscriberId();
						if (imsi != null && !"".equals(imsi)) {
							if (imsi.startsWith("46000") || imsi.startsWith("46002")|| imsi.startsWith("46007")) {// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了�?��46002编号�?34/159号段使用了此编号
								carrier[0]="yidong" ;
							} else if (imsi.startsWith("46001")|| imsi.startsWith("46006")) {
								carrier[0]="liantong" ;
							} else if (imsi.startsWith("46003")|| imsi.startsWith("46005")|| imsi.startsWith("46011")) {
								carrier[0]="dianxin" ;
							}
						}
					}
				}
			}
		});

		return carrier[0];
	}

	/**
	 * 获取网络类型
	 * 
	 * @param context
	 *            上下�?
	 * @return String 返回网络类型
	 */
	public static String getAccessNetworkType(Context context) {
		int type = 0;
		try {
			if (connManager != null) {
				type = connManager.getActiveNetworkInfo().getType();
			} else {
				connManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				type = connManager.getActiveNetworkInfo().getType();
			}
			if (type == ConnectivityManager.TYPE_WIFI) {
				return "wifi";
			} else if (type == ConnectivityManager.TYPE_MOBILE) {
				return "3G/GPRS";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 
	 * 返回当前程序版本�?
	 */  
	public static String getAppVersionName(Context context) {  
	    String versionName = "";  
	    try {  
	        // ---get the package info---  
	        PackageManager pm = context.getPackageManager();  
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
	        versionName = pi.versionName;  
	        if (versionName == null || versionName.length() <= 0) {  
	            return "1";  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace(); 
	    }  
	    return versionName;  
	}
}
