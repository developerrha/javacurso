
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
import java.util.List;
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
						guiFileAdminl.gettextAreaRead().setEditable(true);
						guiFileAdminl.getScrollAreaRead().setVisible(true);				
						guiFileAdminl.getBotonReadTxt().setVisible(true);				
						guiFileAdminl.getButtonsJPanel().setVisible(true);			
						guiFileAdminl.getBotonWrite().setEnabled(false);	
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
						guiFileAdminl.getButtonsJPanel().setVisible(false);					
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
			String filePath = guiFileAdminl.getLabelFileName().getText();
			fileServices = new FileServices();
			String stmp = fileServices.readFile( filePath );	
			(guiFileAdminl.gettextAreaRead()).setText( stmp );
			if( stmp.indexOf("Error:") != 0 && stmp.length() > 10 ){
				guiFileAdminl.getBotonWrite().setEnabled(true);				
			}	
	   	}
		/*
		* Evento sobre boton Escribir Txt
		*/
		if( ae.getSource() == guiFileAdminl.getBotonWrite()){
			String filePath = guiFileAdminl.getLabelFileName().getText();
			String stmp = (guiFileAdminl.gettextAreaRead()).getText();
			fileServices = new FileServices();
			fileServices.writeFile( stmp, filePath );
	   	}
		/*
		* Evento sobre boton Cancelar
		*/
		if( ae.getSource() == guiFileAdminl.getBotonCancel()){
			guiFileAdminl.getFileJPanel().removeAll();
			guiFileAdminl.showPanel();
			agregarEventos();
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();

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
			List<String> selectedTags = guiFileAdminl.getListSelFiles( ).getSelectedValuesList();
		     for (int i = 0, n = selections.length; i < n; i++) {
		       if (i == 0) {
		         System.out.println(" Selections: ");
		       }
		       System.out.println(selections[i] + "  " + selectedTags.get(i) + " ");
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
		guiFileAdminl.getBotonWrite().addActionListener(this);
		guiFileAdminl.getBotonCancel().addActionListener(this);
		listSelectionModel = guiFileAdminl.getListSelFiles( ).getSelectionModel();
		listSelectionModel.addListSelectionListener( this );
	}












}
