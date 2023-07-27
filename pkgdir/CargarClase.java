
package pkgdir;

import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiConfig;
import pkgdir.graficos.GuiFileAdmin;
import pkgdir.control.Controller;
import pkgdir.control.ControllerConfig;
import pkgdir.control.ControllerFileAdmin;

public class CargarClase {
	public static GuiMenu front;
	public static GuiConfig frontConfig;
	public static GuiFileAdmin frontFileAdmin;

	/**
	*Metodo principal de la clase		
	*/
     public static void main(String[] args) {
		try{
			front = new GuiMenu();
			front.showWin("JAVA BASICO");
			frontConfig = new GuiConfig();
			frontFileAdmin = new GuiFileAdmin();
			Controller controlador = new Controller( front );
			ControllerConfig controlConf = new ControllerConfig( front, frontConfig );
			ControllerFileAdmin controllerFileAdmin = new ControllerFileAdmin( front, frontFileAdmin );
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
