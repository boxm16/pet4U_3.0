

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Position Changing Servant X</title>
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

                    <center>
                        <table>
                            <tr>
                                <td>

                                    <form action="changePet4uItemPositionX.htm"  method="POST">
                                        <input hidden name="itemId" value="${itemId}">
                                      Row  <input type="text" name="row" value="">
                                        <hr>
                                     Block Number   <input type="number" name="blockNumber" value="">
                                        <hr>
                                     Position Number   <input type="number" name="positionNumber" value="">
                                        <hr>
                                        <button type="submit" class="btn btn-primary">
                                            Set Position
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </table>
                        <hr>
                        <a href='index.htm'>Index</a>
                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>

        </div>
    </body>
</html>



