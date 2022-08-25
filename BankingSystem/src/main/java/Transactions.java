public class Transactions {

    
	private int tid ;
    private String type;
    private int amount ;
    private int balance;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public Transactions(int tid, String type, int amount, int balance) {
		super();
		this.tid = tid;
		this.type = type;
		this.amount = amount;
		this.balance = balance;
	}

    public Transactions() {
    	
    }
    
	public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    @Override
	public String toString() {
		return "Transactions [tid=" + tid + ", type=" + type + ", amount=" + amount + ", balance=" + balance + "]";
	}
    
}