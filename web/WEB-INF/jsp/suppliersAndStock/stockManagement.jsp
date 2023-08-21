<%-- 
    Document   : stockManagement
    Created on : Aug 15, 2023, 6:35:42 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.Map"%>
<%@page import="SuppliersAndStock.SuppliersItem"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stock Management</title>
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
        
        <h1>${supplier.name} Stock Management</h1>
        <form action="goForAddingItemToSupplier.htm" method="POST">
            <input hidden name="supplierId" value="${supplier.id}">
            <input type="text" name="altercode">
            <button type="submit">Add New Item</button>
        </form>
            
            <hr>
            <table>
            <thead>
            <th>Owner</th>
            <th>Position</th>
            <th>Camelot<br> Position</th>
            <th>Altercode</th>
            <th>Pet4u Description</th>

            <th>Six Months<br>Sales</th>
            <th>Week Sales</th>
            <th>Pet4u <br>Stock</th>
            <th>Pet4u <br>Minimal <br>Stock</th>

            <th>Camelot<br> Stock</th>
            <th>Camelot <br>Minimal<br> Stock</th>

            <th>Order <br>Unit</th>
            <th>Order <br>Quantity</th>
            <th>Note</th>
            <th>  Edit  </th>
            <th>  Show<br>DayRest<br> Snapshots  </th>
            </thead>
            <%
                LinkedHashMap<String, SuppliersItem> items = (LinkedHashMap) request.getAttribute("supplierItems");
                for (Map.Entry<String, SuppliersItem> entrySet : items.entrySet()) {
                    SuppliersItem item = entrySet.getValue();
                    String alarmColor = "";
                    int minimalStock = item.getMinimalStock();
                    int pet4uStock = Integer.parseInt(item.getQunatityAsPieces());
                    if (pet4uStock < minimalStock * 2) {
                        alarmColor = "yellow";
                    }
                    if (pet4uStock < minimalStock) {
                        alarmColor = "#F33A6A";
                    }
                  
                    out.println("<tr style='background-color: " + alarmColor + "'>");
                    

                   

                    out.println("<td>");
                    out.println(item.getCode());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getDescription());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getTotalShippedPieces());
                    out.println("</td>");

                    int shippedPiecesForPeriod = item.getTotalShippedPiecesForPeriod();
                    out.println("<td>");
                    out.println(shippedPiecesForPeriod);
                    out.println("</td>");

                  

                    

                    out.println("<td>");
                    out.println(item.getOrderUnit());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getOrderUnitCapacity());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(item.getNote());
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='goForEditingSuppliersItem.htm?code=" + item.getCode() + "'>Edit</a>");
                    out.println("</td>");

                  

                    out.println("</tr>");

                }
            %>
        </table>
       
    </center>
</body>
</html>
