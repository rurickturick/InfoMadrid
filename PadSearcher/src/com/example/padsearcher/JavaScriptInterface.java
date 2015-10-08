package com.example.padsearcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.webkit.JavascriptInterface;

/**
 * Clase Usada como puente entre JavaScript y Java/Android
 * @author Oscar Perez La Madrid / Angel Luis Ortiz Folgado.
 *
 */

public class JavaScriptInterface {
    ActionBarActivity parentActivity;
    String obj;
    String lat;
    String lon;
    String value;
    File arch;
    DisplayMetrics displayMetrics;
    
    public JavaScriptInterface(ActionBarActivity activity, String myObj,String val) {
        parentActivity = activity;
        obj = myObj;
        value = val;
        displayMetrics = parentActivity.getResources().getDisplayMetrics();
        this.lat = "40.452927";
        this.lon = "-3.733422";
    }
 
    /**
     * Si se ha dado click en un marcador, almacenamos su informacion
     * en la memoria interna del telefono, para luego poder consultar
     * las visitas recientes.
     * @param contenido ->informacion del marcador.
     * @param archivo -> indica si el marcador es un aparcamiento o farmacia.
     */
    @JavascriptInterface
    public void markerClicked(String contenido, String archivo){
    	contenido = contenido.replace("<br>", "-->");
    	
    	FileOutputStream outputStream;
    	try {
    		
    		if(archivo.equalsIgnoreCase("aparcamientos.xml"))
    			outputStream = parentActivity.openFileOutput("visitasRecientesA", Context.MODE_PRIVATE | Context.MODE_APPEND);
    		else 
    			outputStream = parentActivity.openFileOutput("visitasRecientesF", Context.MODE_PRIVATE | Context.MODE_APPEND);
    		
    		contenido = contenido.concat("\n");
    	    outputStream.write(contenido.getBytes());
    	    outputStream.close();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    	


    }
    
    @JavascriptInterface
    public int height(){
    	return displayMetrics.heightPixels;
    }
    @JavascriptInterface
    public int width(){
    	return displayMetrics.widthPixels;
    }
    
    
    @JavascriptInterface
    public String giveMeData(){
        return obj;
    }
    
    @JavascriptInterface
    public String giveMeLat(){
    	return this.lat.replace(",", ".");
    }
    
    @JavascriptInterface
    public String giveMeLong(){
    	
        return this.lon.replace(",", ".");
    }
    
    @JavascriptInterface
    public String giveMeFileName(){
    	return value;
    }
    
    public void setLat(String lat){
    	this.lat = lat;
    }
    
    public void setLon(String lon){
    	this.lon = lon;
    }
}
