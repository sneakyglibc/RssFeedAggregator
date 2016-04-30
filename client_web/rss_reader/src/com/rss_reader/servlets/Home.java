package com.rss_reader.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Home extends HttpServlet
{
	public static final String ACCES_PUBLIC = "/Public.jsp";
	public static final String ACCES_HOME = "/WEB-INF/Home.jsp";
	public static final String ATT_SESSION_USER = "sessionUser";
	public String RSS_URL = "http://feeds.bbci.co.uk/news/rss.xml";
	public static boolean urlCheck = true;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// recupération de la session
		HttpSession session = request.getSession();

		// check is l'user est co
		if (session.getAttribute(ATT_SESSION_USER) == null)
		{
			// s'il est pas co redirection vers la page public
			response.sendRedirect(request.getContextPath() + ACCES_PUBLIC);
		} else
		{
			// set les tables d flux par défault
			List<String> titles = readRSS_title(RSS_URL);
			List<String> links = readRSS_link(RSS_URL);
			String fluxTitle = getFluxTitle(RSS_URL);

			Map<String, String> items = readRSS("http://www.lemonde.fr/rss/une.xml");
			request.setAttribute("items", items);

			request.setAttribute("titles", titles);
			request.setAttribute("links", links);
			request.setAttribute("fluxTitle", fluxTitle);
			request.setAttribute("fluxLink", RSS_URL);
			this.getServletContext().getRequestDispatcher(ACCES_HOME).forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// récupération de la string du nouveau flux
		String rssUrl = getValeurChamp(request, "rssUrl");

		String fluxTitle = getFluxTitle(rssUrl);
		if (urlCheck == true)
		{
			RSS_URL = rssUrl;

			List<String> titles = readRSS_title(RSS_URL);
			List<String> links = readRSS_link(RSS_URL);

			request.setAttribute("titles", titles);
			request.setAttribute("links", links);
			request.setAttribute("fluxLink", RSS_URL);
		}
		request.setAttribute("fluxTitle", fluxTitle);
		this.getServletContext().getRequestDispatcher(ACCES_HOME).forward(request, response);
	}

	public static String getFluxTitle(String urlAdress)
	{
		try
		{
			URL rssUrl = new URL(urlAdress);
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
			String line;
			while ((line = in.readLine()) != null)
			{
				if (line.contains("<title>"))
				{
					int firstPos = line.indexOf("<title>");
					String temp = line.substring(firstPos);
					temp = temp.replace("<title>", " ");
					int lastPos = temp.indexOf("</title>");
					temp = temp.substring(0, lastPos);
					in.close();
					urlCheck = true;
					return temp;
				}
			}
		} catch (MalformedURLException ue)
		{
			System.out.println("Malformed URL");
		} catch (IOException ioe)
		{
			System.out.println("Something went wrong");
		}
		urlCheck = false;
		return "Something went wrong, try with another URL or go back to your last flux";
	}

	public static Map<String, String> readRSS(String urlAdress)
	{
		try
		{
			URL rssUrl = new URL(urlAdress);
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
			String line;
			int chk = 0;
			int chk2 = 0;
			int canAddItem = 0;
			Map<String, String> items = new HashMap<String, String>();
			String tmp_title = "";
			String tmp_url = "";
			while ((line = in.readLine()) != null)
			{
				if (line.contains("<link>"))
				{
					if (chk2 > 1)
					{
						int firstPos = line.indexOf("<link>");
						tmp_url = line.substring(firstPos);
						tmp_url = tmp_url.replace("<link>", " ");
						int lastPos = tmp_url.indexOf("</link>");
						tmp_url = tmp_url.substring(0, lastPos);
						System.out.println(tmp_url + " LINK\n");
						canAddItem++;
					}
					chk2++;
				}
				if (line.contains("<title>"))
				{
					if (chk > 1)
					{
						int firstPos = line.indexOf("<title>");
						tmp_title = line.substring(firstPos);
						tmp_title = tmp_title.replace("<title>", " ");
						int lastPos = tmp_title.indexOf("</title>");
						tmp_title = tmp_title.substring(0, lastPos);
						System.out.println(tmp_title + " TITRE\n");
						canAddItem++;
					}
					chk++;
				}
				if (canAddItem == 2)
				{
					canAddItem = 0;
					items.put(tmp_url, tmp_title);
					// items.add(itemTmp);
					System.out.println("ITEM ADDED\n");
				}
			}
			in.close();
			return items;
		} catch (MalformedURLException ue)
		{
			System.out.println("Malformed URL");
		} catch (IOException ioe)
		{
			System.out.println("Something went wrong");
		}
		return null;
	}

	public static List<String> readRSS_title(String urlAdress)
	{
		try
		{
			URL rssUrl = new URL(urlAdress);
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
			String sourceCode = "";
			String line;
			List<String> list = new ArrayList<String>();
			int chk;
			chk = 0;
			while ((line = in.readLine()) != null)
			{
				if (line.contains("<title>"))
				{
					if (chk > 1)
					{
						int firstPos = line.indexOf("<title>");
						String temp = line.substring(firstPos);
						temp = temp.replace("<title>", " ");
						int lastPos = temp.indexOf("</title>");
						temp = temp.substring(0, lastPos);
						sourceCode += temp + "\n";
						list.add(temp + "<br>");
					}
					chk++;
				}
			}
			System.out.println(sourceCode + "\n");
			in.close();
			return list;
		} catch (MalformedURLException ue)
		{
			System.out.println("Malformed URL");
		} catch (IOException ioe)
		{
			System.out.println("Something went wrong");
		}
		return null;
	}

	public static List<String> readRSS_link(String urlAdress)
	{
		try
		{
			URL rssUrl = new URL(urlAdress);
			BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
			String sourceCode = "";
			String line;
			List<String> list = new ArrayList<String>();
			int chk;
			chk = 0;
			while ((line = in.readLine()) != null)
			{
				if (line.contains("<link>"))
				{
					if (chk > 1)
					{
						int firstPos = line.indexOf("<link>");
						String temp = line.substring(firstPos);
						temp = temp.replace("<link>", " ");
						int lastPos = temp.indexOf("</link>");
						temp = temp.substring(0, lastPos);
						sourceCode += temp + "\n";
						list.add(temp);
					}
					chk++;
				}
			}
			System.out.println(sourceCode + "\n");
			in.close();
			return list;
		} catch (MalformedURLException ue)
		{
			System.out.println("Malformed URL");
		} catch (IOException ioe)
		{
			System.out.println("Something went wrong");
		}
		return null;
	}

	private static String getValeurChamp(HttpServletRequest request, String nomChamp)
	{
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0)
		{
			return null;
		} else
		{
			return valeur.trim();
		}
	}
}