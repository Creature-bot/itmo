<%@ page import="se.ifmo.lab2WEB.entity.ResultStorage" %>
<%@ page import="se.ifmo.lab2WEB.entity.HitResult" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="se.ifmo.lab2WEB.service.fromatter.BigDecimalFormatter" %>
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
        <div class="graph-container">
            <canvas id="graphCanvas"></canvas>
        </div>
        <form action="controller" method="GET" id="main-form">
            <div class="controls">
                <div class="input-group">
                    <label>X:</label>
                    <div class="radio-group">
                        <input type="radio" id="x-4" name="x-visible" value="-4" required>
                        <label for="x-4">-4</label>
                        <input type="radio" id="x-3" name="x-visible" value="-3">
                        <label for="x-3">-3</label>
                        <input type="radio" id="x-2" name="x-visible" value="-2">
                        <label for="x-2">-2</label>
                        <input type="radio" id="x-1" name="x-visible" value="-1">
                        <label for="x-1">-1</label>
                        <input type="radio" id="x0" name="x-visible" value="0">
                        <label for="x0">0</label>
                        <input type="radio" id="x1" name="x-visible" value="1">
                        <label for="x1">1</label>
                        <input type="radio" id="x2" name="x-visible" value="2">
                        <label for="x2">2</label>
                        <input type="radio" id="x3" name="x-visible" value="3">
                        <label for="x3">3</label>
                        <input type="radio" id="x4" name="x-visible" value="4">
                        <label for="x4">4</label>
                    </div>
                    <input type="hidden" id="x" name="x">
                </div>
                <div class="input-group">
                    <label for="y-input">Y:</label>
                    <input type="text" id="y-input" name="y" placeholder="Введите Y от -5 до 5" required>
                </div>
                <div class="input-group">
                    <label>R:</label>
                    <div class="radio-group" id="r-checkbox-group">
                        <input type="checkbox" id="r1-cb" name="r" value="1" class="r-checkbox">
                        <label for="r1-cb">1</label>
                        <input type="checkbox" id="r2-cb" name="r" value="2" class="r-checkbox">
                        <label for="r2-cb">2</label>
                        <input type="checkbox" id="r3-cb" name="r" value="3" class="r-checkbox">
                        <label for="r3-cb">3</label>
                        <input type="checkbox" id="r4-cb" name="r" value="4" class="r-checkbox">
                        <label for="r4-cb">4</label>
                        <input type="checkbox" id="r5-cb" name="r" value="5" class="r-checkbox">
                        <label for="r5-cb">5</label>
                    </div>
                </div>
                <div class="button-group">
                    <button type="button" id="clear-btn">Очистить</button>
                </div>
                <button type="submit" id="submit-btn">Отправить</button>
            </div>
        </form>
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
                                List<HitResult> reversedHistory = new ArrayList<>(history);
                                reversedHistory.sort((o1, o2) -> o2.getTimeStart().compareTo(o1.getTimeStart()));
                                for (HitResult point : reversedHistory) {
                                    java.time.OffsetDateTime date = point.getTimeStart();
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
                        <td class="time-cell"><%= date.getHour() + 3 %>
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
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="resources/js/decimal.js"></script>
<script type="module" src="resources/js/main.js"></script>
</body>
</html>