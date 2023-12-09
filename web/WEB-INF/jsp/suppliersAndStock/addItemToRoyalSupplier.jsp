

<%@page import="SuppliersAndStock.SuppliersItem"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add  Item To Royal Supplier</title>
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
        <h1><a href="stockManagementDashboardd.htm">GO BACK</a></h1>
        <h1 style="background-color: ${resultColor}">${result}</h1>

        <h1>Add  Item To Supplier </h1>

        <form action="addItemToRoyalSupplier.htm" method="POST" >

            <h2><h2><table>
                        <tr>
                            <td>Item Code</td>
                            <td> <input style="background-color:lightgrey" readonly type="text" name="code" value="${item.code}"> </td>
                        </tr>
                        <tr>
                            <td>Description</td>
                            <td> <input width="120" style="background-color:lightgrey" readonly type="text" value="${item.description}"> </td>
                        </tr>



                        <tr>
                            <td>Ποσότητα Τεμαχίων σε Μονάδα Παραγγελίας</td>
                            <td><input type="number" name="orderUnitCapacity" value="${item.orderUnitCapacity}"></td>
                        </tr>
                        <tr>
                            <td>Note</td>
                            <td> <input type="text" name="note" value="${item.note}"> </td>
                        </tr>

                    </table>
                    <br>
                    <button type="submit" style="width:300px; height :60px; background-color: green"><h1>Add Item</h1></button>
                </h2>
        </form>

    </center>
</body>
</html>
