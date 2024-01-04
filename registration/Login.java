package com.uniquedeveloper.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uemail=request.getParameter("username");
		String upwd=request.getParameter("password");
		Connection con=null;
		PreparedStatement ps=null;
		HttpSession session=request.getSession();
		RequestDispatcher rd=null;
		
		if(uemail==null || uemail.equals("")) {
			request.setAttribute("status", "invalidEmail");
			rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
		if(upwd==null || upwd.equals("")) {
			request.setAttribute("status", "invalidPwd");
			rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "angshuk123");
			ps=con.prepareStatement("select * from users where uemail=? and upwd=?");
			ps.setString(1, uemail);
			ps.setString(2,upwd);
			
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				rd=request.getRequestDispatcher("index.jsp");
			}else {
				request.setAttribute("status", "failed");
				rd=request.getRequestDispatcher("login.jsp");
			}
			rd.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
