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
HLDAccessoryDebugDetail

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

  <c:set var="siteName" value="Holden.com.au" />

  <holden:property var="countryCode" name="country.code" />

  <c:if test="${'nz' eq countryCode}">
    <c:set var="siteName" value="Holden.co.nz" />
  </c:if>


<!--  Bootstrap -->
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">

<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//fgnass.github.io/spin.js/spin.min.js"></script>

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

<br><br>					
<div class= "container" ng-app="kenApp" ng-init="pleaseSelect='Holden Accessories:'">	
 	

 <div ng-controller="MyController" >
  <%--
    <button ng-click="myData.doClick(item, $event)">Send AJAX Request</button>
    <br/>
  --%>	   
    
   <!-- Data from server: {{myData}}  --> 
  <script>
    angular.module("kenApp", [])
        .controller("MyController", function($scope, $http) {
        	//alert("before call");
        	$scope.myData = {};
            //$scope.myData.doClick = function(id, event) {
		    $scope.doClick = function(id, event) {
				//alert("call");
                var responsePromise = $http.get("/cs/api/2/vehicles/1367542259913/models/"+ id +"/accessories?expand=true");
		
                responsePromise.success(function(data, status, headers, config) {
                    $scope.myData = data;
                    
            	   $('#modelName').data('spinner').stop();
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
  
  <script>
  //https://docs.angularjs.org/guide/scope
  $(document ).ready(function() {
	  console.log( "ready!" );  
  });  
  
  var opts = {
		  lines: 13, // The number of lines to draw
		  length: 20, // The length of each line
		  width: 14, // The line thickness
		  radius: 30, // The radius of the inner circle
		  corners: 1, // Corner roundness (0..1)
		  rotate: 0, // The rotation offset
		  direction: 1, // 1: clockwise, -1: counterclockwise
		  color: '#000', // #rgb or #rrggbb or array of colors
		  speed: 1, // Rounds per second
		  trail: 60, // Afterglow percentage
		  shadow: false, // Whether to render a shadow
		  hwaccel: false, // Whether to use hardware acceleration
		  className: 'spinner', // The CSS class to assign to the spinner
		  zIndex: 2e9, // The z-index (defaults to 2000000000)
		  top: '50%', // Top position relative to parent
		  left: '50%' // Left position relative to parent
		};
  
  function callAngular(data){
	  //alert(1);
	  //alert(data.value);
	  //alert(data);
	  //var asset = angular.element(data);
	  if(isNaN(data.value)){  
		  return;
	  }
	  
	  var scope = angular.element(data).scope();
	  
	  scope.$apply(function(){
		  //alert(2);
		  scope.doClick(data.value, Math.random()); 
	    }) 
	   //alert(data.options[data.selectedIndex].innerHTML);
	   //alert(document.getElementById("modelName"));
	   document.getElementById("modelName").textContent = data.options[data.selectedIndex].innerHTML;
	   
	   var target = document.getElementById('modelName');
	   var spinner = new Spinner(opts).spin(target);
	   $(target).data('spinner', spinner);
	   
	   //document.getElementById("modelName").innerHTML("ken");
	  //alert(asset);
	  //angular.element(data).scope().$apply().myData.doClick(data.value, Math.random()); 
  }      
  </script>
 	
<!-- Contextual button for informational alert messages -->
<div class="row">
	<p class="btn btn-info col-md-12">Holden Accessories Debug Page for ${siteName} !!</p>
	<br><br><br>
</div>
	<vehicle:list var="vehicles" sortField="VehicleGridPosition" ascending="true" pubid="${pubid}" fetchAll="false"/>
	
	<c:forEach var="vehicle" items="${vehicles}" varStatus="status">
		<vehicle:listmodels vehicleId="${vehicle.id}" var="vehicleModels" fetchAll="false"/>
		<div class="row">
		   <div>  
			<label class="col-md-2">Vehicle ${vehicle.name}: </label>
			<div class="col-md-10"> 
			<select class="form-control input-sm selectModel"  onchange="callAngular(this);">
			    <option selected>Please select Model</option>
			    <c:forEach items="${vehicleModels}" var="vehicleModel">				
					 <option value="${vehicleModel.id}">
						 <vehicle:loadmodel id="${vehicleModel.id}" var="readOnlyModel" fetchAll="false" />${readOnlyModel.name}
					 </option>
		    	</c:forEach>
			  </select> 
			 </div> 
			</div>
			<br>
		</div>
			<br>
	</c:forEach>	
	
	<h2><strong ng-bind="pleaseSelect"></strong></h2><h2 id="modelName">Please Select the Model Above</h2>
	
	<div class="bs-example bs-example-tabs">
	    <ul id="myTab" class="nav nav-tabs" role="tablist">
	      <li class=""><a href="#home" role="tab" data-toggle="tab">Exterior</a></li>
	      <li class="active"><a href="#profile" role="tab" data-toggle="tab">Interior</a></li>
	    </ul>
	    <div id="myTabContent" class="tab-content">
	      <div class="tab-pane fade" id="home">
	       	<div class="summary">
	       	
	       		<%-- if the accessories Category Name is small --%>
	       		<%--
	       	
				<ul>
				  <li ng-repeat="x in myData | filter:isExterior | orderBy:'sortOrder'">
				
					{{x.categoryName}} - <a target="_blank" href="/cs/REST/sites/${siteName}/types/Accessory_C/assets/{{ (x.id | uppercase) }}" > {{ (x.name | uppercase) + ', ' + x.sortOrder }}     {{ (x.id | uppercase) }} </a>
				  </li>
				</ul>
			 --%>
			<%-- starter --%>
			<div class="container">	
				<div class="row">
					<div class="col-md-12">
						<table class="table table-striped table-hover">
							<thead>
								<tr>
									<th>&nbsp;</th>
									<th>Category</th>
									<th>Name</th>
									<th>Accessory ID</th>
									<th>Inspect</th>
									<th>&nbsp;</th>
								</tr>
							</thead>
					            <tr ng-repeat="x in myData | filter:isExterior | orderBy:['sortOrder', 'categoryName', 'price', 'name']">
					            	<td>&nbsp;</td>
									<td>{{x.categoryName}} </td>
									<td> {{ (x.name | uppercase) }}   </td>
									<td> {{ ((x.id | uppercase) }}   </td>
									<td><a href="/cs/REST/sites/${siteName}/types/Accessory_C/assets/{{ (x.id | uppercase) }}" target="_blank">Inspect Accessory</a></td>
									<td>&nbsp;</td>
								</tr>
	
						</table>
					</div>
					
					<div class="col-md-4">
					</div>
				</div>
			</div>	
			
			
			<%-- end  --%>
			</div>
	      </div>
	      <div class="tab-pane fade active in" id="profile">
	     	<div class="summary">
			
			<div class="container">	
				<div class="row">
					<div class="col-md-12">
						<table class="table table-striped table-hover">
							<thead>
								<tr>
									<th>&nbsp;</th>
									<th>Category</th>
									<th>Name</th>
									<th>Accessory ID</th>
									<th>Inspect</th>
									<th>&nbsp;</th>
								</tr>
							</thead>
					            <tr ng-repeat="x in myData | filter:isInterior | orderBy:['sortOrder', 'categoryName', 'price', 'name']">
					            	<td>&nbsp;</td>
									<td>{{x.categoryName}} </td>
									<td> {{ (x.name | uppercase) }}   </td>
									<td> {{ ((x.id | uppercase) }}   </td>
									<td><a href="/cs/REST/sites/${siteName}/types/Accessory_C/assets/{{ (x.id | uppercase) }}" target="_blank">Inspect Accessory</a></td>
									<td>&nbsp;</td>
								</tr>
	
						</table>
					</div>
					
					<div class="col-md-4">
					</div>
				</div>
			</div>	
			
			
			<%--
			
			
				<ul>
				  <li ng-repeat="x in myData | filter:isInterior | orderBy:'sortOrder'">
				 	{{x.categoryName}} - <a target="_blank" href="/cs/REST/sites/${siteName}/types/Accessory_C/assets/{{ (x.id | uppercase) }}" > {{ (x.name | uppercase) + ', ' + x.sortOrder }}     {{ (x.id | uppercase) }} </a>
				  </li>
				</ul>
				
		 --%>		
			</div>
	      </div>
	    </div>
	  </div>
</div>	
<br><br>
</div>

</body>
</html>				
</cs:ftcs>
