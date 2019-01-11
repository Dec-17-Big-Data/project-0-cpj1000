package com.revature.bank_assignment.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.revature.bank_assignment.dao.TransactionDao;
import com.revature.bank_assignment.dao.TransactionOracle;
import com.revature.bank_assignment.models.Transaction;

public class TransactionService {
	private static TransactionService transaction_Service;
	final static TransactionDao transaction_dDao = TransactionOracle.getDao();

	private TransactionService() {

	}

	public static TransactionService getService() {
		if (transaction_Service == null) {
			transaction_Service = new TransactionService();
		}
		return transaction_Service;
	}

	// Gets list of transactions for a given bank acct
	public List<Transaction> getTransactionList(int bankID) {
		try {
			Optional<List<Transaction>> transactionList = transaction_dDao.getTransactions(bankID);
			if (transactionList.isPresent()) {
				return transactionList.get();
			} else {
				throw new NoSuchElementException();
			}
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
