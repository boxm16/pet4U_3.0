<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>DELTIO APOSOTLHS</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


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
                text-align: center;
                background: #eee;
                position: sticky;
                top: 0px;
            }

        </style>
    </head>
    <body>
    <center>
        <h2>DELTIO APOSOTLHS</h2>
        <hr>

        <center> <input type="text" onkeypress="check(event, this)"></center>
        <center> <p id="descriptionDisplay"></center>

        <hr>
        <form:form method="post" action="saveEndo.htm" modelAttribute="endo">
            <input name="id" value="${endo.id}"/>
            <table>
                <thead> 
                    <tr>
                        <th>Abbrev</th>
                        <th>Description</th>
                        <th>QTY</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${endo.items}" var="itemEntry" varStatus="status">
                        <tr>
                            <td><input name="items['${itemEntry.key}'].code" value="${itemEntry.value.code}"/></td>
                            <td><input name="items['${itemEntry.key}'].description" value="${itemEntry.value.description}"/></td>
                            <td><input name="items['${itemEntry.key}'].quantity" value="${itemEntry.value.quantity}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>	
            <br/>
            <input type="submit" value="Save" />
        </form:form>
    </center>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <script type="text/javascript">

            class Item {
                constructor(altercode, code, description) {
                    this.altercode = altercode;
                    this.code = code;
                    this.description = description;
                }
            }

            var itemsArray = new Array();
        <c:forEach items="${pet4UItemsRowByRow}" var="itemEntry">
            console.log(${itemEntry.key});
            var altercode = "${itemEntry.key}";
            var code = "${itemEntry.value.code}";
            var description = "${itemEntry.value.description}";
            var item = new Item(altercode, code, description);
            itemsArray[altercode] = item;
        </c:forEach>




            function check(event, input) {
                if (event.keyCode === 13) {
                    var altercode = input.value;
                    console.log("altercode:" + altercode);
                    var item = items[altercode];
                    if (item == null) {
                        document.getElementById("descriptionDisplay").innerHTML = altercode + " : Unkown Item";
                        addRow(altercode, "Unkown Item");
                    } else {
                        var code = item.code;
                        console.log(code);
                        var description = item.description;
                        document.getElementById("descriptionDisplay").innerHTML = altercode + " : " + description;


                        let sent = document.getElementById(code + "_sent");
                        if (sent == null) {
                            addRow(item.code, item.description);
                        } else {
                            sent = sent.value * 1;
                        }

                        let delivered = document.getElementById(code + "_delivered").innerHTML * 1;
                        delivered++;

                        document.getElementById(code + "_delivered").innerHTML = delivered;


                    }

                    input.value = "";

                }
            }

            function addRow(code, description) {
                // Get the table body element in which you want to add row
                let table = document.getElementById("tableBody");

                // Create row element
                let row = document.createElement("tr")

                // Create cells
                let c1 = document.createElement("td")
                let c2 = document.createElement("td")
                let c3 = document.createElement("td")
                let c4 = document.createElement("td")

                // Insert data to cells

                c1.innerText = code;
                c2.innerText = description;
                c3.innerHTML = "<div class='sent' type='number' id='" + code + "_sent' >0</div>";
                c4.innerHTML = "<div class='delivered'  id='" + code + "_delivered'>0</div>";



                // Append cells to row
                row.appendChild(c1);
                row.appendChild(c2);
                row.appendChild(c3);
                row.appendChild(c4);



                // Append row to table body
                table.appendChild(row)
            }

            //---------------------------------
            //--------------------------------
            //---------------------------------
            function requestRouter(requestTarget) {
                form.action = requestTarget;

                let sent = collectSentData();
                sentItems.value = sent;

                let delivered = collectDeliveredData();
                deliveredItems.value = delivered;


                // console.log(data);
                form.submit();
            }

            function collectSentData() {
                var returnValue = "";
                var sentItems = document.querySelectorAll(".sent");

                for (x = 0; x < sentItems.length; x++) {

                    returnValue += sentItems[x].id + ":" + sentItems[x].value + ",";
                }
                return returnValue;
            }

            function collectDeliveredData() {
                var returnValue = "";
                var deliveredItems = document.querySelectorAll(".delivered");

                for (x = 0; x < deliveredItems.length; x++) {

                    returnValue += deliveredItems[x].id + ":" + deliveredItems[x].value + ",";
                }
                return returnValue;
            }
    </script>
</body>
</html>