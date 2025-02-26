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
            /* Reset default margin and padding */
            html, body {
                margin: 0;
                padding: 0;
                height: 100%; /* Ensure the body takes full height */
            }

            /* Make the container fluid to span the full width */
            .container-fluid {
                padding: 0;
                margin: 0;
                width: 100%;
                height: 100%; /* Ensure the container takes full height */
                display: flex;
                align-items: center; /* Center content vertically */
                justify-content: center; /* Center content horizontally */
                background-color: #35B62F; /* Match the container background */
            }

            /* Adjust row height and center content */
            .row {
                width: 100%;
                margin: 0;
                padding: 20px; /* Add some padding around the columns */
            }

            /* Adjust column height */
            .col-sm-3 {
                background-color: #b9b984; /* Default background color */
                padding: 20px; /* Add padding inside columns */
                height: auto; /* Let the height adjust to content */
                margin: 10px; /* Add some margin between columns */
            }

            input[type="text"] {
                font-size: 30px;
            }
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3" style="background-color: #b9b984">
                    <h1>Εκτύπωση Μικρής Ετικέτας Θέσης</h1>
                    <h5 style='color: red; font-weight: bold'>Προσοχη, ετικέτα εκτυπώνετε άμεσα μετα από σκανάρισμα</h5>
                    <hr>
                    <form action="printSmallLabelsInARow.htm" method="GET">
                        <input type="text" class="form-control input-lg" name='altercode' ${psliarlAutofocus}>
                        <br>
                        <input class="btn btn-primary btn-lg btn-block" type="submit" value="PRINT">
                    </form>
                </div>
                <div class="col-sm-3" style="background-color: #39a0bf">
                    <h1>Εκτύπωση Ετικέτας Με Κωδικό Προϊόντος</h1>
                    <h5 style='color: red; font-weight: bold'>Προσοχη, ετικέτα εκτυπώνετε άμεσα μετα από σκανάρισμα</h5>
                    <hr>
                    <form action="printItemCodeLabel.htm" method="GET">
                        <input type="text" class="form-control input-lg" name='altercode' ${piclAutofocus}>
                        <br>
                        <input class="btn btn-primary btn-lg btn-block" type="submit" value="PRINT">
                    </form>
                </div>
                <div class="col-sm-3" style="background-color: #98d83a">
                    <h1>Εκτύπωση Κειμένου</h1>
                    <h5 style='color: blue; font-weight: bold'>Γράψε κείμενο (7 γράμματα max) και πάτησε Enter</h5>
                    <hr>
                    <form action="printText.htm" method="GET">
                        <input type="text" class="form-control input-lg" name='altercode' ${ptAutofocus}>
                        <br>
                        <input class="btn btn-primary btn-lg btn-block" type="submit" value="PRINT">
                    </form>
                </div>
                <div class="col-sm-3"></div>
            </div>
        </div>
    </body>
</html>