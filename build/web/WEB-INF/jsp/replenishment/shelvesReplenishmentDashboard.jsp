


<%@page import="Replenishment.Replenishment"%>
<%@page import="CamelotItemsOfOurInterest_V_3_1.CamelotItemOfInterest"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shelves Replenishment Dashboard</title>

        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 20px;
            }
            th{
                font-size: 20px;
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
        <h1><a href="shelvesReplenishment.htm">Go Back</a></h1>
        <hr>

        <table>
            <thead>

            <th>Item<br>Code</th>
            <th>Position</th>
            <th>Pet4u Description</th>
            <th>Referral DateTime</th>
            <th>Replenishment Quantity</th>
            <th>Sails After Referral DateTime</th>
            <th>Minimal Shelf Stock</th>
            <th>Stock</th>
            <th>Note</th>
         
            </thead>

            <%
                LinkedHashMap<String, Replenishment> replenishments = (LinkedHashMap) request.getAttribute("replenishments");
                for (Map.Entry<String, Replenishment> entrySet : replenishments.entrySet()) {
                    Replenishment replenishment = entrySet.getValue();
                    out.println("<tr>");
                    
                    out.println("<td>");
                    out.println("<a href='itemAnalysis.htm?code=" + replenishment.getCode() + "' target='_blank'>" + replenishment.getCode() + "</a>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getPosition());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getDescription());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getDateTime());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getReplenishmentQuantity());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getSailsAfterReplenishment());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getMinimalShelfStock());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getQunatityAsPieces());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getNote());
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='goForEditingReplenishment.htm?itemCode=" + replenishment.getCode() + "' target='_blank'>Edit</a>");
                    out.println("</td>");

                    out.println("</tr>");
                }

            %>
        </table>
        <hr> <hr> <hr>

    </center>
</body>
</html>
