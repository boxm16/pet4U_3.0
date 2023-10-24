


<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>End Offer</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            input[type="date"]
            {
                font-size:30px;
            }
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    </head>
    <body>
    <center>

        <h1><a href="index.htm">INDEX</a></h1>

        <h1>End Offer For:</h1>


        <h1 >${item.code} : ${item.description}</h1>
        <h1 style="background-color: ${resultColor}">${result}</h1>

        <form action="endOffer.htm" method="POST" >
            <input hidden name="id" type="text" value="${offer.id}">
            <input hidden name="code" type="text" value="${item.code}">
            <table>  
                <tr>
                    <td><h2>Offer Title</h2></td>
                    <td><h2>${offer.title}</h2></td>
                </tr>
                <tr>
                    <td>----------</td>
                    <td>---------------------------------------------------</td>

                </tr>
                <tr>
                    <td><h2>Start Date</h2></td>
                    <td> <h2>${offer.getStartDateString()}</h2></td>
                </tr>
                <tr>
                    <td>----------</td>
                    <td>---------------------------------------------------</td>

                </tr>
                <tr>
                    <td><h2>End Date</h2></td>
                    <%
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String n = dtf.format(now);
                        out.println("<td>");
                        out.println("<input type=\"date\" name=\"endDate\" value=" + n + ">");
                        out.println("</td>");
                    %>
                  
                </tr>
            </table>
            <br>
            <button class="btn btn-primary" type="submit" ><h1>End Offer</h1></button>
            </h2>
        </form>

    </center>
</body>
</html>
