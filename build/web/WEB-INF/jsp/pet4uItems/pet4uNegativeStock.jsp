<%@page import="CamelotItemsOfInterest.CamelotItemOfInterest"%>
<%@page import="java.util.ArrayList"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="java.util.Map"%>
<%@page import="BasicModel.Item"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pet4u Negative Stock</title>
        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }

        </style>
    </head>
    <body>
    <center>
        <h1><a href='index.htm'>INDEX</a></h1>
        <h1>Pet4u Negative Stock</h1>



        <table>
            <thead>
                <tr> 

                    <th>DESCRIPTION</th>
                    <th>POSITION</th>
                    <th>Altercodes</th>
                    <th>QUANTITY</th>
                    <th>Camelot QUANTITY</th>
                    <th>Supplier</th>
                </tr>
            </thead>
            <tbody>
                <%
                    LinkedHashMap<Integer, CamelotItemOfInterest> nega = (LinkedHashMap) request.getAttribute("pet4uNegativeItems");
                    for (Map.Entry<Integer, CamelotItemOfInterest> negativeStockItemEntry : nega.entrySet()) {

                        CamelotItemOfInterest item = negativeStockItemEntry.getValue();
                        String quantity = item.getQuantity();
                        Double quantityDouble = Double.parseDouble(quantity);
                        if (quantityDouble < 0) {
                            if (item.getDescription().equals("ΕΥΡΩΠΑΛΕΤΕΣ")
                                    || item.getDescription().equals("Περιβ. Τέλος Πλαστ. Σακούλα(16μ)")
                                    || item.getDescription().equals("ΜΕΤΑΦΟΡΙΚΑ ΕΞΟΔΑ 23%")
                                    || item.getDescription().equals("ΠΑΛΕΤΕΣ EURO")
                                    || item.getDescription().equals("ΠΑΛΕΤΕΣ CHEP")
                                    || item.getDescription().equals("ΜΕΤΑΦΟΡΙΚΑ ΕΞΟΔΑ")
                                    || item.getDescription().equals("ΠΑΛΕΤΕΣ ΜΠΛΕ ( RERFECT PET )")
                                    || item.getDescription().contains("ΠΑΛΕΤΕΣ")) {

                                continue;
                            } else {

                                out.println("<tr>");

                                out.println("<td>");
                                out.println(item.getDescription());
                                out.println("</td>");

                                out.println("<td>");
                                out.println(item.getPosition());
                                out.println("</td>");

                                out.println("<td style='text-align:center; font-size:20px;'>");
                                ArrayList<AltercodeContainer> altercodes = item.getAltercodes();
                                for (AltercodeContainer altercode : altercodes) {
                                    if (altercode.getStatus().equals("eshop")
                                            || altercode.getStatus().equals("eshop-on")
                                            || altercode.getStatus().equals("eshop-barf")
                                            || altercode.getStatus().equals("eshop-pro")) {

                                        out.println("<a href='https://www.pet4u.gr/search-products-el.html?subcats=Y&status=A&match=all&pshort=N&pfull=N&pname=Y&pkeywords=N&pcode_from_q=Y&wg_go_direct=Y&search_performed=Y&q=" + altercode.getAltercode() + "' target='_blank'>" + altercode.getAltercode() + "</a>");
                                        out.println("<br>");
                                    } else {
                                        out.println(altercode.getAltercode());
                                        out.println("<br>");
                                    }

                                }
                                out.println("</td>");

                                out.println("<td style='text-align:center; font-size:20px'>");
                                out.println(item.getQuantity());
                                out.println("</td>");

                                String quantityAlert = "inherited";

                                double camelotStock = item.getCamelotStock();
                                if (camelotStock < 1) {
                                    quantityAlert = "red";
                                }
                                out.println("<td style='text-align:center; font-size:20px; background-color:" + quantityAlert + "'>");
                                out.println(item.getCamelotStock());
                                out.println("</td>");

                                String supplierAlert = "inherited";

                                String supplier = item.getSupplier();

                                if (supplier.equals("CAMELOT")) {
                                } else {
                                    supplierAlert = "red";
                                }
                                out.println("<td style='text-align:center; font-size:20px; background-color:" + supplierAlert + "'>");
                                out.println(supplier);
                                out.println("</td>");

                                out.println("</tr>");
                            }

                        }
                    }
                %>
            </tbody>
        </table>
    </center>
</body>
</html>
