
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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 15px;
            }
            th{
                font-size: 15px;
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

        <h5>Camelot: Notes</h5>


        <table>
            <thead>
            <th>Altercode</th>
            <th>Position</th>

            <th>Description</th>

            <th>Note</th>

            <th>Delete</th>


            </thead>
            <%
                ArrayList<InventoryItem> items = (ArrayList) request.getAttribute("notes");
                if (items != null) {
                    for (InventoryItem inventoryItem : items) {

                        out.println("<tr>");

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
                        out.println("<a href='deleteCamelotNote.htm?id=" + inventoryItem.getId() + "'>Delete</a>");
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
                }
            %>
        </table>
        <hr>

        <!-- Button trigger modal -->
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
            DELETE ALL NOTES
        </button>

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


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

</body>
</html>
