<%-- 
    Document   : labelPrintInRowDashboard
    Created on : Dec 20, 2024, 12:25:01 AM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Label Print In Row Dashboard</title>
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
        <div class="container" style="background-color: #35B62F">
            <div class="row">
                <center> <a href="pet4uDashboard.htm" ><h1>Pet4u Dashboard</h1></a></center>
                <div class=" col-sm-3">
                    <h1>Εκτύπωση Μικρής Ετικέτας Θέσης</h1>
                    <h1 style='color: red; font-weight:  bold'>Προσοχη, ετικέτα εκτυπώνετε άμεσα μετα από σκανάρισμα</h1>
                    <hr>
                    <form action="printSmallLabelsInARow.htm" method="GET" >
                        <input type="text" class="form-control input-lg" name='altercode' ${psliarlAutofocus}>
                        <br>
                        <input class="btn btn-primary btn-lg btn-block"  type="submit" value="PRINT">

                    </form>
                </div>
                <div class=" col-sm-3">
                    <center>

                        <hr>

                        <br>  <br> 
                        <hr>
                        <h1>Εκτύπωση  Ετικέτας Με Κωδικό Προϊόντος</h1>
                        <h1 style='color: red; font-weight:  bold'>Προσοχη, ετικέτα εκτυπώνετε άμεσα μετα από σκανάρισμα</h1>

                        <form action="printItemCodeLabel.htm" method="GET" >
                            <input type="text" class="form-control input-lg" name='altercode' ${piclAutofocus}>
                            <br>
                            <input class="btn btn-primary btn-lg btn-block"  type="submit" value="PRINT">

                        </form>
                    </center>
                </div>
                <div class=" col-sm-3">
                    <h1>Εκτύπωση Κειμένου</h1>
                    <h1 style='color: red; font-weight:  bold'>Προσοχη, ετικέτα εκτυπώνετε άμεσα μετα από σκανάρισμα</h1>
                    <h1 style='color: blue; font-weight:  bold'>Γράψε κείμενο (7 γράμματα max)</h1>

                    <hr>

                    <form action="printText.htm" method="GET" >
                        <input type="text" class="form-control input-lg" name='altercode' ${ptAutofocus}>
                        <br>
                        <input class="btn btn-primary btn-lg btn-block"  type="submit" value="PRINT">

                    </form>
                </div>
                <div class=" col-sm-3">

                </div>


            </div>
        </div>
    </center>
</body>
</html>
