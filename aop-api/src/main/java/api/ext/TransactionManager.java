package api.ext;

public class TransactionManager implements ITransactionManager {

	@Override
	public Transaction begin() {
		Transaction tx = new Transaction();
		tx.begin();
		return tx;
	}
}
