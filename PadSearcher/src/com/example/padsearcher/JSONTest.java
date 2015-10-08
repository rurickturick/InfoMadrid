package com.example.padsearcher;



public class JSONTest {

    public JSONTest(){
    }
    
   
    /**
     * Creamos un Objeto JSON a partir de un String generado parseando un archivo XML
     * @param misDatos
     * @return texto: string con formato JSON.
     */
    public String getJSONObj(String misDatos){
    	String s = misDatos;
    	String[] aparcamientos = s.split(";");
    	String texto = "[";
    	
    	String[] aparcamiento;
    	
    	for (int i = 0; i < aparcamientos.length; i++){
    		 aparcamiento= aparcamientos[i].split(">");
    		 if(aparcamiento.length == 4){
    			 String contenidoMarker = aparcamiento[0] +"<br>" + aparcamiento[1];
    			 if(i != aparcamientos.length -1)
    				 texto = texto + "{ position: {lat: " + aparcamiento[2] + ", lng: "+ aparcamiento[3]+ "}, contenido: " + "'" + contenidoMarker + "'" + ", title: " +"'"+ aparcamiento[0]+"'"   + "},";
    			 else
    				 texto = texto + "{ position: {lat: " + aparcamiento[2] + ", lng: "+ aparcamiento[3]+ "}, contenido: " + "'" + contenidoMarker+ "'" + ", title: " +"'"+ aparcamiento[0]+"'"   + "}]";
    		 }
    	}
         
        return texto;
    }

  
    
}
