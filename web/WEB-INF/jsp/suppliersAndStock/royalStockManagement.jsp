<%-- 
    Document   : stockManagement
    Created on : Aug 15, 2023, 6:35:42 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="SuppliersAndStock.RoyalItem"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="SuppliersAndStock.Supplier"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="MonthSales.Eksagoges"%>
<%@page import="java.util.Map"%>
<%@page import="SuppliersAndStock.SuppliersItem"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Royal Stock Management</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 18px;
            }
            th{
                font-size: 20px;
                font-weight: bold;
                text-align: left;
                background: #eee;
                position: sticky;
                top: 0px;
            }

            /*----------------------------------------*/


        </style>
    </head>
    <body>
        <div class=""container-fluid">
            <div class="row">
                <div class="col-lg">


                    <center>
                        <h1><a href="index.htm">INDEX</a></h1>


                        <h1>Royal Stock Management</h1>
                        <form action="goForAddingItemToRoyalSupplier.htm" method="POST">

                            <input type="text" name="altercode">
                            <button type="submit">Add New Item</button>
                        </form>

                        <hr>

                        <table class="table table-hover table-sm">
                            <thead>  

                                <tr>   
                                    <th>Posi/on</th>
                                    <th>ΚΩΔΙΚΟΣ</th>
                                    <th>Description</th>
                                    <th>State</th>
                                    <th>Stock Now</th>
                                    <th>"On Line" Stock</th>
                                    <th>"Off Site" Stock</th>
                                    <th>Maximal Stock</th>

                                    <th>Note</th>
                                    <th>Edit</th>
                                    <th> <button class="btn btn-primary btn-lg" onclick="requestRouter('goForAddingItemToRoyalSupplier.htm')">PRINT MODE </button></th>
                                </tr>
                            </thead>
                            <%
                                LinkedHashMap<String, RoyalItem> items = (LinkedHashMap) request.getAttribute("supplierItems");
                                for (Map.Entry<String, RoyalItem> entrySet : items.entrySet()) {
                                    RoyalItem item = entrySet.getValue();

                                    out.println("<tr>");

                                    out.println("<td>");
                                    out.println(item.getPosition());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println("<a href='itemAnalysis.htm?code=" + item.getCode() + "' target='_blank'>" + item.getCode() + "</a>");
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getDescription());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getState());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getQuantity());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getOnLineStock());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getOffLineStock());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getMaximalStock());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getNote());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println("<a href='goForEditingRoyalItem.htm?code=" + item.getCode() + "'>Edit</a>");
                                    out.println("</td>");

                                    out.println("</tr>");

                                }
                            %>
                        </table>

                    </center>
                    <form id="form" action="royalStockManagementPrintMode.htm" target="_blank" method="POST">

                        <input hidden type="text" id="orderItemsInput" name="itemsIds" >
                    </form>



                    <!-- Modal -->
                    <div class="modal fade bd-example-modal-lg" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                            <div class="modal-content">

                                <div class="modal-body">
                                    <h3>  <div id="modal-text"></div></h3>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>

                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <center>  
                        <h1>Royal Stock Management</h1>
                        <form action="" method="POST">

                            <input type="text" name="altercode">
                            <button type="submit">Add New Item</button>
                        </form>
                    </center>
                </div>   
            </div>  
        </div>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script>
                                        ////--------------------
                                        function requestRouter(target) {
                                            form.action = target;
                                            form.submit();
                                        }



        </script>

    </body>
</html>
