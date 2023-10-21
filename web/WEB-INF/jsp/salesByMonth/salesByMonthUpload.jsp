<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Uploads Dashboard</title>
    </head>
    <body>

    <center>
        <h1> <a href="index.htm">INDEX</a></h1>
        <hr>
        <h1>${uploadTitle}</h1>
        <h2>SELECT FILE FOR UPLOAD</h2>
        <h2 style="color:red">${uploadStatus}<br>${errorMessage}</h2>
        <form action="${uploadTarget}" method="POST" enctype="multipart/form-data">  

            <input type="file" name="file"/>  
            <input type="submit" value="Upload"/>  
        </form>  
    </center>
</body>
</html>