

import java.io.IOException;
//import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Operations
 */
@WebServlet("/Operations")
public class Operations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	//static int id= Login.id;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String reqdata  =request.getReader().lines().collect(Collectors.joining());
		
		System.out.println(reqdata);
	
		JSONObject value = new JSONObject(reqdata);
		
		HttpSession session = request.getSession();
		int loginId = (int)session.getAttribute("cid");
		
		if(value.getString("operation").equals("withdraw")) withdraw(request,response,value,loginId);
		else if(value.getString("operation").equals("deposit")) deposit(request,response,value,loginId);
		else if(value.getString("operation").equals("accountTransfer")) accountTransfer(request,response,value,loginId);
		else transferHistory(request, response,loginId);
	
		
	}
	
	private void withdraw(HttpServletRequest request, HttpServletResponse response,JSONObject objJson, int loginId) throws ServletException, IOException {
		
		

		int amount = Integer.parseInt(objJson.getString("amount"));
		
		//Customer c = Register.customers.get(id);
		
		int currBalance = CustomerDao.getBalance(loginId);
		
		boolean check = amount<=currBalance-1000;
		
//		int updatedBalance = c.getBalance();
		
		
		
		System.out.println(CustomerDao.getBalance(loginId)+" After");
		if(check) {
			CustomerDao.updateBalance(currBalance-amount, loginId);
			//c.setBalance(c.getBalance()-amount);
			currBalance = CustomerDao.getBalance(loginId);//c.getBalance();
		}
	
	
		Transactions t = new Transactions();
//        t.setTid(c.getTransactions().size()+1);
		t.setTid(TransactionsDao.getTransID(loginId));
        t.setAmount(amount);
        t.setType("AtmWithdrawal");
        t.setBalance(currBalance);
        
        //t.setBalance(c.getBalance());

        //c.getTransactions().add(t);
        
        TransactionsDao.insertTransaction(t,loginId);
		
		String  reqInfo= "{\"check\":"+check+",\"balance\":"+currBalance+"}";
		
		//System.out.println(reqInfo);
		
		JSONObject info= new JSONObject(reqInfo);
		
		response.getWriter().write(info.toString());
		
	}

	private void deposit(HttpServletRequest request, HttpServletResponse response,JSONObject objJson,int loginId) throws ServletException, IOException {
		


		int amount = Integer.parseInt(objJson.getString("amount"));
		
//		System.out.println(amount);
		
		//Customer c = Register.customers.get(id);

		//c.setBalance(c.getBalance()+amount);
		
		CustomerDao.updateBalance(CustomerDao.getBalance(loginId)+amount, loginId);
		
		Transactions t = new Transactions();
		t.setTid(TransactionsDao.getTransID(loginId));
        t.setAmount(amount);
        t.setType("CashDeposit");
        int currBalance =CustomerDao.getBalance(loginId);
        t.setBalance(currBalance);

        //c.getTransactions().add(t);
        TransactionsDao.insertTransaction(t,loginId);
        

		String  reqInfo= "{\"balance\":"+currBalance+"}";
		
		//System.out.println(reqInfo);
		
		JSONObject info= new JSONObject(reqInfo);
		
		response.getWriter().write(info.toString());
		
		
	}
	
	public void accountTransfer(HttpServletRequest request, HttpServletResponse response,JSONObject objJson,int loginId)throws ServletException, IOException {
		

		int amount = Integer.parseInt(objJson.getString("amount"));
		
		int tacNum = Integer.parseInt(objJson.getString("tacNum"));
		
		//System.out.println(amount+""+tacNum);

//		Customer c1 = Register.customers.get(id);
//		Customer c2 = null;

		int currBalance = CustomerDao.getBalance(loginId);
		
		boolean checkForBalance = currBalance-amount>=1000;
		
		int transToCusID = CustomerDao.getTransToCusID(tacNum);
		
		if((transToCusID==0 || transToCusID==loginId) || !checkForBalance) {
			//insufficient balance or invalid accnum
			String  reqInfo="";
			
			if(!checkForBalance)
			{
				 reqInfo= "{\"check\":"+false+",\"balance\":"+currBalance+"}";
			}
			else if(transToCusID==0 || transToCusID==loginId) {
				
				reqInfo= "{\"check\":"+false+",\"balance\":"+currBalance+"}";
			}
			
			JSONObject info= new JSONObject(reqInfo);
			
	        response.getWriter().write(info.toString());
		}
		else if(checkForBalance && transToCusID!=0) {
			//valid entry
			CustomerDao.updateBalance(CustomerDao.getBalance(loginId)-amount, loginId);
			CustomerDao.updateBalance(CustomerDao.getBalance(transToCusID)+amount, transToCusID);
	        
	        Transactions t1 = new Transactions();
	        
	        t1.setTid(TransactionsDao.getTransID(loginId));
	        t1.setAmount(amount);
	        t1.setType("Transfer to AccNum:"+tacNum);
	        t1.setBalance(CustomerDao.getBalance(loginId));
	        
	        TransactionsDao.insertTransaction(t1,loginId);
	        
	        Transactions t2 = new Transactions();
	        
	        t2.setTid(TransactionsDao.getTransID(transToCusID));
	        t2.setAmount(amount);
	        t2.setType("Transfer from AccNum:"+CustomerDao.getAccNum(loginId));
	        t2.setBalance(CustomerDao.getBalance(transToCusID));
	        
	        TransactionsDao.insertTransaction(t2,transToCusID);
	        
	        String  reqInfo= "{\"check\":"+true+",\"balance\":"+t1.getBalance()+"}";
	        
	        JSONObject info= new JSONObject(reqInfo);
			
	        response.getWriter().write(info.toString());
	        
		}
		
//		for (Map.Entry<Integer,Customer> mapElement : Register.customers.entrySet()) {
//
//				if(mapElement.getValue().getAccountNumber()==tacNum) {
//					c2=mapElement.getValue();
//				}
//    
//        }
		
//		c1.setBalance(c1.getBalance()-amount);
//        c2.setBalance(c2.getBalance()+amount);
		

		
        
        //t1.setTid(c1.getTransactions().size()+1);
        
     

        
        
        
        //c1.getTransactions().add(t1);

       
       // t2.setTid(c2.getTransactions().size()+1);
       

        //c2.getTransactions().add(t2);
        
		
       
        
        
        

	}
	
	public void transferHistory(HttpServletRequest request, HttpServletResponse response,int loginId) throws IOException {
		
		
		
		//Customer c = Register.customers.get(id);
		
		JSONArray jArr = new JSONArray();
		
//		for(Transactions t : c.getTransactions()) {
//			
//			JSONObject temp = new JSONObject(t);
//			jArr.put(temp);
//
//		}
		
		for(Transactions t : TransactionsDao.getTransactions(loginId)) {
			
			System.out.println(t);
			JSONObject temp = new JSONObject(t);
			jArr.put(temp);

		}
		
		response.getWriter().write(jArr.toString());
		
	}
}

