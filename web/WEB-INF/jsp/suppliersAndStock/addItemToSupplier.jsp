

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add  Item To Supplier</title>
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

        <h1>Add  Item To Supplier </h1>

        <form action="addItemToSupplier.htm" method="POST" >
            <h2><h2><table>
                        <tr>
                            <td>Item Code</td>
                            <td> <input type="text" name="code" value="${item.code}"> </td>
                        </tr>

                        <tr>
                            <td>Minimal Stock</td>
                            <td>  <input type="number" name="minimalStock" value="${item.minimalStock}">  </td> 
                        </tr>
                        <tr>
                            <td>Weight Coefficient</td>
                            <td>  <input type="number" name="weightCoefficient" value="${item.weightCoefficient}">  </td> 
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
                            <td>Ποσοτητά Παραγγελίας</td>
                            <td><input type="number" name="orderQuantity" value="${itemOfInterest.orderQuantity}"></td>
                        </tr>
                        <tr>
                            <td>
                                CAMELOT MINIMAL STOCK
                            </td>
                            <td>
                                <input type="number" class="form-control input-lg" name="camelotMinimalStock" value="${itemOfInterest.camelotMinimalStock}">
                            </td>

                        </tr>
                        <tr>
                            <td>
                                Note
                            </td>
                            <td>
                                <input type="text" name="note" value="${itemOfInterest.note}">
                            </td>
                        </tr>
                    </table>
                    <br>
                    <button type="submit" style="width:300px; height :60px; background-color: green"><h1>Add Item</h1></button>
                </h2>
        </form>

    </center>
</body>
</html>
