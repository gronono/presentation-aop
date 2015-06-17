package spring;

import org.springframework.stereotype.Service;

import api.Account;
import api.IWithdrawService;
import api.WithdrawException;

@Service
public class WithdrawService implements IWithdrawService {

	@Override
	@Profile
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
