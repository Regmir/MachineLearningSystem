<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Objects Page</title>

    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg .tg-4eph {
            background-color: #f9f9f9
        }
    </style>
</head>
<body>
<a href="../../MLS">Back to main menu</a>

<br/>
<br/>

<h1>Object List</h1>

<c:if test="${!empty listObjects}">
    <table class="tg">
        <tr>
            <th width="80">ID</th>
            <th width="120">Name</th>
            <th width="120">Type</th>
            <th width="60">Edit</th>
            <th width="60">Delete</th>
        </tr>
        <c:forEach items="${listObjects}" var="obj">
            <tr>
                <td>${obj.id}</td>
                <td><a href="objectsfromdbdata/${obj.id}" target="_blank">${obj.name}</a></td>
                <td>${obj.type}</td>
                <td><a href="<c:url value='/edit/${obj.id}'/>">Edit</a></td>
                <td><a href="<c:url value='/remove/${obj.id}'/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<a href="AddPerceptron" target="_blank">Add Perceptron</a>

<c:url var="addAction" value="/objects/add"/>

<%--@elvariable id="obj" type="dataBaseManagement.model.ObjectFromDB"--%>
<form:form action="${addAction}" commandName="obj">
    <table>
        <c:if test="${!empty obj.name}">
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
            <td colspan="2">
                <c:if test="${!empty obj.name}">
                    <input type="submit"
                           value="<spring:message text="Edit Object"/>"/>
                </c:if>
                <c:if test="${empty obj.name}">
                    <input type="submit"
                           value="<spring:message text="Add Object"/>"/>
                </c:if>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
