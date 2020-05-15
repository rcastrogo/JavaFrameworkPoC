<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <base href="">
  <title>Java App</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="./assets/css/w3.css">
  <link rel="stylesheet" href="./assets/css/styles.css">
  <link rel="icon" type="image/x-icon" href="./favicon.ico">
  <link rel="manifest" href="./manifest.json">

  <style type=text/css>

  </style>

  <script>
    
  </script>

</head>

<body>
	<div id="notificationPanel"></div>
	<div id="appContent">
		<header id="appHeader" class="w3-container w3-teal">
			<h1>Java App</h1>
		</header>
		<nav id="appMenu" class="w3-bar w3-black">
			<a href="#" class="w3-bar-item w3-button selected">Inicio</a> 
			<a href="#" class="w3-bar-item w3-button w3-right">?</a>
		</nav>
		<section id="app-content-container" class="w3-container" style="min-height: 440px; padding: 0px 0px 60px;">
			<div class="w3-container w3-center w3-animate-left"	style="padding-top: 60px;">			
				

			<h2 align="center">Login Validation</h2>
			
			<form method="POST" action="usuarios">
				Enter User Name <input type="text" name="t1"> <br>
				Enter Password  <input type="text" name="t2"> <br>
				<input type="submit" value="SEND">
				<input type="reset" value="CLEAR">
			</form> 

				
				
			</div>
		</section>
		<footer id="appFooter" class="w3-container w3-teal w3-center">
			<p>© Rafael Castro Gómez, 2020</p>
		</footer>
	</div>
</body>
</html>