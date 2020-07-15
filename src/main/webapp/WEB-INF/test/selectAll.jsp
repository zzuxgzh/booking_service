
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tbody style="font-size: 15px;font-family:FangSong,NSimSun,Microsoft YaHei,SimSun; font-weight: bold;">
<!--内容字体和粗细-->
<c:forEach var="user" items="${allUser}" varStatus="index">
    <tr>
            <%--            line-height: 80px 与单元格高度对应  --%>
        <td>${index.index+1}</td>
        <td id="${user.id}2">${user.id}</td>
        <td>${user.myAge}</td>
    </tr>
</c:forEach>
</tbody>

