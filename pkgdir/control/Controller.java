
package pkgdir.control;

import java.io.File;
import pkgdir.modelo.MysqlServices;
import pkgdir.modelo.TextEncryption;
import pkgdir.modelo.OsCommandServices;
import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiDatabase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class Controller implements ActionListener{

	private GuiMenu guiMenul;
	private MysqlServices msqlserv;
	private TextEncryption textEncryption;
	private OsCommandServices osComServ;
	private String stmpg;
	private CaretListener listener;
	private Thread thread;
	public GuiDatabase guiDatabase;


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
		guiDatabase = new GuiDatabase();
		guiDatabase.showPanel();
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
		* Evento sobre boton LeerDb
		*/
		if( ae.getSource() == guiDatabase.getBotonReadDb()){
			//Obtiene el texto que digita el usuario en el textarea
			String stmp = (guiDatabase.gettextAreaRead( ) ).getText();	
			//Instancia la clase del modelo de datos
			msqlserv = new MysqlServices();
			//Obtiene datos desde MysqlServices arreglo objeto con 2 elementos
			Object[] objtemp = msqlserv.getDataFromMysql( stmp );
			//Asigna resultado de split sobre el elemento 1 del arrego enviado desde el modelo de datos
			String[] dataRows = ((String)objtemp[1]).split("\n");
			//Limpia elcontenido de la tabla
			((DefaultTableModel)guiDatabase.getTableRead( ).getModel()).setRowCount(0);
			((DefaultTableModel)guiDatabase.getTableRead( ).getModel()).setColumnCount(0);
			//Escribe los titulos del la columnas que envio el modelo de datos
			for(int i = 0; i< ((String[])objtemp[0]).length; i++){
				((DefaultTableModel)guiDatabase.getTableRead( ).getModel()).addColumn( ((String[])objtemp[0])[i] );
			}
			//Recorre el arreglo resultante del split sobre el strin que envio el modelo de datos
			for(int i = 0; i< dataRows.length; i++){
				//Asigna a un arreglo temporal el split por comas sobre cada fila que envio elmodelo de datos
				String[] dataCols = dataRows[i].split(",");
				//Adiciona el arreglo temporal como una fila al DefaultTableModel de datos de la tabla
				((DefaultTableModel)guiDatabase.getTableRead( ).getModel()).addRow( dataCols );
			}
			// Repinta el Main Panel
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
	   	}
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
		* Evento sobre boton Encriptar
		*/
		if( ae.getSource() == guiMenul.getBotonEncrypt() ){
			textEncryption = new TextEncryption();
			textEncryption.doCrypto(1, "lassorh", new File("historial.txt"), new File("historialEnc.txt"));
			File ftempE = new File( "historialEnc.txt" );
			if( ftempE.exists() )
				guiMenul.gettextAreaRead( guiMenul.getEncryptJPanel() ).setText( "Encriptacion realizada\n" );
			else
				guiMenul.gettextAreaRead( guiMenul.getEncryptJPanel() ).append( "Encriptacion fallo\n" );
			textEncryption.doCrypto(2, "lassorh", new File("historialEnc.txt"), new File("historialDec.txt"));
			File ftempD = new File( "historialDec.txt" );
			if( ftempD.exists() )
				guiMenul.gettextAreaRead( guiMenul.getEncryptJPanel() ).append( "Desencriptacion realizada\n" );
			else
				guiMenul.gettextAreaRead( guiMenul.getEncryptJPanel() ).append( "Desencriptacion fallo\n" );

	   	}
		/*
		* Evento sobre item salir
		*/
		if( ae.getSource() == guiMenul.getItemExit()){
			System.exit(0);
	   	}
		/*
		* Evento sobre item DB
		*/
		if( ae.getSource() == guiMenul.getItemDB()){
			guiMenul.getMainJPanel().removeAll();
			guiMenul.getMainJPanel().add(Box.createVerticalStrut(10));
			guiMenul.getMainJPanel().add(  guiDatabase.getDBJPanel() );	
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
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

		/*
		* Evento sobre item salir
		*/
		if( ae.getSource() == guiMenul.getItemEncr()){
			guiMenul.getMainJPanel().removeAll();
			guiMenul.getMainJPanel().add(Box.createVerticalStrut(10));
			guiMenul.getMainJPanel().add(  guiMenul.getEncryptJPanel() );	
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
	   	}
	}

	
	/**
     * Metodo que agrega eventos sobre los componentes de GuiMenu
     */
	private void agregarEventos(){
		guiMenul.getItemExit().addActionListener(this);
		guiMenul.getItemDB().addActionListener(this);
		guiMenul.getItemEncr().addActionListener(this);
		guiMenul.getItemCommand().addActionListener(this);
		guiDatabase.getBotonReadDb().addActionListener(this);
		guiMenul.getBotonCommand().addActionListener(this);
		guiMenul.getBotonEncrypt().addActionListener(this);
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
