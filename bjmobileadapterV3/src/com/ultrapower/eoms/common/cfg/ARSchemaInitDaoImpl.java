package com.ultrapower.eoms.common.cfg;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.ultrapower.eoms.common.dao.impl.DaoImpl;

public class ARSchemaInitDaoImpl extends DaoImpl {
	static ARSchemaInitDaoImpl instance = new ARSchemaInitDaoImpl();

	public ARSchemaInitDaoImpl() {
	}

	public static ARSchemaInitDaoImpl getInstance() {
		return instance;
	}

	public Map<String, String> loadARSchema() {
		Map<String, String> cache = new HashMap<String, String>();
		Connection conn = this.getConn();
		Statement stmt = null;
		try {
			if (conn == null) {
				return cache;
			}
			stmt = conn.createStatement();
			String sql = "select name,schemaid from "
					+ Config.getValue(ConfigKeys.EOMS_SCHEMA) + ".arschema";
			ResultSet rs = stmt.executeQuery(sql);
			String name;
			String schemaid;
			int i = 1;
			while (rs.next()) {
				name = rs.getString("name");
				schemaid = rs.getString("schemaid");
				if (name != null && schemaid != null) {
					cache.put(name.trim(), "T" + schemaid);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(conn);
		}
		return cache;
	}
}
