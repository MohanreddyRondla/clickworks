package com.uniquedeveloper.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String user_name = request.getParameter("name");
		String user_password = request.getParameter("pass");
		String user_email = request.getParameter("email");
		String  user_mobile= request.getParameter("contact");
		RequestDispatcher dispatcher =null;
		Connection con=null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "mohan21");
			PreparedStatement pst=con.prepareStatement("insert into user_d(user_name,user_password,user_email,user_mobile) values(?,?,?,?)");
			pst.setString(1, user_name);
			pst.setString(2, user_password);
			pst.setString(3, user_email);
			pst.setString(4, user_mobile);
			
			int rowCount=pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if(rowCount>0){
				request.setAttribute("status","success");
			}
			else {
				request.setAttribute("status","failed");
			}
			dispatcher.forward(request,response);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
