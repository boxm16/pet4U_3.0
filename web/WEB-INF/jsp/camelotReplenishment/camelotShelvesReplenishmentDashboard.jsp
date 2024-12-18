


<%@page import="CamelotReplenishment.CamelotReplenishment"%>
<%@page import="java.util.TreeMap"%>
<%@page import="Replenishment.Replenishment"%>
<%@page import="CamelotItemsOfOurInterest_V_3_1.CamelotItemOfInterest"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot Shelves Replenishment Dashboard</title>

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
        <h1><a href="camelotShelvesReplenishment.htm">Go Back</a></h1>
        <hr>

        <table>
            <thead>

            <th>Item<br>Code</th>
            <th>Θέση</th>
            <th>Περιγραφή</th>
            <th>Μονάδα Αναπλήρωσης</th>
            <th>Τεμάχια Σε  <br> Μονάδα Αναπλήρωσης</th>
            <th>Ποσοτητα Αναπλληρωσης<br>Σε Μονάδα Αναπλήρωσης</th>
            <th>Ποσοτητα Αναπλληρωσης<br>Σε Τεμάχια</th>
            <th>Referral DateTime</th>
            <th>Sails After<br>Referral DateTime</th>
            <th>ΒΑΡ-PC Sails After<br>Referral DateTime</th>
            <th>Minimal Shelf Stock</th>
            <th>Stock</th>
            <th>Note</th>
            <th>Edit</th>
            </thead>

            <%
                TreeMap<String, CamelotReplenishment> replenishments = (TreeMap) request.getAttribute("sortedByPositionReplenishment");
                for (Map.Entry<String, CamelotReplenishment> entrySet : replenishments.entrySet()) {
                    CamelotReplenishment replenishment = entrySet.getValue();
                    out.println("<tr>");

                    out.println("<td>");
                    out.println(replenishment.getCode());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getPosition());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getDescription());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getReplenishmentUnit());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getItemsInReplenishmentUnit());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getReplenishmentQuantity());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(replenishment.getReplenishmentQuantity() * replenishment.getItemsInReplenishmentUnit());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getDateTime());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getSailsAfterReplenishment());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(replenishment.getEndoSailsAfterReplenishment());
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
                    out.println("<a href='goForEditingCamelotReplenishment.htm?itemCode=" + replenishment.getCode() + "' target='_blank'>Edit</a>");
                    out.println("</td>");

                    out.println("</tr>");
                }

            %>
        </table>
        <hr> <hr> <hr>

    </center>
</body>
</html>
