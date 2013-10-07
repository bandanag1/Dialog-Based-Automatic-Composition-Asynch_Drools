package edu.vsr.massachusetts.dialogue.agent.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Dashboard")
public class Dashboard {
	
	private String id;
	private String title;
	@XmlElement
	public List<Widget> widgets;
	
	public Dashboard() {
		this.widgets = new ArrayList<Widget>();
	}
	
	public Dashboard(String id, String title) {
		this.widgets = new ArrayList<Widget>();
		this.setId(id);
		this.setTitle(title);
	}
	
	@XmlElement
	public void setId (String id) {
		this.id = id;
	}
	
	public String getId () {
		return this.id;
	}
	
	@XmlElement
	public void setTitle (String title) {
		this.title = title;
	}
	
	public String getTitle () {
		return this.title;
	}
	
	public void addWidget (Widget w) {
		this.widgets.add(w);
	}
	

}
