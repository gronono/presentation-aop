package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import api.Account;
import api.IWithdrawService;
import api.SecurityException;
import api.WithdrawException;
import api.ext.ITraceService;
import api.ext.ITransactionManager;
import api.ext.IUserService;
import api.ext.TraceService;
import api.ext.TransactionManager;
import api.ext.User;
import api.ext.UserService;

@SpringBootApplication
public class Main {

	private static User gerard = new User("Gérard Bouchard");

	public static void main(String[] args) throws WithdrawException, SecurityException {
		ApplicationContext springContext = SpringApplication.run(Main.class, args);
		IWithdrawService withdrawService = springContext.getBean(IWithdrawService.class);

		Account account = new Account(gerard);
		account.setBalance(10_000L);

		account = withdrawService.withdraw(account, 1_000L);

		System.out.println("--------------------------");
		account.setOwner(new User("Madame Bouchard"));
		account = withdrawService.withdraw(account, 1_000L);
	}

	@Bean
	public ITransactionManager transactionManager() {
		return new TransactionManager();
	}

	@Bean
	public IUserService userService() {
		return new UserService(gerard);
	}

	@Bean
	public ITraceService traceService() {
		return new TraceService();
	}
}
