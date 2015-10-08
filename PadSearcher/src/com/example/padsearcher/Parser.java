package com.example.padsearcher;


import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class Parser extends ActionBarActivity implements LocationListener{

	WebView mWebView;
	
	private LocationManager locationManager;
	private JavaScriptInterface misDatos;
	
	class GeoClient extends WebChromeClient {
	    @Override
	    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
	        super.onGeolocationPermissionsShowPrompt(origin, callback);
	        Log.d("geolocation permission", "permission >>>"+origin);
	        callback.invoke(origin, true, false);
	    }
	}
	
	
	
    private class GeoWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
           
            view.loadUrl(url);
            return true;
        }
    }
 
   
    private class GeoWebChromeClient extends WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                GeolocationPermissions.Callback callback) {
           
            callback.invoke(origin, true, false);
        }
    }
	
	
	
	
    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parser);
        String datos = "";
        Bundle b = getIntent().getExtras();
        String value = b.getString("archivo");
      //Localizacion
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,3000, 10, (LocationListener) this);
        
		XmlPullParserFactory pullParserFactory;
		
			try {
				
		       
		        
		        
				
				pullParserFactory = XmlPullParserFactory.newInstance();
				XmlPullParser parser = pullParserFactory.newPullParser();

			    InputStream in_s = getAssets().open(value);
			    
		        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in_s, null);
	           datos = parseXML(parser);
	          
	            JSONTest aux = new JSONTest();
	            String miObj;
			
					miObj = aux.getJSONObj(datos);
					 misDatos = new JavaScriptInterface(this, miObj, value);
					WebView myWebView = (WebView) this.findViewById(R.id.webView);
				
					
					myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
			        myWebView.getSettings().setBuiltInZoomControls(true);
			        myWebView.setWebViewClient(new GeoWebViewClient());
			        // Below required for geolocation
			        myWebView.getSettings().setJavaScriptEnabled(true);
			        myWebView.getSettings().setGeolocationEnabled(true);
			        myWebView.setWebChromeClient(new GeoWebChromeClient());
			        
			        
			       
			       
			        
			        myWebView.addJavascriptInterface(misDatos, "myHandler");
					
					
					
					
			        myWebView.loadUrl("file:///android_asset/index.html");
			       
			      
				
	            
	          

			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
        
    }
    
    /*****************************************************************************/
    //geolocalizacion
    
    /*******************************************************************************/
    public void onLocationChanged(Location location) {
    	
    	misDatos.setLat(Double.toString(location.getLatitude()));
    	misDatos.setLon(Double.toString(location.getLongitude()));
    	
    	Context context = getApplicationContext();
    	CharSequence text = Double.toString(location.getLatitude())+ "," +Double.toString(location.getLongitude()) ;
    	int duration = Toast.LENGTH_LONG;

    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    	
        
    }
 
    public void onProviderDisabled(String provider) {
         
        /******** Called when User off Gps *********/
         
        Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
    }
 
    public void onProviderEnabled(String provider) {
         
        /******** Called when User on Gps  *********/
         
        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }
 
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
         
    }
    
 /*********************************************************************************/
 /********************************************************************************/
 /*                                 Parser                                       */
 /********************************************************************************/   

	@SuppressWarnings("unused")
	private String parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
	{
			String total = "";
			String t = "";
		
		
        int eventType = parser.getEventType();
      
       String tag = "";
       boolean nombre = false;
       boolean latitud = false;
       boolean longitud = false;
       boolean telefono = false;
       boolean nombreVia = false;
       boolean claseVial = false;
       boolean num = false;
       boolean codigo_postal = false;
       
       String nombre_via = "";
       String clase_vial = "";
       String numero = "";
       String codigo = "";
       
       int cuentaDatos = 0;
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = parser.getName();
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                	if(name.equals("atributo")){
                		tag = parser.getName();
                		if(parser.getAttributeValue(0).equals("NOMBRE"))
                			nombre = true;
                		else if(parser.getAttributeValue(0).equals("LATITUD"))
                			latitud = true;
                		else if(parser.getAttributeValue(0).equals("LONGITUD"))
                			longitud = true;
                		
                		//telefono = parser.getAttributeValue(0).equals("TELEFONO");
                		telefono = false;
                		claseVial = parser.getAttributeValue(0).equals("CLASE-VIAL");
                		nombreVia  = parser.getAttributeValue(0).equals("NOMBRE-VIA");
                		num = parser.getAttributeValue(0).equals("NUM");
                		codigo_postal = parser.getAttributeValue(0).equals("CODIGO-POSTAL");
                	}
                    break;
                case XmlPullParser.TEXT:
                	t = parser.getText();
                	break;
                		
                case XmlPullParser.END_TAG:
                	if(name.equals("atributo") && nombre){
                		nombre = false;
                		//total = total + "nombre: " +  t + "\n";
                		total = total + t + ">";
                		cuentaDatos++;
                	}
                	else if(name.equals("atributo") && latitud){
                		latitud = false;
                		//total = total +  "latitud: " + t + "\n";
                		total = total + t + ">";
                		cuentaDatos++;
                	}
                	else if(name.equals("atributo") && longitud){
                		longitud = false;
                		//total = total +  "longitud: " + t + "\n";
                		total = total + t + ";";
                		cuentaDatos++;
                	}
                	else if(name.equals("atributo") && telefono){
                		telefono = false;
                		total = total +  "telefono: " + t + "\n";
                		//return total;
                	}
                	else if(name.equals("atributo") && nombreVia){
                		nombreVia = false;
                		nombre_via = t + " ";
                	}
                	else if(name.equals("atributo") && claseVial){
                		claseVial = false;
                		clase_vial = t + " ";
                	}
                	else if(name.equals("atributo") && num){
                		num = false;
                		numero = t + " "; 
                	}
                	else if(name.equals("atributo") && codigo_postal){
                		codigo_postal = false;
                		codigo = clase_vial  + nombre_via + numero + ", " + t +  ">"; 
                		total = total +  codigo;
                		
                	}
                	
                    break;
            }
            eventType = parser.next();
        }

        return total;
	}
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    
        getMenuInflater().inflate(R.menu.parser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
}
