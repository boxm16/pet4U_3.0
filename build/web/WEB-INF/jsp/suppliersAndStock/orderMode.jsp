<%-- 
    Document   : stockManagement
    Created on : Aug 15, 2023, 6:35:42 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="MonthSales.Eksagoges"%>
<%@page import="SuppliersAndStock.Supplier"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="SuppliersAndStock.SuppliersItem"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Mode</title>
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

            input[type='number']{
                width: 100px;
            } 
        </style>

    </head>
    <body>
        <div class=""container-fluid">
            <div class="row">
                <div class="col-lg">
                    <center>

                        <h1><a href="index.htm">INDEX</a></h1>



                        <table class="table table-hover table-sm">
                            <thead>  

                                <tr>
                                    <th>ΚΩΔΙΚΟΣ</th>
                                    <th>Description</th>

                                    <th>
                                        <button type="button" onclick="setText('Objective Sales')" class="btn btn-light" style="background-color: #90EE90" data-toggle="modal" data-target="#exampleModalCenter">
                                            OS
                                        </button>
                                    </th>

                                    <th>
                                        <button type="button" onclick="setText('Order Horizon')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            OH
                                        </button>
                                    </th>


                                    <th>
                                        <button type="button" onclick="setText('Stock')" class="btn btn-light" style='background-color:#D6D0EC' data-toggle="modal" data-target="#exampleModalCenter">
                                            Stk
                                        </button>
                                    </th>

                                    <th>
                                        <button type="button" onclick="setText('Maximal Stock <br> Maximal Stock=Objective Sales * Order Horizon <br>MxStk=OS*OH')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            MxStk
                                        </button>
                                    </th>



                                    <th>
                                        <button type="button" onclick="setText('Order Unit')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            OU
                                        </button>
                                    </th>

                                    <th>
                                        <button type="button" onclick="setText('Order Unit Capacity')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            OUC
                                        </button>
                                    </th>

                                    <th>Note</th>

                                    <th> <button class="btn btn-primary btn-lg" onclick="requestRouter()">ORDER MODE </button></th>
                                </tr>
                            </thead>
                            <% Supplier supplier = (Supplier) request.getAttribute("supplier");
                                LinkedHashMap<String, SuppliersItem> items = (LinkedHashMap) request.getAttribute("supplierItems");
                                for (Map.Entry<String, SuppliersItem> entrySet : items.entrySet()) {
                                    SuppliersItem item = entrySet.getValue();

                                    //----------------------------------
                                    double objectiveSales = 0;
                                    Eksagoges eksagoges = item.getEksagogesForLastMonths(6);
                                    double grandTotalEksagoges = eksagoges.getEshopSales() + eksagoges.getShopsSupply();

                                    double oneMonthSalesFromCalculation = grandTotalEksagoges / 6;
                                    DecimalFormat df = new DecimalFormat("0.00");

                                    Eksagoges oneMontheksagoges = item.getEksagogesForLastMonths(1);
                                    double grandTotalEksagogesOneMonth = oneMontheksagoges.getEshopSales() + oneMontheksagoges.getShopsSupply();
                                    double diff = oneMonthSalesFromCalculation - grandTotalEksagogesOneMonth;

                                    if (diff < 0) {
                                        diff = diff * -1;
                                    }
                                    double onePercent = oneMonthSalesFromCalculation / 100;
                                    double fiftyPercerntDifference = onePercent * 50;
                                    if (diff <= fiftyPercerntDifference) {
                                        objectiveSales = oneMonthSalesFromCalculation;
                                    } else {
                                        if (item.getObjectiveSales() > 0) {
                                            objectiveSales = item.getObjectiveSales();
                                        }

                                    }

                                    double stock = Double.parseDouble(item.getQuantity());
                                    boolean needOrder = false;
                                    if (stock < (objectiveSales * 2)) {
                                        needOrder = true;
                                    }

                                    //----------------------------------
                                    out.println("<tr>");

                                    out.println("<td>");
                                    out.println("<a href='itemAnalysis.htm?code=" + item.getCode() + "' target='_blank'>" + item.getCode() + "</a>");
                                    out.println("</td>");

                                    out.println("<td>");
                                    int length = item.getDescription().length();
                                    if (length < 30) {
                                    } else {
                                        length = 30;
                                    }
                                    String shortDescriptiom = item.getDescription().substring(0, length);
                                    out.println("<button type='button' onclick='setText(\"" + item.getDescription() + "\")' class='btn btn-light' style='background-color:#D6D0EC' data-toggle='modal' data-target='#exampleModalCenter'>" + shortDescriptiom + "</button>");
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(df.format(objectiveSales));
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(item.getOrderHorizon());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(stock);
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println("X");
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

                                    out.println("<td style='background-color:white'>");
                                    out.println("<input onkeyup='recalculateItems(event)' id='" + item.getCode() + ":" + item.getOrderUnitCapacity() + "' class='unitsOrdered' style='font-size:20px' type='number'>");
                                    out.println("</td>");

                                    out.println("<td style='background-color:white'>");
                                    out.println("<input id='" + item.getCode() + "' class='orderedItem' style='font-size:20px' type='number' >");
                                    out.println("</td>");

                                    out.println("</tr>");

                                }
                            %>
                        </table>

                        <form id="form" action="downlodOrderInExcelFormat.htm" target="_blank" method="POST">
                            <input hidden type="text" id="supplierId" name="supplierId" value="${supplier.id}">
                            <input hidden type="text" id="orderedItemsData" name="orderedItemsData" >
                        </form>
                        <button class="btn btn-success " onclick="downloadInExcelFormat()">DOWNLOD IN EXCEL FORMAT</button>
                        <hr>  <hr>
                        <hr>
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
                    </center>
                </div>  
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

        ------------------------------------------------------------------------------+++++++++++++++++++++++++++++++++


        <script>
                            //------------

                            function setText(text) {
                                document.getElementById("modal-text").innerHTML = text;

                            }
                            //------------
                            function recalculateItems(event) {

                                let id = event.target.id;
                                let value = event.target.value;
                                const idArray = id.split(":");
                                id = idArray[0];
                                let capacity = idArray[1];
                                let receiver = document.getElementById(id);
                                receiver.value = value * capacity;

                            }
                            //--------down is function to change focus. Not mine, just copied from stackover :)
                            function getTabStops(o, a, el) {
                                // Check if this element is a tab stop
                                if (el.tabIndex > 0) {
                                    if (o[el.tabIndex]) {
                                        o[el.tabIndex].push(el);
                                    } else {
                                        o[el.tabIndex] = [el];
                                    }
                                } else if (el.tabIndex === 0) {
                                    // Tab index "0" comes last so we accumulate it seperately
                                    a.push(el);
                                }
                                // Check if children are tab stops
                                for (var i = 0, l = el.children.length; i < l; i++) {
                                    getTabStops(o, a, el.children[i]);
                                }
                            }

                            function focusNext() {
                                var o = [],
                                        a = [],
                                        stops = [],
                                        active = document.activeElement;
                                getTabStops(o, a, document.body);
                                // Use simple loops for maximum browser support
                                for (var i = 0, l = o.length; i < l; i++) {
                                    if (o[i]) {
                                        for (var j = 0, m = o[i].length; j < m; j++) {
                                            stops.push(o[i][j]);
                                        }
                                    }
                                }
                                for (var i = 0, l = a.length; i < l; i++) {
                                    stops.push(a[i]);
                                }
                                // If no items are focusable, then blur
                                if (stops.length === 0) {
                                    active.blur();
                                    return;
                                }
                                // Shortcut if current element is not focusable
                                if (active.tabIndex < 0) {
                                    stops[0].focus();
                                    return;
                                }
                                // Attempt to find the current element in the stops
                                for (var i = 0, l = stops.length; i < l; i++) {
                                    if (stops[i] === active) {
                                        if (i + 1 === stops.length) {
                                            active.blur();
                                            return;
                                        }
                                        stops[i + 1].focus();
                                        return;
                                    }
                                }
                                // We shouldn't make it this far
                                active.blur();
                            }

                            document.addEventListener('keypress', function (e) {
                                if (e.which === 13) {
                                    e.preventDefault();
                                    focusNext();
                                }
                            });

                            //-------------


                            function downloadInExcelFormat() {

                                orderedItemsData.value = collectOrderData();
                                console.log(collectOrderData());
                                form.submit();
                            }
                            //this function collects data
                            function collectOrderData() {
                                var returnValue = "";
                                var collectedItems = document.querySelectorAll(".orderedItem");
                                for (x = 0; x < collectedItems.length; x++) {
                                    let v = document.getElementById(collectedItems[x].id).value;
                                    if (v == "") {
                                        v = 0;
                                    }
                                    returnValue += collectedItems[x].id + ":" + v + ",";
                                }
                                return returnValue;
                            }
        </script>
    </body>
</html>
