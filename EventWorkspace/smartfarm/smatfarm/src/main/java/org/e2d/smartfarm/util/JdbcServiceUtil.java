/*
 
 */
package org.e2d.smartfarm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class JdbcServiceUtil {

	protected static final String SQL_UPDATE = "UPDATE {0} SET {1} WHERE {2}";
	protected static final String SQL_DELETE = "DELETE FROM {0} WHERE ({1})";
	protected static final String SQL_SELECT = "SELECT {1} FROM {0} WHERE {2}";

	private final static String SQL_SCHEMAS = "select farm_db_schema_name from public.fc_farms where farm_do_inference = 1";
	private final static String SQL_GET_SCHEMA_BY_FARM = "select farm_db_schema_name from public.fc_farms where farm_identnr = {0}";

	private static Logger logger = Logger.getLogger(JdbcServiceUtil.class);
	public static DataSource ds = null;

	public static Connection getDSConnection() throws SQLException {
		if (ds == null) {
			logger.debug("CREAZIONE DATASOURCE");
			Context initContext;
			try {
				initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				ds = (DataSource) envContext.lookup("jdbc/smartfarm");
				logger.debug("DATASOURCE CREATO");
			} catch (NamingException e) {
				logger.fatal("CONNESSIONE AL DATABASE NON DISPONIBILE " + e);
			}

		}
		return ds.getConnection();
	}

	public static void close(Connection conn, ResultSet resultSet, Statement ps) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			logger.error("Exception in class " + JdbcServiceUtil.class.getName() + " (while closing db connection ): "
					+ ex.getMessage(), ex);
		}
	}

	public static List<String> getSchemaNames() {
		ArrayList<String> schemas = new ArrayList<String>();
		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		try {
			conn = getDSConnection();
			ps = conn.prepareStatement(SQL_SCHEMAS);
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				schemas.add(resultSet.getString(1));
			}
			return schemas;
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Exception in class " + JdbcServiceUtil.class.getName() + " (method getSchemaNames): "
					+ e.getMessage());
			return schemas;
		} finally {
			JdbcServiceUtil.close(conn, resultSet, ps);
		}
	}

	public static String getSchemaByFarmId(String farmId) throws Exception {
		StringBuilder sqlQuery = new StringBuilder();
		String farmSchema = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcServiceUtil.getDSConnection();
			sqlQuery.append(MessageFormat.format(SQL_GET_SCHEMA_BY_FARM, farmId));
			ps = conn.prepareStatement(sqlQuery.toString());
			rs = ps.executeQuery();
			rs.next();
			farmSchema = rs.getString(1);
			return farmSchema;
		} catch (SQLException e) {
			logger.fatal(e);
			e.printStackTrace();
			return farmSchema;
		} finally {
			JdbcServiceUtil.close(conn, rs, ps);
		}
	}
}