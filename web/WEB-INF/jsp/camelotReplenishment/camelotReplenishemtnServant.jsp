<%-- 
    Document   : orderServant
    Created on : Dec 10, 2022, 11:32:35 PM
    Author     : Michail Sitmalidis
--%>

<%@page import="Replenishment.Replenishment"%>
<%@page import="BasicModel.AltercodeContainer"%>
<%@page import="BasicModel.Item"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Replenishment Servant</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

        <style>

            input[type="text"] {
                font-size: 30px;
            }

            table, th, td {
                border: 1px solid ;
                border-collapse: collapse;

            }
            td{
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="container" >
            <div class="row">
                <div class=" col-sm-4">
                </div>
                <div class=" col-sm-4">

                    <center>
                        <table>
                            <tr>
                                <td>
                                    ${replenishment.description}
                                </td>
                            </tr>


                            <tr>
                                <td style='background-color: lightblue; font-size: 20px' >
                                    ${replenishment.position}
                                </td>
                            </tr>
                            <tr>
                                <td> <center>    <input type='text' readonly name='systemStock' style='font-size:30px; background-color: lightgreen' value='${replenishment.quantity}'></center></td>
                            </tr>
                            <tr style="background-color:#f955d4">
                                <td>MONADA ΑΝΑΠΛΗΡΩΣΗ:

                                    <h4> 
                                        <select disabled  name="replenishmentUnit" > 
                                            <option value="box" ${replenishment.replenishmentUnit == "box" ? 'selected="selected"' : ''}>ΚΟΥΤΙ-ΠΑΚΕΤΟ</option>
                                            <option value="pallet" ${replenishment.replenishmentUnit == "pallet" ? 'selected="selected"' : ''}>ΠΑΛΕΤΑ</option>
                                            <option value="item" ${replenishment.replenishmentUnit == "item" ? 'selected="selected"' : ''}>ΤΕΜΑΧΙΟ</option>
                                        </select>
                                    </h4>
                                </td>
                            </tr>
                            <tr style="background-color:#f955d4">
                                <td>ΤΕΜΑΧΙΑ ΣΤΗ ΜΟΝΑΔΑ ΑΝΑΠΛΗΡΩΣΗΣ:

                                    ${replenishment.itemsInReplenishmentUnit}  
                                </td>
                            </tr>
                            <tr>
                                <td>

                                    <form action="${saveType}" method="POST">

                                        <input type='number' name='replenishmentQuantity'  value='${replenishment.replenishmentQuantity}' style='font-size:30px' >
                                        <hr>
                                        <input name='itemCode' hidden value='${replenishment.code}'>
                                        Make notes - 500 char. max.
                                        <input type='text' name='note'>
                                        <hr>

                                        <input class='btn btn-primary' type='submit' value='MAKE REPLENISHMENT'>

                                    </form>
                                </td>
                            </tr>
                        </table>
                        <hr>
                        <a href='index.htm'>Index</a>
                    </center>
                </div>
                <div class=" col-sm-4">

                </div>
            </div>

        </div>
    </body>
</html>


