package com.rss_reader.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rss_reader.beans.User;
import com.rss_reader.forms.SignupForm;

public class Signup extends HttpServlet
{
	public static final String VUE = "/WEB-INF/Signup.jsp";
    public static final String ATT_USER = "user";
    public static final String ATT_FORM = "form";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		SignupForm form = new SignupForm();

		// check les infos du formulaire
		User user = form.inscription(request);

		// stock le bean et le formulaire dans la request
		request.setAttribute(ATT_FORM, form);
		request.setAttribute(ATT_USER, user);

		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}
}