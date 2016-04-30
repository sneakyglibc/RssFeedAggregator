<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Login</title>
<link type="text/css" rel="stylesheet" href="form.css" />
</head>
<body>
<div class="element">
	<form method="post" action="login">
		<fieldset>
			<legend>Login</legend>

			<label for="email">Email Adress <span class="requis">*</span></label>
			<input type="text" id="email" name="email" value="<c:out value="${user.email}"/>" size="20" maxlength="60"/>
            <span class="erreur">${form.erreurs['email']}</span>
			<br/>

			<label for="motdepasse">Password <span class="requis">*</span></label>
			<input type="password" id="password" name="password" value="" size="20" maxlength="20"/>
			<span class="erreur">${form.erreurs['password']}</span>
			<br/>
			
			<input type="submit" value="Login" class="sansLabel"/><br/>
			
			<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
			
            <%-- Vérification de la présence d'un objet utilisateur en session --%>
            <c:if test="${!empty sessionScope.sessionUser}">
            <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
            <p class="succes">You are connected with : ${sessionScope.sessionUser.email}</p>
            </c:if>
			
			<p>
				<span class="requis">* : Required</span>
			</p>
		</fieldset>
	</form>
	<c:out value="No account yet ?" />

	<c:url value="signup" var="url_signup" />
	<a href="<c:url value="${url_signup}" />">Signup</a>
</div>
</body>
</html>