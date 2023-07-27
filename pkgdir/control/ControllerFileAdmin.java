
package pkgdir.control;

import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiFileAdmin;
import pkgdir.modelo.FileServices;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Box;
import javax.swing.JFileChooser;
import java.io.File;


public class ControllerFileAdmin implements ActionListener{

	private GuiMenu guiMenul;
	public GuiFileAdmin guiFileAdminl;
	private FileServices fileServices;

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
					File mp4_file = guiFileAdminl.getFileChooser().getSelectedFile();
					String n_msource = mp4_file.getParent()+"/"+mp4_file.getName();
					guiFileAdminl.getLabelFileName().setText( n_msource );
					guiFileAdminl.getLabelFileName().setVisible(true);				
					guiFileAdminl.getFileChooser().setVisible(false);	
					guiFileAdminl.getBotonReadTxt().setVisible(true);				
					guiFileAdminl.getBotonWrite().setVisible(true);				
					guiFileAdminl.getScrollAreaRead().setVisible(true);				
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
     * Metodo que agrega eventos sobre los componentes de GuiMenu
     */
	private void agregarEventos(){
		System.out.println("Eventos en Files Admin");
		guiMenul.getItemTxt().addActionListener(this);
		guiFileAdminl.getFileChooser().addActionListener(this);
		guiFileAdminl.getBotonReadTxt().addActionListener(this);
	}












}
