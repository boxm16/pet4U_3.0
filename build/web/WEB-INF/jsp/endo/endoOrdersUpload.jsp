<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Endo Uploads</title>
    </head>
    <body>

    <center>
        <h1> <a href="index.htm">INDEX</a></h1>
        <hr>
        <h1>${uploadTitle}</h1>
        <hr>

        <h2 style="color:red">${uploadStatus}<br>${errorMessage}</h2>
        <form action="${uploadTarget}" method="POST" enctype="multipart/form-data">  
            <h1>SELECT  DAY OF UPLOAD </h1>
            <input type="date"  name="date" value='${date}'>

            <hr>
            <h1>SELECT FILE FOR UPLOAD</h1>
            <input type="file" name="file"/>  
            <input type="submit" value="Upload"/>  
        </form>  
    </center>
</body>
</html>