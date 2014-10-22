<%@ taglib prefix="cs" uri="futuretense_cs/ftcs1_0.tld"%>
<%@ taglib prefix="asset" uri="futuretense_cs/asset.tld"%>
<%@ taglib prefix="assetset" uri="futuretense_cs/assetset.tld"%><%@ taglib
	prefix="commercecontext" uri="futuretense_cs/commercecontext.tld"%><%@ taglib
	prefix="ics" uri="futuretense_cs/ics.tld"%><%@ taglib
	prefix="listobject" uri="futuretense_cs/listobject.tld"%><%@ taglib
	prefix="render" uri="futuretense_cs/render.tld"%><%@ taglib
	prefix="searchstate" uri="futuretense_cs/searchstate.tld"%>
	<%@ taglib prefix="siteplan" uri="futuretense_cs/siteplan.tld"%>
	<%@ taglib prefix="price" uri="http://www.holden.com.au/tags/price"%>
	<%@ taglib prefix="vehicle" uri="http://www.holden.com.au/tags/vehicle"%>	
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" 
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" 
%><%@ taglib prefix="vehicledata" uri="http://www.holden.com.au/tags/vehicledata"
%><%@ taglib prefix="holden" uri="http://www.holden.com.au/tags"%>
<%@ page import="java.util.*"%>  
<%@ taglib
	prefix="holden" uri="http://www.holden.com.au/tags"%><%@ page
	import="COM.FutureTense.Interfaces.*,
                   COM.FutureTense.Util.ftMessage,
                   COM.FutureTense.Util.ftErrors"%><cs:ftcs><%--

INPUT

OUTPUT

--%>
<%-- Record dependencies for the SiteEntry and the CSElement --%>
<ics:if condition='<%=ics.GetVar("seid")!=null%>'><ics:then><render:logdep cid='<%=ics.GetVar("seid")%>' c="SiteEntry"/></ics:then></ics:if>
<ics:if condition='<%=ics.GetVar("eid")!=null%>'><ics:then><render:logdep cid='<%=ics.GetVar("eid")%>' c="CSElement"/></ics:then></ics:if>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Accessories Debug Page</title>

</head>

<body>

<!--  Bootstrap -->
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">

<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<!-- Angular JS -->
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.15/angular.min.js"></script>
<%-- 
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
--%>


	
<%-- 
<div class="dropdown">
  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
    Dropdown
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Action</a></li>
    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Another action</a></li>
    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Something else here</a></li>
    <li role="presentation" class="divider"></li>
    <li role="presentation"><a role="menuitem" tabindex="-1" href="#">Separated link</a></li>
  </ul>
</div>	
--%>
<br><br>					
<div class= "container" ng-app="kenApp" ng-init="pleaseSelect='Accessories'" ng-controller="namesController">	
 	

 <div ng-controller="MyController" >
  <%--
    <button ng-click="myData.doClick(item, $event)">Send AJAX Request</button>
    <br/>
  --%>	   
    
   <!-- Data from server: {{myData}}  --> 
  <script>
    angular.module("kenApp", [])
        .controller("MyController", function($scope, $http) {
            $scope.myData = {};
            $scope.myData.doClick = function(id, event) {
		
				alert("call");
                var responsePromise = $http.get("/cs/api/2/vehicles/1367542259913/models/"+ id +"/accessories?expand=true");
		
                responsePromise.success(function(data, status, headers, config) {
                    $scope.myData = data;
                    
                    $scope.isExterior = function(data) {
                		
        				//alert("isExterior call");
                        return data.accessoryFamily === "Exterior";
                    };
                    
                    
                    $scope.isInterior = function(data) {
                    	
                		//alert("isInterior call");
                        return data.accessoryFamily === "Interior";
                    };
                    
                });
                responsePromise.error(function(data, status, headers, config) {
                    alert("AJAX failed!");
                });
            }


        } );
    

    
  </script>
 	
<!-- Contextual button for informational alert messages -->
<div class="row">
	<p class="btn btn-info col-md-12">Holden Accessories Debug Page !</p>
	<br><br><br>
</div>
	<vehicle:list var="vehicles" sortField="VehicleGridPosition" ascending="true" pubid="${pubid}" fetchAll="false"/>
	
	<c:forEach var="vehicle" items="${vehicles}" varStatus="status">
		<vehicle:listmodels vehicleId="${vehicle.id}" var="vehicleModels" fetchAll="false"/>
		<div class="row">
		   <div>  
			<label class="col-md-2">Vehicle ${vehicle.name}: </label>
			<div class="dropdown col-md-10">
			  <p class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
			    Please select Model
			    <span class="caret"></span>
			  </p>
			  <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">		    
			    <c:forEach items="${vehicleModels}" var="vehicleModel">				
					<li role="presentation">
						<a role="menuitem" tabindex="-1" href="#${vehicleModel.id}" ng-click="myData.doClick(${vehicleModel.id}, $event)">
						<vehicle:loadmodel id="${vehicleModel.id}" var="readOnlyModel" fetchAll="false" />${readOnlyModel.name}</a>
					</li>
		    	</c:forEach>
			  </ul>
			  </div>
			</div>
			<br>
		</div>
			<br>
	</c:forEach>	
	

	<p><h2><strong ng-bind="pleaseSelect"> for </strong></h2></p>
	
	<div class="bs-example bs-example-tabs">
	    <ul id="myTab" class="nav nav-tabs" role="tablist">
	      <li class=""><a href="#home" role="tab" data-toggle="tab">Exterior</a></li>
	      <li class="active"><a href="#profile" role="tab" data-toggle="tab">Interior</a></li>
	    </ul>
	    <div id="myTabContent" class="tab-content">
	      <div class="tab-pane fade" id="home">
	       	<div class="summary">
				<ul>
				  <li ng-repeat="x in myData | filter:isExterior | orderBy:'sortOrder'">
				    {{ (x.name | uppercase) + ', ' + x.sortOrder }}
				    {{ (x.id | uppercase) }}
				  </li>
				</ul>
			</div>
	    
	      </div>
	      <div class="tab-pane fade active in" id="profile">
	     	<div class="summary">
				<ul>
				  <li ng-repeat="x in myData | filter:isInterior | orderBy:'sortOrder'">
				    {{ (x.name | uppercase) + ', ' + x.sortOrder }}
				    {{ (x.id | uppercase) }}
				  </li>
				</ul>
			</div>
	      </div>
	    </div>
	  </div>



<%--
<p>Filtering input:</p>

<p><input type="text" ng-model="test"></p>
<ul>
  <li ng-repeat="x in names | filter:test | orderBy:'country'">
    {{ (x.name | uppercase) + ', ' + x.country }}
  </li>
</ul>
 --%>
	
</div>	
<br><br>


<script>
//$(document).foundation();
function namesController($scope) {
    $scope.names = [
        {name:'Jani',country:'Norway'},
        {name:'Hege',country:'Sweden'},
        {name:'Kai',country:'Denmark'}
    ];
}

</script>

  </div>

</body>
</html>				
</cs:ftcs>
