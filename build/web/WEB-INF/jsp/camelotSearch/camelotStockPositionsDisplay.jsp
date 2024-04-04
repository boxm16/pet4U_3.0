
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
                font-size: 15px;
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



                <h5>Camelot Items By Stock Position</h5>


                <table>
                    <thead>

                    <th>Position</th>

                    <th>Item Code</th>




                    </thead>
                    <%
                        LinkedHashMap<String, ArrayList<String>> positions = (LinkedHashMap) request.getAttribute("camelotItemsByStockPosition");
                        if (positions != null) {
                     
                       
                            
                            for (Map.Entry<String, ArrayList<String>> positionsEntry : positions.entrySet()) {
                                String position = positionsEntry.getKey();
                                ArrayList<String> itemCodes = positionsEntry.getValue();

                                for (String itemCode : itemCodes) {
                                    out.println("<tr>");

                                    out.println("<td>");
                                    out.println(position);
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(itemCode);
                                    out.println("</td>");

                                    out.println("</tr>");

                                }
                            }
                        }

                    %>
                </table>
                <hr>
            </div>
        </div>


    </body>
</html>
