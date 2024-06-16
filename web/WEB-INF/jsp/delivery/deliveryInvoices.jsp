<%-- 
    Document   : deliveryInvoices
    Created on : Jun 16, 2024, 5:32:46 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.Map"%>
<%@page import="Delivery.DeliveryInvoice"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table>
            <tbody>
                <tr><td colspan="5">ΔΕΛΤΙΑ ΑΠΟΣΤΟΛΗΣ ΑΠΟ ΠΡΟΜΗΘΕΥΤΕΣ</td></tr>
                <tr>

                    <th>A/A</th>
                    <th>Date</th>
                    <th>ΠΡΟΜΗΘΕΥΤΗΣ</th>
                    <th>Number</th>
                    <th>Select</th>
                </tr>


                <%                                LinkedHashMap<String, DeliveryInvoice> deliveryInvoices = (LinkedHashMap) request.getAttribute("deliveryInvoices");
                    for (Map.Entry<String, DeliveryInvoice> entrySet : deliveryInvoices.entrySet()) {

                        out.println("<tr>");

                        out.println("<td>");
                        out.println("<a href='showDeltioApostolis.htm?id=" + entrySet.getValue().getId() + "' target='_blank'>" + entrySet.getValue().getId() + "</a>");
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getInsertionDate());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(entrySet.getValue().getSupplier());
                        out.println("</td>");

                        out.println("<td style='font-weight: bold;'>");
                        out.println(entrySet.getValue().getNumber());
                        out.println("</td>");

                        out.println("</tr>");
                    }
                %>    
            </tbody>
        </table>
    </body>
</html>
