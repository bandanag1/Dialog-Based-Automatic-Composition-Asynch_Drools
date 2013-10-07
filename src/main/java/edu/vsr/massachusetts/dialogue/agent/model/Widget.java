package edu.vsr.massachusetts.dialogue.agent.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Widget")
public class Widget {

	private String name;
	private String IRI;
	private String description;
	
	public Widget() {
		
	}
	
	public Widget(String IRI, String Name, String Desc) {
		this.setIRI(IRI);
		this.setName(Name);
		this.setDescription(Desc);
	}
	
	@XmlElement
	public void setName (String name) {
		this.name = name;
	}
	
	public String getName () {
		return this.name;
	}
	
	@XmlElement
	public void setIRI (String iri) {
		this.IRI = iri;
	}
	
	public String getIRI () {
		return this.IRI;
	}
	
	@XmlElement
	public void setDescription (String desc) {
		this.description = desc;
	}
	
	public String getDescription () {
		return this.description;
	}
}
