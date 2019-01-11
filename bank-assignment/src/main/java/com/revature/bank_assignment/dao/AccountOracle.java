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

import com.revature.bank_assignment.models.Account;
import com.revature.bank_assignment.util.ConnectionUtil;

public class AccountOracle implements AccountDao {

	private static AccountOracle account_Oracle;

	private static final Logger log = LogManager.getLogger(LoginOracle.class);

	private AccountOracle() {

	}

	public static AccountOracle getDao() {
		if (account_Oracle == null)
			account_Oracle = new AccountOracle();
		return account_Oracle;
	}

	public Optional<List<Account>> getAccounts(int userNumber) {
		log.traceEntry("getAccount: " + "User Number = {}", userNumber);

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "select * from bank_account where user_number = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userNumber);
			ResultSet rs = ps.executeQuery();

			List<Account> accountList = new ArrayList<Account>();

			while (rs.next()) {
				accountList.add(
						new Account(rs.getInt("bank_account_id"), rs.getInt("user_number"), rs.getDouble("balance")));
			}
			if (!accountList.isEmpty())
				return log.traceExit(Optional.of(accountList));
			return log.traceExit(Optional.empty());

		} catch (SQLException e) {
			log.catching(e);
			log.error("SQL exception occurred", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}

	// Superuser only
	public Optional<List<Account>> getAllAccounts() {
		log.traceEntry("getAllAccounts: ");

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "select * from bank_account";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			List<Account> accountList = new ArrayList<Account>();

			while (rs.next()) {
				accountList.add(
						new Account(rs.getInt("bank_account_id"), rs.getInt("user_number"), rs.getDouble("balance")));
			}

			if (!accountList.isEmpty())
				return log.traceExit(Optional.of(accountList));
			return log.traceExit(Optional.empty());

		} catch (SQLException e) {
			log.catching(e);
			log.error("SQL exception occurred", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();

	}

	public Optional<Integer> openNewAccount(int userNumber) {
		log.traceEntry("openNewAccount: " + "User Number = {}", userNumber);

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "call Add_Account(?)";
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

	public Optional<Integer> depositMoney(int bankID, double amount) {
		log.traceEntry("depositMoney: " + "Bank Account ID = {1}, Deposit Amount = {2}", bankID, amount);

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "call deposit(?, ?)";
			CallableStatement cb = con.prepareCall(sql);
			cb.setInt(1, bankID);
			cb.setDouble(2, amount);
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

	public Optional<Integer> withdrawMoney(int bankID, double amount) {
		log.traceEntry("withdrawMoney: " + "Bank Account ID = {1}, Withdrawal Amount = {2}", bankID, amount);

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "call withdraw(?, ?, ?)";
			CallableStatement cb = con.prepareCall(sql);
			cb.setInt(1, bankID);
			cb.setDouble(2, amount);
			cb.registerOutParameter(3, java.sql.Types.INTEGER);
			cb.execute();

			Integer result = cb.getInt(3);
			return log.traceExit(Optional.of(result));

		} catch (SQLException e) {
			log.catching(e);
			log.error("SQL exception occurred", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}

	public Optional<Integer> closeAccount(int bankID) {
		log.traceEntry("closeAccount: " + "Bank Account ID = {}", bankID);

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "call delete_account(?, ?)";
			CallableStatement cb = con.prepareCall(sql);
			cb.setInt(1, bankID);
			cb.registerOutParameter(2, java.sql.Types.INTEGER);
			cb.execute();

			Integer result = cb.getInt(2);
			return log.traceExit(Optional.of(result));

		} catch (SQLException e) {
			log.catching(e);
			log.error("SQL exception occurred", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}
}
