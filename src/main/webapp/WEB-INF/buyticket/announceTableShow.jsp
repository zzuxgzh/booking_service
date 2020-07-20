<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table onselectstart="return false" οnselect="document.selection.empty()" id="announceInfoShowTable" class="table table-striped" style="margin-top: 20px;">
    <caption>已发布公告( ${list.size()}<span style="color: red;"></span> )个</caption>
    <thead>
    <tr>
        <th class="col-lg-2">时间</th>
        <th class="col-lg-2">机场</th>
        <th class="col-lg-6">内容</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="announce" items="${list}" varStatus="index">
    <tr>
        <td class="col-lg-2"><fmt:formatDate value="${announce.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td class="col-lg-2">${announce.name}</td>
        <td class="col-lg-6">${announce.content}</td>
    </tr>
    </c:forEach>
    </tbody>
</table>