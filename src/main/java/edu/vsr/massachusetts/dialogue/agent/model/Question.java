package edu.vsr.massachusetts.dialogue.agent.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Question")
public class Question {

	private String text;
	private String id;
	private List<QuestionInput> input;
	
	public Question() {
		this.input = new ArrayList<QuestionInput>();
	}
	
	public Question (String Id) {
		this.input = new ArrayList<QuestionInput>();
		this.setId(Id);
	}
	
	public Question (String Id, String text) {
		this.input = new ArrayList<QuestionInput>();
		this.setId(Id);
		this.setText(text);
	}
	
	@XmlElement
	public void setId (String Id) {
		this.id = Id;
	}
	
	public String getId () {
		return this.id;
	}
	
	@XmlElement
	public void setText (String text) {
		this.text = text;		
	}
	
	public String getText () {
		return this.text;
	}
	
	@XmlElement
	public void setInput (List<QuestionInput> input) {
		this.input = input;
	}
	
	public List<QuestionInput> getInput () {
		return this.input;
	}
	
	public void addInput (QuestionInput input) {
		this.input.add(input);
	}
	
	private QuestionInput getQuestionInputByName (String name) {
		
		for(QuestionInput in : this.input) {
			if(in.getName() == name) {
				return in;
			}
		}
		
		return this.input.get(0);
		
	}
	
	public void saveAnswer (String name, String answer) {
		this.getQuestionInputByName(name).setAnswer(answer);
	}
	
}
