<%-- 
    Document   : deliveryDashboard
    Created on : May 18, 2023, 8:42:07 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="BasicModel.Item"%>
<%@page import="java.util.Map"%>
<%@page import="Inventory.InventoryItem"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
            }

        </style>
    </head>
    <body>
    <center>
        <h1><a href="index.htm">INDEX</a></h1>
        <h1>Inventories Display</h1>
        <hr>

        <table>
            <thead>

            <th>Altercodes</th>
            <th>Description</th>
            <th>Quantity</th>


            </thead>
            <%
                LinkedHashMap<String, InventoryItem> deliveredItems = (LinkedHashMap) request.getAttribute("deliveredItems");
                for (Map.Entry<String, InventoryItem> entrySet : deliveredItems.entrySet()) {
                    Item deliveredItem = entrySet.getValue();

                    out.println("<td>");
                    ArrayList<AltercodeContainer> altercodes = deliveredItem.getAltercodes();
                    for (AltercodeContainer altercodeContainer : altercodes) {
                        if (altercodeContainer.getStatus().equals("eshop")
                                || altercodeContainer.getStatus().equals("eshop-on")
                                || altercodeContainer.getStatus().equals("eshop-barf")
                                || altercodeContainer.getStatus().equals("eshop-pro")) {

                            out.println("<a href='https://www.pet4u.gr/search-products-el.html?subcats=Y&status=A&match=all&pshort=N&pfull=N&pname=Y&pkeywords=N&pcode_from_q=Y&wg_go_direct=Y&search_performed=Y&q=" + altercodeContainer.getAltercode() + "' target='_blank'>" + altercodeContainer.getAltercode() + "</a>");
                            out.println("<br>");
                        } else {
                            out.println(altercodeContainer.getAltercode() + "</strong>");
                            out.println("<br>");
                        }
                    }
                    out.println("</td>");

                    out.println("<td>");
                    out.println(deliveredItem.getDescription());
                    out.println("</td>");
                    
                    
                    out.println("<td>");
                    out.println(deliveredItem.getQuantity());
                    out.println("</td>");


                    /*

                    out.println("<td>");
                    out.println("<a href='goForEditingCamelotItemOfInterest.htm?code=" + camelotItemOfInterest.getCode() + "'>Edit</a>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='itemSnapshots.htm?code=" + camelotItemOfInterest.getCode() + "' target='_blank'>Show Day Rest Snapshots</a>");
                    out.println("</td>");
                     */
                    out.println("</tr>");

                }
            %>
        </table>

    </center>
</body>
</html>
