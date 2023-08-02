package pkgdir.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.ArrayList;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

public class ZipFilesService{


	public ZipFilesService(){
		
	}

	public void zipTsFiles( ArrayList<String> entryPaths, String zName ){
		try {
			File oldFile = new File(zName);
			if( oldFile.exists() ){
				oldFile.delete();
			}
			ZipFile zipFile = new ZipFile( zName );               
			for (String path : entryPaths) {
				File fltmp = new File(path);
				if( fltmp.isDirectory() ){
			  		zipFile.addFolder(fltmp);					
				}else{
			  		zipFile.addFile(fltmp);					
				}
			}
		} catch (Exception e) {
		  e.printStackTrace();
		}
    }


}
