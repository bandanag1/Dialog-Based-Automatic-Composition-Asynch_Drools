package edu.vsr.massachusetts.algorithms.drools;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import edu.vsr.massachusetts.algorithms.IDialogAlgorithm;
import edu.vsr.massachusetts.dialogue.agent.model.Dialogue;
import edu.vsr.massachusetts.dialogue.agent.model.Question;

public class DroolsBasedAlgorithm implements IDialogAlgorithm {

	
	HttpSession session;
	StatefulKnowledgeSession ksession;
	
	UserInput currentAnswer;
	DroolsState droolsState;
	
	public DroolsBasedAlgorithm(HttpSession session) {		
		this.session = session;
	}
	@Override
	public void Initialize() throws Exception {
		KnowledgeBase kbase = readKnowledgeBase();
		ksession = kbase.newStatefulKnowledgeSession();
		ksession.setGlobal("currentQuestion", new DroolsQuestion());				
		ksession.setGlobal("currentAnswer", new UserInput());		
		droolsState = new DroolsState();
		droolsState.isFinished = false;
		ksession.setGlobal("droolsState", droolsState);
	}

	@Override
	public Dialogue GetNextQuestion() {
		Dialogue nextQuestion = new Dialogue("bla");
		ksession.fireUntilHalt();
		DroolsQuestion currentQuestion = (DroolsQuestion)ksession.getGlobal("currentQuestion");

		Question newQuestion = new Question();
		newQuestion.setId("test");
		newQuestion.setText(currentQuestion.text);
		
		nextQuestion.addQuestionToQueue(newQuestion);
		return nextQuestion;
	}

	@Override
	public void ProcessAnswer(Map<String, String[]> userInput) throws Exception {
		currentAnswer = new UserInput();
		currentAnswer.answer = userInput.get("answer")[0];
		currentAnswer.answers = userInput.get("answer");
		currentAnswer.length = userInput.get("answer").length;
		
		ksession.setGlobal("currentAnswer", currentAnswer);
		ksession.fireUntilHalt();
		
		droolsState = (DroolsState)ksession.getGlobal("droolsState");
	}
	
    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        kbuilder.add(ResourceFactory.newClassPathResource("SampleRulesFile.drl"), ResourceType.DRL);
        
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }
	@Override
	public Boolean isFinished() {
		return droolsState.isFinished;
	}

}
