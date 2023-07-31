package pkgdir.modelo;

import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.nio.file.*;


public class FileServices{

	private File textfile;
	private FileWriter writer;
	private Scanner txtReader;

	/**
     * Constructor sin parametros
     * @see empty
     */
	public FileServices(){
		super();
	}

	/**
     * Metodo que escribe texto en archivo plano
     * @param String mess
     * @param String fileName
     */
	public void writeFile(String mess, String fileName){
        try {
		System.out.println("mess: "+mess);
		textfile = new File(fileName);
		writer = new FileWriter(textfile, false);
		writer.write(mess);
		writer.flush();
		writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

	/**
     * Metodo que lee texto desde archivo plano
     * @param String fileName
     */

	public String readFile(String fileName) {
		String data = new String("");
		try {
			textfile = new File(fileName);
			if(textfile.exists() && !textfile.isDirectory()) { 
				txtReader = new Scanner(textfile);
				while (txtReader.hasNextLine()) {
					data += txtReader.nextLine()+"\n";
				}
				txtReader.close();
			}else{
				data = "Error: No se encontro archivo: "+textfile.getName();
			}
			System.out.println("data: "+data.length());	
			if( data.length() <= 0 ){
				data = "Archivo no valido: No es posible editar el archivo\nSi fue encriptado por esta aplicacion\nPruebe con boton Desencriptar\n";
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
     * Metodo que borra texto en archivo plano
     * @param String fileName
     */

	public void delText(String fileName, String dataModel) {
		try {
			System.out.println("dataModel: "+dataModel);	
			textfile = new File(fileName);
			//tmpPath guarda nombre para nuevo archivo temporal
			String tmpPath = (fileName.substring( 0, fileName.indexOf( "." ))) + ".tmp";		
			System.out.println("tmpPath: "+tmpPath);	
			//Se crea elobjeto File para el archivo temporal
			File tmpfile = new File( tmpPath );
			if(textfile.exists() && !textfile.isDirectory()) {
				//Lee sobre archivo original 
				BufferedReader br = new BufferedReader(new FileReader(fileName));
				//Escribe sobre archivo temporal
				PrintWriter pw = new PrintWriter(new FileWriter(tmpfile));
				String line = null;
				while ((line = br.readLine()) != null) {
					if ( !dataModel.trim().contains(line)) {
						System.out.println("line: "+line.trim());			
						pw.println(line.trim());
						pw.flush();
					}
				}
				pw.close();
				br.close();
				if (tmpfile.renameTo( new File(fileName) )) {
					System.out.println("archivo renombrado");
				} else {
					System.out.println("error");
				}
			}else{
				System.out.println( "No se encontro archivo: "+textfile.getName() );			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
