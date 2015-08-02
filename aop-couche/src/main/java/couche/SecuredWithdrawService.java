package couche;

import api.Account;
import api.IWithdrawService;
import api.SecurityException;
import api.WithdrawException;
import api.ext.IUserService;
import api.ext.ServiceLocator;
import api.ext.User;

public class SecuredWithdrawService implements IWithdrawService {

	private IUserService usrSrv = ServiceLocator.INSTANCE.get(IUserService.class);

	private IWithdrawService delegate;

	public SecuredWithdrawService(IWithdrawService delegate) {
		this.delegate = delegate;
	}

	@Override
	public Account withdraw(Account account, long withdrawAmount) throws WithdrawException, SecurityException {
		// Sécurité
		User accountOwner = account.getOwner();
		User currentUser = usrSrv.getCurrentUser();
		if (!accountOwner.equals(currentUser)) {
			throw new SecurityException(currentUser, accountOwner);
		}

		return this.delegate.withdraw(account, withdrawAmount);
	}

}
