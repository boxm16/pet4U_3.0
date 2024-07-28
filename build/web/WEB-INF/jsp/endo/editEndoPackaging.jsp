<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Endo Packaging</title>
        <style>
            input {
                font-size: 40px;
                width: 50em;
            }
        </style>
    </head>
    <body>
    <center>
        <h1><a href="index.htm">INDEX</a></h1>
        <h1>Endo Packaging</h1>
        <h1 style="background-color: ${resultColor}">${result}</h1>

        <form action="${saveType}" method="POST" >
            <h2><table>
                    <tr>
                        <td>Item Code</td>
                        <td> <input type="text" name="code" value="${endoPackaging.itemCode}"> </td>
                    </tr>

                    <tr>
                        <td>Items</td>
                        <td>  <input type="number" name="item" value="${endoPackaging.item}">  </td> 
                    </tr>
                    <tr>
                        <td>Labels</td>
                        <td>  <input type="number" name="label" value="${endoPackaging.label}">  </td> 
                    </tr>


                </table>
                <br>
                <button type="submit" style="width:300px; height :60px; background-color: lightblue"><h1>Edit Item Packaging</h1></button>
            </h2>
        </form>
        <hr>
        <hr>   <hr>   <hr>   <hr>   <hr>   <hr>   <hr>   <hr>
        <h1 ><a href="deleteEndoPackaging.htm?code=${endoPackaging.itemCode}" style="color:red">DELETE Endo Packaging</a></h1>
    </center>
</body>
</html>
