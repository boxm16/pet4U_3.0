<%@page import="Service.Scheduler"%>
<%@page import="Service.StaticsDispatcher"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Camelot Dashboard</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

    </head>

    <body>
        <%
            if (!StaticsDispatcher.isTimerOn()) {
                Scheduler scheduler = new Scheduler();
                scheduler.startScheduledTasks();
                StaticsDispatcher.setTimerOn();
            }
        %>
        <div class="container">
            <center>
                <hr> 
                <a href="searchDashboard.htm" class="btn btn-primary" role="button" style='background-color: green;'><h1>Find Pet4u Items</h1></a>
                <hr>
                <a href="camelotSearchDashboard.htm" class="btn btn-primary" role="button" style='background-color: #762276;'><h1>Find Camelot Items</h1></a>
                <hr>
                <a href="camelotShelvesReplenishment.htm" class="btn btn-primary" style='background-color: #ab48aa' role="button"><h1>Αναπλήρωση Camelot</h1></a>
                <hr>
                <a href="camelotNotesCardMode.htm" class="btn btn-primary" role="button" style='background-color: #3166df'><h1>Camelot Notes<br>Card Mode</h1></a>
                <hr>
                <a href="camelotNotesDisplay.htm" class="btn btn-primary" role="button" style='background-color: #B48199'><h1>Camelot Notes</h1></a>
                <hr>
                <a href="camelotStockPositionsDisplay.htm"><h1>Camelot Stock Positions By Position </h1></a>
                <hr>
                <a href="camelotStockPositionsByItemCodeDisplay.htm"><h1>Camelot Stock Positions By Item Code </h1></a>
                <hr>
                Camelot: All Items
                Σημαντική επισήμανση. Εδώ είναι όλοι οι κωδικοί ΠΟΥ ΜΟΥ ΕΡΧΟΝΤΑΙ ΜΕΣΩ VIEW. Που είναι περίπου 1/3 από ΟΛΟΥΣ τους κωδικούς. Οι υπόλοιπου 2/3 κατά 99.99…%  είναι  πλέον άχρηστοι(ανενεργοί). Αλλά που και που μέσα σε αυτούς τους «άχρηστους» μπορεί να  υπάρχει και ακόμα κάποιος «ζωντανός» κωδικός. Όμως δε ζαλίζω πολύ τον Βασίλη για αυτό γιατί έχω στη σειρά και άλλα πολύ ποιο σημαντικά πράγματα να του ζητήσω, οπότε το αφήνω έτσι. (του ζήτησα πολλές φορές να μου έρχονται ΟΛΑ,  αλλά για κάποιο λόγο δυσκολεύετε)
                <a href='camelotAllItems.htm'> <h1>Camelot: All Items</h1></a>3633-18
                <hr>
                <a href="index.htm"><h1>Index</h1></a>

            </center>
        </div>
    </body>
</html>
