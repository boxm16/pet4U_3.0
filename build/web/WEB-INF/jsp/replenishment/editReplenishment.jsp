<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Replenishment</title>
    </head>
    <body>
    <center>
        <h1><a href="index.htm">INDEX</a></h1>
        <h1 style="background-color: ${resultColor}">${result}</h1>

        <form action="editReplenishmentMinimalStock.htm" method="POST" >
            <table>
                <tr>
                    <td>Item Code</td>
                    <td> ${replenishment.code}</td>
                <input type="text"  hidden name="itemCode" value="${replenishment.code}">   

                </tr>

                <tr>
                    <td>Minimal Stock</td>
                    <td>  <input type="number" name="minimalShelfStock" value="${replenishment.minimalShelfStock}">  </td> 
                </tr>




            </table>
            <br>
            <button type="submit" style="width:300px; height :60px; background-color: lightblue"><h1>Edit Replenishment</h1></button>

        </form>
        <hr>
    </center>
</body>
</html>
