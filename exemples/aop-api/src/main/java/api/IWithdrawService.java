package api;

public interface IWithdrawService {
	Account withdraw(Account account, long withdrawAmount) throws WithdrawException, SecurityException;
}
