


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
        <h1>Camelot: Items Of Our Interest: Orders Alert</h1>
        <hr>
        <table>
            <thead>
            <th>Position</th>
            <th>Pet4u Description</th>





            <th>Pet4u <br>Stock</th>
            <th>Pet4u <br>Minimal<br>Stock</th>

            <th>Camelot<br> Stock</th>
            <th>Camelot<br>Minimal<br>Stock</th>


            <th>Camelot<br> Position</th>
            <th>Referral<br>Altercode</th>
            <th>Order<br>Unit</th>
            <th>Ord.<br>Qty.</th>


            <th>Note</th>
            <th>State</th>

            <th>Edit</th>
            </thead>
            <tbody>

                <%
                    LinkedHashMap<String, CamelotItemOfInterest> items = (LinkedHashMap) request.getAttribute("camelotItemsOfOurInterest");
                    for (Map.Entry<String, CamelotItemOfInterest> entrySet : items.entrySet()) {
                        CamelotItemOfInterest camelotItemOfInterest = entrySet.getValue();
                        String alarmColor = "";
                        int minimalStock = camelotItemOfInterest.getMinimalStock();
                        int CamelotMinimalStock = camelotItemOfInterest.getCamelotMinimalStock();
                        double pet4uStock = camelotItemOfInterest.getPet4uStock() / camelotItemOfInterest.getWeightCoefficient();
                        double camelotFreeStock = camelotItemOfInterest.getCamelotStock();
                        Double threeWeekSales = camelotItemOfInterest.getLastSixMonthsSoldPieces() * 21 / 182.5;
                        Double threeWeekSalesByDailySales = camelotItemOfInterest.getLast30DaysSales() / 30 * 21 / camelotItemOfInterest.getWeightCoefficient();
                        if (camelotFreeStock < CamelotMinimalStock || pet4uStock < minimalStock * 2) {
                            if (camelotItemOfInterest.getMinimalStock() == -989898) {
                                continue;
                            }
                            if (pet4uStock < minimalStock * 2) {
                                alarmColor = "yellow";
                            }
                            if (pet4uStock < minimalStock) {
                                alarmColor = "#F33A6A";
                            }

                            if (camelotItemOfInterest.getCamelotStock() < 1) {
                                if (pet4uStock < threeWeekSales || pet4uStock < threeWeekSalesByDailySales) {
                                    alarmColor = "#CD7F32";
                                }
                                if (pet4uStock < 1) {
                                    alarmColor = "brown";
                                } else {
                                    continue;
                                }
                            } else {
                                if (camelotFreeStock < CamelotMinimalStock) {
                                    if (pet4uStock < threeWeekSales || pet4uStock < threeWeekSalesByDailySales) {
                                        alarmColor = "red";
                                    } else {
                                        continue;
                                    }
                                }

                            }
                            if (alarmColor.equals("")) {
                                continue;
                            }

                            out.println("<tr style='background-color: " + alarmColor + "'>");

                            out.println("<td>");
                            out.println(camelotItemOfInterest.getPosition());
                            out.println("</td>");

                            out.println("<td>");
                            out.println(camelotItemOfInterest.getDescription());
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

                            out.println("<td style='font-weight: bold; font-size:30px;'>");
                            out.println(camelotItemOfInterest.getCamelotPosition());
                            out.println("</td>");

                            out.println("<td style='font-weight: bold; font-size:30px;'>");
                            out.println(camelotItemOfInterest.getReferralAltercode());
                            out.println("</td>");

                            out.println("<td style='font-weight: bold; font-size:30px;'>");
                            out.println(camelotItemOfInterest.getOrderQuantity());
                            out.println("</td>");

                            out.println("<td style='font-weight: bold; font-size:30px;'>");
                            out.println(camelotItemOfInterest.getOrderUnit());
                            out.println("</td>");

                            out.println("<td>");
                            out.println(camelotItemOfInterest.getNote());
                            out.println("</td>");

                            out.println("<td>");
                            out.println(camelotItemOfInterest.getState());
                            out.println("</td>");

                            out.println("<td>");
                            out.println("<a href='goForEditingCamelotItemOfInterest.htm?code=" + camelotItemOfInterest.getReferralAltercode() + "'>Edit</a>");
                            out.println("</td>");

                            out.println("</tr>");
                        }
                    }
                %>
            </tbody>
        </table>
        <hr> <hr> <hr>

    </center>
</body>
</html>
