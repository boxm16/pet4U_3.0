<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
        <h1>DELIVERY DEMO</h1>
        <hr>
        <h2 style='background-color: red'> The cursor for scanner goes here</h2>
        <input type="text" onkeypress="check(event, this)">
        <hr>
        <p id="descriptionDisplay"></p>
        <hr>
        <table>
            <thead>

            <th>Altercodes</th>
            <th>Description</th>
            <th>Sent</th>
            <th>Delivered</th>
            <th>Difference<br>Alert</th>


            </thead>
            <%
                LinkedHashMap<String, InventoryItem> deliveredItems = (LinkedHashMap) request.getAttribute("deliveredItems");
                for (Map.Entry<String, InventoryItem> entrySet : deliveredItems.entrySet()) {
                    Item deliveredItem = entrySet.getValue();

                    out.println("<td>");
                    out.println(deliveredItem.getCode());
                    out.println("</td>");

                    out.println("<td>");
                    out.println(deliveredItem.getDescription());
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<input type='number' id='" + deliveredItem.getCode() + "_sent' value='" + deliveredItem.getQuantity() + "'>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<input type='number' id='" + deliveredItem.getCode() + "_delivered' value='0'>");
                    out.println("</td>");

                    out.println("<td>");
                    out.println("<dev id='" + deliveredItem.getCode() + "_colorDisplay'>____</dev>");
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
        <hr>
        <h1>Scan these barcodes and watch the magic happen :D</h1>
        <h3>Η απλά πέρασε τα νούμερα του barcode ή τον κωδικό του είδους και πάτα Enter</h3>
        <hr>
        <img src="https://i.ibb.co/bdzrz8c/digestive.gif" alt="digestive" border="0"  width="300" height="100"/>
        <hr>  
        <img src="https://i.ibb.co/D5DchDP/oral.gif" alt="oral" border="0"  width="300" height="100"/>
        <hr>   
        <img src="https://i.ibb.co/5n1zCSp/urinary.gif" alt="urinary" border="0"  width="300" height="100"/>

        <hr>
        <hr>
        <h1>Παρατηρήσεις</h1>
        <h1>Μη ξεχάσεις να βάλεις cursor στο  πεδίο πρίν αρχίσεις να σκανάρεις </h1>
        <h1>Αυτά τα barcodes εδώ είναι μόνο για DEMO, κανονικά ,σαφώς, δε θα υπάρχουνε εδώ.</h1>

    </center>
    <script type="text/javascript">

        class Item {
            constructor(altercode, code, description) {
                this.altercode = altercode;
                this.code = code;
                this.description = description;
            }
        }

        var items = new Array();
        <c:forEach items="${pet4UItemsRowByRow}" var="item">

        var altercode = "${item.altercode}";
        var code = "${item.code}";
        var description = "${item.description}";
        var item = new Item(altercode, code, description);
        items[altercode] = item;
        </c:forEach>




        function check(event, input) {
            if (event.keyCode === 13) {
                var altercode = input.value;
                console.log("altercode:" + altercode);
                var item = items[altercode];
                if (item == null) {
                    document.getElementById("descriptionDisplay").innerHTML = altercode+" : Unkown Item";
                } else {
                    var code = item.code;
                    console.log(code);
                    var description = item.description;
                    document.getElementById("descriptionDisplay").innerHTML = altercode+" : "+description;
                    let sent = document.getElementById(code + "_sent").value * 1;
                    let delivered = document.getElementById(code + "_delivered").value * 1;
                    delivered++;

                    document.getElementById(code + "_delivered").value = delivered;

                    let colorDisplay = document.getElementById(code + "_colorDisplay");

                    let diff = sent - delivered;
                    if (diff > 0) {
                        colorDisplay.style.backgroundColor = 'red';
                    }
                    if (diff < 0) {
                        colorDisplay.style.backgroundColor = 'yellow';
                    }
                    if (diff === 0) {
                        colorDisplay.style.backgroundColor = 'green';
                    }
                }

                input.value = "";

            }
        }
    </script>
</body>
</html>
