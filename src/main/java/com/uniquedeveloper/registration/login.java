package com.uniquedeveloper.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String user_email = request.getParameter("username");
			String user_password = request.getParameter("password");
			HttpSession session = request.getSession();
			RequestDispatcher dispatcher= null;
		
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "mohan21");
				PreparedStatement pst=con.prepareStatement("select * from user_d where user_email=? and user_password =?");
				
				pst.setString(1, user_email);
				pst.setString(2, user_password);
				
				ResultSet rs=pst.executeQuery();
				if(rs.next()) {
					session.setAttribute("name", rs.getString("user_name"));
					dispatcher =request.getRequestDispatcher("index.jsp");
					
				}
				else {
					request.setAttribute("status","failed");
					dispatcher=request.getRequestDispatcher("login.jsp");
				}
				
				dispatcher.forward(request,response);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
	}

}
