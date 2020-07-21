<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table onselectstart="return false" οnselect="document.selection.empty()" id="luruInfoShowTable" class="table table-striped">
    <caption>已录入的信息(共 ${list.size()} 个结果 <span style="color: red;"></span> )<a onclick="flushluruInfo()"  style="margin-left: 20px; cursor: pointer;">清空信息</a></caption>
    <thead>
    <tr>
        <th class="col-lg-2">电话号码</th>
        <th class="col-lg-2">姓名</th>
        <th class="col-lg-1">性别</th>
        <th class="col-lg-3">身份证号</th>
        <th class="col-lg-4">公司</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${list}" varStatus="index">
    <tr id="user-id-${user.userId}" style="cursor: pointer;"  >
        <td class="col-lg-2">${user.tel}</td>
        <td class="col-lg-2">${user.name}</td>
        <c:if test="${user.gender==1}"><td class="col-lg-1">男</td></c:if>
        <c:if test="${user.gender==0}"><td class="col-lg-1">女</td></c:if>
        <td class="col-lg-3">${user.IDCard}</td>
        <td class="col-lg-4">${user.company}</td>
    </tr>
    </c:forEach>
    </tbody>
</table>