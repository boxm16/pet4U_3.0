

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Supplier</title>
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

        <h1>Add Supplier</h1>

        <form action="addSupplier.htm" method="POST" >
            <h2><h2><table>
                        <tr>
                            <td>ΟΝΟΜΑΣΙΑ</td>
                            <td> <input type="text" name="name" value="${supplier.name}"> </td>
                        </tr>
                        <tr>

                            <td colspan="2"> <center> ---------------------------------------------</center> </td> 
                        </tr>
                        <tr>
                            <td>ΑΦΜ</td>
                            <td>  <input type="number" name="afm" value="${supplier.afm}">  </td> 
                        </tr>
                    </table>
                    <br>
                    <button type="submit" style="width:300px; height :60px; background-color: green"><h1>Add Supplier</h1></button>
                </h2>
        </form>

    </center>
</body>
</html>
