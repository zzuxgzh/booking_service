<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table onselectstart="return false" οnselect="document.selection.empty()" id="searchTicketShowTable" class="table table-striped">
    <caption>查询到的航班( 共 ${num} 个结果<span style="color: red;"></span> )</caption>
    <thead>
    <tr>
        <th>航班号</th>
        <th>出发机场</th>
        <th>目的机场</th>
        <th>起飞时间</th>
        <th>到达时间</th>
        <th>座位数</th>
        <th>票价</th>
    </tr>
    </thead>
    <tbody>
<c:forEach var="flight" items="${flights}" varStatus="index">
    <tr id="flight-id-${flight.id}" style="cursor: pointer;" title="点击查看详细信息" onclick="flightShowInfo(this)" >
        <td>${flight.id}</td>
        <td>${flight.startname}</td>
        <td>${flight.endname}</td>
        <td>${flight.starttime}</td>
        <td>${flight.preendtime}</td>
        <td>${flight.economyClass}-${flight.businessClass}-${flight.firstClass}</td>
        <td>${flight.oprice}元</td>
    </tr>
    <tr  >
        <td colspan="7">
            <div id="flight-id-${flight.id}-showinfo" style="display: none;" style="text-align: center;">
                出发地区：${flight.startpos}&nbsp;&nbsp;${flight.startname}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                到达地区：${flight.endpos}&nbsp;&nbsp;${flight.endname}<br /><br />
                经济舱： ${flight.economyClass} 座&nbsp;&nbsp;${flight.oprice} 元&nbsp;&nbsp;&nbsp;&nbsp;
                <input style="width: 100px;" type="button" onclick="gotoBuyTicket('economy-${flight.id}')" class="btn btn-primary" value="预订" />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                商务舱： ${flight.businessClass} 座&nbsp;&nbsp;${flight.oprice*1.5} 元&nbsp;&nbsp;&nbsp;&nbsp;
                <input style="width: 100px;" type="button" onclick="gotoBuyTicket('business-${flight.id}')" class="btn btn-primary" value="预订" />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                头等舱： ${flight.firstClass} 座&nbsp;&nbsp;${flight.oprice*2.25} 元&nbsp;&nbsp;&nbsp;&nbsp;
                <input style="width: 100px;" type="button" onclick="gotoBuyTicket('first-${flight.id}')" class="btn btn-primary" value="预订" />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

            </div>
        </td>
    </tr>
</c:forEach>
    </tbody>
</table>