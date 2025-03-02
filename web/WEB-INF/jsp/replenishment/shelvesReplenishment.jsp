
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
        <title>Shelves Replenishment Dashboard</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

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
        <div class="container">
            <div class="row">
                <div class=" col-sm-4">

                </div>
                <div class=" col-sm-4">

                    <h1><a href="index.htm">INDEX</a></h1>
                    <h1>ΑΝΑΠΛΗΡΩΣΗ ΡΑΦΙΩΝ</h1>
                    <hr>

                    <%
                        TreeMap<String, Replenishment> orderedRreplenishmentsCardModeX = (TreeMap) request.getAttribute("sortedByPositionReplenishment");
                        for (Map.Entry<String, Replenishment> entrySet : orderedRreplenishmentsCardModeX.entrySet()) {
                            String alarmColor = "";
                            Replenishment replenishment = entrySet.getValue();

                            double stockNow = Double.parseDouble(replenishment.getQuantity());

                            int stockOnShelfNow = replenishment.getReplenishmentQuantity() - replenishment.getSailsAfterReplenishment() - replenishment.getEndoSailsAfterReplenishment();

                            if (replenishment.getReplenishmentQuantity() == 0 || stockNow == 0 || stockNow == stockOnShelfNow) {
                                continue;
                            }

                            if (stockOnShelfNow < replenishment.getMinimalShelfStock()) {
                                alarmColor = "#F33A6A";;
                            } else if (stockOnShelfNow < replenishment.getMinimalShelfStock() * 2) {
                                alarmColor = "yellow";
                            } else {
                                continue;
                            }

                            out.println("<table class='table' style='background-color:" + alarmColor + "'>");
                            out.println("<tbody>");

                            out.println("<tr>");
                            out.println("<td style='width:70px'>");
                            out.println("Code");
                            out.println("</td>");
                            out.println("<td>");
                            out.println("<strong>" + replenishment.getCode() + "</strong>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td style='width:70px'>");
                            out.println("Πε/φη");
                            out.println("</td>");
                            out.println("<td>");
                            out.println("<strong>" + replenishment.getDescription() + "</strong>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td style='width:70px'>");
                            out.println("Θεση");
                            out.println("</td>");
                            out.println("<td>");
                            out.println("<strong>" + replenishment.getPosition() + "</strong>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td style='width:70px'>");
                            out.println("Υπλ.");
                            out.println("</td>");
                            out.println("<td>");
                            out.println("<strong>" + replenishment.getQuantity() + "</strong>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td style='width:70px'>");
                            out.println("Ref.Date");
                            out.println("</td>");
                            out.println("<td>");
                            out.println("<strong>" + replenishment.getDateTime() + "</strong>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");

                            out.println("<td colspan='2'>");
                            out.println("<strong>R.Q-" + replenishment.getReplenishmentQuantity()
                                    + ": S-" + replenishment.getSailsAfterReplenishment()
                                    + ": E.S-" + replenishment.getEndoSailsAfterReplenishment()
                                    + ": M.S.S-" + replenishment.getMinimalShelfStock()
                                    + "</strong>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("<tr>");
                            out.println("<td style='width:70px'>");
                            out.println("Note.");
                            out.println("</td>");
                            out.println("<td>");
                            out.println("<strong>" + replenishment.getNote() + "</strong>");
                            out.println("</td>");
                            out.println("</tr>");

                            out.println("</tbody>");
                            out.println("</table>");
                            out.println("<div STYLE=\"background-color:lightblue; height:10px; width:100%;\"></div>");
                        }
                    %>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>
            <!--  <div class="row">
                  <hr>
                  <h1>ΑΓΝΟΗΣΕ ΤΑ ΣΤΟΙΧΙΑ ΚΑΤΩ ΑΠΟ ΑΥΤΗ ΤΗ ΓΡΑΜΜΗ</h1>
                  <table>
                      <thead>
  
                      <th>Item<br>Code</th>
                      <th>Position</th>
                      <th>Pet4u Description</th>
                      <th>Referral DateTime</th>
                      <th>Replenishment Quantity</th>
                      <th>Sails After Referral DateTime</th>
                      <th>Endo Sails After Referral DateTime</th>
                      <th>Minimal Shelf Stock</th>
                      <th>Stock</th>
                      <th>Note</th>
  
                      </thead>
  
            <%                TreeMap<String, Replenishment> orderedRreplenishmentsCardMode = (TreeMap) request.getAttribute("sortedByPositionReplenishment");
                for (Map.Entry<String, Replenishment> entrySet : orderedRreplenishmentsCardMode.entrySet()) {
                    String alarmColor = "";

                    Replenishment replenishment = entrySet.getValue();

                    double stockNow = Double.parseDouble(replenishment.getQuantity());

                    int stockOnShelfNow = replenishment.getReplenishmentQuantity()
                            - replenishment.getSailsAfterReplenishment()
                            - replenishment.getEndoSailsAfterReplenishment();

                    if (stockNow == 0 || stockNow == stockOnShelfNow) {
                        continue;
                    }

                    if (stockOnShelfNow < replenishment.getMinimalShelfStock() * 2) {
                        alarmColor = "yellow";
                    } else if (stockOnShelfNow < replenishment.getMinimalShelfStock()) {
                        alarmColor = "#F33A6A";;
                    } else {
                        continue;
                    }
                    if (stockNow == 0 || stockNow == stockOnShelfNow) {
                        continue;
                    }

                    if (stockOnShelfNow < replenishment.getMinimalShelfStock()) {
                        alarmColor = "#F33A6A";;
                    } else if (stockOnShelfNow < replenishment.getMinimalShelfStock() * 2) {
                        alarmColor = "yellow";
                    } else {
                        continue;
                    }

                    out.println("<tr style='background-color: " + alarmColor + "'>");
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

                    out.println("</tr>");
                }

            %>
        </table>


        <hr><hr><hr><hr><hr>
        <table>
            <thead>

            <th>Item<br>Code</th>
            <th>Position</th>
            <th>Pet4u Description</th>
            <th>Referral DateTime</th>
            <th>Replenishment Quantity</th>
            <th>Sails After Referral DateTime</th>
            <th>Endo Sails After Referral DateTime</th>
            <th>Minimal Shelf Stock</th>
            <th>Stock</th>
            <th>Note</th>

            </thead>

            <%                LinkedHashMap<String, Replenishment> replenishments = (LinkedHashMap) request.getAttribute("replenishments");
                for (Map.Entry<String, Replenishment> entrySet : replenishments.entrySet()) {
                    String alarmColor = "";

                    Replenishment replenishment = entrySet.getValue();
                    int stockOnShelfNow = replenishment.getReplenishmentQuantity()
                            - replenishment.getSailsAfterReplenishment()
                            - replenishment.getEndoSailsAfterReplenishment();
                    if (stockOnShelfNow < replenishment.getMinimalShelfStock() * 2) {
                        alarmColor = "yellow";
                    }
                    if (stockOnShelfNow < replenishment.getMinimalShelfStock()) {
                        alarmColor = "#F33A6A";;
                    }
                    double stockNow = Double.parseDouble(replenishment.getQuantity());
                    if (stockNow == 0 || stockNow == stockOnShelfNow) {
                        alarmColor = "";;
                    }
                    out.println("<tr style='background-color: " + alarmColor + "'>");
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

                    out.println("</tr>");
                }

            %>
        </table>
        <hr> <hr> <hr>
        <h1>   Ordered By Position  </h1>
        <hr>
        <table>
            <thead>

            <th>Item<br>Code</th>
            <th>Position</th>
            <th>Pet4u Description</th>
            <th>Referral DateTime</th>
            <th>Replenishment Quantity</th>
            <th>Sails After Referral DateTime</th>
            <th>Endo Sails After Referral DateTime</th>
            <th>Minimal Shelf Stock</th>
            <th>Stock</th>
            <th>Note</th>

            </thead>

            <%                TreeMap<String, Replenishment> orderedRreplenishments = (TreeMap) request.getAttribute("sortedByPositionReplenishment");
                for (Map.Entry<String, Replenishment> entrySet : orderedRreplenishments.entrySet()) {
                    String alarmColor = "";

                    Replenishment replenishment = entrySet.getValue();
                    int stockOnShelfNow = replenishment.getReplenishmentQuantity()
                            - replenishment.getSailsAfterReplenishment()
                            - replenishment.getEndoSailsAfterReplenishment();
                    if (stockOnShelfNow < replenishment.getMinimalShelfStock() * 2) {
                        alarmColor = "yellow";
                    }
                    if (stockOnShelfNow < replenishment.getMinimalShelfStock()) {
                        alarmColor = "#F33A6A";;
                    }
                    double stockNow = Double.parseDouble(replenishment.getQuantity());
                    if (stockNow == 0 || stockNow == stockOnShelfNow) {
                        alarmColor = "";;
                    }
                    out.println("<tr style='background-color: " + alarmColor + "'>");
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

                    out.println("</tr>");
                }

            %>
        </table>
      


    </div> -->
            <div> <center>
                    <hr> <hr> <hr>
                    <a href="shelvesReplenishmentDashboard.htm" class="btn btn-success btn-lg" role="button" aria-disabled="true"><h3>Shelves Replenishment Dashboard</h3></a>
                    <hr>
                </center>
            </div> 
        </div>
    </body>
</html>
