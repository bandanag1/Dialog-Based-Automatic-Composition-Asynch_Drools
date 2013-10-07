package edu.vsr.massachusetts.dialogue.agent.controller;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hp.hpl.jena.ontology.AnnotationProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import edu.vsr.massachusetts.HomeController;
import edu.vsr.massachusetts.algorithms.IDialogAlgorithm;
import edu.vsr.massachusetts.algorithms.OntologyBasedAlgorithm;
import edu.vsr.massachusetts.dialogue.agent.model.Dialogue;
import edu.vsr.massachusetts.dialogue.agent.model.Question;
import edu.vsr.massachusetts.dialogue.agent.model.QuestionInput;

@Controller
public class DialogueController {
			
	private static final Boolean USE_INTERNAL_REDIRECTS = true;
	private static final String DIALOGUE_NAME = "massachusettsDialogue";
		
	private static final Logger logger = LoggerFactory.getLogger(DialogueController.class);

	@RequestMapping(value = "/DialogueAgent")
	public ModelAndView Default() {
		return new ModelAndView("redirect:/DialogueAgent/Open");
	}
	
	@RequestMapping(value = "/DialogueAgent/{Component}")
    public ModelAndView DialogueAgent(@PathVariable String Component, HttpServletRequest Request, HttpSession Session) throws Exception {
		
//		Snippet: Assign sth to model
//
//		model.addAttribute("serverTime", formattedDate );
		
		Map<String,String[]> paramMap = Request.getParameterMap();
		
		// workaround for internal redirects
		String QueryString = Request.getRequestURI();
		String requestFileType = "";
		if(QueryString.contains("."))
			requestFileType =  QueryString.substring(QueryString.lastIndexOf("."));
		
		IDialogAlgorithm algorithm = new OntologyBasedAlgorithm(Request, Session);
		
		switch(Component) {
			default:
			case "Open":

				algorithm.Initialize();
				Session.setAttribute(DIALOGUE_NAME, algorithm);				
				
				return new ModelAndView("redirect:/DialogueAgent/Ask" + requestFileType);
								
			case "Ask":

				algorithm = (IDialogAlgorithm) Session.getAttribute(DIALOGUE_NAME);
				if(algorithm == null && USE_INTERNAL_REDIRECTS)
					return new ModelAndView("redirect:/DialogueAgent/Open" + requestFileType);
				Session.setAttribute(DIALOGUE_NAME, algorithm);
				
				Dialogue nextQuestion = algorithm.GetNextQuestion();
				if(nextQuestion == null) {
					// Algorithm finished
					return new ModelAndView("redirect:/DialogueAgent/Close" + requestFileType);
				}					
				
				return new ModelAndView("dialogue", DIALOGUE_NAME, nextQuestion);
				
			case "Get_response":				
				
				algorithm = (IDialogAlgorithm) Session.getAttribute(DIALOGUE_NAME);
				if(algorithm == null && USE_INTERNAL_REDIRECTS)
					return new ModelAndView("redirect:/DialogueAgent/Open" + requestFileType);
				
				algorithm.ProcessAnswer(paramMap);
				Session.setAttribute(DIALOGUE_NAME, algorithm);				
				return new ModelAndView("redirect:/DialogueAgent/Ask" + requestFileType);
				
			case "Re-initiate":
				
				Session.removeAttribute(DIALOGUE_NAME);
				return new ModelAndView("redirect:/DialogueAgent/Open" + requestFileType);				
				
			case "Close":
				
				Session.removeAttribute(DIALOGUE_NAME);
				// TODO: ask for anwsers; build queries; ask OMR for urls; give them back to client
				return new ModelAndView("redirect:/DialogueAgent/Open" + requestFileType);
		}			       
    }
	
}
