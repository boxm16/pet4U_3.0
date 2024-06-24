<%-- 
    Document   : deliveryPreloadCheck
    Created on : Jul 22, 2023, 5:17:24 PM
    Author     : Michail Sitmalidis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delivery Preload Checking</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    </head>
    <body>
    <center>
        <h1>Delivery Preload Checking</h1>
        <hr>
        <h4>It seems the delivery invoice you are going to check, has already been checked</h4>
        <h4>Invoice Id ${checkedInvoiceId}</h4>
        <hr>
      <h1>   <button class='btn-primary' onclick="location.href = 'reloadRoyalData.htm'" type="button">
             ReLoad Same Invoice (Not Checked)</button></h1>
        <hr>
       <h1>  <button class='btn-success' onclick="location.href = 'loadCheckedDataFromDatabase.htm?invoiceId=${checkedInvoiceId}'" type="button">
            Load From Database SAVED (Already Checked) Invoice </button></h1>
        <hr>
       <h1>  <button class='btn-danger' onclick="location.href = 'checkDataFromViewAndDatabase.htm?invoiceId=${checkedInvoiceId}'" type="button"></h1>
            JOINT LOAD- Load 'Sent' Data From The View, But 'Delivered' Data From Saved (Checked) Data </button>
        <hr>

    </center>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>