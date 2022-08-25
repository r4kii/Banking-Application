

import java.io.IOException;
//import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    //static int id ;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String loginDetails = request.getReader().lines().collect(Collectors.joining());
		JSONObject loginData = new JSONObject(loginDetails);
		
		int tempId = Integer.parseInt(loginData.getString("id"));
		String password = Register.passwordEncryption(loginData.getString("password"));

		boolean check = CustomerDao.verifyLogin(tempId, password);

		//System.out.println(check2+" Myfriend");
		
		//boolean check = authenticate(tempId,password);
		
		if(check) {
			HttpSession session = request.getSession();
			session.setAttribute("cid", tempId);
			//id= tempId;
		}
		
		String  checkAuth= "{\"check\":"+check+"}";
		
//		System.out.println(checkAuth);
//		
//		System.out.println(loginDetails);

		JSONObject validLogin = new JSONObject(checkAuth);

		response.getWriter().write(validLogin.toString());;

	}
	
//	private boolean authenticate (int id, String password){
//		
//		
//		System.out.println(Register.customers.get(id).toString());
//		
//		if(Register.customers.containsKey(id))  return Register.customers.get(id).getPassword().equals(password);
//		return false;
//		
//	}

}
