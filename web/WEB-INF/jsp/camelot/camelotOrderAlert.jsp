


<%@page import="CamelotItemsOfOurInterest_V_3_1.CamelotItemOfInterest"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot Order Alert</title>

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
        <h1>Camelot: Items Of Our Interest: Orders Alert</h1>
        <hr>
        <h3>  <a href="camelotItemsOfOurInterestDashboard.htm">Go Back To Dashboard</a></h3>
        <hr>

        <table>
            <thead>

            <th>Item<br>Code</th>
            <th>Position</th>
            <th>Pet4u Description</th>




            <th>6 Months<br>Sales</th>
            <th>2 Weeks<br> Sales</th>
            <th>Pet4u <br>Stock</th>
            <th>Pet4u <br>Minimal<br>Stock</th>

            <th>Camelot<br> Stock</th>
            <th>Camelot<br>Minimal<br>Stock</th>
            <th>Camelot<br>Last 30<br> Days Sales </th>

            <th>Camelot<br> Position</th>
            <th>Referral<br>Altercode</th>
            <th>Order<br>Unit</th>
            <th>Ord.<br>Qty.</th>


            <th>Note</th>
            <th>State</th>

            <th>Show<br>Snapshot</th>
            </thead>

            <%
                LinkedHashMap<String, CamelotItemOfInterest> items = (LinkedHashMap) request.getAttribute("camelotItemsOfOurInterest");
                for (Map.Entry<String, CamelotItemOfInterest> entrySet : items.entrySet()) {
                    CamelotItemOfInterest camelotItemOfInterest = entrySet.getValue();
                    String alarmColor = "";
                    int minimalStock = camelotItemOfInterest.getMinimalStock();
                    int CamelotMinimalStock = camelotItemOfInterest.getCamelotMinimalStock();
                    double pet4uStock = camelotItemOfInterest.getPet4uStock() / camelotItemOfInterest.getWeightCoefficient();
                    double camelotFreeStock = camelotItemOfInterest.getCamelotStock();
                    Double twoWeekSales = camelotItemOfInterest.getLastSixMonthsSoldPieces() / 13.0357;

                    out.println("<td>");
                    out.println("<a href='itemAnalysis.htm?code=" + camelotItemOfInterest.getCode() + "' target='_blank'>" + camelotItemOfInterest.getCode() + "</a>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println(camelotItemOfInterest.getPosition());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(camelotItemOfInterest.getDescription());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(camelotItemOfInterest.getLastSixMonthsSoldPieces());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(twoWeekSales.intValue());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(camelotItemOfInterest.getPet4uStock() / camelotItemOfInterest.getWeightCoefficient());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(camelotItemOfInterest.getMinimalStock());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(camelotFreeStock);
                    out.println("</td>");

                    out.println("<td>");
                    out.println(camelotItemOfInterest.getCamelotMinimalStock());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(camelotItemOfInterest.getCamelotLast30DaysSales());
                    out.println("</td>");

                    out.println("<td style='font-weight: bold;'>");
                    out.println(camelotItemOfInterest.getCamelotPosition());
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='itemAnalysis.htm?code=" + camelotItemOfInterest.getReferralAltercode() + "' target='_blank'>" + camelotItemOfInterest.getCode() + "</a>");
                    out.println("</td>");

                    out.println("<td style='font-weight: bold;'>");
                    out.println(camelotItemOfInterest.getOrderQuantity());
                    out.println("</td>");

                    out.println("<td style='font-weight: bold;'>");
                    out.println(camelotItemOfInterest.getOrderUnit());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(camelotItemOfInterest.getNote());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(camelotItemOfInterest.getState());
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='itemSnapshots.htm?code=" + camelotItemOfInterest.getCode() + "' target='_blank'>Show Day Rest Snapshots</a>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='goForEditingCamelotItemOfInterest.htm?code=" + camelotItemOfInterest.getCode() + "'>Edit</a>");
                    out.println("</td>");

                    out.println("</tr>");
                }

            %>
        </table>
        <hr> <hr> <hr>
        <!--    <h1><a href='makeCamelotItemsInterestDayRestSnapshot.htm'>Make Snapshot Of Day Rest Of Items Of Interest</a></h1> -->
        <h3>${snapshotInsertionResult}</h3>
    </center>
</body>
</html>
