import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionsDao {
	
	public static Connection getCon() {
        Connection con = null;
        try {
        	Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/BankingSystem";
            String un = "root";
            String pw = "Mysqldata#1";
             con = DriverManager.getConnection(url, un, pw);
             System.out.println("Connection Done");
            return con;
        } catch (SQLException | ClassNotFoundException e ) {
        	return null;
        }
    }
	static Connection con = getCon();
	
	public static void insertTransaction(Transactions t,int id) {
    	//Connection con = getCon();
    	String query = "insert into Transactions(TransID,CustomerID,TransType,TransAmount,Balance) values(?,?,?,?,?)";
    	PreparedStatement pst;
		try {
			pst = con.prepareStatement(query);
	    	pst.setInt(1, t.getTid());
	    	pst.setInt(2,id);
	    	pst.setString(3,t.getType());
	    	pst.setInt(4, t.getAmount());
	    	pst.setInt(5, t.getBalance());
	    	pst.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public static int getTransID(int id) {
		
		int tid=0;
		String query ="Select Count(*) as transId  from Transactions where CustomerID=?";
		PreparedStatement pst ;
		try {
			pst= con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			rs.next();
			tid = rs.getInt("transId");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return tid+1;
	}
	
	public static ArrayList<Transactions> getTransactions(int id){
		
		ArrayList<Transactions> allTransactions = new ArrayList<>();
		

    	String query = "select * from Transactions where CustomerID=? ";
    	PreparedStatement  pst;
    	try {
    		pst = con.prepareStatement(query);
    		pst.setInt(1, id);
    		ResultSet rs = pst.executeQuery();
    		while(rs.next()) {
    			int tid = rs.getInt("TransID");
//    			int cid = rs.getInt("CustomerID");
    			
    			String transType = rs.getString("TransType");
    			int amount = rs.getInt("TransAmount");
    			int balance = rs.getInt("Balance");
    			
    			Transactions t = new Transactions(tid,transType,amount,balance);
    			allTransactions.add(t);
    		}
    		
    	}
    	catch (SQLException e){
    		e.printStackTrace();
    	}
		return allTransactions;
		
	}
}
