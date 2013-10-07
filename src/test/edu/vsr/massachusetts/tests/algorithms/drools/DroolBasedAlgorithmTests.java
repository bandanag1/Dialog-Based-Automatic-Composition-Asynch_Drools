package edu.vsr.massachusetts.tests.algorithms.drools;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;

import edu.vsr.massachusetts.algorithms.IDialogAlgorithm;
import edu.vsr.massachusetts.algorithms.drools.DroolsBasedAlgorithm;
import edu.vsr.massachusetts.dialogue.agent.model.Dialogue;


public class DroolBasedAlgorithmTests {
	
	@Test
	public void testDroolsBasedAlgorithmWorksCorrectly() throws Exception {
		HttpSession session = new MockHttpSession();
		IDialogAlgorithm dialogAlgorithm = new DroolsBasedAlgorithm(session);
		dialogAlgorithm.Initialize();
		
		// TODO: Bandana - Check and put right condition for assertEquals()
		// TODO: Bandana - Check and put right condition for assertTrue()
		
		Dialogue nextQuestion = dialogAlgorithm.GetNextQuestion();		
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("First question is wrong.", "Are you looking for information (y/n)?", nextQuestion.getCurrentQuestion().getText());
		Map<String,String[]> userInput = new HashMap<String,String[]>();
		userInput.put("answer",new String[] {"yes"});
		dialogAlgorithm.ProcessAnswer(userInput);
	
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("Second question is wrong.", "On which topics (topic1/topic2/topic3) - <Enter maximTopic1topics>?", nextQuestion.getCurrentQuestion().getText());
		userInput.put("answer",new String[] {"flood/social feeds/incidence report"});
		dialogAlgorithm.ProcessAnswer(userInput);

		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Third question is wrong (not 'How do you want to visualize...')", nextQuestion.getCurrentQuestion().getText().startsWith("How do you want to visualize"));
		//userInput.put("answer",new String[] {"map"});
		//userInput.put("answer",new String[] {"chart"});
		//userInput.put("answer",new String[] {"table"});
		//userInput.put("answer",new String[] {"webcam"});
		//userInput.put("answer",new String[] {"list"});
		userInput.put("answer",new String[] {"map", "webcam", "table"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Fourth question is wrong (not 'How do you want to visualize...')", nextQuestion.getCurrentQuestion().getText().startsWith("How do you want to visualize"));
		//userInput.put("answer",new String[] {"map"});
		//userInput.put("answer",new String[] {"chart"});
		//userInput.put("answer",new String[] {"table"});
		//userInput.put("answer",new String[] {"webcam"});
		//userInput.put("answer",new String[] {"list"});
		userInput.put("answer",new String[] {"webcam", "list", "table", "chart"});
		dialogAlgorithm.ProcessAnswer(userInput);

		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Fifth question is wrong (not 'Which type of chart you are looking for...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which type of chart you are looking for"));
		userInput.put("answer",new String[] {"pieChart"});
		//userInput.put("answer",new String[] {"lineChart"});
		//userInput.put("answer",new String[] {"barChart"});
		//userInput.put("answer",new String[] {"histogram"});
		//userInput.put("answer",new String[] {"areaChart"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Sixth question is wrong (not 'How do you want to visualize...')", nextQuestion.getCurrentQuestion().getText().startsWith("How do you want to visualize"));
		//userInput.put("answer",new String[] {"map"});
		//userInput.put("answer",new String[] {"chart"});
		//userInput.put("answer",new String[] {"table"});
		//userInput.put("answer",new String[] {"webcam"});
		//userInput.put("answer",new String[] {"list"});
		userInput.put("answer",new String[] {"chart", "list", "map", "table", "webcam"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Seventh question is wrong (not 'Which type of chart you are looking for...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which type of chart you are looking for"));
		userInput.put("answer",new String[] {"otherChart"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("Eighth question is wrong.", "Please provide the name of the chart you would prefer?", nextQuestion.getCurrentQuestion().getText());
		userInput.put("answer",new String[] {"gantChart"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("Ninth question is wrong.", "Do you need anything else (y/n)?", nextQuestion.getCurrentQuestion().getText());
		userInput.put("answer",new String[] {"yes"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("Tenth question is wrong.", "Do you need any communication tools (y/n)?", nextQuestion.getCurrentQuestion().getText());
		userInput.put("answer",new String[] {"yes"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Eleventh question is wrong (not 'Which type of communication tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which type of communication tool would you prefer"));		
		userInput.put("answer",new String[] {"telecommunication"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		
		//
		// If Communication tool is telecommunication - Begin
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Twelfth question is wrong (not 'Which mode of telecommunication you would prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which mode of telecommunication you would prefer"));		
		userInput.put("answer",new String[] {"telephoneConference"});
		//userInput.put("answer",new String[] {"videoConference"});
		//userInput.put("answer",new String[] {"telephoneCall"});
		//userInput.put("answer",new String[] {"videoCall"});
		//userInput.put("answer",new String[] {"mmsMessaging"});
		//userInput.put("answer",new String[] {"smsMessaging"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		// If Communication tool is telecommunication - End
		
		
		//
		// If Communication tool is collaboration - Begin
		
		/*
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Eleventh question is wrong (not 'Which type of communication tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which type of communication tool would you prefer"));		
		userInput.put("answer",new String[] {"collaboration"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Twelfth question is wrong (not 'Which collaboration tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which collaboration tool would you prefer"));		
		userInput.put("answer",new String[] {"textEditing"});
		//userInput.put("answer",new String[] {"drawing"});
		dialogAlgorithm.ProcessAnswer(userInput);		
		*/
		
		
		/*
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Eleventh question is wrong (not 'Which type of communication tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which type of communication tool would you prefer"));		
		userInput.put("answer",new String[] {"collaboration"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Twelfth question is wrong (not 'Which collaboration tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which collaboration tool would you prefer"));		
		userInput.put("answer",new String[] {"others"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("Thirteenth question is wrong.", "Please provide the name of the collaboration tool you would prefer?", nextQuestion.getCurrentQuestion().getText());		
		userInput.put("answer",new String[] {"blogs"});
		dialogAlgorithm.ProcessAnswer(userInput);
		*/
		
		// If Communication tool is collaboration - End
		
		
		//
		// If Communication tool is instantMessaging - Begin
		
		/*
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Eleventh question is wrong (not 'Which type of communication tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which type of communication tool would you prefer"));		
		userInput.put("answer",new String[] {"instantMessaging"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Eleventh question is wrong (not 'Which instant messaging tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which instant messaging tool would you prefer"));		
		userInput.put("answer",new String[] {"twitter"});
		//userInput.put("answer",new String[] {"facebook"});
		dialogAlgorithm.ProcessAnswer(userInput);		
		*/
				
		/*
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Eleventh question is wrong (not 'Which type of communication tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which type of communication tool would you prefer"));		
		userInput.put("answer",new String[] {"instantMessaging"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Eleventh question is wrong (not 'Which instant messaging tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which instant messaging tool would you prefer"));		
		userInput.put("answer",new String[] {"chat"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("Twelfth question is wrong.", "Please provide the name of the chat tool you would prefer?", nextQuestion.getCurrentQuestion().getText());				
		userInput.put("answer",new String[] {"gtalk"});
		dialogAlgorithm.ProcessAnswer(userInput);
		*/
		
		/*
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Eleventh question is wrong (not 'Which type of communication tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which type of communication tool would you prefer"));		
		userInput.put("answer",new String[] {"instantMessaging"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Eleventh question is wrong (not 'Which instant messaging tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which instant messaging tool would you prefer"));		
		userInput.put("answer",new String[] {"others"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("Twelfth question is wrong.", "Please provide the name of the instant messaging tool you would prefer?", nextQuestion.getCurrentQuestion().getText());				
		userInput.put("answer",new String[] {"IPMessenger"});
		dialogAlgorithm.ProcessAnswer(userInput);
		*/
		
		// If Communication tool is instantMessaging - End
		
		
		//
		// If Communication tool is others - Begin
		
		/*
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Eleventh question is wrong (not 'Which type of communication tool would you prefer...')", nextQuestion.getCurrentQuestion().getText().startsWith("Which type of communication tool would you prefer"));		
		userInput.put("answer",new String[] {"others"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("Twelfth question is wrong.", "Please provide the name of the communication tool you would prefer?", nextQuestion.getCurrentQuestion().getText());				
		userInput.put("answer",new String[] {"pigeons"});
		dialogAlgorithm.ProcessAnswer(userInput);
		*/
		
		// If Communication tool is others - End
		
		
		//
		// Do you want to compose the workspace now [Yes] - Begin
		
		/*
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("Fourteenth question is wrong.", "Do you want to compose the workspace now (y/n)?", nextQuestion.getCurrentQuestion().getText());				
		userInput.put("answer",new String[] {"yes"});
		dialogAlgorithm.ProcessAnswer(userInput);
		*/
		
		// Do you want to compose the workspace now [Yes] - End
		
		
		//
		// Do you want to compose the workspace now [No] - Begin
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("Fourteenth question is wrong.", "Do you want to compose the workspace now (y/n)?", nextQuestion.getCurrentQuestion().getText());				
		userInput.put("answer",new String[] {"no"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Fifteenth question is wrong (not 'Ok, then are you looking for some...')", nextQuestion.getCurrentQuestion().getText().startsWith("Ok, then are you looking for some"));		
		//userInput.put("answer",new String[] {"entertainment"});
		userInput.put("answer",new String[] {"creativity"});
		dialogAlgorithm.ProcessAnswer(userInput);		
		
		/*
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertEquals("Fourteenth question is wrong.", "Do you want to compose the workspace now (y/n)?", nextQuestion.getCurrentQuestion().getText());				
		userInput.put("answer",new String[] {"no"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Fifteenth question is wrong (not 'Ok, then are you looking for some...')", nextQuestion.getCurrentQuestion().getText().startsWith("Ok, then are you looking for some"));		
		userInput.put("answer",new String[] {"others"});
		dialogAlgorithm.ProcessAnswer(userInput);
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertNotNull(nextQuestion.retrieveCurrentQuestion());
		Assert.assertTrue("Sixteenth question is wrong (not 'Please mention here, what else you are looking for...')", nextQuestion.getCurrentQuestion().getText().startsWith("Please mention here, what else you are looking for"));		
		userInput.put("answer",new String[] {"news"});
		dialogAlgorithm.ProcessAnswer(userInput);
		*/
		
		// Do you want to compose the workspace now [No] - End
		
		
		//
		// Ok, composing the workspace - Begin
		
		nextQuestion = dialogAlgorithm.GetNextQuestion();
		Assert.assertTrue("Algorithm is not finished yet", dialogAlgorithm.isFinished());
		
		// Ok, composing the workspace - End
	}

}

