
package pkgdir.control;

import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiMediaAdmin;
import pkgdir.modelo.ServicePlayVideo;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRootPane;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Files;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Desktop;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.spi.vorbis.sampled.file.VorbisAudioFileReader;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.tritonus.share.sampled.file.TAudioFileReader;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

public class ControllerMediaAdmin implements ActionListener{

	private int BUFFER_SIZE = 128000;
	private GuiMenu guiMenul;
	public GuiMediaAdmin guiMediaAdminl;
	private ServicePlayVideo servicePlayVideo;
	private File[] sel_files;
	private String currentdir;		
	private AudioInputStream in;	
	private AudioFormat baseFormat;
	private Clip clip;
	private Sequencer sequencer;
	private String n_msource;
	private Process p_streaming;

	

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
					n_msource = sel_file.getParent()+"/"+sel_file.getName();
					currentdir = sel_file.getParent();
					System.out.println("currentdir: " + currentdir+" n_msource: "+n_msource);
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
						guiMediaAdminl.getLabelFileName().setVisible(true);	
						guiMediaAdminl.getFileJPanel().setVisible(true);
						guiMediaAdminl.getFileChooser().setVisible(false);	
					}else if( mimeType.equals( "video/mp4" ) 
						|| mimeType.equals( "video/x-matroska" )
						|| mimeType.equals( "video/x-msvideo"  ) ){
							System.out.println("Soy un video. java -classpath java_clases.jar pkgdir.control.ServicePlayVideo" );
							guiMediaAdminl.getButtonsJPanel().setVisible(false);
							guiMediaAdminl.getMediaContainer().setVisible(false);
							guiMediaAdminl.getLabelFileName().setVisible(false);	

							String cmd_preview_str = "java -classpath java_clases.jar pkgdir.modelo.ServicePlayVideo "+n_msource;
					          System.out.println("preview_spo_cmd_preview_str= "+cmd_preview_str);
					          p_streaming = Runtime.getRuntime().exec(cmd_preview_str);
							Scanner sc_rtm = new Scanner(p_streaming.getInputStream());
							while (sc_rtm.hasNextLine()) {
								String line = sc_rtm.nextLine();
								System.out.println ("line: "+line);
							} 


//							guiMediaAdminl.getFileChooser().setVisible(false);	
					}else if( mimeType.equals( "audio/mpeg" ) 
						|| mimeType.equals( "audio/x-vorbis+ogg" )
						|| mimeType.equals( "audio/ogg" )
						|| mimeType.equals( "audio/x-wav" )
						|| mimeType.equals( "audio/midi" )  ){
							System.out.println("Soy un audio: "+mimeType );
						playSound( mimeType, n_msource );
						guiMediaAdminl.getLabelFileName().setVisible(true);	
						guiMediaAdminl.getFileJPanel().setVisible(true);
						guiMediaAdminl.getFileChooser().setVisible(false);	
					}else{
						System.out.println("Un desconocido." );
					}					
					System.out.println("Im after ifs");	
					//guiMediaAdminl.getLabelFileName().setVisible(true);	
					//guiMediaAdminl.getFileChooser().setVisible(false);	
					guiMenul.getMainJFrame().revalidate();
					guiMenul.getMainJFrame().repaint();
				}else{
					System.out.println("Seleccion de archivo cancelada");
					return;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	   	}
		/*
		* Evento sobre boton Cancelar
		*/
		if( ae.getSource() == guiMediaAdminl.getBotonBack() ){
			if( clip != null ){
				clip.close();
			}
			if( sequencer != null ){
				sequencer.stop();
			}
			System.out.println("To stop thread." );

			//Remueve el boton flotante sobre la multimedia
			guiMenul.getMainJFrame().getContentPane().remove( guiMediaAdminl.getButtonsJPanel() );
			guiMediaAdminl.getFileJPanel().removeAll();
			guiMediaAdminl.showPanel();
			agregarEventos();
			guiMediaAdminl.getFileChooser().setCurrentDirectory( new File( currentdir ) );
			guiMenul.getMainJFrame().revalidate();
			guiMenul.getMainJFrame().repaint();	   	}
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
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Multimedia", "mp4", "mkv", "avi","jpg","jpeg","png","ogg","mp3","wav","mid");
          guiMediaAdminl.getFileChooser().setAcceptAllFileFilterUsed(false);
		guiMediaAdminl.getFileChooser().addChoosableFileFilter(filter);
		guiMediaAdminl.getFileChooser().addActionListener(this);
		guiMediaAdminl.getBotonBack().addActionListener(this);
		guiMediaAdminl.getFileChooser().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
				//if (JFileChooser.DIALOG_TITLE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
					System.out.println("CHANGE PROPERTY NOW");
					guiMenul.getMainJPanel().revalidate();
					guiMenul.getMainJPanel().repaint();
			 	}
			}
        	});
	}

	/**
     * Metodo que ajusta la imagen al JPanel conservando la proporcion
     */
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
	/**
     * Metodo que reproduce archivos de sonido
     */
	private void playSound( String mimeType, String pathm ){
		try{
			switch (mimeType) {
				case "audio/midi":
					sequencer = MidiSystem.getSequencer();
					sequencer.open();
					InputStream is = new BufferedInputStream(new FileInputStream(new File( pathm )));
					sequencer.setSequence(is);
					sequencer.start();
				 	break;
				case "audio/x-wav":
					in = AudioSystem.getAudioInputStream( new File( pathm ) );
					baseFormat = in.getFormat();
      				AudioFormat targetFormatw = new AudioFormat(
					    AudioFormat.Encoding.PCM_SIGNED,
					    baseFormat.getSampleRate(), 16,  baseFormat.getChannels(),
					    baseFormat.getChannels() * 2,  baseFormat.getSampleRate(), false
					);
					AudioInputStream audioInputStreamw = AudioSystem.getAudioInputStream(targetFormatw, in);
					clip = AudioSystem.getClip();
					clip.open(audioInputStreamw);
					clip.start();
					in.close();
				 	break;
				case "audio/mpeg":
					MpegAudioFileReader mp=new MpegAudioFileReader();
					in=mp.getAudioInputStream( new File( pathm ) );
					AudioFormat baseFormatm = in.getFormat();
      				AudioFormat targetFormatm=new AudioFormat(
					    AudioFormat.Encoding.PCM_SIGNED,
					    baseFormatm.getSampleRate(), 16,  baseFormatm.getChannels(),
					    baseFormatm.getChannels() * 2,  baseFormatm.getSampleRate(), false
					);
					AudioInputStream audioInputStreamm = AudioSystem.getAudioInputStream(targetFormatm, in);
					clip = AudioSystem.getClip();
					clip.open(audioInputStreamm);
					clip.start();
					in.close();
				 	break;
				case "audio/ogg":
					VorbisAudioFileReader vb=new VorbisAudioFileReader();
					in=vb.getAudioInputStream( new File( pathm ) );
					baseFormat=in.getFormat();
      				AudioFormat targetFormat=new AudioFormat(
					    AudioFormat.Encoding.PCM_SIGNED,
					    baseFormat.getSampleRate(), 16,  baseFormat.getChannels(),
					    baseFormat.getChannels() * 2,  baseFormat.getSampleRate(), false
					);
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
					clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
					in.close();
				 	break;
			}
			URL url = GuiMenu.class.getResource("../../res/midi_graph_m.jpeg");
		     BufferedImage img = ImageIO.read(url);
			ImageIcon imgIcon = new ImageIcon( img );
			guiMediaAdminl.getMediaContainer().add( new JLabel( imgIcon ) );						
			
		}catch( Exception e){
			System.out.println( e.toString() );
		}	
	}

	public synchronized String getPathFileSelected(){
		System.out.println( "To send n_msource: "+n_msource );
		return "Value build for me";
	}

}
