<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>  
    <title>Food</title>  
   </head>
  <body ng-app="foodOrder">
      <div ng-controller="FoodController as ctrl">
    
          <div>
              <div ><span >List of Food </span></div>
              <div>
                  <table>
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
       
     <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular.min.js"></script>
     <script src="<c:url value='/static/js/app.js' />"></script>
     <script src="<c:url value='/static/js/service/food_service.js' />"></script>
     <script src="<c:url value='/static/js/controller/food_controller.js' />"></script>
  </body>
</html>