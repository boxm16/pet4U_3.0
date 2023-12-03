
<%@page import="Offer.Offer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.TreeMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Active Offers</title>
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
        <hr> 
        <h2><a href="offerStamping.htm">Go For Offer Stamping</a></h2>
        <h1>Active Offers</h1>
        <hr>
        <table>
            <thead>
            <th>ID</th>
            <th>Item Code</th>
            <th>Item Description</th>
            <th>Title</th>
            <th>Start Date</th>
            <th>End Offer</th>

            </thead>
            <%
                ArrayList<Offer> offers = (ArrayList) request.getAttribute("activeOffers");
                for (Offer offer : offers) {

                    out.println("<tr>");

                    out.println("<td>");
                    out.println(offer.getId());
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<a href='itemAnalysis.htm?code=" + offer.getItemCode() + "' target='_blank'>" + offer.getItemCode() + "</a>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println(offer.getItemDescription());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(offer.getTitle());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(offer.getStartDateString());
                    out.println("</td>");

                    out.println("<td>");
                    if (offer.getEndDate() == null) {
                        out.println("<a href='endOfferDashboard.htm?id=" + offer.getId() + "'>End Offer</a>");
                    } else {
                     out.println(offer.getEndDateString());
                   
                    }
                    out.println("</td>");

                    out.println("</tr>");

                }
            %>
        </table>

    </center>


</body>
</html>
