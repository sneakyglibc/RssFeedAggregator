package com.rss_reader.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rss_reader.beans.User;
import com.rss_reader.forms.LoginForm;

public class Login extends HttpServlet
{
	public static final String ATT_USER = "user";
	public static final String ATT_FORM = "form";
	public static final String ATT_SESSION_USER = "sessionUser";
	public static final String VUE = "/WEB-INF/Login.jsp";
	public static final String ACCES_HOME = "/home";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		LoginForm form = new LoginForm();

		User user = form.connection(request);

		HttpSession session = request.getSession();

		// check si le formulaire est bien rempli
		if (form.getErreurs().isEmpty())
		{
			session.setAttribute(ATT_SESSION_USER, user);
			response.sendRedirect(request.getContextPath() + ACCES_HOME);
		} else
		{
			session.setAttribute(ATT_SESSION_USER, null);
			//si c'est bon en enregistre le bean
			request.setAttribute(ATT_FORM, form);
			request.setAttribute(ATT_USER, user);
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		}
		System.out.println(user.getEmail());
	}
}