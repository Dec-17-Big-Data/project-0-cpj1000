package com.revature.bank_assignment.models;

public class Transaction {
	private int transaction_id;
	private int bank_account_id;
	private String transaction_type;
	private double transaction_amount;

	public Transaction(int transaction_id, int bank_account_id, String transaction_type, double transaction_amount) {
		super();
		this.transaction_id = transaction_id;
		this.bank_account_id = bank_account_id;
		this.transaction_type = transaction_type;
		this.transaction_amount = transaction_amount;
	}

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	public int getBank_account_id() {
		return bank_account_id;
	}

	public void setBank_account_id(int bank_account_id) {
		this.bank_account_id = bank_account_id;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public double getTransaction_amount() {
		return transaction_amount;
	}

	public void setTransaction_amount(double transaction_amount) {
		this.transaction_amount = transaction_amount;
	}

	@Override
	public String toString() {
		return "Transaction [transaction_id=" + transaction_id + ", bank_account_id=" + bank_account_id
				+ ", transaction_type=" + transaction_type + ", transaction_amount=" + transaction_amount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bank_account_id;
		long temp;
		temp = Double.doubleToLongBits(transaction_amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + transaction_id;
		result = prime * result + ((transaction_type == null) ? 0 : transaction_type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (bank_account_id != other.bank_account_id)
			return false;
		if (Double.doubleToLongBits(transaction_amount) != Double.doubleToLongBits(other.transaction_amount))
			return false;
		if (transaction_id != other.transaction_id)
			return false;
		if (transaction_type == null) {
			if (other.transaction_type != null)
				return false;
		} else if (!transaction_type.equals(other.transaction_type))
			return false;
		return true;
	}

}
