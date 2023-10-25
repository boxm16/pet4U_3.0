
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
        <h1>Active Offers</h1>
        <hr>

        <table>
            <thead>
            <th>ID</th>
            <th>Item Code</th>
            <th>Title</th>
            <th>Start Date</th>


            </thead>
            <%
                ArrayList<Offer> offers = (ArrayList) request.getAttribute("activeOffers");
                for (Offer offer : offers) {

                    out.println("<tr>");

                    out.println("<td>");
                    out.println(offer.getId());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(offer.getItemCode());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(offer.getTitle());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(offer.getStartDateString());
                    out.println("</td>");

                    out.println("</tr>");

                }
            %>
        </table>

    </center>

    <script>

        ////--------------------
        function requestRouter(requestTarget) {
            if (requestTarget == "printMode.htm" || requestTarget == "showArchivizedInventories.htm") {
                form.target = "_blank";
            } else {

            }
            form.action = requestTarget;
            inventoryItemsInput.value = collectSellectedCheckBoxes();
            console.log(form.action);
            form.submit();
        }
        //this function collects all checked checkbox values, concatinates them in one string and returns that string to send it after by POST method to server
        function collectSellectedCheckBoxes() {
            var returnValue = "";
            var targetCheckBoxes = document.querySelectorAll(".inventoryItemId");
            for (x = 0; x < targetCheckBoxes.length; x++) {
                if (targetCheckBoxes[x].checked)
                    returnValue += targetCheckBoxes[x].id + ",";
            }
            return returnValue;
        }
    </script>
</body>
</html>
