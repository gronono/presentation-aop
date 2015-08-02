package couche;

import api.Account;
import api.IWithdrawService;
import api.SecurityException;
import api.WithdrawException;
import api.ext.ITransactionManager;
import api.ext.ServiceLocator;
import api.ext.Transaction;

public class TransactionalWithdrawService implements IWithdrawService {

	private IWithdrawService delegate;
	private ITransactionManager txMgr = ServiceLocator.INSTANCE.get(ITransactionManager.class);

	public TransactionalWithdrawService(IWithdrawService delegate) {
		this.delegate = delegate;
	}

	@Override
	public Account withdraw(Account account, long withdrawAmount) throws WithdrawException, SecurityException {
		Transaction tx = txMgr.begin();
		try {
			Account result = delegate.withdraw(account, withdrawAmount);
			tx.commit();
			return result;
		} catch (WithdrawException | SecurityException e) {
			tx.rollback();
			throw e;
		}
	}

}
