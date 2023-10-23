


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Offer Dashboard</title>
        <style>
            input[type="text"]
            {
                font-size:30px;
            }
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    </head>
    <body>
    <center>

        <h1><a href="index.htm">INDEX</a></h1>

        <h1 style="background-color: ${resultColor}">${result}</h1>

        <h1 >${item.description}</h1>

        <form action="addOffer.htm" method="POST" >
            <input hidden name="code" type="text" value="${item.code}">
            <h2><h2><table>
                        <tr>
                            <td>Offer Titel</td>
                           
                        </tr>
                       
                       
                        <tr>
                            <td>Offer Title</td>
                            <td> <input type="text" name="title"> </td>
                        </tr>

                    </table>
                    <br>
                    <button type="submit" style="width:300px; height :60px; background-color: green"><h1>Add Item</h1></button>
                </h2>
        </form>

    </center>
</body>
</html>
