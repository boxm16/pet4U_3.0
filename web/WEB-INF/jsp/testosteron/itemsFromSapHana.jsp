<%-- 
    Document   : camelot
    Created on : Mar 1, 2023, 10:29:43 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="BasicModel.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TESTOSTERON RESULT</title>
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
        SAP HANA
        <table>
            <tbody>
                <%
                    LinkedHashMap<String, Item> items = (LinkedHashMap) request.getAttribute("allItems");
                    int index = 0;
                    for (Map.Entry<String, Item> entrySet : items.entrySet()) {
                        Item item = entrySet.getValue();

                        out.println("<tr>");

                        out.println("<td>");
                        out.println(index);
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getCode());

                        ArrayList<AltercodeContainer> altercodes = item.getAltercodes();
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
                        out.println(item.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getPosition());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(item.getQuantity());
                        out.println("</td>");

                        out.println("</tr>");
                        index++;
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
