package com.rss_reader.beans;

public class Item
{
	protected String link;
	protected String title;

	public void setLink(String url)
	{
		this.link = url;
	}

	public void setTitle(String name)
	{
		this.title = name;
	}

	public String getLink()
	{
		return this.link;
	}

	public String getTitle()
	{
		return this.title;
	}
}
