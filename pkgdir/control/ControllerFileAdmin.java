
package pkgdir.control;

import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiFileAdmin;
import pkgdir.modelo.FileServices;
import pkgdir.modelo.TextEncryption;
import pkgdir.modelo.ZipFilesService;
import pkgdir.modelo.FTPFilesService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;


public class ControllerFileAdmin implements ActionListener, ListSelectionListener{

	private GuiMenu guiMenul;
	public GuiFileAdmin guiFileAdminl;
	private FileServices fileServices;
	private TextEncryption textEncryption;
	private ZipFilesService zipFilesService;
	private FTPFilesService ftpFilesService;
   	private DefaultListModel lst_files_model = new DefaultListModel();
	private ListSelectionModel listSelectionModel;
	private ArrayList<String> arrayPaths;
	private File[] sel_files;
	private String currentdir;		

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
						guiFileAdminl.getBotonEncrypt().setEnabled(false);	
						guiFileAdminl.getBotonZipFiles().setVisible(false);				
					}else{
						currentdir = System.getProperty("user.dir");
						currentdir = currentdir.substring( currentdir.lastIndexOf("/")+1 );
						System.out.println("currentdir: " + currentdir);
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
						guiFileAdminl.getButtonsFilesList().setVisible(true);				
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
				guiFileAdminl.getBotonEncrypt().setEnabled(true);								
			}
			if( stmp.indexOf("Archivo no valido:") == 0){
				guiFileAdminl.getBotonWrite().setEnabled(false);
				guiFileAdminl.getBotonEncrypt().setText("Desencriptar archivo");
				guiFileAdminl.getBotonEncrypt().setEnabled(true);								
			}
	
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
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
		* Evento sobre boton Encriptar Txt
		*/
		if( ae.getSource() == guiFileAdminl.getBotonEncrypt() ){
			String filePath = guiFileAdminl.getLabelFileName().getText();
			textEncryption = new TextEncryption();
			if( guiFileAdminl.getBotonEncrypt().getText().equals( "Encriptar archivo" ) ){
				boolean encok = textEncryption.doCrypto(1, "lassorh", new File( filePath ));
				//File ftempE = new File( "historial.txt" );
				if( encok ){
					guiFileAdminl.gettextAreaRead().setText( "Encriptacion realizada\n" );
					guiFileAdminl.getBotonWrite().setEnabled(false);
					guiFileAdminl.getBotonEncrypt().setText("Desencriptar archivo");

				}else{
					guiFileAdminl.gettextAreaRead().append( "Encriptacion fallo\n" );
				}
			}else{
				boolean encok =  textEncryption.doCrypto(2, "lassorh", new File( filePath ));
				if( encok ){
					guiFileAdminl.gettextAreaRead().append( "Desencriptacion realizada\n" );
					guiFileAdminl.getBotonEncrypt().setText("Encriptar archivo");
					guiFileAdminl.getBotonEncrypt().setEnabled(false);								
				}else{
					guiFileAdminl.gettextAreaRead().append( "Desencriptacion fallo\n" );
				}
			}
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
	   	}
		/*
		* Evento sobre boton Enzipar
		*/
		if( ae.getSource() == guiFileAdminl.getBotonZipFiles()){
			Timestamp timeNow = new Timestamp(System.currentTimeMillis());
			String zname = "homzode_"+(timeNow.toString()).replaceAll( " ", "-" )+".zip";
			zipFilesService = new ZipFilesService();
			zipFilesService.zipTsFiles( arrayPaths, zname );
			File fzip = new File( zname );
			if( fzip.exists() ){
				guiFileAdminl.getFileJPanel().removeAll();
				guiFileAdminl.showPanel();
				agregarEventos();
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();	
			}
			
	   	}
		/*
		* Evento sobre boton Enviar a FTP
		*/
		if( ae.getSource() == guiFileAdminl.getBotonSendFtp()){
			try{
				Component parent = guiFileAdminl.getParent();
				Component parent_a = (JPanel)parent.getParent();
				ftpFilesService = new FTPFilesService();
				if( ftpFilesService.ftpConnect() ){
					System.out.println("Conectado a servidor FTP");	
					ftpFilesService.ftpLoadFiles( arrayPaths, currentdir );
					JOptionPane.showInternalMessageDialog( parent_a , "Se cargaron "+ftpFilesService.getCountFilesUpload( )+" archivos al servidor FTP");  
				}else{
					JOptionPane.showInternalMessageDialog( parent_a , "No se conecto al servidor FTP");
					guiFileAdminl.getBotonSendFtp().setEnabled( false );
					guiFileAdminl.getFileJPanel().removeAll();
					guiFileAdminl.showPanel();
					agregarEventos();
					guiMenul.getMainJPanel().revalidate();
					guiMenul.getMainJPanel().repaint();
					
				}
				if( ftpFilesService.ftpClient.isConnected() ){
					ftpFilesService.ftpClient.logout();
				}
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();	
			}catch( Exception e){
				e.printStackTrace();
			}
	   	}
		/*
		* Evento sobre boton Cancelar
		*/
		if( ae.getSource() == guiFileAdminl.getBotonCancel() || ae.getSource() == guiFileAdminl.getBotonCancelZip() ){
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
		try{
		if (!evlist.getValueIsAdjusting()) {
			int selections[] = guiFileAdminl.getListSelFiles( ).getSelectedIndices();
			List<String> selectedTags = guiFileAdminl.getListSelFiles( ).getSelectedValuesList();
			arrayPaths = new ArrayList<>();
		     for (int i = 0, n = selections.length; i < n; i++) {
				arrayPaths.add(selectedTags.get(i));	

		     }
			if( arrayPaths.size() < 1 ){
				guiFileAdminl.getBotonZipFiles().setEnabled(false);
				guiFileAdminl.getBotonSendFtp().setEnabled(false);
			}else{
				guiFileAdminl.getBotonZipFiles().setEnabled(true);
				guiFileAdminl.getBotonSendFtp().setEnabled(true);
			}
		}
		}catch( Exception e){
			e.printStackTrace();
		}
	}
	
	/**
     * Metodo que agrega eventos sobre los componentes de GuiMenu
     */
	private void agregarEventos(){
		guiMenul.getItemTxt().addActionListener(this);
		guiFileAdminl.getFileChooser().addActionListener(this);
		guiFileAdminl.getBotonReadTxt().addActionListener(this);
		guiFileAdminl.getBotonWrite().addActionListener(this);
		guiFileAdminl.getBotonCancel().addActionListener(this);
		guiFileAdminl.getBotonCancelZip().addActionListener(this);
		guiFileAdminl.getBotonEncrypt().addActionListener(this);
		guiFileAdminl.getBotonZipFiles().addActionListener(this);
		guiFileAdminl.getBotonSendFtp().addActionListener(this);
		listSelectionModel = guiFileAdminl.getListSelFiles( ).getSelectionModel();
		listSelectionModel.addListSelectionListener( this );
	}












}
