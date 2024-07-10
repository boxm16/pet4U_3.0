<%@page import="Delivery.DeliveryInvoice"%>
<%@page import="java.util.ArrayList"%>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Delivery Dashboard</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

        <style>
            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;
            }
            td {
                font-size: 20px;
            }
            th{
                font-size: 30px;
                font-weight: bold;
                text-align: center;
                background: #eee;
                position: sticky;
                top: 0px;
            }

        </style>

    </head>
    <body>
    <center>
        <h1>Delivery Dashboard</h1>
        <h4>ROYAL deliveryExport.xlsx file data</h4>
        <h4>Last modified at:${timeStamp}</h4>
        <hr>
        <button class='btn-primary' onclick="location.href = 'loadRoyalData.htm'" type="button">   
            <h1>  Load Data  </h1> </button>
        <hr>     <hr>     <hr>     <hr>     <hr>     <hr>
       
        <form action="deliveryInvoicesForDate.htm" method="POST">
            <h1>  <input type="date"  name="date" value="${date}"></h1>
            <hr>
            <button type="submit" class="btn btn-danger"> <h1> Delivery Dashboard </h1></button>
        </form>
        <hr>     <hr>     <hr>     <hr>     <hr>     <hr>
        <h4>All Deliveries</h4>
        <table>
            <thead>


                <tr> 

                    <th>Invoice Id</th>
                    <th>Supplier</th>
                    <th>Invoice Number</th>

                </tr>
            </thead>
            <tbody id="tableBody">
                <%

                    ArrayList<DeliveryInvoice> allCheckedDeliveryInvoices = (ArrayList) request.getAttribute("allCheckedDeliveryInvoices");

                    for (int x = allCheckedDeliveryInvoices.size() - 1; x > -1; x--) {
                        DeliveryInvoice deliveryInvoice = allCheckedDeliveryInvoices.get(x);
                        out.println("<tr>");

                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println("<a href='loadCheckedRoyalDataFromDatabaseByInvoiceId.htm?invoiceId=" + deliveryInvoice.getInvoiceId() + "'>" + deliveryInvoice.getInvoiceId() + "</a>");
                        out.println("</td>");

                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println(deliveryInvoice.getSupplier());
                        out.println("</td>");

                        out.println("<td style='padding-left: 5px; padding-left: 5px;'>");
                        out.println(deliveryInvoice.getNumber());
                        out.println("</td>");

                        out.println("</tr>");

                    }
                %>
            </tbody>
        </table>
    </center>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>