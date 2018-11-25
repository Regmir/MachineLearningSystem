<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PerceptronCreation</title>
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
                        <li><a href="createPerceptron">Решатель</a></li>
                        <li><a href="<c:url value="/createAlgo"/>">Алгоритм обучения</a></li>
                        <li><a href="<c:url value="/createTask"/>">Задачу</a></li>
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

<form method="POST" action="<c:url value="/perceptron/addOrEdit/"/>">
    <table class="table information_json">
        <tr><th>Имя</th><td></td><td><input type="text" class="form-control" name="name"  value="${perceptronSolver.name}"></td></tr>
        <tr><th>Входной слой</th><td></td><td><input type="number" min="1" class="form-control" name="neurons" value="${perceptronSolver.layers[0].neuronCount}"></td></tr>
        <tr>
            <th>Внутренние слои</th>
            <th>Активационная функция</th>
            <th>Количество нейронов</th>
            <th></th>
        </tr>
        <c:forEach var="i" begin="1" end="${perceptronSolver.layerCount-2}">
            <tr>
            <td></td>
                <c:if test="${perceptronSolver.layers[i].activationFunction=='SIGMOID'}">
            <td><select attrs class="form-control" name="func" placeholder="Активационная функция">
                <option value="linear" linear>Линейная</option>
                <option selected="selected" sigmoid value="sigmoid">Сигмоидальная</option>
            </select></td>
                </c:if>
                <c:if test="${perceptronSolver.layers[i].activationFunction=='LINEAR'}">
                    <td><select attrs class="form-control" name="func" placeholder="Активационная функция">
                        <option selected="selected" value="linear" linear>Линейная</option>
                        <option  sigmoid value="sigmoid">Сигмоидальная</option>
                    </select></td>
                </c:if>
            <td><input type="number" min="1" class="form-control" name="neurons" value=${perceptronSolver.layers[i].neuronCount}></td>
            <td><span class="btn btn-danger minus pull-right">&ndash;</span></td>
            </tr>
        </c:forEach>
        <tr class="new_perceptron">
            <td></td>
            <td></td>
            <td></td>
            <td><span class="btn btn-success plus pull-right">+</span></td>
        </tr>
        <tr><th>Выходной слой</th>
            <c:if test="${perceptronSolver.layers[perceptronSolver.layerCount-1].activationFunction=='SIGMOID'}">
                <td><select attrs class="form-control" name="func" placeholder="Активационная функция">
                    <option value="linear" linear>Линейная</option>
                    <option selected="selected" sigmoid value="sigmoid">Сигмоидальная</option>
                </select></td>
            </c:if>
            <c:if test="${perceptronSolver.layers[perceptronSolver.layerCount-1].activationFunction=='LINEAR'}">
                <td><select attrs class="form-control" name="func" placeholder="Активационная функция">
                    <option selected="selected" value="linear" linear>Линейная</option>
                    <option  sigmoid value="sigmoid">Сигмоидальная</option>
                </select></td>
            </c:if>
            <td><input type="number" min="1" class="form-control" name="neurons" value="${perceptronSolver.layers[perceptronSolver.layerCount-1].neuronCount}"></td></tr>
        <tr>
            <td><select class="form-control" name="flag" ><option selected="selected" value="new" >Сохранить как новый</option><option  value="old">Изменить существующий</option></select></td>
            <td><input type="hidden" name="id" class="form-control" id="id" value="${perceptronSolver.id}"></td>
        </tr>
        <tr><th><input type="submit" class="form-control" value="<spring:message text="Сохранить"/>"></th></tr>
    </table>
</form>
</body>
<script>
    // формируем новые поля
    jQuery('.plus').click(function(){
        jQuery('.new_perceptron').before(
            '<tr>' +
            '<td></td>'+
            '<td><select attrs class="form-control" name="func" placeholder="Активационная функция"><option value="linear" linear>Линейная</option><option sigmoid value="sigmoid">Сигмоидальная</option></select></td>'+
            '<td><input type="number" min="1" class="form-control" name="neurons" placeholder="Количество нейронов"></td>' +
            '<td><span class="btn btn-danger minus pull-right">&ndash;</span></td>' +
            '</tr>'
        );
    });
    // on - так как элемент динамически создан и обычный обработчик с ним не работает
    jQuery(document).on('click', '.minus', function(){
        jQuery( this ).closest( 'tr' ).remove(); // удаление строки с полями
    });// JavaScript Document
</script>
</html>