<%@ page import="se.ifmo.lab2WEB.entity.ResultStorage" %>
<%@ page import="se.ifmo.lab2WEB.entity.HitResult" %>
<%@ page import="java.util.List" %>
<%@ page import="se.ifmo.lab2WEB.service.fromatter.BigDecimalFormatter" %>
<%@ page import="java.time.OffsetDateTime" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Graph Visualization</title>
    <link rel="stylesheet" href="resources/css/file.css">
    <link rel="icon" href="resources/img/favicon.ico" type="image/x-icon">
</head>
<body>
<div class="container">
    <div class="content">
        <div class="student-header">
            ⭐ Кудрявцева Руслана Сергеевна | Группа P3217 | Вариант 3319 ⭐
        </div>
        <div class="gif-wrapper">
            <img src="resources/img/animation-cat.gif" alt="Милая гифка" class="cat-gif">
        </div>
        <div class="controls">
            <form action="index.jsp" method="GET">
                <button id="back-btn">Вернуться на страницу заполнения</button>
            </form>
        </div>
        <div id="results">
            <h3>Результаты:</h3>
            <div class="table-wrapper">
                <table id="results-table">
                    <thead>
                    <tr>
                        <th rowspan="2">X</th>
                        <th rowspan="2">Y</th>
                        <th rowspan="2">R</th>
                        <th rowspan="2">Результат</th>
                        <th colspan="6">Время начала</th>
                        <th rowspan="2">Время выполнения (Сек)</th>
                    </tr>
                    <tr>
                        <th>Год</th>
                        <th>Мес</th>
                        <th>День</th>
                        <th>Час</th>
                        <th>Мин</th>
                        <th>Сек</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        ResultStorage resultStorage = (ResultStorage) application.getAttribute("list");
                        if (resultStorage != null) {
                            List<HitResult> history = resultStorage.getResults();
                            if (history != null) {
                                HitResult point = history.get(history.size() - 1);
                                OffsetDateTime date = point.getTimeStart();
                                String backgroundColor = point.isHitStatus() ? "rgba(0, 255, 0, 0.1)" : "rgba(255, 0, 0, 0.1)";
                                String hitClass = point.isHitStatus() ? "hit" : "miss";
                    %>
                    <tr style="background-color: <%= backgroundColor %>">
                        <td><%= BigDecimalFormatter.formatBigDecimal(point.getX()) %>
                        </td>
                        <td><%= BigDecimalFormatter.formatBigDecimal(point.getY()) %>
                        </td>
                        <td><%= BigDecimalFormatter.formatBigDecimal(point.getR()) %>
                        </td>
                        <td class="<%= hitClass %>"><%= point.isHitStatus() ? "Попал" : "Промах" %>
                        </td>
                        <td class="time-cell"><%= date.getYear() %>
                        </td>
                        <td class="time-cell"><%= date.getMonthValue() %>
                        </td>
                        <td class="time-cell"><%= date.getDayOfMonth() %>
                        </td>
                        <td class="time-cell"><%= date.getHour() + 3%>
                        </td>
                        <td class="time-cell"><%= date.getMinute() %>
                        </td>
                        <td class="time-cell"><%= date.getSecond() %>
                        </td>
                        <td><%= String.format("%.5f", (point.getExecutionTime() / 1000000000.0)) %>
                        </td>
                    </tr>
                    <%

                            }
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
