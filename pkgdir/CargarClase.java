/**
* Aplicacion de escritorio en java
* Clase main del proyecto
* @author Homzode
* @version 1.1.7, 2023/07/30
*/

package pkgdir;

import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiConfig;
import pkgdir.graficos.GuiFileAdmin;
import pkgdir.graficos.GuiMediaAdmin;
import pkgdir.graficos.GuiDatabase;
import pkgdir.control.Controller;
import pkgdir.control.ControllerConfig;
import pkgdir.control.ControllerFileAdmin;
import pkgdir.control.ControllerMediaAdmin;
import pkgdir.control.ControllerSQLAdmin;
/**
* Aplicacion de escritorio en java
* Clase main del proyecto
* @author Homzode
* @version 1.1.7, 2023/07/30
*/

public class CargarClase {
	public static GuiMenu front;
	public static GuiConfig frontConfig;
	public static GuiFileAdmin frontFileAdmin;
	public static GuiMediaAdmin frontMediaAdmin;
	public static GuiDatabase frontDatabase;

	/**
	*Metodo principal de la clase		
	*@param String[] args
	*@return void
	*/
     public static void main(String[] args) {
		try{
			front = new GuiMenu();
			front.showWin("JAVA BASICO");
			frontConfig = new GuiConfig();
			frontFileAdmin = new GuiFileAdmin( );
			frontMediaAdmin = new GuiMediaAdmin( );
			frontDatabase = new GuiDatabase( );
			Controller controlador = new Controller( front );
			ControllerConfig controlConf = new ControllerConfig( front, frontConfig );
			ControllerFileAdmin controllerFileAdmin = new ControllerFileAdmin( front, frontFileAdmin );
			ControllerMediaAdmin controllerMediaAdmin = new ControllerMediaAdmin( front, frontMediaAdmin );
			ControllerSQLAdmin controllerSQLAdmin = new ControllerSQLAdmin( front, frontDatabase );
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
