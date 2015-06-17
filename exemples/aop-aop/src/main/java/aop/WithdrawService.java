package aop;

import api.Account;
import api.IWithdrawService;
import api.WithdrawException;

public class WithdrawService implements IWithdrawService {

	@Override
	public Account withdraw(Account account, long withdrawAmount) throws WithdrawException, SecurityException {
		// Métier
		long oldBalance = account.getBalance();
		long newBalance = oldBalance - withdrawAmount;
		if (newBalance < 0) {
			throw new WithdrawException(account, withdrawAmount);
		}
		account.setBalance(newBalance);

		return account;
	}
}
