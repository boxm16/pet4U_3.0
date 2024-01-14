<%-- 
    Document   : stockManagement
    Created on : Aug 15, 2023, 6:35:42 PM
    Author     : Michail Sitmalidis
--%>

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
        <title>Stock Management</title>
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
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg">


                    <center>
                        <h2><a href="index.htm">INDEX</a></h2>
                        <h4><a href="suppliersAndStockDashboard.htm">GO BACK</a></h4>

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

                                    <th>
                                        <button type="button" onclick="setText('Last Six Months Grand Total Sales (Εξαγωγες=Πωλήσεις+Ενδοδιακίνηση)')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            L6MS
                                        </button>
                                    </th>
                                    <th>
                                        <button type="button" onclick="setText('One Month Grand Total Sales  From Six Months Sales Calculation <br>L6MS/6')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            1MSFC
                                        </button>
                                    </th>
                                    <th>
                                        <button type="button" onclick="setText('Last Month Grand Total Sales (Εξαγωγες=Πωλήσεις+Ενδοδιακίνηση)')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            LMS
                                        </button>
                                    </th>
                                    <th>
                                        <button type="button" onclick="setText('Objective Sales')" class="btn btn-light" style="background-color: #90EE90" data-toggle="modal" data-target="#exampleModalCenter">
                                            OS
                                        </button>
                                    </th>
                                    <th>
                                        <button type="button" onclick="setText('Objective Sales Expiration Date')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            OSED
                                        </button>
                                    </th>
                                    <th>
                                        <button type="button" onclick="setText('Order Horizon')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            OH
                                        </button>
                                    </th>
                                    <th>
                                        <button type="button" onclick="setText('Minimal Stock Horizon')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            MnSH
                                        </button>
                                    </th>

                                    <th>
                                        <button type="button" onclick="setText('Minimal  Stock')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            MnS
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
                                        <button type="button" onclick="setText('Order')" class="btn btn-light" data-toggle="modal" data-target="#exampleModalCenter">
                                            Ord
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
                                    <th>Edit</th>
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
                                    int minimalStockHorizon = item.getMinimalStockHorizon();
                                    double stock = 0;
                                    if (item.getQuantity() != null) {//getQuantity here is sold quantity, not stock quantity
                                        stock = Double.parseDouble(item.getQuantity());
                                    }
                                    boolean needOrder = false;
                                    if (stock < (objectiveSales * minimalStockHorizon) || objectiveSales == 0) {
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
                                    out.println(item.getPosition());
                                    out.println("</td>");

                                    out.println("<td>");
                                    out.println(grandTotalEksagoges);
                                    out.println("</td>");

                                    if (oneMonthSalesFromCalculation == 0) {
                                        out.println("<td style='background-color:red'>");
                                    } else {
                                        out.println("<td>");
                                    }
                                    out.println(df.format(oneMonthSalesFromCalculation));
                                    out.println("</td>");

                                    if (diff > fiftyPercerntDifference) {
                                        out.println("<td style='background-color:#F36FD9'>");
                                    } else {
                                        if (objectiveSales == 0) {
                                            out.println("<td style='background-color:red'>");
                                        } else {
                                            out.println("<td>");
                                        }
                                    }
                                    out.println(grandTotalEksagogesOneMonth);
                                    out.println("</td>");

                                    if (diff > fiftyPercerntDifference) {
                                        if (item.getObjectiveSales() > 0) {
                                            out.println("<td style='background-color:#90EE90'>");
                                            out.println("<a href='objectiveSalesDashboard.htm?supplierId=" + supplier.getId() + "&itemCode=" + item.getCode() + "' >" + item.getObjectiveSales() + "</a>");
                                        } else {
                                            out.println("<td style='background-color:#BA4A00'>");
                                            out.println("<a href='objectiveSalesDashboard.htm?supplierId=" + supplier.getId() + "&itemCode=" + item.getCode() + "' >" + item.getObjectiveSales() + "</a>");
                                        }
                                    } else {
                                        if (objectiveSales == 0) {
                                            out.println("<td style='background-color:green'>");
                                            out.println("<a href='objectiveSalesDashboard.htm?supplierId=" + supplier.getId() + "&itemCode=" + item.getCode() + "' >" + item.getObjectiveSales() + "</a>");

                                        } else {
                                            out.println("<td style='background-color:#90EE90'>");
                                            out.println(df.format(objectiveSales));
                                        }
                                    }
                                    out.println("</td>");

                                    out.println("<td>");
                                    LocalDate objectiveSalesExpirationDate = item.getObjectiveSalesExpirationDate();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                    if (objectiveSalesExpirationDate == null) {
                                        out.println();
                                    } else {
                                        out.println(formatter.format(objectiveSalesExpirationDate));
                                    }

                                    out.println("</td>");

                                    if (item.getOrderHorizon() == 0) {
                                        out.println("<td style=''background-color:red; text-align: center; font-size:14px'>");
                                    }
                                    if (item.getOrderHorizon() == 1) {
                                        out.println("<td style='background-color:#F9C8B6; text-align: center; font-size:20px'>");
                                    }
                                    if (item.getOrderHorizon() == 2) {
                                        out.println("<td style='background-color:#F1F9B6; text-align: center; font-size:20px'>");
                                    }
                                    if (item.getOrderHorizon() == 3) {
                                        out.println("<td style='background-color:#DCFCC9; text-align: center; font-size:20px'>");
                                    }
                                    if (item.getOrderHorizon() == 4) {
                                        out.println("<td style='background-color:#D8F8F8; text-align: center; font-size:20px'>");
                                    }
                                    if (item.getOrderHorizon() == 5) {
                                        out.println("<td style='background-color:#DCD8F8; text-align: center;  font-size:20px'>");
                                    }
                                    out.println("<a href='objectiveSalesDashboard.htm?supplierId=" + supplier.getId() + "&itemCode=" + item.getCode() + "' >" + item.getOrderHorizon() + "</a>");
                                    out.println("</td>");

                                    if (item.getMinimalStockHorizon() == 0) {
                                        out.println("<td style='background-color:red; text-align: center; font-size:14px'>");
                                    }
                                    if (item.getMinimalStockHorizon() == 1) {
                                        out.println("<td style='background-color:#F9C8B6; text-align: center; font-size:20px'>");
                                    }
                                    if (item.getMinimalStockHorizon() == 2) {
                                        out.println("<td style='background-color:#F1F9B6; text-align: center; font-size:20px'>");
                                    }
                                    if (item.getMinimalStockHorizon() == 3) {
                                        out.println("<td style='background-color: F06093; text-align: center; font-size:20px'>");
                                    }
                                    if (item.getMinimalStockHorizon() == 4) {
                                        out.println("<td style='background-color:pink; text-align: center; font-size:20px'>");
                                    }
                                    if (item.getMinimalStockHorizon() == 5) {
                                        out.println("<td style='background-color:red; text-align: center;  font-size:20px'>");
                                    }
                                    out.println("<a href='objectiveSalesDashboard.htm?supplierId=" + supplier.getId() + "&itemCode=" + item.getCode() + "' >" + item.getMinimalStockHorizon() + "</a>");
                                    out.println("</td>");

                                    if (objectiveSales > 0) {
                                        out.println("<td>");
                                        out.println(df.format(objectiveSales * minimalStockHorizon));
                                    } else {

                                        if (item.getObjectiveSales() == 0) {
                                            out.println("<td style='background-color:#BA4A00'>");
                                        } else {
                                            out.println("<td>");
                                        }
                                        out.println(df.format(item.getObjectiveSales() * item.getMinimalStockHorizon()));
                                    }

                                    out.println("</td>");

                                    if (needOrder) {
                                        out.println("<td style='background-color:red'>");
                                    } else {
                                        out.println("<td style='background-color:#D6D0EC '>");

                                    }
                                    out.println(stock);
                                    out.println("</td>");

                                    if (objectiveSales > 0) {
                                        out.println("<td>");
                                        out.println(df.format(objectiveSales * item.getOrderHorizon()));
                                    } else {
                                        if (item.getObjectiveSales() == 0) {
                                            out.println("<td style='background-color:#BA4A00'>");
                                        } else {
                                            out.println("<td>");
                                        }
                                        out.println(df.format(item.getObjectiveSales() * item.getOrderHorizon()));
                                    }

                                    out.println("</td>");

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

                                    out.println("</tr>");

                                }
                            %>
                        </table>

                    </center>
                    <form id="form" action="orderMode.htm" target="_blank" method="POST">
                        <input hidden type="text" id="supplierId" name="supplierId" value="${supplier.id}">
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

                                        //------------

                                        function setText(text) {
                                            document.getElementById("modal-text").innerHTML = text;

                                        }
        </script>

    </body>
</html>
