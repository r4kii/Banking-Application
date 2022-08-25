import java.util.ArrayList;

public class Customer {

	private int id;
	private String name;
	private String password;
	private String emailId;
	private int accountNumber;
	private int balance;
	private ArrayList<Transactions> transactions ;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public ArrayList<Transactions> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(ArrayList<Transactions> transactions) {
		this.transactions = transactions;
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", password=" + password + ", emailId=" + emailId
				+ ", accountNumber=" + accountNumber + ", balance=" + balance + "]";
	}
	
}
