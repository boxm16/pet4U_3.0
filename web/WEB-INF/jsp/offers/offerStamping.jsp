


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Offer Stamping</title>
        <style>
            input[type="text"]
            {
                font-size:30px;
            }
            input[type="date"]
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

        <h1>Create New Offer For:</h1>




        <h1 style="background-color: ${resultColor}">${result}</h1>

        <form action="stampOffer.htm" method="POST" target="_blank">

            <table>  
                <tr>
                    <td><h2>Offer Title</h2></td>
                    <td> <input type="text" name="title" width="80"> </td>
                </tr>
                <tr>
                    <td>----------</td>
                    <td>---------------------------------------------------</td>

                </tr>
                <tr>
                    <td><h2>Start Date</h2></td>
                    <td> <input type="date" name="startDate"> </td>
                </tr>
                <tr>
                    <td><h1> Add (Exact) Item Code</h1></td>
                    <td>   <input  name="code" type="text" value="${item.code}"> </td>
                </tr>
                
                  <tr>
                    <td><h1> Add (Exact)Code Of Offer Part Item   </h1></td>
                    <td>   <input  name="offerPartCode" type="text" value="${item.code}"> </td>
                </tr>
            </table>
            <br>
            <button class="btn btn-primary" type="submit" ><h1>Stamp Offer For The Code</h1></button>
            </h2>
        </form>

    </center>
</body>
</html>
