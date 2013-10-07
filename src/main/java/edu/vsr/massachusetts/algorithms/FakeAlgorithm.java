package edu.vsr.massachusetts.algorithms;

import java.util.Map;

import edu.vsr.massachusetts.dialogue.agent.model.Dialogue;
/*
 * This would be the configuration dialog: 
S (System): Are you looking for some information?
U (User): Yes.
S: On which topics? 
U: Flood levels and social feeds.
S: How do you want to visualize flood levels?
U: On a map and using a chart diagram.
S: Ok. How do you want to visualize social feeds?
U: In a list.
S: Do you need something else?
U: I want to check incidents on public web cams.
S: Ok. Composing workspace... (additional widgets are added later manually and by recommendation to reduce the cognitive load for the reviewers)

Widgets needed as outcome of the config dialogue:
Incident Map
Flood Levels (Chart)
Social Feeds (List)
Remote Cam

PREFIX  om: &lt;http://www.ict-omelette.eu/schema.rdf#&gt;
          PREFIX  dc: &lt;http://purl.org/dc/elements/1.1/&gt;
          PREFIX  rdf: &lt;http://www.w3.org/1999/02/22-rdf-syntax-ns#&gt;
          PREFIX  rdfs: &lt;http://www.w3.org/2000/01/rdf-schema#&gt;
          PREFIX  ctag: &lt;http://commontag.org/ns#&gt;


SELECT ?widget ?title ?desc ?downloadURL ?type ?registryEntry

 WHERE
 { 
?widget rdf:type om:W3CWidget. 
?widget  rdfs:label ?title. 
?widget dc:description ?desc. 
?widget om:hasRegistryEntry ?registryEntry. 
OPTIONAL { ?widget dc:source ?downloadURL }
	          FILTER (
                       regex(?title, "{aaa}") || regex(?desc, "{aaa}")
                  )
  }        
  LIMIT {limit}
  OFFSET {offset}
 */
public class FakeAlgorithm implements IDialogAlgorithm {

	@Override
	public void Initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Dialogue GetNextQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ProcessAnswer(Map<String, String[]> userInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean isFinished() {
		// TODO Auto-generated method stub
		return null;
	}

}
