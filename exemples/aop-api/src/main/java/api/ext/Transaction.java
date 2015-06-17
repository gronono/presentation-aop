package api.ext;

public class Transaction {

	public void begin() {
		System.out.println("tx begin");
	}

	public void commit() {
		System.out.println("tx commit");
	}

	public void rollback() {
		System.out.println("tx rollback");
	}
}
