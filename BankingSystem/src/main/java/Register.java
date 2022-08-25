

import java.io.IOException;

import java.util.ArrayList;
//import java.io.PrintWriter;
//import java.util.HashMap;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/Register")
public class Register extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	//static HashMap<Integer,Customer> customers = new HashMap<>();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		String cName = request.getParameter("name");
//		String emailId = request.getParameter("emailId");
//		String password = request.getParameter("passwordCheck");
		
	
		String formDetails =request.getReader().lines().collect(Collectors.joining());
		
		
//		System.out.println(formDetails);
	
//		String as1 ="{\"name\":\"Josh\",\"email\":\"josh@gmail.com\",\"password\":\"Wsaikl523#\"}";
//		   
//		System.out.println(as1);
		
	
	
		
		
		
		JSONObject formData = new JSONObject(formDetails);
		
		
	//	System.out.println(formData.get("password"));
		  
		
		
		
		
		//System.out.println(CustomerDao.numOfCustomers()+ " my Size ");
		Customer c = new Customer();
		
		int tempSize =CustomerDao.numOfCustomers();
		
		c.setName(formData.getString("name"));
		c.setEmailId(formData.getString("email"));
		c.setId(tempSize+1);
		//c.setId(customers.size()+1);
		c.setAccountNumber(tempSize+10000+1);
		//c.setAccountNumber(customers.size()+10000+1);
		c.setBalance(10000);
		c.setPassword(passwordEncryption(formData.getString("password")));
		
		ArrayList<Transactions> transactionsList = new ArrayList<>();
        c.setTransactions(transactionsList);

        
        Transactions t = new Transactions();
        t.setTid(1);t.setType("Opening");
        t.setAmount(10000);
        t.setBalance(c.getBalance());

        //c.getTransactions().add(t);
        boolean check =CustomerDao.insertCustomer(c);
		TransactionsDao.insertTransaction(t,c.getId());
		
		
		
		if(check) {
			JSONObject user = new JSONObject(c);
			user.accumulate("check",check);
			
			response.getWriter().write(user.toString());
		}
		else {
			String  reqInfo= "{\"check\":"+check+"}";
			JSONObject info= new JSONObject(reqInfo);
			
			response.getWriter().write(info.toString());
		}
		
		//JSONArray  users = new JSONArray();
		//users.put(user);
	
		
		//System.out.println(userJson);
		
		//response.setContentType(userJson);
		
		
//		request.setAttribute(userJson, userJson);
		
		//System.out.println(userJson);
		
		
		//customers.put(c.getId(), c);
		
//		HttpSession session = request.getSession();
//		session.setAttribute("customers",customers);   
		
		//out.write(userJson);
	//	out.print(users);
		
//		out.println("Account has been created");
//
//        out.println("Customer ID:"+ c.getId());
//        out.println("Account Number:"+ c.getAccountNumber());
    
	}
	
	public static String passwordEncryption(String password){

        StringBuilder encryptedPass = new StringBuilder();

        for(int i =0; i<password.length();i++){

            char ch= password.charAt(i);

            if(ch>=97&&ch<=122) encryptedPass.append((char)(96+((int)ch+1-96)%26));
            else if(ch>=65&&ch<=90) encryptedPass.append((char)(64+((int)ch+1-64)%26));
            else encryptedPass.append((char)(47+((int)ch+1-47)%10));

        }

        return encryptedPass.toString();

    }
	
//	public static void main(String[] args) {
//		
//		System.out.println(customers.size());
//		
//	}



}
