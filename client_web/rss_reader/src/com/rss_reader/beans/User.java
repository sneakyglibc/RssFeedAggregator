package com.rss_reader.beans;

import java.util.Iterator;
import java.util.List;

public class User
{
	protected String email;
	protected String password;
	protected List<Flux> flux;
	protected List<Item> items;
	
	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setFlux(List<Flux> flx)
	{
		this.flux = flx;
	}

	public void setItems(List<Item> itms)
	{
		this.items = itms;
	}
	
	public String getEmail()
	{
		return this.email;
	}

	public String getPassword()
	{
		return this.password;
	}

	public List<Flux> getFlux()
	{
		return this.flux;
	}

	public List<Item> getItems()
	{
		return this.items;
	}
	
	public void addFlux(String url, String name)
	{
		Flux tmp = new Flux();

		tmp.setLink(url);
		tmp.setTitle(name);

		flux.add(tmp);
	}

	public void addItem(String url, String name)
	{
		Item tmp = new Item();

		tmp.setLink(url);
		tmp.setTitle(name);

		items.add(tmp);
	}

	public void remFlux(String url)
	{
		Iterator<Flux> it = flux.iterator();

		while (it.hasNext())
		{
			Flux tmp = it.next();
			if (tmp.link == url)
				flux.remove(it);
		}
	}
	
	public void remItem(String url)
	{
		Iterator<Item> it = items.iterator();

		while (it.hasNext())
		{
			Item tmp = it.next();
			if (tmp.link == url)
				items.remove(it);
		}
	}
}
