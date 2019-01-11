package com.revature.bank_assignment.dao;

import java.util.List;
import java.util.Optional;

import com.revature.bank_assignment.models.Transaction;

public interface TransactionDao {

	public Optional<List<Transaction>> getTransactions(int bankAcct);

}