package edu.vsr.massachusetts.dialogue.agent.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="QuestionInput")
public class QuestionInput {

	private String type;
	private String name;
	private String answer;
	private List<String> options;
	
	public QuestionInput () {
		this.options = new ArrayList<String>();
	}
	
	public QuestionInput (String type, String name) {
		this.options = new ArrayList<String>();
		this.type = type;
		this.name = name;
	}
	
	public String getType () {
		return this.type;
	}

	@XmlElement
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName () {
		return this.name;
	}

	@XmlElement
	public void setName (String name) {
		this.name = name;
	}
	
	public String getAnswer () {
		return this.answer;
	}

	@XmlElement
	public void setAnswer (String answer) {
		this.answer = answer;
	}
	
	public List<String> getOptions () {
		return this.options;
	}

	@XmlElement
	public void setOptions (List<String> options) {
		this.options = options;
	}
	
	public void addOption (String option) {
		this.options.add(option);
	}
	
}
