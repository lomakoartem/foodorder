<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>  
    <title>Food</title>
    <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
      
   </head>
  <body ng-app="foodOrder">
  <div class="container">
      <div ng-controller="FoodController as ctrl">
    
          <div>
              <div ><span ><h1>List of Food</h1> </span></div>
              <div>
                  <table class="table table-striped">
                      <thead>
                          <tr>
                              <th>ID.</th>
                              <th>Name</th>
                              <th>Price</th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="f in ctrl.food">
                              <td><span ng-bind="f.id"></span></td>
                              <td><span ng-bind="f.name"></span></td>
                              <td><span ng-bind="f.price"></span></td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
      </div>
       
     <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular.min.js"></script>
     <script src="<c:url value='/static/js/app.js' />"></script>
     <script src="<c:url value='/static/js/service/food_service.js' />"></script>
     <script src="<c:url value='/static/js/controller/food_controller.js' />"></script>
  </body>
</html>