package com.rss_reader.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rss_reader.beans.User;

public final class LoginForm
{
	private static final String CHAMP_EMAIL = "email";
	private static final String CHAMP_PASS = "password";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public String getResultat()
	{
		return resultat;
	}

	public Map<String, String> getErreurs()
	{
		return erreurs;
	}

	public User connection(HttpServletRequest request)
	{
		String email = getValeurChamp(request, CHAMP_EMAIL);
		String motDePasse = getValeurChamp(request, CHAMP_PASS);

		User utilisateur = new User();

		try
		{
			validEmail(email);
		} catch (Exception e)
		{
			setErreur(CHAMP_EMAIL, e.getMessage());
		}
		utilisateur.setEmail(email);

		try
		{
			validPassword(motDePasse);
		} catch (Exception e)
		{
			setErreur(CHAMP_PASS, e.getMessage());
		}
		utilisateur.setPassword(motDePasse);

		if (erreurs.isEmpty())
		{
			resultat = "Signup success !";
		} else
		{
			resultat = "Signup fail.";
		}

		return utilisateur;
	}

	private void validEmail(String email) throws Exception
	{
		if (email != null && email.trim().length() != 0)
		{
			if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)"))
			{
				throw new Exception("Please enter a valid mail adress.");
			}
		} else
		{
			throw new Exception("Please enter a mail adress.");
		}
	}

	private void validPassword(String password) throws Exception
	{
		if (password != null)
		{
			if (password.trim().length() < 3)
			{
				throw new Exception("Your password must be at least 3 characters long.");
			}
		} else
		{
			throw new Exception("Please enter your password");
		}
	}

	private void setErreur(String champ, String message)
	{
		erreurs.put(champ, message);
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