package com.revature.bank_assignment.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.bank_assignment.models.Login;
import com.revature.bank_assignment.util.ConnectionUtil;

public class LoginOracle implements LoginDao {
	private static LoginOracle login_Oracle;

	private static final Logger log = LogManager.getLogger(LoginOracle.class);

	private LoginOracle() {

	}

	public static LoginOracle getDao() {
		if (login_Oracle == null)
			login_Oracle = new LoginOracle();
		return login_Oracle;
	}

	@Override
	public Optional<Login> getLogin(String loginID) {
		log.traceEntry("getLogin: " + "LoginID = {}", loginID);

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "select * from login where user_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, loginID);
			ResultSet rs = ps.executeQuery();

			Login user = null;

			while (rs.next()) {
				user = new Login(rs.getInt("user_Number"), rs.getString("user_ID"), rs.getString("user_Password"));
			}

			if (user == null) {
				log.traceExit(Optional.empty());
				return Optional.empty();
			}

			return log.traceExit(Optional.of(user));

		} catch (SQLException e) {
			log.catching(e);
			log.error("SQL exception occurred", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}

	@Override
	public Optional<Login> getLoginByNum(int userNumber) {
		log.traceEntry("getLoginByNum: " + "UserNumber = {}", userNumber);

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "select * from login where user_number = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userNumber);
			ResultSet rs = ps.executeQuery();

			Login user = null;

			while (rs.next()) {
				user = new Login(rs.getInt("user_Number"), rs.getString("user_ID"), rs.getString("user_Password"));
			}

			if (user == null) {
				log.traceExit(Optional.empty());
				return Optional.empty();
			}

			return log.traceExit(Optional.of(user));

		} catch (SQLException e) {
			log.catching(e);
			log.error("SQL exception occurred", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}

	@Override
	public Optional<Integer> addNewLogin(String login, String password) {
		log.traceEntry("addNewLogin: " + "Login ID = {0} Login Password = {1}", login, password);

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "call Add_Login(?, ?)";
			CallableStatement cb = con.prepareCall(sql);
			cb.setString(1, login);
			cb.setString(2, password);
			cb.execute();
			// cb.registerOutParameter(3, java.sql.Types.INTEGER);
			// int result = cb.getInt(3);
			// TODO: implement return flag for logging
			return Optional.empty();

		} catch (SQLException e) {
			log.catching(e);
			log.error("SQL exception occurred", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}

	@Override
	public Optional<Integer> updatePassword(int userNumber, String newPassword) {
		log.traceEntry("updatePassword: " + "User number = {0}, New Login Password = {1}", userNumber, newPassword);

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "update login set user_password = ? where ? = user_number";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, newPassword);
			ps.setInt(2, userNumber);
			ps.executeQuery();

			// TODO: implement procedure and add int flag return for logging
			// cb.registerOutParameter(3, java.sql.Types.INTEGER);
			// int result = cb.getInt(3);
			return Optional.empty();

		} catch (SQLException e) {
			log.catching(e);
			log.error("SQL exception occurred", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}

	@Override
	// Should be restricted to superuser
	public Optional<List<Login>> getAllLogins() {
		log.traceEntry("getAllLogins: ");

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "select * from Login";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			List<Login> loginList = new ArrayList<Login>();

			while (rs.next()) {
				loginList.add(
						new Login(rs.getInt("user_Number"), rs.getString("user_ID"), rs.getString("user_Password")));
			}

			return log.traceExit(Optional.of(loginList));

		} catch (SQLException e) {
			log.catching(e);
			log.error("SQL exception occurred", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}

	@Override
	// Should be restricted to superuser
	public Optional<Integer> purgeMember(int userNumber) {
		log.traceEntry("purgeMember: " + "User Number = {}", userNumber);

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "call Delete_login(?)";
			CallableStatement cb = con.prepareCall(sql);
			cb.setInt(1, userNumber);
			cb.execute();
			// cb.registerOutParameter(2, java.sql.Types.INTEGER);
			// int result = cb.getInt(2);
			// TODO: implement return flag for logging
			return Optional.empty();

		} catch (SQLException e) {
			log.catching(e);
			log.error("SQL exception occurred", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}
}
