
package pkgdir.control;

import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiFileAdmin;
import pkgdir.modelo.FileServices;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Box;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;


public class ControllerFileAdmin implements ActionListener, ListSelectionListener{

	private GuiMenu guiMenul;
	public GuiFileAdmin guiFileAdminl;
	private FileServices fileServices;
   	private DefaultListModel lst_files_model = new DefaultListModel();
	private ListSelectionModel listSelectionModel;

	/**
     * Constructor sin parametros
     * @see empty
     */
    public ControllerFileAdmin() {
        super();
    }

	/**
	* Constructor GuiMenu y GuiConfig como parametros
	* @param GuiMenu
	*/
	public ControllerFileAdmin(GuiMenu guiMenu, GuiFileAdmin guiFileAdmin ) {
		super();
		this.guiMenul = guiMenu;
		this.guiFileAdminl = guiFileAdmin;
		guiFileAdminl.showPanel();
		agregarEventos();
	}

	/**
     * Metodo que administra los eventos sobre los componentes
	* pertenece a la clase ActionListener
     * @param ae
     */
    @Override
	public void actionPerformed(ActionEvent ae) {
		/*
		* Evento sobre JFileChooser 
		*/
		if( ae.getSource() == guiFileAdminl.getFileChooser()){
			try{
				if( ae.getActionCommand().equals("ApproveSelection") ){
					String name_src = ( (JFileChooser)ae.getSource() ).getName();
					File[] sel_files = guiFileAdminl.getFileChooser().getSelectedFiles();
					if( sel_files.length == 1){
						String n_msource = sel_files[0].getParent()+"/"+sel_files[0].getName();
						guiFileAdminl.getLabelFileName().setText( n_msource );
						guiFileAdminl.getScrollAreaRead().setVisible(true);				
						guiFileAdminl.getBotonReadTxt().setVisible(true);				
						guiFileAdminl.getBotonWrite().setVisible(true);	
						guiFileAdminl.getBotonEncrypt().setVisible(true);				
						guiFileAdminl.getBotonZipFiles().setVisible(false);				
					}else{
						guiFileAdminl.getLabelFileName().setText( "Lista de seleccion" );
						for(int i=0;i<sel_files.length;i++){
							String n_msource = sel_files[i].getParent()+"/"+sel_files[i].getName();
							lst_files_model.addElement( n_msource );
						}
						guiFileAdminl.getListSelFiles( ).setModel( lst_files_model );
						guiFileAdminl.getScrollSelFiles( ).setVisible(true);
						guiFileAdminl.getScrollAreaRead().setVisible(false);				
						guiFileAdminl.getBotonReadTxt().setVisible(false);				
						guiFileAdminl.getBotonWrite().setVisible(false);				
						guiFileAdminl.getBotonEncrypt().setVisible(false);				
						guiFileAdminl.getBotonZipFiles().setVisible(true);				
					}
					guiFileAdminl.getLabelFileName().setVisible(true);	
					guiFileAdminl.getFileChooser().setVisible(false);	
					guiMenul.getMainJPanel().revalidate();
					guiMenul.getMainJPanel().repaint();
				}else{
					System.out.println("Seleccion de archivo cancelada");
					return;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	   	}
		/*
		* Evento sobre boton LeerTxt
		*/
		if( ae.getSource() == guiFileAdminl.getBotonReadTxt()){
			fileServices = new FileServices();
			String stmp = fileServices.readFile( "historial.txt" );	
			(guiFileAdminl.gettextAreaRead()).setText( stmp );	
	   	}
		/*
		* Evento sobre boton Escribir Txt
		*/
		if( ae.getSource() == guiFileAdminl.getBotonWrite()){
			fileServices = new FileServices();
			//fileServices.writeFile( stmp, "historial.txt" );
	   	}
		/*
		* Evento sobre boton Borrar Txt
		*/
		if( ae.getSource() == guiMenul.getBotonDel()){
			fileServices = new FileServices();
//			fileServices.delText( "historial.txt", stmpg);
	   	}

		/*
		* Evento sobre MenuItem file admin 
		*/
		if( ae.getSource() == guiMenul.getItemTxt()){
			guiMenul.getMainJPanel().removeAll();
			guiMenul.getMainJPanel().add(Box.createVerticalStrut(10));
			guiMenul.getMainJPanel().add(  guiFileAdminl.getFileJPanel() );	
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
	   	}
	}
	/**
     * Metodo que administra los eventos sobre JList
	* pertenece a la clase ListSelectionListener
     * @param evlist
     */
	@Override
	public void valueChanged(ListSelectionEvent evlist) { 
		if (!evlist.getValueIsAdjusting()) {
			int selections[] = guiFileAdminl.getListSelFiles( ).getSelectedIndices();
		     Object selectionValues[] = guiFileAdminl.getListSelFiles( ).getSelectedValues();
		     for (int i = 0, n = selections.length; i < n; i++) {
		       if (i == 0) {
		         System.out.println(" Selections: ");
		       }
		       System.out.println(selections[i] + "  " + selectionValues[i] + " ");
		     }
			
		}
	}
	
	/**
     * Metodo que agrega eventos sobre los componentes de GuiMenu
     */
	private void agregarEventos(){
		System.out.println("Eventos en Files Admin");
		guiMenul.getItemTxt().addActionListener(this);
		guiFileAdminl.getFileChooser().addActionListener(this);
		guiFileAdminl.getBotonReadTxt().addActionListener(this);
		listSelectionModel = guiFileAdminl.getListSelFiles( ).getSelectionModel();
		listSelectionModel.addListSelectionListener( this );
	}












}
