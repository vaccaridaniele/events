package org.e2d.smartfarm.rest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;
import org.e2d.smartfarm.util.LogUtil;

/**
 * Servlet Filter implementation class RESTLoggingFilter
 */
public class RESTLoggingFilter implements Filter {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private FileOutputStream fos = null;

	Logger logger4j = Logger.getLogger("RESTLoggingFilter");

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {

		try {
			String logFilePath = LogUtil.getLogFilePath();
			logger4j.info("ho caricato il file di properties");

			fos = new FileOutputStream(logFilePath);
		} catch (Exception ex) {
			// Log.logInfo(7, "could not load property file");
			logger4j.info("Non sono riuscito a caricare il file di properties");
		}
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		try {
			if (fos != null)
				fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		try {
			if (fos != null)
				fos.write(String.format("\n\nRemoteAddr: %s\tDate: %s\tRequest: %s\n", req.getRemoteAddr(),
						FORMAT.format(new Date()), req.getRequestURI()).getBytes());
		} catch (Exception e) {
		}

		try {
			chain.doFilter(new LogServletRequest(req, fos), response);
		} catch (IOException e) {
			if (fos != null)
				fos.write(String.format("\nERROR: %s", e.getMessage()).getBytes());
			throw e;
		} catch (Throwable e) {
			if (fos != null) {
				fos.write(String.format("\nERROR: %s", e.getMessage()).getBytes());
			}

		}
		try {
			if (fos != null) {
				// qui
				// fos.write(String.format("HTTP: %s",
				// resp.getStatus()).getBytes());
				/*
				 * Enumeration<String> names = req.getHeaderNames(); while
				 * (names.hasMoreElements()) { String name =
				 * names.nextElement(); fos.write(String.format("\n%s: %s",
				 * name, req.getHeader(name)).getBytes()); }
				 */
				fos.flush();
			}
		} catch (Exception e) {
		}
	}

	private class LogServletRequest extends HttpServletRequestWrapper {

		private final OutputStream outputStream;

		public LogServletRequest(HttpServletRequest request, OutputStream outputStream) {
			super(request);
			this.outputStream = outputStream;
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			final ServletInputStream stream = super.getInputStream();
			return new ServletInputStream() {

				@Override
				public int read() throws IOException {
					int i = stream.read();
					if (outputStream != null)
						outputStream.write(i);
					return i;
				}

			};
		}

	}
}
