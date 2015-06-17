package couche;

import api.Account;
import api.IWithdrawService;
import api.SecurityException;
import api.WithdrawException;
import api.ext.ITraceService;
import api.ext.ITransactionManager;
import api.ext.IUserService;
import api.ext.ServiceLocator;
import api.ext.TraceService;
import api.ext.TransactionManager;
import api.ext.User;
import api.ext.UserService;

public class Main {

	private static final User gerard = new User("Gérard Bouchard");

	public static void main(String[] args) throws WithdrawException, SecurityException {
		registerExtServices();
		ServiceLocator.INSTANCE.register(IWithdrawService.class, createWithdrawService());

		IWithdrawService withdrawService = ServiceLocator.INSTANCE.get(IWithdrawService.class);

		Account account = new Account(gerard);
		account.setBalance(10_000L);

		account = withdrawService.withdraw(account, 1_000L);

		System.out.println("--------------------------");
		account.setOwner(new User("Madame Bouchard"));
		account = withdrawService.withdraw(account, 1_000L);
	}

	private static IWithdrawService createWithdrawService() {
		IWithdrawService withdrawService = new WithdrawService();
		withdrawService = new SecuredWithdrawService(withdrawService);
		withdrawService = new TraceWithdrawService(withdrawService);
		withdrawService = new TransactionalWithdrawService(withdrawService);
		return withdrawService;
	}

	private static void registerExtServices() {
		ServiceLocator.INSTANCE.register(ITransactionManager.class, new TransactionManager());
		ServiceLocator.INSTANCE.register(IUserService.class, new UserService(gerard));
		ServiceLocator.INSTANCE.register(ITraceService.class, new TraceService());
	}
}
