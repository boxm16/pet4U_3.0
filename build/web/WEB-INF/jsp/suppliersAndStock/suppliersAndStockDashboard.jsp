<%-- 
    Document   : sasDashboard
    Created on : Aug 15, 2023, 6:06:13 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="SuppliersAndStock.Supplier"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Suppliers And Stock Dashboard</title>
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
        <h1>Suppliers And Stock Dashboard</h1>
        <h3><a href="goForAddingSupplier.htm">Add Supplier</a></h3>
        <hr>
        <a href="royalStockManagement.htm">Royal Stock Management</a>

        <table>
            <thead>
            <th>ID</th>
            <th>Name</th>


            </thead>
            <%
                ArrayList<Supplier> suppliers = (ArrayList) request.getAttribute("suppliers");
                for (Supplier supplier : suppliers) {

                    out.println("<tr>");

                    out.println("<td>");
                    out.println(supplier.getId());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(supplier.getName());
                    out.println("</td>");

                    out.println("</tr>");

                }
            %>
        </table>
    </center>
</body>
</html>
