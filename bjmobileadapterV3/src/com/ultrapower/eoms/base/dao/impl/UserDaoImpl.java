package com.ultrapower.eoms.base.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ultrapower.eoms.base.model.User;
import com.ultrapower.eoms.common.cfg.Config;
import com.ultrapower.eoms.common.cfg.ConfigKeys;
import com.ultrapower.eoms.common.dao.impl.DaoImpl;
import com.ultrapower.eoms.base.dao.IUserDao;

public class UserDaoImpl extends DaoImpl implements IUserDao {

	private static UserDaoImpl instance = new UserDaoImpl();

	public static UserDaoImpl getInstance() {
		return instance;
	}

	private UserDaoImpl() {

	}

	public User getUser(String username) {
		User user = null;
		Connection conn = this.getConn();
		Statement stmt = null;
		String usertable = Config.getValue(ConfigKeys.EOMS_SCHEMA) + "."
				+ Config.getValue("UltraProcess:SysUser");
		try {
			stmt = conn.createStatement();
			String sql = "select c1,c630000001,c630000002,c630000003,c630000004,c630000005,c630000006,c630000007,c630000008,c630000009,c630000010,c630000011,c630000012,c630000013,c630000014,c630000015,c630000016,c630000017,c630000029,c630000036 from "
					+ usertable + " where c630000001 = '" + username + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				user = new User();
				user.setUserId(rs.getString("c1"));
				user.setUsername(rs.getString("c630000001"));
				user.setPassword(rs.getString("c630000002"));
				user.setFullname(rs.getString("c630000003"));
				user.setMobile(rs.getString("c630000008"));
				user.setPhone(rs.getString("c630000009"));
				user.setEmail(rs.getString("c630000011"));
				user.setCpId(rs.getString("c630000013"));
				user.setDpId(rs.getString("c630000015"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(conn);
		}
		return user;
	}

}
