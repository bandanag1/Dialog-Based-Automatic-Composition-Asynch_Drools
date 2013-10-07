package edu.vsr.massachusetts.dialogue.agent.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;

import edu.vsr.massachusetts.dialogue.agent.model.Dashboard;
import edu.vsr.massachusetts.dialogue.agent.model.Widget;

import info.aduna.iteration.CloseableIteration;

@Controller
public class MatchicianController {
	
	// https://vsr-web.informatik.tu-chemnitz.de/omr/components/sparql
	// private static final String SPARQL_ENDPOINT = "http://astrid.informatik.tu-chemnitz.de:8080/useekm-http-workbench/repositories/useekm/";
	private static final String SESAME_SERVER = "http://astrid.informatik.tu-chemnitz.de:8080/useekm-http-server";
	private static final String SESAME_REPOSITORY = "useekm";
	
	private static final Logger logger = LoggerFactory.getLogger(MatchicianController.class);

	@RequestMapping(value = "/Matchician")
	public ModelAndView Matchician (HttpSession Session) {
		
		String description = "news";
		
		String query = "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n";
		query += "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>\n";
		query += "PREFIX ns0:<http://www.w3.org/2004/02/skos/core#>\n";
		query += "PREFIX ns1:<http://www.ict-omelette.eu/schema.rdf#>\n";
		query += "PREFIX ns2:<http://www.netvibes.com/#>\n";
		query += "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		query += "PREFIX netvibes:<http://www.netwibes.com/#>\n";
		query += "PREFIX purl:<http://purl.org/dc/elements/1.1/>\n";
		query += "PREFIX dc:<http://purl.org/dc/elements/1.1/>\n\n";
		query += "SELECT ?widget ?widgetName ?installAmountDec ?widgetDescription\n";
		query += "WHERE  {\n";
		query += "	?widget rdf:type ns1:Widget .\n";
		query += "	?widget dc:description ?widgetDescription .\n";
		query += "	?widget rdfs:label ?widgetName .\n";
		query += "	?widget ns2:installs ?installAmount .\n";
		query += "	BIND(xsd:decimal(?installAmount) as ?installAmountDec)\n";
		query += "	FILTER regex(?widgetDescription, \"" + description + "\", \"i\")";
		query += "}";
		query += "ORDER BY DESC(?installAmountDec)";
		query += "LIMIT 10";
		
		/**
		 * Querying with JENA throws com.hp.hpl.jena.query.QueryException:
		 * Endpoint returned Content-Type: application/xml which is not currently
		 * supported for SELECT queries
		 */
//		QueryExecution qe = QueryExecutionFactory.sparqlService(SPARQL_ENDPOINT, query);
//		ResultSet result = qe.execSelect();
//		logger.info(result.toString());
		
		Dashboard d = new Dashboard(Session.getId(), "Test");
		
		Repository Repository = new HTTPRepository(SESAME_SERVER, SESAME_REPOSITORY);
		try {
			Repository.initialize();
			RepositoryConnection con = Repository.getConnection();
			try {
				TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, query);
				TupleQueryResult result = tupleQuery.evaluate();
				try {
					while (result.hasNext()) {
						   BindingSet bindingSet = result.next();
						   Value widget = bindingSet.getValue("widget");
						   Value widgetName = bindingSet.getValue("widgetName");
						   Value widgetDescription = bindingSet.getValue("widgetDescription");
						   Value installAmount = bindingSet.getValue("installAmountDec");
						   
						   logger.info(widgetName.stringValue() + " (" + widget.stringValue() + ") - " + installAmount.stringValue());
						   
						   Widget w = new Widget(widget.stringValue(), widgetName.stringValue(), widgetDescription.stringValue());
						   d.addWidget(w);
					}					
				}
				finally {					
					result.close();
				}
			} catch (OpenRDFException e) {
				e.printStackTrace();
			}
			finally {
				con.close();
			}			
		} catch (RepositoryException e) {
			e.printStackTrace();
		}		
		
		return new ModelAndView("dashboard", "omlDashboard", d);
	}
}
