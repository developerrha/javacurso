package pkgdir.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.net.UnknownHostException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPCommand;

public class FTPFilesService{

	public FTPClient ftpClient;
	private String server = "reemplazar";
	private int port = 21;
	private String[] login;
	private String user;
	private String pass;
	private String dircreate = "";
	private int countFilesUpload = 0;
	private boolean serverUp = true;

	/**
     * Constructor sin parametros
     * @see empty
     */
	public void FTPFilesService(){
	}
	/**
     * Metodo que Establece conexion con servidor FTP
	*@return boolean serverUP
     */
	public boolean ftpConnect(){
		//boolean serverUp = true;
		user = "reemplazar";
		pass = "reemplazar";
		try{
			ftpClient = new FTPClient();
			try{
				ftpClient.connect(server, port);
			}catch( UnknownHostException ce){
				System.out.println( "Connect failed: "+ce.toString() );
				serverUp = false;
			}
			if( serverUp ){
				ftpClient.login(user, pass);
				int reply = ftpClient.getReplyCode();
				System.out.println("login reply: "+reply);
				if(!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					serverUp= false;
					System.out.println("Servidor FTP no disponible.");
				}
			}
		}catch( Exception e){
			e.printStackTrace();
			serverUp = false;
		}
		return serverUp;
	}
	/**
     * Metodo que sube los archivos al servidor FTP
	*@return void
     */
	public synchronized void ftpLoadFiles( ArrayList<String> filesSel, String currentdir ){
		try{
			boolean created = ftpClient.makeDirectory( currentdir );
			for (String path : filesSel) {
				File fltmp = new File(path);
				if( fltmp.isDirectory() ){
					dircreate = path.substring( path.lastIndexOf( currentdir ) );
					System.out.println("dircreate: "+  dircreate );
					created = ftpClient.makeDirectory( dircreate );
					ArrayList<String> arraySub = new ArrayList<>();
					File[] subFiles = fltmp.listFiles();	
					for (File item : subFiles) {
						String subPath = item.getParent()+"/"+item.getName();
						arraySub.add( subPath );	
					}
					ftpLoadFiles( arraySub, currentdir );
				}else{
					String parent = fltmp.getParent();
					parent = parent.substring( parent.lastIndexOf("/")+1 );
				  	FileInputStream fis = new FileInputStream( path );
					if( parent.equals( currentdir ) ){
						dircreate = currentdir;
					}
		   			ftpClient.storeFile( dircreate+"/"+fltmp.getName(), fis );
					fis.close();
					countFilesUpload++;
				}
			}
		}catch( Exception e){
			e.printStackTrace();
		}
	}

	/**
	* Devuelve el entero contador 
	* @return int
	*/
	public int getCountFilesUpload( ) {
		return countFilesUpload;
	}

}
