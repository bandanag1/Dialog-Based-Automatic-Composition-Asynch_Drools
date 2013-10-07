package edu.vsr.massachusetts.algorithms;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;

import edu.vsr.massachusetts.dialogue.agent.model.Dialogue;
import edu.vsr.massachusetts.dialogue.agent.model.Question;
import edu.vsr.massachusetts.dialogue.agent.model.QuestionInput;

public class OntologyBasedAlgorithm implements IDialogAlgorithm {
	
	private static final Logger logger = LoggerFactory.getLogger(OntologyBasedAlgorithm.class);
	
	private static final String DEFAULT_LANGUAGE = "en";
	private static final String FILE_PATH_DIALOGUE = "/resources/ontologies/dialogue_s.owl";
	private static final String FILE_PATH_CAPABILITIES = "/resources/ontologies/mashup_capabilities.owl";
	private static final String FILE_PATH_SKOS = "/resources/ontologies/skos.owl";
	private static final String NS_DIALOGUE = "http://www.ict-omelette.eu/ontologies/ace/dialogue_s.owl#";
	private static final String NS_CAPABILITIES = "http://www.ict-omelette.eu/ontologies/ace/mashup_capabilities.owl#";
	private static final String NS_SKOS = "http://www.w3.org/2004/02/skos/core#";

	private OntModel DialogueOntologyModel;
	
	private Dialogue DialogueInstance;
	
	private HttpServletRequest Request;
	private HttpSession Session;
	
	public OntologyBasedAlgorithm(HttpServletRequest Request, HttpSession Session) {
		this.Request = Request;
		this.Session = Session;
	}
	
	@Override
	public void Initialize() {
		DialogueInstance = new Dialogue(Session.getId());
		DialogueInstance.setPreferredLanguage(Request.getParameter("strPreferredLanguage"));
		DialogueInstance.setLocation(Request.getParameter("strLocation"));
		
		loadOntologyModel(Session);
		
		Property propIsAbout = DialogueOntologyModel.getProperty(NS_DIALOGUE + "isAbout");
		Property propHasOption = DialogueOntologyModel.getProperty(NS_DIALOGUE + "hasOption");
		
		for(Iterator i = DialogueOntologyModel.getOntClass(NS_CAPABILITIES + "Needs").listInstances(true); i.hasNext();) {
			Individual need = (Individual) i.next();
			
			for(Iterator j = DialogueOntologyModel.listResourcesWithProperty(propIsAbout, need); j.hasNext();) {
				Resource q = (Resource) j.next();
				Individual qi = DialogueOntologyModel.getIndividual(NS_DIALOGUE + q.getLocalName());
				String label = (qi.getLabel(DialogueInstance.getPreferredLanguage()) != null) ? qi.getLabel(DialogueInstance.getPreferredLanguage()) : qi.getLabel(DEFAULT_LANGUAGE);
				String type = qi.getRDFType().getLocalName();
										
				Question question = new Question(qi.getLocalName(), label);
				QuestionInput input = new QuestionInput(type, qi.getLocalName());
				
				for(Iterator h = qi.listProperties(propHasOption); h.hasNext();) {
					Statement o = (Statement) h.next();
					String optionName = o.getResource().getLocalName();
					if(optionName != null)
						// @TODO: Label fuer options auswerten
						input.addOption(optionName);
				}
				logger.info(need.getLocalName() + " - " + label + " - " + type);
				question.addInput(input);
				DialogueInstance.addQuestionToQueue(question);
			}
		}
	}

	@Override
	//TODO: Rename return class into Question
	public Dialogue GetNextQuestion() {
		if(DialogueInstance.getQuestionQueueSize() > 0)
			DialogueInstance.retrieveCurrentQuestion();

		return DialogueInstance;
	}

	@Override
	public void ProcessAnswer(Map<String,String[]> userInput) {

		Set<String> parameterNames = userInput.keySet();
		for(String name : parameterNames) {
			DialogueInstance.getCurrentQuestion().saveAnswer(name, userInput.get(name)[0]);
		}
		
		DialogueInstance.moveQuestionToAnswers(DialogueInstance.getCurrentQuestion());

	}


	private OntModel loadOntologyModel (HttpSession Session) {
		
//		Problem?: #Functionalities are inferred to as #Needs due to the two #Questions isAbout #Functionality
//				This even occurs when looking only for direct RDFTypes and Instances as well as using the default
//				'weak' reasoner
//		Therefore for the moment no reasoning applied.
//		
//		for(Iterator i = DialogueOntology.getOntClass(CapabilitiesNameSpace + "Needs").listInstances(true); i.hasNext();) {
//			Individual q = (Individual) i.next();
//			for(Iterator j = q.listRDFTypes(true); j.hasNext();) {
//				Resource t = (Resource) j.next();
//				logger.info(q.getLocalName() + " - " + t.getLocalName());
//			}
//		}
		
		DialogueOntologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		
		OntDocumentManager DocumentManager = DialogueOntologyModel.getDocumentManager();
		DocumentManager.addAltEntry("http://www.ict-omelette.eu/ontologies/ace/mashup_capabilities.owl", "file:" + Session.getServletContext().getRealPath(FILE_PATH_CAPABILITIES));
		DocumentManager.addAltEntry("http://www.ict-omelette.eu/ontologies/ace/dialogue_s.owl", "file:" + Session.getServletContext().getRealPath(FILE_PATH_DIALOGUE));
		DocumentManager.addAltEntry("http://www.w3.org/2004/02/skos/core", "file:" + Session.getServletContext().getRealPath(FILE_PATH_SKOS));
		
		InputStream DialogueOntologyFile = FileManager.get().open(Session.getServletContext().getRealPath(FILE_PATH_DIALOGUE));
		DialogueOntologyModel.read(DialogueOntologyFile, "http://www.ict-omelette.eu/ontologies/ace/dialogue_s.owl");
			
		return DialogueOntologyModel;
		
	}

	@Override
	public Boolean isFinished() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
