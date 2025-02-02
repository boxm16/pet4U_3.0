

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Position Changing Servant</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <style>

            input[type="text"] {
                font-size: 30px;
            }
            input[type="number"] {
                font-size: 30px;
            }

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
        <div class="container" >
            <div class="row">
                <div class=" col-sm-4">
                </div>
                <div class=" col-sm-4">
                    <center>  <h1>Set New Position <br>For Camelot Item</h1></center>
                    <center>
                        <table style='background-color: #D052DB'>
                            <tr>
                                <td>

                                    <form action="confirmCamelotItemPositionChanging.htm"  method="POST">
                                        <input hidden name="itemCode" value="${itemCode}">
                                        <h1> Enter Exact Position Name  </h1>
                                        <hr>
                                        <input type="text" name="pickingPositionName" value="">
                                        <hr>
                                        <h1>      
                                            <button type="submit" class="btn btn-primary">
                                                Set  Position
                                            </button>
                                        </h1>
                                    </form>
                                </td>
                            </tr>
                        </table>
                        <hr>
                        <a href='index.htm'><h3>Index</h3></a>
                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>

        </div>
    </body>
</html>



