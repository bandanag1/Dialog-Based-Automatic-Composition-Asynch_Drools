package edu.vsr.massachusetts.algorithms;

import java.util.Map;

import edu.vsr.massachusetts.dialogue.agent.model.Dialogue;
import edu.vsr.massachusetts.dialogue.agent.model.Question;

public interface IDialogAlgorithm {
	public void Initialize() throws Exception;
	public Dialogue GetNextQuestion();
	public void ProcessAnswer(Map<String,String[]> userInput) throws Exception;
	public Boolean isFinished();
}
