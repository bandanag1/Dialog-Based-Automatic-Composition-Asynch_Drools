package edu.vsr.massachusetts.algorithms.drools;

public class Topic2 {
	
	private String topic;
	
	//private String visualizationForTopic;
	
	private String[] visualizationsForTopic;
	
	private String typeOfChart;
	
	private String userGivenChartName;
	
	public Topic2() {
		
	}
	
	public Topic2(String topic) {
		this.topic = topic;		
	}
	
	public String getTopic() {
		return this.topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	/*public String getVisualizationForTopic() {
		return visualizationForTopic;
	}

	public void setVisualizationForTopic(String visualizationForTopic) {
		this.visualizationForTopic = visualizationForTopic;
	}*/
	
	public String[] getVisualizationsForTopic() {
		return visualizationsForTopic;
	}

	public void setVisualizationsForTopic(String[] visualizationsForTopic) {
		this.visualizationsForTopic = visualizationsForTopic;
	}
	
	public String getTypeOfChart() {
		return typeOfChart;
	}

	public void setTypeOfChart(String typeOfChart) {
		this.typeOfChart = typeOfChart;
	}

	public String getUserGivenChartName() {
		return userGivenChartName;
	}

	public void setUserGivenChartName(String userGivenChartName) {
		this.userGivenChartName = userGivenChartName;
	}
}