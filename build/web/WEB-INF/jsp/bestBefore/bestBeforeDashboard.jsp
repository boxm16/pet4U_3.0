
<%@page import="BestBefore.BestBeforeStatement"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="Inventory.InventoryItem"%>
<%@page import="java.util.TreeMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Best Before Dashboard</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 20px;
            }
            th{
                font-size: 30px;
                font-weight: bold;
                text-align: left;
                background: #eee;
                position: sticky;
                top: 0px;
            }

        </style>
    </head>
    <body>
    <center>
        <h1><a href="index.htm">INDEX</a></h1>
        <h1>Best Before Dashboard</h1>
        <hr>

        <table>
            <thead>
            <th>ID</th>
            <th>Altercode</th>
            <th>Position</th>
            <th>Description</th>
            <th>Alert Date</th>
            <th>Best Before Date</th>
            <th>Note</th>

            <th>Edit</th>
            <th>Delete</th>

            </thead>
            <%
                ArrayList<BestBeforeStatement> statements = (ArrayList) request.getAttribute("bestBeforeStatements");
                if (statements.size() > 0) {
                    for (BestBeforeStatement statement : statements) {

                        out.println("<tr style='background-color:" + statement.getAlertColor() + "'>");

                        out.println("<td>");
                        out.println(statement.getId());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(statement.getAltercode());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(statement.getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(statement.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input id='" + statement.getId() + "_alert' style='font-size:25px' type='date' value='" + statement.getAlert() + "'");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<input id='" + statement.getId() + "_bestBefore' style='font-size:25px'  type='date' value='" + statement.getBestBefore() + "'");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(statement.getNote());
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<button style='background-color:green' onclick='submit(" + statement.getId() + ")'>Edit Statement </button><a href='editBestBeforeStatement.htm?id=" + statement.getId() + "&bestBefore=" + statement.getBestBefore() + "&alert=" + statement.getAlert() + "'></a>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<a href='deleteBestBeforeStatement.htm?id=" + statement.getId() + "'>Delete Best Before Statement</a>");
                        out.println("</td>");

                        out.println("</tr>");

                    }
                }
            %>

        </table>
        <hr>

    </center>
    <form id="form" action="editBestBeforeStatement.htm" method="POST">
        <input hidden id="id" name="id" value="">
        <input hidden id="alrt" name="alert" value="">
        <input hidden id="bb" name="bestBefore" value="">
    </form>

    <script>
        function submit(idInput) {
            id.value = idInput;
            alrt.value = document.getElementById(idInput + "_alert").value;
            bb.value = document.getElementById(idInput + "_bestBefore").value;
            form.submit();
        }
    </script>
</body>
</html>
