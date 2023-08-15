
package pkgdir.control;

import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiMediaAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.File;
import java.nio.file.Files;


public class ControllerMediaAdmin implements ActionListener{

	private GuiMenu guiMenul;
	public GuiMediaAdmin guiMediaAdminl;
	private File[] sel_files;
	private String currentdir;		

	/**
     * Constructor sin parametros
     * @see empty
     */
    public ControllerMediaAdmin() {
        super();
    }

	/**
	* Constructor GuiMenu y GuiConfig como parametros
	* @param GuiMenu
	*/
	public ControllerMediaAdmin(GuiMenu guiMenu, GuiMediaAdmin guiMediaAdmin ) {
		super();
		this.guiMenul = guiMenu;
		this.guiMediaAdminl = guiMediaAdmin;
		guiMediaAdminl.showPanel();
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
		if( ae.getSource() == guiMediaAdminl.getFileChooser()){
			try{
				if( ae.getActionCommand().equals("ApproveSelection") ){
					File sel_file = guiMediaAdminl.getFileChooser().getSelectedFile();
					String n_msource = sel_file.getParent()+"/"+sel_file.getName();
					guiMediaAdminl.getLabelFileName().setText( n_msource );
					guiMediaAdminl.getMediaContainer().setVisible(true);														
					guiMediaAdminl.getButtonsJPanel().setVisible(true);
					//Agrega el boton flotante sobre la multimedia
					guiMenul.getMainJFrame().getContentPane().add( guiMediaAdminl.getButtonsJPanel() );

					String mimeType = Files.probeContentType( sel_file.toPath() );					
					System.out.println("mimeType: " + mimeType );
					if( mimeType.equals( "image/jpeg" ) 
						|| mimeType.equals( "image/png" ) ){						
						System.out.println("Soy una imagen." );
						ImageIcon imgIcon = new ImageIcon( n_msource );
						Image img = imgIcon.getImage();
						int ws = img.getWidth(null);
						int hs = img.getHeight(null);
						Dimension imgSize = new Dimension(ws, hs);
						Dimension boundary = guiMediaAdminl.getMediaContainer().getPreferredSize();
						int[] newSize = getResizeRatio( imgSize, boundary );
						System.out.println("size: "+newSize[0] +" , "+ newSize[1] );
						Image newImg = img.getScaledInstance( newSize[0], newSize[1], Image.SCALE_SMOOTH);
						ImageIcon newImc = new ImageIcon(newImg);
						guiMediaAdminl.getMediaContainer().add( new JLabel( newImc ) );
					}else if( mimeType.equals( "video/mp4" ) 
						|| mimeType.equals( "video/x-matroska" )
						|| mimeType.equals( "video/x-msvideo"  ) ){
							System.out.println("Soy un video." );
					}else if( mimeType.equals( "audio/mpeg" ) 
						|| mimeType.equals( "audio/x-vorbis+ogg" ) ){
							System.out.println("Soy un audio." );
					}else{
						System.out.println("Un desconocido." );
					}						
					guiMediaAdminl.getLabelFileName().setVisible(true);	
					guiMediaAdminl.getFileChooser().setVisible(false);	
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
		* Evento sobre boton Escribir Txt
		*/
		if( ae.getSource() == guiMediaAdminl.getBotonWrite()){
			String filePath = guiMediaAdminl.getLabelFileName().getText();
	   	}
		/*
		* Evento sobre boton Cancelar
		*/
		if( ae.getSource() == guiMediaAdminl.getBotonBack() ){
			//Remueve el boton flotante sobre la multimedia
			guiMenul.getMainJFrame().getContentPane().remove( guiMediaAdminl.getButtonsJPanel() );
			guiMediaAdminl.getFileJPanel().removeAll();
			guiMediaAdminl.showPanel();
			agregarEventos();
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
	   	}
		/*
		* Evento sobre MenuItem Media
		*/
		if( ae.getSource() == guiMenul.getItemMedia()){
			System.out.println("Soy el item Media");
			guiMenul.getMainJPanel().removeAll();
			guiMenul.getMainJPanel().add(Box.createVerticalStrut(10));
			guiMenul.getMainJPanel().add(  guiMediaAdminl.getFileJPanel() );	
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
	   	}
	}
	/**
     * Metodo que agrega eventos sobre los componentes de GuiMenu
     */
	private void agregarEventos(){
		guiMenul.getItemMedia().addActionListener(this);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Multimedia", "mp4", "mkv", "avi","jpg","png","ogg","mp3");
          guiMediaAdminl.getFileChooser().setAcceptAllFileFilterUsed(false);
		guiMediaAdminl.getFileChooser().addChoosableFileFilter(filter);
		guiMediaAdminl.getFileChooser().addActionListener(this);
		guiMediaAdminl.getBotonBack().addActionListener(this);
	}




	private int[] getResizeRatio( Dimension imgSize, Dimension boundary ){
		int[] resizeData = new int[2];
		try{
			int original_width = imgSize.width;
			int original_height = imgSize.height;
			int bound_width = boundary.width;
			int bound_height = boundary.height;
			int new_width = original_width;
			int new_height = original_height;
			if (original_width != bound_width) {
			   new_width = bound_width;
			   new_height = (new_width * original_height) / original_width;
			}
			if (new_height > bound_height) {
			   new_height = bound_height;
			   new_width = (new_height * original_width) / original_height;
			}
			resizeData[0] = new_width;
			resizeData[1] = new_height;
		}catch( Exception e){
			e.printStackTrace();
		}
		return resizeData;
	}
}
