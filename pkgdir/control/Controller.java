
package pkgdir.control;

import java.io.File;
import pkgdir.modelo.MysqlServices;
import pkgdir.modelo.OsCommandServices;
import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiDatabase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.Box;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class Controller implements ActionListener{

	private GuiMenu guiMenul;
	private MysqlServices msqlserv;
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
			String stmp = (guiDatabase.gettextAreaRead( ) ).getText();	
			msqlserv = new MysqlServices();
			Object[] objtemp = msqlserv.getDataFromMysql( stmp );
			String[] dataRows = ((String)objtemp[1]).split("\n");
			((DefaultTableModel)guiDatabase.getTableRead( ).getModel()).setRowCount(0);
			((DefaultTableModel)guiDatabase.getTableRead( ).getModel()).setColumnCount(0);
			for(int i = 0; i< ((String[])objtemp[0]).length; i++){
				((DefaultTableModel)guiDatabase.getTableRead( ).getModel()).addColumn( ((String[])objtemp[0])[i] );

			}
			for(int i = 0; i< dataRows.length; i++){
				String[] dataCols = dataRows[i].split(",");
				((DefaultTableModel)guiDatabase.getTableRead( ).getModel()).addRow( dataCols );
			}
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
		/*
		* Agrega eventos sobre las columnas de la tabla
		*/

		guiDatabase.getTableRead( ).getColumnModel().addColumnModelListener(new TableColumnModelListener(){
			public void columnMarginChanged(ChangeEvent e){
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();
			}
			public void columnSelectionChanged(ListSelectionEvent e){
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();			
			}
			public void columnAdded(TableColumnModelEvent e){
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();			
			}
			public void columnMoved(TableColumnModelEvent e){
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();			
			}
			public void columnRemoved(TableColumnModelEvent e){
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();			
			}
		});		

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
