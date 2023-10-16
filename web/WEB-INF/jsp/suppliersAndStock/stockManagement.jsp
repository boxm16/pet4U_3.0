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
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
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
        <div class=""container-fluid">
            <div class="row">
                <div class="col-lg">
                    <center>
                        <h1><a href="index.htm">INDEX</a></h1>

                        <h1>${supplier.name} Stock Management</h1>
                        <form action="goForAddingItemToSupplier.htm" method="POST">
                            <input hidden name="supplierId" value="${supplier.id}">
                            <input type="text" name="altercode">
                            <button type="submit">Add New Item</button>
                        </form>

                        <hr>
                        <table class="table table-hover table-sm">
                            <thead>  

                                <tr>
                                    <th>ΚΩΔΙΚΟΣ</th>
                                    <th>Description</th>
                                    <th>Posi/on</th>

                                    <th>Six Months<br>Sales</th>
                                    <th>2 Week`s Sales</th>
                                    <th>Stock</th>
                                    <th>Minimal <br>Stock</th>
                                    <th>Order</th>


                                    <th>Order <br>Unit</th>
                                    <th>Order <br>Unit<br>Capacity</th>
                                    <th>Note</th>
                                    <th>  Edit  </th>
                                    <th> <button class="btn btn-primary btn-lg" onclick="requestRouter()">ORDER MODE </button></th>
                                </tr>
                            </thead>
                            <%
                                LinkedHashMap<String, SuppliersItem> items = (LinkedHashMap) request.getAttribute("supplierItems");
                                for (Map.Entry<String, SuppliersItem> entrySet : items.entrySet()) {
                                    SuppliersItem item = entrySet.getValue();
                                    String alarmColor = "";
                                    int minimalStock = item.getMinimalStock();
                                    Double pet4uStock = Double.parseDouble(item.getQuantity());
                                    boolean needOrder = false;
                                    if (pet4uStock < minimalStock * 2) {
                                        alarmColor = "yellow";
                                        needOrder = true;
                                    }
                                    if (pet4uStock < minimalStock) {
                                        alarmColor = "#F33A6A";
                                        needOrder = true;
                                    }

                                    out.println("<tr style='background-color: " + alarmColor + "'>");

                                    out.println("<td>");
                                    out.println(item.getCode());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getDescription());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getPosition());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getTotalShippedPieces());
                                    out.println("</td>");

                                    int shippedPiecesForPeriod = item.getTotalShippedPiecesForPeriod();
                                    out.println("<td>");
                                    out.println(shippedPiecesForPeriod);
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getQuantity());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getMinimalStock());
                                    out.println("</td>");

                                    if (needOrder) {
                                        out.println("<td>");
                                        out.println("<input class='itemId' type='checkbox'  id='" + item.getCode() + "' style='width:28px;height:28px' checked>");
                                        out.println("</td>");
                                    } else {
                                        out.println("<td>");
                                        out.println("<input class='itemId' type='checkbox'  id='" + item.getCode() + "' style='width:28px;height:28px'>");
                                        out.println("</td>");
                                    }
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
                                    out.println("<a href='goForEditingSuppliersItem.htm?supplierId=" + item.getSupplierId() + "&code=" + item.getCode() + "'>Edit</a>");
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println("<a href='pet4uItemSnapshots.htm?code=" + item.getCode() + "' target='_blank'>Show  Snapshots</a>");
                                    out.println("</td>");

                                    out.println("</tr>");

                                }
                            %>
                        </table>

                    </center>
                    <form id="form" action="orderMode.htm" target="_blank" method="POST">
                        <input hidden type="text" id="supplierId" name="supplierId" value="${supplier.id}">
                        <input hidden type="text" id="orderItemsInput" name="itemsIds" >
                    </form>


                </div>   
            </div>  
        </div>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script>
                                        ////--------------------
                                        function requestRouter() {
                                            //console.log(collectSellectedCheckBoxes());
                                            orderItemsInput.value = collectSellectedCheckBoxes();
                                            // console.log("VALUE"+orderItemsInput.value);
                                            form.submit();
                                        }
                                        //this function collects all checked checkbox values, concatinates them in one string and returns that string to send it after by POST method to server
                                        function collectSellectedCheckBoxes() {
                                            var returnValue = "";
                                            var targetCheckBoxes = document.querySelectorAll(".itemId");
                                            for (x = 0; x < targetCheckBoxes.length; x++) {
                                                if (targetCheckBoxes[x].checked)
                                                    returnValue += targetCheckBoxes[x].id + ",";
                                            }
                                            return returnValue;
                                        }
        </script>

    </body>
</html>
