
package pkgdir.control;

import java.io.File;
import pkgdir.modelo.MysqlServices;
import pkgdir.modelo.OsCommandServices;
import pkgdir.graficos.GuiMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.Box;


public class Controller implements ActionListener{

	private GuiMenu guiMenul;
	private OsCommandServices osComServ;
	private String stmpg;
	private CaretListener listener;
	private Thread thread;


	/**
     * Constructor sin parametros
     * @see empty
     */
    public Controller() {
        super();
    }

	/**
	* Constructor GuiMenu como parametros
	* @param GuiMenu
	*/
	public Controller(GuiMenu guiMenu) {
		super();
		this.guiMenul = guiMenu;
		agregarEventos();
		osComServ = new OsCommandServices();		
	}

	/**
     * Metodo que administra los eventos sobre los componentes
	* pertenece a la clase ActionListener
     * @param ae
     */
    @Override
	public void actionPerformed(ActionEvent ae) {

		/*
		* Evento sobre boton Comando
		*/
		if( ae.getSource() == guiMenul.getBotonCommand()){
			thread = new Thread(){
		          public void run(){
					String data = guiMenul.getTextField( guiMenul.getCommandJPanel() ).getText();
					System.out.println("data: "+data);
		       	 	guiMenul.gettextAreaRead( guiMenul.getCommandJPanel() ).setText( osComServ.exeCommand( data  ) );
				}
			};
			thread.start();
	   	}
		/*
		* Evento sobre item salir
		*/
		if( ae.getSource() == guiMenul.getItemExit()){
			System.exit(0);
	   	}
		
		/*
		* Evento sobre item Command
		*/
		if( ae.getSource() == guiMenul.getItemCommand()){
			guiMenul.getMainJPanel().removeAll();
			guiMenul.getMainJPanel().add(Box.createVerticalStrut(10));
			guiMenul.getMainJPanel().add(  guiMenul.getCommandJPanel() );	
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
	   	}

	}

	
	/**
     * Metodo que agrega eventos sobre los componentes de GuiMenu
     */
	private void agregarEventos(){
		guiMenul.getItemExit().addActionListener(this);
		guiMenul.getItemCommand().addActionListener(this);
		guiMenul.getBotonCommand().addActionListener(this);

		/*
		* Obtiene la seleccion delusuario soble el TextArea para Borrar
		*/
		listener = new CaretListener() {
			public void caretUpdate(CaretEvent caretEvent) {
				stmpg = "";
				int posM = caretEvent.getMark();
				int posD = caretEvent.getDot();
				if( posD > posM){
					//stmpg = (guiMenul.gettextAreaRead( guiMenul.getTxtJPanel() ).getText()).substring( posM, posD );					
				}else{
					//stmpg = (guiMenul.gettextAreaRead( guiMenul.getTxtJPanel() ).getText()).substring( posD, posM );					
				}	
				System.out.println("stmpg: "+stmpg);	
			}
	     };
		//guiMenul.gettextAreaRead( guiMenul.getTxtJPanel() ).addCaretListener(listener);
	}



}
