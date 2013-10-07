<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
	<head>
		<title>ICT-OMELETTE DialogueAgentServlet</title>
	</head>
	<body>
		<p>${omlDialog.currentQuestion.text}</p>
		<select name="${omlDialog.currentQuestion.id}">
			<c:forEach var="answer" items="${omlDialog.currentQuestion.expectedAnswers}">
			<option value="${answer.text}">${answer.text}</option>
			</c:forEach>
		</select>
	</body>
</html>