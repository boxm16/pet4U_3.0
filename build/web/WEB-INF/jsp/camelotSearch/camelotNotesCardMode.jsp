<%@page import="Inventory.InventoryItem"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="BasicModel.Item"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot Notes:Card Mode</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;

            }
            td{
                text-align: center;
            }

        </style>
    </head>
    <body>

        <div class="container">
            <div class="row">
                <div class=" col-sm-4">

                </div>
                <div class=" col-sm-4">


                    <center>

                        <%
                            ArrayList<InventoryItem> items = (ArrayList) request.getAttribute("notes");

                            if (items.size() > 0) {
                                for (InventoryItem item : items) {

                                    out.println("<table class='table'>");
                                    out.println("</tbody>");
                                    out.println("<tr>");
                                    out.println("<td style='width:70px'>");
                                    out.println("Πε/φη");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("<strong>" + item.getDescription() + "</strong>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td style='width:70px'>");
                                    out.println("Θεση");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("<strong>" + item.getPosition() + "</strong>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td style='width:70px'>");
                                    out.println("Υπλ.");
                                    out.println("</td>");
                                    out.println("<td>");
                                    out.println("<strong>" + item.getQuantity() + "</strong>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td colspan='2' style='font-size:20px'>");
                                    out.println("<strong>" + item.getNote() + "</strong>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("<tr>");
                                    out.println("<td colspan='2'>");
                                    out.println("<a href='deleteCamelotNoteCardMode.htm?id=" + item.getId() + "'>Delete</a>");
                                    out.println("</td>");
                                    out.println("</tr>");

                                    out.println("</tbody>");
                                    out.println("</table>");
                                    out.println("<div STYLE=\"background-color:lightblue; height:10px; width:100%;\"></div>");
                                }
                            }
                        %>

                        <hr><hr>

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
                                        ...
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel Deletion</button>
                                        <a href="#deleteAllCamelotNotes.htm" class="btn btn-info" role="button">CONFIRM Deletion</a>   </div>
                                </div>
                            </div>
                        </div>


                        <hr><hr>
                        <a href="index.htm"><h4>INDEX</h4></a>



                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

    </body>
</html>

