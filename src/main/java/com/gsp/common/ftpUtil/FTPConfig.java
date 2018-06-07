package com.gsp.common.ftpUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class FTPConfig {

	private static String mServerAddress;
	private static int mServerPort;
	private static String mUserName;
	private static String mPassword;
	private static String mRootPath;
	private static String mSystem;
	
	private static final String KEY_SERVER 		= "server";
	private static final String KEY_PORT 		= "port";
	private static final String KEY_USERNAME 	= "username";
	private static final String KEY_PASSWORD 	= "password";
	private static final String KEY_ROOTPATH 	= "rootpath";
	private static final String KEY_SYSTEM 		= "system";
	static Logger log = Logger.getLogger(FTPConfig.class);
	static {
		 parseFtpConfig("/cfg/ftp.ini");
	}

	public static String getServerAddress() {
		return mServerAddress;
	}

	public static int getServerPort() {
		return mServerPort;
	}

	public static String getUserName() {
		return mUserName;
	}

	public static String getPassword() {
		return mPassword;
	}

	public static String getRootPath() {
		return mRootPath;
	}

	public static String getSystem() {
		return mSystem;
	}

	private static void parseFtpConfig(String fileName) {
		InputStream in = FTPConfig.class.getResourceAsStream(fileName);
		BufferedReader reader = null;
		String tmp = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					in, "UTF-8"));
			while ((tmp = reader.readLine()) != null) {

				if ("".equals(tmp) || tmp.charAt(0) == '#') {
					continue;
				}

				String[] array = tmp.split("=");
				if (array.length != 2) {
					continue;
				}

				String key = array[0];
				String value = array[1];

				if (key.equals(KEY_SERVER)) {
					mServerAddress = value;
				} else if (key.equals(KEY_PORT)) {
					mServerPort = Integer.parseInt(value);
				} else if (key.equals(KEY_USERNAME)) {
					mUserName = value;
				} else if (key.equals(KEY_PASSWORD)) {
					mPassword = value;
				} else if (key.equals(KEY_ROOTPATH)) {
					mRootPath = value;
				} else if (key.equals(KEY_SYSTEM)) {
					mSystem = value;
				}
			}
		} catch (Throwable e) {
			log.error("Exception", e);
		} finally {
			IOUtils.silenceClose(reader);
		}
	}
}
