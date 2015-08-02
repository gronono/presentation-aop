package classique;

import api.Account;
import api.IWithdrawService;
import api.SecurityException;
import api.WithdrawException;
import api.ext.ITraceService;
import api.ext.ITransactionManager;
import api.ext.IUserService;
import api.ext.ServiceLocator;
import api.ext.Transaction;
import api.ext.User;

public class WithdrawService implements IWithdrawService {

	private static final String WITHDRAW_METHOD = "withdraw(Account, long)";

	private ITransactionManager txMgr = ServiceLocator.INSTANCE.get(ITransactionManager.class);

	private IUserService usrSrv = ServiceLocator.INSTANCE.get(IUserService.class);

	private ITraceService traceSrv = ServiceLocator.INSTANCE.get(ITraceService.class);

	@Override
	public Account withdraw(Account account, long withdrawAmount) throws WithdrawException, SecurityException {
		Transaction tx = txMgr.begin();
		try {
			// Journal
			traceSrv.entering(WithdrawService.class, WITHDRAW_METHOD, account, withdrawAmount);

			// Sécurité
			User accountOwner = account.getOwner();
			User currentUser = usrSrv.getCurrentUser();
			if (!accountOwner.equals(currentUser)) {
				throw new SecurityException(currentUser, accountOwner);
			}

			// Métier
			long oldBalance = account.getBalance();
			long newBalance = oldBalance - withdrawAmount;
			if (newBalance < 0) {
				throw new WithdrawException(account, withdrawAmount);
			}
			account.setBalance(newBalance);

			// Journal
			traceSrv.exiting(WithdrawService.class, WITHDRAW_METHOD, account);

			tx.commit();
		} catch (WithdrawException | SecurityException e) {
			tx.rollback();
			throw e;
		}
		return account;
	}
}
