package com.ultrapower.eoms.common.support;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.TableGenerator;
import org.hibernate.type.Type;

import com.ultrapower.eoms.common.cfg.ConfigKeys;

public class EomsIDGenerator extends TableGenerator {

	private static final Log log = LogFactory.getLog(EomsIDGenerator.class);
	private String tableName;
	String SQL_GETNO = "SELECT NEXT_ID,STEP FROM APP_ID_SERIAL WHERE TABLE_NAME = ?";
	String SQL_INSERT = "INSERT INTO APP_ID_SERIAL (TABLE_NAME, NEXT_ID) VALUES(?,?)";
	String SQL_UPDATE = "UPDATE APP_ID_SERIAL SET NEXT_ID=? WHERE TABLE_NAME=? AND NEXT_ID < ?";

	public void configure(Type type, Properties params, Dialect d) {
		super.configure(type, params, d);
		tableName = (params.getProperty("tableName") == null ? "EOMS_TEMP"
				: params.getProperty("tableName")).toUpperCase();
	}

	public synchronized Serializable generate(SessionImplementor session,
			Object obj) throws HibernateException, HibernateException {

		long nextId = ConfigKeys.DEFAULT_NEXT_ID;
		long step;
		Connection conn = session.getBatcher().openConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(SQL_GETNO);
			pstmt.setString(1, tableName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				step = rs.getLong(2);
				step = step > 0 ? step : ConfigKeys.DEFAULT_STEP;
				nextId = rs.getLong(1);
				nextId = (nextId > 0 ? nextId : ConfigKeys.DEFAULT_NEXT_ID)
						+ step;
				rs.close();
				pstmt = conn.prepareStatement(SQL_UPDATE);
				pstmt.setLong(1, nextId);
				pstmt.setString(2, tableName);
				pstmt.setLong(3, nextId);
				int flag = pstmt.executeUpdate();
				int times = 1;
				// 最多重试5次，防止多服务器并发情况
				while (flag < 1 && times < 5) {
					nextId += step;
					pstmt.setLong(1, nextId);
					pstmt.setLong(3, nextId);
					times++;
					flag = pstmt.executeUpdate();
				}
				if (flag < 1) {
					log.error("获取" + tableName + "表主键的重试次数达到最大值");
				}
			} else {
				// 数据库不存在最大ID记录 就添加一个初始值,服务器端设置为1开始
				nextId = ConfigKeys.DEFAULT_NEXT_ID;
				pstmt = conn.prepareStatement(SQL_INSERT);
				pstmt.setString(1, tableName);
				pstmt.setLong(2, nextId);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			session.getBatcher().abortBatch(e);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					session.getBatcher().closeConnection(conn);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return nextId;
	}
}
