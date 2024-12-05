
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="Inventory.InventoryItem"%>
<%@page import="java.util.TreeMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot Notes Display</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 15px;
            }
            th{
                font-size: 25px;
                font-weight: bold;
                text-align: left;
                background: #eee;
                top: 0px;
            }

        </style>
    </head>
    <body>
    <center>
        <br>
        <h5>Camelot: Notes</h5>

        <table>
            <thead>
            <th>Altercode</th>
            <th>Position</th>

            <th>Description</th>

            <th>Note</th>
            <th>ΘΕΣΕΙΣ STOCK</th>
            <th>Delete</th>


            </thead>
            <%
                ArrayList<InventoryItem> items = (ArrayList) request.getAttribute("notes");
                if (items != null) {
                    int index = 0;
                    for (InventoryItem inventoryItem : items) {

                        out.println("<tr>");

                        //  out.println("<td>");
                        //   out.println(index);
                        //   out.println("</td>");
                        out.println("<td>");
                        out.println(inventoryItem.getCode());
                        out.println("</td>");

                        out.println("<td style='font-size: 15px;'>");
                        out.println(inventoryItem.getPosition());
                        out.println("</td>");

                        out.println("<td style='font-size: 15px;'>");
                        out.println(inventoryItem.getDescription());
                        out.println("</td>");

                        out.println("<td>");
                        out.println(inventoryItem.getNote());
                        out.println("</td>");

                        out.println("<td>");

                        LinkedHashMap<Integer, String> stockPositions = inventoryItem.getStockPositions();
                        if (stockPositions == null) {
                            out.println("");
                        } else {

                            for (Map.Entry<Integer, String> stockPositionsEntry : stockPositions.entrySet()) {
                                out.println(stockPositionsEntry.getValue());
                                out.println("<br>");
                            }
                        }
                        out.println("</td>");

                        out.println("<td>");
                        out.println("<button  ' class='btn btn-outline-info' role='button' onclick='del(" + inventoryItem.getId() + ")'>Delete</button>");
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

                        index++;

                    }
                }
            %>
        </table>
        <hr>

        <!-- Button trigger modal -->
        <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#exampleModal">
            DELETE ALL NOTES
        </button>
        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;

        <button onclick="window.print()">PRINT THIS PAGE</button>
        <hr>
        <a href='printCamelotNotes.htm' class='btn btn-danger btn-lg' role='button' aria-disabled='true'><h3>FOR TRIAL ONLY</h3></a>



        <!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        ΠΡΟΣΟΧΗ!!! ΠΑΣ ΝΑ ΣΒΗΣΕΙΣ ΟΛΑ ΤΑ ΣΗΜΕΙΩΜΑΤΑ. Η ΠΡΑΞΗ ΕΙΝΑΙ ΑΜΕΤΑΚΛΗΤΗ
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel Deletion</button>
                        <a href="deleteAllCamelotNotes.htm" class="btn btn-info" role="button">CONFIRM Deletion</a>   </div>
                </div>
            </div>
        </div>

    </center>

    <script>
        function del(id) {
            window.location.href = "deleteCamelotNote.htm?id=" + id;
        }
    </script>

</body>
</html>
