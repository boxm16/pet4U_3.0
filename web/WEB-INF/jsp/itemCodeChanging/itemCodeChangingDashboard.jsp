<%-- 
    Document   : itemCodeChangingDashboard
    Created on : Sep 22, 2024, 4:39:20 AM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Item Code</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    </head>
    <body>
    <center>
        <h1>Change Item Code</h1>
        <hr>
        <form action="changeItemCode.htm" method="POST">
            <h1>OLD ITEM CODE  <input type="text" name='oldItemCode' ></h1>
            <hr>
            <h1>NEW ITEM CODE  <input type="text" name='newItemCode'></h1>
            <button type="submit" class="btn btn-secondary"> <h1> Change Item Code In MySQL DataBase Tables </h1></button>
        </form>
        <hr><hr>



        <hr><hr>
        <form action="changeItemCodes.htm" method="POST" enctype="multipart/form-data">  
            <h1>Upload Excel File With Old And New Item Codes</h1>
            <input type="file" name="file"/>  
            <input type="submit" value="ატვირთვა"/>   
        </form> 
        <hr><hr>
        <h1>  ${result} </h1>
        <h1>  ${errorMessage} </h1>

    </center>
</body>
</html>
