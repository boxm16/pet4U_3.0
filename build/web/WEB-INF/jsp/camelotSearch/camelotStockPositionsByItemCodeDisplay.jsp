
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
        <title>Camelot Items By Stock Position</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 25px;
            }
            th{
                font-size: 15px;
                font-weight: bold;
                text-align: left;
                background: #eee;
                position: sticky;
                top: 0px;
            }

        </style>
    </head>
    <body>
        <div class="container" >
            <div class="row">
                <div class=" col-sm-4">
                </div>
                <div class=" col-sm-4">
                    <center>
                        <h3><a href="index.htm">INDEX</a></h3>
                        <h5>Camelot Items By Stock Position</h5>


                        <table>
                            <thead>
                            <th>Item Code</th>
                            <th>Position</th>



                         


                            </thead>
                            <%
                                LinkedHashMap<String, ArrayList<String>> itemCodes = (LinkedHashMap) request.getAttribute("camelotStockPositionsByItemCode");
                                if (itemCodes != null) {

                                    for (Map.Entry<String, ArrayList<String>> itemCodeEntry : itemCodes.entrySet()) {
                                        String itemCode = itemCodeEntry.getKey();
                                        ArrayList<String> positions = itemCodeEntry.getValue();

                                        for (String position : positions) {
                                            out.println("<tr>");

                                            out.println("<td style='width:100px'>");
                                            out.println(itemCode);
                                            out.println("</td>");

                                            out.println("<td style='text-align: center'>");
                                            out.println(position);
                                            out.println("</td>");

                                            out.println("</tr>");

                                        }
                                    }
                                }

                            %>
                        </table>
                        <hr>
                    </center>
                </div>

                <div class=" col-sm-4">
                </div>
            </div>
        </div>
    </body>
</html>
