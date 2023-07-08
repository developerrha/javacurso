
package pkgdir.control;

import pkgdir.modelo.FileServices;
import pkgdir.modelo.MysqlServices;
import pkgdir.modelo.OsCommandServices;
import pkgdir.graficos.GuiUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;


public class Controller implements ActionListener{

	private GuiUser guiUserl;
	private FileServices fileServices;
	private MysqlServices msqlserv;
	private OsCommandServices osComServ;	
	private CaretListener listener;
	private String stmpg;
	private String selected;

	/**
     * Constructor sin parametros
     * @see empty
     */
    public Controller() {
        super();
    }

	/**
	* Constructor GuiUser como parametros
	* @param GuiUser
	*/
	public Controller(GuiUser guiUser) {
		super();
		this.guiUserl = guiUser;
		agregarEventos();		
		osComServ = new OsCommandServices();
	}

	/**
     * Metodo que administra los eventos sobre los botones
	* pertenece a la clase ActionListener
     * @param ae
     */
    @Override
	public void actionPerformed(ActionEvent ae) {
		if( ae.getSource() == guiUserl.getBotonWrite()){
			/*fileServices = new FileServices();
			String stmp = guiUserl.getTextField().getText();
			fileServices.writeFile( stmp, "historial.txt" );
			guiUserl.getTextField().setText("");*/
			osComServ.exeCommand();
	   	}
		if( ae.getSource() == guiUserl.getBotonRead()){
			osComServ.setFlag( false );	
			/*if( selected == null ){
				int index = guiUserl.getCboxModelo().getSelectedIndex(); 
				selected = (String)guiUserl.getCboxModelo().getItemAt( index );
			}
			if( selected.equals( "Archivo plano" ) ){
				fileServices = new FileServices();
				String stmp = fileServices.readFile( "historial.txt" );	
				guiUserl.gettextAreaRead().setText( stmp );
			}else{
				msqlserv = new MysqlServices();
				guiUserl.gettextAreaRead().setText( msqlserv.getDataFromMysql() );	
			}*/
	   	}
		if( ae.getSource() == guiUserl.getBotonDel()){
			fileServices = new FileServices();
			fileServices.delText( "historial.txt", stmpg);
	   	}
		if( ae.getSource() == guiUserl.getCboxModelo()){
			int index = guiUserl.getCboxModelo().getSelectedIndex(); 
			selected = (String)guiUserl.getCboxModelo().getItemAt( index );
			System.out.println("Soy un JComboBOx: "+selected);
	   	}

	}

	
	/**
     * Metodo que agrega eventos sobre los componentes de GuiUser
     */
	private void agregarEventos(){
		guiUserl.getCboxModelo().addActionListener(this);
		guiUserl.getBotonWrite().addActionListener(this);
		guiUserl.getBotonRead().addActionListener(this);
		guiUserl.getBotonDel().addActionListener(this);
		listener = new CaretListener() {
			public void caretUpdate(CaretEvent caretEvent) {
				stmpg = "";
				int posM = caretEvent.getMark();
				int posD = caretEvent.getDot();
				if( posD > posM){
					stmpg = (guiUserl.gettextAreaRead().getText()).substring( posM, posD );					
				}else{
					stmpg = (guiUserl.gettextAreaRead().getText()).substring( posD, posM );					
				}	
				System.out.println("stmpg: "+stmpg);	
			}
	     };
		guiUserl.gettextAreaRead().addCaretListener(listener);
	}

}
