package com.uniquedeveloper.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Reg")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upwd=request.getParameter("pass");
		String urpwd=request.getParameter("re_pass");
		String umobile=request.getParameter("contact");
		RequestDispatcher rd=null; 
		Connection con=null;
		PreparedStatement pst=null;
		if(uname==null || uname.equals("")) {
			request.setAttribute("status", "invalidName");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}
		if(uemail==null || uemail.equals("")) {
			request.setAttribute("status", "invalidEmail");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}
		if(upwd==null || upwd.equals("")) {
			request.setAttribute("status", "invalidPwd");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}else if(!upwd.equals(urpwd)) {
			request.setAttribute("status", "invalidConfirmPwd");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}
		if(umobile==null || umobile.equals("")) {
			request.setAttribute("status", "invalidMob");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}else if(umobile.length()>10) {
			request.setAttribute("status", "invalidMobileLength");
			rd=request.getRequestDispatcher("registration.jsp");
			rd.forward(request, response);
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "angshuk123");
			 pst=con.prepareStatement("insert into users(uname,upwd,uemail,umobile)values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			pst.setString(4, umobile);
			int rowCount=pst.executeUpdate();
			rd=request.getRequestDispatcher("registration.jsp");
			if(rowCount>0) {
				request.setAttribute("status", "success");
				
				
			}else {
				request.setAttribute("status", "failed");
			}
			rd.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
