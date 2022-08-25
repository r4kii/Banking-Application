import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class CustomerDao {
	
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
	
	public static boolean insertCustomer(Customer c) {
    	//Connection con = getCon();
    	String query = "insert into Customers(CustomerID,Name,EmailID,Password,AccNum,Balance) values(?,?,?,?,?,?)";
    	PreparedStatement pst;
		try {
			pst = con.prepareStatement(query);
	    	pst.setInt(1, c.getId());
	    	pst.setString(2, c.getName());
	    	pst.setString(3, c.getEmailId());
	    	pst.setString(4, c.getPassword());
	    	pst.setInt(5, c.getAccountNumber());
	    	pst.setInt(6, c.getBalance());
	    	pst.executeUpdate();

		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
		        return false;
		    } else {
		        // Other SQL Exception
		    	e.printStackTrace();
		    }
		}
		return true;
    }
	
	public  static boolean verifyLogin(int id , String pwd) {
		
		
		String query = "SELECT * FROM Customers where CustomerID=? and Password=?";
		PreparedStatement pst;
		
		try {
			
			pst = con.prepareStatement(query);
			pst.setInt(1, id);
			pst.setString(2, pwd);
			
	        ResultSet results = pst.executeQuery();
	        
	        if(!results.next()) return false;

	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return true;
	}
	
	public static int numOfCustomers() {
		
		//Connection con = getCon();
		Statement st;
		String query  ="Select Count(*) as count from Customers;";
		int size =0;
		
		try {
			
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			size = rs.getInt("count");

	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return size;
		
	}
	
	public static void updateBalance(int balance, int id) {
		
		PreparedStatement pst ;
		String query = "Update Customers Set Balance=? where CustomerID=?";
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, balance);
			pst.setInt(2, id);
			pst.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int getBalance(int id) {
		
		PreparedStatement pst;
		int balance =10000;
		
		String query = "Select Balance as balance from Customers where CustomerID=?;";
		try {
			pst =con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			rs.next();
			balance = rs.getInt("balance");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return balance;
	}
	
	public static int getTransToCusID(int tacNum) {
		
		PreparedStatement pst;
		String query ="Select CustomerID as transToCusID from Customers where AccNum =?;";
		int transToCusID=0;
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, tacNum);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				transToCusID = rs.getInt("transToCusID");
			} 
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return transToCusID;
	}
	
	public static int getAccNum(int id) {
		
		int accNum=10001;
		PreparedStatement pst;
		String query ="Select AccNum as accNum from Customers where CustomerID =?;";
		
		try {
			pst=con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			rs.next();
			accNum = rs.getInt("accNum");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return accNum;
	}
}
