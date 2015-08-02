package api;

import api.ext.User;

public class Account {

	private long balance;

	private User owner;

	public Account(User owner) {
		this.owner = owner;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return String.format("Account [balance=%s, owner=%s]", balance, owner);
	}
}
