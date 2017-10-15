/*

 */
package org.e2d.smartfarm.util;

/**
 * @author pasqualina.sacco
 * 
 */
public class LogUtil {

	public static String logFilePath = System.getProperty("user.home") + "/log_to_download";

	public static String getLogFilePath() {
		return logFilePath;
	}
}