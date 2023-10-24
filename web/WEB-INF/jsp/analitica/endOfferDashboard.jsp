


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>End Offer</title>
        <style>

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

        <h1>End Offer For:</h1>


        <h1 >${item.code} : ${item.description}</h1>
        <h1 style="background-color: ${resultColor}">${result}</h1>

        <form action="endOffer.htm" method="POST" >
            <input hidden name="code" type="text" value="${item.code}">
            <table>  
                <tr>
                    <td><h2>Offer Title</h2></td>
                    <td><h2>${offer.title}</h2></td>
                </tr>
                <tr>
                    <td>----------</td>
                    <td>---------------------------------------------------</td>

                </tr>
                <tr>
                    <td><h2>Start Date</h2></td>
                    <td> ${offer.getStartDateString()}</td>
                </tr>
                <tr>
                    <td>----------</td>
                    <td>---------------------------------------------------</td>

                </tr>
                <tr>
                    <td><h2>End Date</h2></td>
                    <td> <input type="date" name="endDate"> </td>
                </tr>
            </table>
            <br>
            <button class="btn btn-primary" type="submit" ><h1>End Offer</h1></button>
            </h2>
        </form>

    </center>
</body>
</html>
