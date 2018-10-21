<%--
  Created by IntelliJ IDEA.
  User: arteo
  Date: 21.10.2018
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Perceptron</title>
</head>
<body>
<h1>Add Perceptron</h1>

<c:url var="addAction" value="/objects/add"/>

<%--@elvariable id="obj" type="dataBaseManagement.model.ObjectFromDB"--%>
<form:form action="${addAction}" commandName="obj">
    <table>
        <c:if test="${empty obj.name}">
            <tr>
                <td>
                    <form:label path="id">
                        <spring:message text="ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="id" readonly="true" size="8" disabled="true"/>
                    <form:hidden path="id"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="name">
                    <spring:message text="name"/>
                </form:label>
            </td>
            <td>
                <form:input path="name"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="type">
                    <spring:message text="type"/>
                </form:label>
            </td>
            <td>
                <form:input path="type"/>
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="parameters">
                    <spring:message text="parameters"/>
                </form:label>
            </td>
            <td>
                <form:input path="parameters"/>
            </td>
        </tr>
        <tr>
            <input type="submit" value="<spring:message text="Add Object"/>"/>
        </tr>
    </table>
</form:form>
</body>
</html>
