<%@page import="com.teamsankya.mapgoogleapi.jaxb.GeocodeResponse"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%
	GeocodeResponse geocodeResponse = (GeocodeResponse) session.getAttribute("geocodeResponse");
%>
</head>
<body>
	<%
		if (geocodeResponse != null) {
	%>
	<table>
		<thead>
			<tr>
				<td>Long Name :</td>
				<td>Short Name :</td>
				<td>Latitude :</td>
				<td>Longitude :</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><%=geocodeResponse.getResult().getAddressComponent().get(0).getLongName()%>
					</td>
			</tr>
			<tr>
				<td><%=geocodeResponse.getResult().getAddressComponent().get(0).getShortName()%>
				</td></tr>
			<tr>
				<td><%=geocodeResponse.getResult().getGeometry().getLocation().getLat()%>
				</td></tr>
			<tr>
				<td><%=geocodeResponse.getResult().getGeometry().getLocation().getLng()%>
				</td></tr>
		</tbody>
	</table>
	<%
		} else {
	%>
	Error Happened!!!!!
	<%
		}
	%>
<form action="./send" method="post">
	Enter Your Mobile Number :
	<input type="text" name="mobileNumber" />
	<button type="submit">send message</button>
</form>	

	<form action="./sendMail" method="post">
		Enter Email Id : <input type="text" name="emailId" />
		<button type="submit">send mail</button>
	</form>
</body>
</html>