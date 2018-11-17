<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MLS</title>
    <!-- <link href="css/bootstrap.css" rel="stylesheet" type="text/css"> -->
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
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
            <a class="navbar-brand" href="/">Главная страница</a></div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="defaultNavbar1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="<c:url value="/show/perceptron"/>">Решатели<span class="sr-only">(current)</span></a></li>
                <li><a href="<c:url value="/show/backpropagation"/>">Алгоритмы обучения</a></li>
                <li><a href="<c:url value="/show/task"/>">Задачи</a></li>
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Создать<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href=createPerceptron>Решатель</a></li>
                        <li><a href="<c:url value="/createAlgo"/>">Алгоритм обучения</a></li>
                        <li><a href="createTask">Задачу</a></li>
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
<form method="POST" action="<c:url value="/learn"/>">
    <table class="table information_json">
        <tr>
            <th>Решатель</th>
            <th>Задача</th>
            <th>Алгоритм обучения</th>
            <th></th>
        </tr>
        <tr>
            <td><select class="form-control" name="solver" placeholder="Решатель">
                <c:forEach items="${solvers}" var="obj1">
                    <option value="${obj1.name}">${obj1.name}</option>
                </c:forEach>
            </select>
            </td>
            <td><select  class="form-control" name="task" placeholder="Задача">
                <c:forEach items="${tasks}" var="obj2">
                    <option value="${obj2.name}">${obj2.name}</option>
                </c:forEach>
            </select>
            </td>
            <td><select  class="form-control" name="algo" placeholder="Алгоритм обучения">
                <c:forEach items="${algo}" var="obj3">
                    <option value="${obj3.name}">${obj3.name}</option>
                </c:forEach>
            </select>
            </td>
        </tr>
        <tr><th><input type="submit" class="form-control" value="Обучить"></th></tr>
    </table>
</form>
<br/>
</body>
</html>