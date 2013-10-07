package edu.vsr.massachusetts.dialogue.agent.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.web.context.request.RequestContextHolder;

import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

@XmlRootElement(name = "omlDialogue")
public class Dialogue {
	
	private Question currentQuestion;
	private List<Question> questionQueue;
	@XmlElement
	public List<Question> answeredQuestions;
	private String id;
	private String currentComponent;
	private String location;
	private String preferredLanguage;
	private String followedBy;
	
	public Dialogue (String sessionId) {
		this.setId(sessionId);
				
		this.questionQueue = new ArrayList<Question>();
		this.answeredQuestions = new ArrayList<Question>();
	}
	
	@XmlElement
	public void setCurrentQuestion (Question q) {
		this.currentQuestion = q;		
	}
	
	public Question getCurrentQuestion () {
		return this.currentQuestion;
	}
	
	@XmlElement
	public void setId (String id){
		this.id = id;
	}
	
	public String getId () {
		return this.id;
	}
	
	@XmlElement
	public void setCurrentComponent (String comp) {
		this.currentComponent = comp;		
	}
	
	public String getCurrentComponent () {
		return this.currentComponent;
	}
	
	@XmlElement
	public void setPreferredLanguage (String lang) {
		this.preferredLanguage = lang;		
	}
	
	public String getPreferredLanguage () {
		return this.preferredLanguage;
	}
	
	@XmlElement
	public void setLocation (String loc) {
		this.location = loc;		
	}
	
	public String getLocation () {
		return this.location;
	}
	
	@XmlElement
	public void setFollowedBy (String fb) {
		this.followedBy = fb;		
	}
	
	public String getFollowedBy () {
		return this.followedBy;
	}
	
	public void moveQuestionToAnswers (Question question) {
		this.answeredQuestions.add(question);
	}
	
	public void addQuestionToQueue (Question question) {
		this.questionQueue.add(question);
	}
	
	public Question retrieveCurrentQuestion () {
		this.currentQuestion = this.questionQueue.remove(0);
		return this.currentQuestion;
	}
	
	public Integer getQuestionQueueSize () {
		return this.questionQueue.size();
	}
		
}
