

<%@page import="SuppliersAndStock.SuppliersItem"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Supplier`s Item</title>
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

        <h1>Edit Item Of Supplier: ${supplier.name}</h1>

        <form action="editItemOfSupplier.htm" method="POST" >
            <input hidden name="supplierId" type="text" value="${supplier.id}">
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
                            <td>Sales For 6 Months</td>

                            <td> <input style="background-color:lightgray" readonly type="number" value="${item.getTotalShippedPieces()}"> </td>
                        </tr>
                        <tr>
                            <td>Sales For 2 weeks</td>
                            <td> <input style="background-color:lightgray" readonly type="number" value="${item.getTotalShippedPiecesForPeriod()}"> </td>
                        </tr>

                        <tr>
                            <td>Minimal Stock</td>
                            <td>  <input  type="number" name="minimalStock" value="${item.minimalStock}">  </td> 
                        </tr>
                        <tr>
                            <td>
                                <label for="orderUnit">Μοναδα Παραγγελίας</label>
                            </td>

                            <td>
                                <select name="orderUnit" > 
                                    <option value="box" ${item.orderUnit == "box" ? 'selected="selected"' : ''}>ΚΟΥΤΙ-ΠΑΚΕΤΟ</option>
                                    <option value="pallet" ${item.orderUnit == "pallet" ? 'selected="selected"' : ''}>ΠΑΛΕΤΑ</option>
                                    <option value="item" ${item.orderUnit == "item" ? 'selected="selected"' : ''}>ΤΕΜΑΧΙΟ</option>
                                </select>
                            </td>
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
                    <button type="submit" style="width:300px; height :60px; background-color: yellowgreen"><h1>Edit Item</h1></button>
                </h2>
        </form>
        <hr>
        ${supplier.id}
        <hr>
        <a href="deleteSupplierItem.htm?supplierId=${supplier.id}&itemCode=${item.code}" class="btn btn-danger" role="button"><h1>DELETE ITEM</h1></a>
    </center>
</body>
</html>
