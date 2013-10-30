package com.ultrapower.eoms.common.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ultrapower.eoms.common.cfg.Config;
import com.ultrapower.eoms.common.cfg.ConfigKeys;

public class DaoImpl {

	public Connection getConn() {
		try {
			return DriverManager.getConnection("proxool."
					+ Config.getValue(ConfigKeys.DB_ALIAS));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean close(Connection conn) {
		if (conn == null) {
			return false;
		}
		try {
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean close(Statement stmt) {
		if (stmt == null) {
			return false;
		}
		try {
			stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean close(ResultSet rs) {
		if (rs == null) {
			return false;
		}
		try {
			rs.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
