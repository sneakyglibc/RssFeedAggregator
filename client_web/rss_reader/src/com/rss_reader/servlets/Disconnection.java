package com.rss_reader.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Disconnection extends HttpServlet
{
	public static final String URL_REDIRECTION = "login";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/* Récupération et destruction de la session en cours */
		HttpSession session = request.getSession();
		session.invalidate();

		response.sendRedirect(URL_REDIRECTION);
	}
}