package com.example.padsearcher;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * Esta clase gestiona la parte de las visitas recientes
 * creando un dialogo que muestra las visitas recientes.
 * @author Oscar Perez La Madrid / Angel Luis Ortiz Folgado.
 *
 */
public class Formulario extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_formulario);
		
		Button aparcamientos = (Button)findViewById(R.id.aparcamiento);
		aparcamientos.setOnClickListener(this);
		Button farmacias = (Button)findViewById(R.id.farmacia);
		farmacias.setOnClickListener(this);
		
		Button recientes = (Button)findViewById(R.id.reciente);
		recientes.setOnClickListener(this);
		
		Button recientesF = (Button)findViewById(R.id.recienteFarmacia);
		recientesF.setOnClickListener(this);
				
	}
	/**
	 * Creamos el dialogo qu emuestra las visitas recientes.
	 * @param archivo
	 */
	private  void crearDialogo(String archivo){
		
		String[] o;
		try {
			o = leerVisitasRecientes(archivo);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle("Visitas Recientes")
		           .setItems(o, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		              
		           }
		    });
		   builder.create().show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gestionamos los eventos de los botones.
	 */
	public void onClick(View v){
		Bundle b = new Bundle();
		Intent i = null;
		if(v.getId() == R.id.aparcamiento){
			i = new Intent(this, Parser.class);
			b.putString("archivo", "aparcamientos.xml");
			i.putExtras(b); 
			startActivity(i);
		}
		else if(v.getId() == R.id.farmacia){
			i = new Intent(this, Parser.class);
			b.putString("archivo", "farmacias.xml");
			i.putExtras(b); 
			startActivity(i);
		}
		else if(v.getId() == R.id.reciente){
			this.crearDialogo("visitasRecientesA");
			
		}
		else if(v.getId() == R.id.recienteFarmacia){
			this.crearDialogo("visitasRecientesF");
			
		}

		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.formulario, menu);
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
	
	/**
	 * Leemos la informacion almacenada en la memoria interna del dispositivo.
	 * @param archivo -> nombre del archivo a procesar
	 * @return array con la informacion de las visitas recientes.
	 * @throws IOException
	 */
	public String[] leerVisitasRecientes(String archivo) throws IOException{
    	//Lectura del archivo.
    	BufferedReader input = null;
    	File file2 = null;
    	 StringBuffer buffer = new StringBuffer();
    	 
    	 String []aux = new String[100];
    	 

    		
    	    file2 = new File(this.getFilesDir(), archivo); 
    	 
    	    input = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
    	    String line;
    	   int i = 0;
    	    while ((line = input.readLine()) != null) {
    	        buffer.append(line);
    	        aux[i] = line + "\n";
    	        
    	        i++;
    	        
    	    }
    	    String[] contenido = new String[i];
    	    
    	    for(int j = i-1 ; j >= 0; j--)
    	    	contenido[i - j - 1] = aux[j];
    	    
    	    input.close();
    	 
    	
    	
         return contenido;
    }
	
	
	
}
