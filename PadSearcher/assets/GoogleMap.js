/*
 * Creacion del mapa y marcadores del programa
 * Oscar Perez La Madrid 	 54268894A
 * Angel Luis  Ortiz Folgado 50774922F
 */
window.addEventListener("load",function() {
	function initialize() {
		
		var directionsDisplay = new google.maps.DirectionsRenderer();
		var directionsService = new google.maps.DirectionsService();
		var myPosition = new google.maps.LatLng(window.myHandler.giveMeLat(),window.myHandler.giveMeLong());
		var miMarcadorGlobal = '';
		
		
		
		
		
		//====Creamos e inicializamos las variables globales.====
		
	 	//Mapa
		var map;
		
		//Datos de los marcadores.
		var miString = window.myHandler.giveMeData();
		
		//Nombre del archivo xml que hemos procesado.
		var archivo = window.myHandler.giveMeFileName();
		
		var latitud;
		var longitud;
		

		/*
		 * Creamos una variable marcadadores 
		 * con la ubicacion de las famarcias o aparcamientos
		 * 
		 */
		JSON.stringify(eval("var marcadores = " + miString + ";"));
		
    //==========================================Calculamos nuestra ruta ==================================
		//Generamos la ruta desde nuestra posicion hasta el marcador elegido
		function calcRoute(destino) {
			miMarcadorGlobal.setMap(null);
			miMarcadorGlobal = crearMiMarcador();
			  var start = new google.maps.LatLng(window.myHandler.giveMeLat(),window.myHandler.giveMeLong());
			  var end = destino;
			  //Inicializamos el request
			  var request = {
			      origin:start,
			      destination:end,
			      travelMode: google.maps.TravelMode.DRIVING
			  };
			  directionsService.route(request, function(response, status) {
			    if (status == google.maps.DirectionsStatus.OK) {
			      directionsDisplay.setDirections(response);
			    }
			  });
			}
		
		
	//==================================================OBTENEMOS NUESTRA GEOLOCALIZACION==================
		
		function getLocation() {
		    if (navigator.geolocation) {
		        navigator.geolocation.getCurrentPosition(showPosition);
				
		    } else {
		        x.innerHTML = "Geolocation is not supported by this browser.";
		    }
		}
		
		function showPosition(position) {
			latitud = position.coords.latitude;
			longitud = position.coords.longitude;
			
		}
	//======================================================   CREAR MARCADOR   ==========================
		var crearMiMarcador = function(){
			var marker = new google.maps.Marker({
			    position: new google.maps.LatLng(window.myHandler.giveMeLat(), window.myHandler.giveMeLong()),
			    map: map,
			    optimized: false,
			    visible: true,
			  });
			marker.setIcon("http://i.stack.imgur.com/3ppnM.png");
			
			return marker;
		}
		
		
		
		
	//======================================================   CREAR MAPA   ==========================	
		var crearMapa = function(){
			 var mapProp = {
						center:new google.maps.LatLng("40.452927","-3.733422"),
					    zoom:13,
					    draggable: true,
					    mapTypeId:google.maps.MapTypeId.ROADMAP
					   
					  };
					  
					  map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
					  directionsDisplay.setMap(map);
			
		}

	var infowindow = new google.maps.InfoWindow({  
	  content: ''
	});


	//======================================================CREAR   MARCADORES=======================
	var crearMarcadores = function(){
	for (var i = 0, j = marcadores.length; i < j; i++) {  
	  var contenido = marcadores[i].contenido;
	  var marker = new google.maps.Marker({
	    position: new google.maps.LatLng(marcadores[i].position.lat, marcadores[i].position.lng),
	    map: map,
	    title:marcadores[i].title,
	    optimized: false,
	    visible: true,
	    tipo: archivo
	  });
	  
	  (function(marker, contenido) {
	    google.maps.event.addListener(marker, 'click', function() {
	      window.myHandler.markerClicked(contenido, archivo);
	      calcRoute(marker.position);
	      infowindow.setContent(contenido);
	      infowindow.open(map, marker);
	    });
	  })(marker, contenido);
	  
	  if(archivo == "aparcamientos.xml")
	  	marker.setIcon("aparcamiento.png");
	  else if(archivo == "farmacias.xml")
		marker.setIcon("farmacia.png");  
	}

	}
	//====================================================================================================
	   
	   //Creamos el Mapa
	   crearMapa();
	   
	   
	   //Obtenemos nuestra localizacion, para mostrarla en el mapa
	  // getLocation();
	   
	   //Creo el marcador usando mi geolocalizacion
	  miMarcadorGlobal = crearMiMarcador();
	   
	   /*Creo los demas marcadores(Datos obtenidos desde un archivo XML, tratado desde
	   Android*/
	   crearMarcadores();
	  

		

	}
	//google.maps.event.addDomListener(window, 'load', initialize);	
	
	initialize();
	
	
	
	
});