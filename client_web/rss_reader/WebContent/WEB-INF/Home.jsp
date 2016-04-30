<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Home</title>
<link type="text/css" rel="stylesheet" href="home.css" />
</head>
<body>
	<p class="adress">Your are connected with : ${sessionScope.sessionUser.email}</p>
	<%-- <p>password : ${sessionScope.sessionUser.password}</p> --%>
	<form method="post" action="home">
		<fieldset class="field">
			<legend>Load another RSS Feed</legend>

			<input type="text" id="rssUrl" name="rssUrl" value="" size="50"/>
			<input type="submit" value="Load feed" class="sansLabel"/>

		</fieldset>
	</form>
	<div id="tables">
	<div id="table_flux">
	<table>
		<thead>
			<tr>
				<th>Current Flux</th>
			</tr>
		</thead>

		<tbody>
			<tr>
			<td><p class="_flux">${fluxTitle}</p></td>
			<td><p class="_flux"><a href="${fluxLink}">Link</a></p></td>
			</tr>
		</tbody>
	</table>
	</div>

	<div id="table_items">
	<table>
		<thead>
			<tr>
				<th>Feeds</th>

				<th> </th>
			</tr>
		</thead>

		<tbody>
			<tr>
				<td class="left"><c:forEach var="titles" items="${titles}">
					<p class="_items"><c:out value="${titles}" escapeXml="false"/></p>
					</c:forEach></td>
					
				<td class="right"><c:forEach var="links" items="${links}">
					<p class="_items"><a href="${links}">Link</a></p>
					</c:forEach></td>
			</tr>
		</tbody>
	</table>
	</div>
	</div>
</body>
</html>