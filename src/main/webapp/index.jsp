<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MLS</title>
    <!-- <link href="css/bootstrap.css" rel="stylesheet" type="text/css"> -->
    <link href="resources/css/bootstrap-3.3.7.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#defaultNavbar1" aria-expanded="false"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
      <a class="navbar-brand" href="">Главная страница</a></div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="defaultNavbar1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Решатели<span class="sr-only">(current)</span></a></li>
        <li><a href="#">Алгоритмы обучения</a></li>
        <li><a href="#">Задачи</a></li>
        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Создать<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href=createPerceptron>Решатель</a></li>
            <li><a href="#">Алгоритм обучения</a></li>
            <li><a href="#">Задачу</a></li>
          </ul>
        </li>
      </ul>
      <form class="navbar-form navbar-left" role="search">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Имя объекта">
        </div>
        <button type="submit" class="btn btn-default">Поиск</button>
      </form>
    </div>
    <!-- /.navbar-collapse -->
  </div>
  <!-- /.container-fluid -->
</nav>
<h3>Machine Learning System</h3>
<br/>
<a href="<c:url value="/objects"/>" target="_blank">Objects list</a>
<br/>
</body>
<script src="resources/js/jquery-1.11.3.min.js"></script>
<script src="resources/js/bootstrap.js"></script>
</html>