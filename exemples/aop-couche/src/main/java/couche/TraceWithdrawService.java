package couche;

import api.Account;
import api.IWithdrawService;
import api.SecurityException;
import api.WithdrawException;
import api.ext.ITraceService;
import api.ext.ServiceLocator;

public class TraceWithdrawService implements IWithdrawService {

	private static final String WITHDRAW_METHOD = "withdraw(Account, long)";

	private ITraceService traceSrv = ServiceLocator.INSTANCE.get(ITraceService.class);

	private IWithdrawService delegate;

	public TraceWithdrawService(IWithdrawService delegate) {
		this.delegate = delegate;
	}

	@Override
	public Account withdraw(Account account, long withdrawAmount) throws WithdrawException, SecurityException {
		traceSrv.entering(WithdrawService.class, WITHDRAW_METHOD, account, withdrawAmount);

		Account result = delegate.withdraw(account, withdrawAmount);

		traceSrv.exiting(WithdrawService.class, WITHDRAW_METHOD, account);

		return result;
	}

}
