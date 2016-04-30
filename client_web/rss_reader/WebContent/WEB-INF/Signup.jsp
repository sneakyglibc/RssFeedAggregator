<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Signup</title>
<link type="text/css" rel="stylesheet" href="form.css" />
</head>
<body>
<div class="element">
	<form method="post" action="signup">
		<fieldset>
			<legend>Signup</legend>

			<label for="email">Email Adress <span class="requis">*</span></label>
			<input type="text" id="email" name="email" value="<c:out value="${param.email}"/>" size="20" maxlength="60"/>
			<span class="erreur">${form.erreurs['email']}</span>
			<br/>
			
			<label for="motdepasse">Password <span class="requis">*</span></label>
			<input type="password" id="password" name="password" value="" size="20" maxlength="20"/>
			<span class="erreur">${form.erreurs['password']}</span>
			<br/>
			
			<input type="submit" value="Signup" class="sansLabel" /><br/>
			<p>
				<span class="requis">* : Required</span>
			</p>
			
			<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
		</fieldset>
	</form>
	<c:out value="Already got an account ?" />

	<c:url value="login" var="url_login" />
	<a href="<c:url value="${url_login}" />">Login</a>

</div>
</body>
</html>