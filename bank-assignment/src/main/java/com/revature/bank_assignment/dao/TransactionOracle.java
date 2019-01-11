package com.revature.bank_assignment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.bank_assignment.models.Transaction;
import com.revature.bank_assignment.util.ConnectionUtil;

public class TransactionOracle implements TransactionDao {
	private static TransactionOracle transaction_Oracle;

	private static final Logger log = LogManager.getLogger(TransactionOracle.class);

	private TransactionOracle() {

	}

	public static TransactionOracle getDao() {
		if (transaction_Oracle == null)
			transaction_Oracle = new TransactionOracle();
		return transaction_Oracle;
	}

	public Optional<List<Transaction>> getTransactions(int bankAcct) {
		log.traceEntry("getTransactions: " + "Bank Acct Number = {}", bankAcct);

		Connection con = ConnectionUtil.getConnection();

		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "select * from transactions where bank_account_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, bankAcct);
			ResultSet rs = ps.executeQuery();

			List<Transaction> transList = new ArrayList<Transaction>();

			while (rs.next()) {
				transList.add(new Transaction(rs.getInt("transaction_id"), rs.getInt("bank_account_id"),
						rs.getString("transaction_type"), rs.getDouble("transaction_amount")));
			}
			if (!transList.isEmpty())
				return log.traceExit(Optional.of(transList));
			return log.traceExit(Optional.empty());

		} catch (SQLException e) {
			log.catching(e);
			log.error("SQL exception occurred", e);
		}

		log.traceExit(Optional.empty());
		return Optional.empty();
	}
}
