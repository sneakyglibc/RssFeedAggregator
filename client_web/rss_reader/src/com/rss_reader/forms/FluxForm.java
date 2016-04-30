package com.rss_reader.forms;

import javax.servlet.http.HttpServletRequest;

import com.rss_reader.beans.Flux;

public final class FluxForm
{
	private static final String CHAMP_URL = "fluxurl";
	private static final String CHAMP_NAME = "fluxname";

	public Flux addFlux(HttpServletRequest request)
	{
		String flux_url = getValeurChamp(request, CHAMP_URL);
		String flux_name = getValeurChamp(request, CHAMP_NAME);

		Flux flux = new Flux();
		
		flux.setLink(flux_url);
		flux.setTitle(flux_name);

		return flux;
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