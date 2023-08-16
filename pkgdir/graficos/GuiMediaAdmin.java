package pkgdir.graficos;

import javax.swing.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.imageio.ImageIO;
import java.net.URL;

/**
*
**/
public class GuiMediaAdmin extends JPanel{
	
	private JPanel panel;
	private JPanel jtmpCb;
	private JFileChooser fileChTxt;
	private JButton butBack;
	private JLabel labFileName;
	private JPanel drawMedia;
	private OverlayLayout overlay;

	/**
	*Metodo constructor de la clase		
	*/
	public GuiMediaAdmin( ) {
	}

	/**
	*Metodo que pinta el JPanel		
	*Retorna vacio
	*/
	public void showPanel(){
		try{
		     this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			this.setBackground( new Color( 146, 168, 73 , 30 ) );
			this.setPreferredSize(new Dimension(750, 450));
		     this.setMaximumSize(new Dimension(750, 450));
			this.add(Box.createVerticalStrut(10));
			this.add( drawLabelFileName() );
			this.add(Box.createVerticalStrut(10));
			this.add( drawMediaFiles() );
			this.add(Box.createVerticalStrut(10));
			this.add( drawFileChooser() );
			this.add(Box.createVerticalStrut(10));
			drawButtonsMedia();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	/**
	*Metodo que pinta el JPanel botonera flotante
	*Retorna JPanel
	*/
	private JPanel drawButtonsMedia(){
		jtmpCb = new JPanel();
		try{
			OverlayLayout overlay = new OverlayLayout( jtmpCb );
			jtmpCb.setLayout(overlay);
			jtmpCb.add(drawButtonBack());
			jtmpCb.setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return jtmpCb;
	}

	/**
	*Metodo que pinta el JFileChooser 
	*Retorna JFileChooser
	*/
	private JFileChooser drawFileChooser() {
	  	Boolean old = UIManager.getBoolean("FileChooser.readOnly");  
		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
          UIManager.put("FileChooser.openButtonText","Seleccionar");
		UIManager.put("FileChooser.openButtonToolTipText", "Seleccionar archivo o directorio");
          UIManager.put("FileChooser.cancelButtonToolTipText", "Cancelar exploracion");
		//UIManager.put("FileView.directoryIcon", new ImageIcon(GuiMenu.class.getResource("../../res/back_bl.png")));
		fileChTxt = new JFileChooser(".");
		UIManager.put("FileChooser.readOnly", old);
		fileChTxt.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChTxt.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//fileChTxt.setMultiSelectionEnabled(false);
		return fileChTxt;
    }
	/**
	*Metodo que pinta el JPanel que contiene media
	*Retorna JPanel
	*/
	private JPanel drawMediaFiles(){
		drawMedia = new JPanel();
		try{
			drawMedia.setLayout(new FlowLayout( FlowLayout.CENTER, 0, 0) );
			drawMedia.setPreferredSize(new Dimension(700, 450));
			drawMedia.setMaximumSize(new Dimension(700, 450));
			drawMedia.setOpaque(false);
			drawMedia.setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return drawMedia;
	}

	/**
	*Metodo que pinta el JLabel labFileName
	*Retorna JLabel
	*/
	private JLabel drawLabelFileName(){
		try{
			labFileName = new JLabel();
			labFileName.setBackground( new Color( 168, 168, 73, 50 ) );
			labFileName.setFont(labFileName.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			labFileName.setAlignmentX(panel.CENTER_ALIGNMENT);
			labFileName.setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return labFileName;
	}
	
	/**
	*Metodo que pinta el JButton butBack Files
	*Retorna JButton
	*/
	private JButton drawButtonBack(){
		try{
		     URL url = GuiMenu.class.getResource("../../res/back_bl.png");
		     BufferedImage img = ImageIO.read(url);
			butBack = new JButton("Regresar",new ImageIcon(img));
			butBack.setBackground( new Color( 168, 168, 73, 120 ) );
			butBack.setFont(butBack.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			butBack.setAlignmentX(0.1f);
		    	butBack.setAlignmentY(0.9f);    
			butBack.setFocusPainted(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return butBack;
	}
	/**
     * Devuelve el File JPanel
     * @return Jpanel
     */
    public JPanel getFileJPanel() {
        return this;
    }
	/**
     * Devuelve el Botones salvar JPanel
     * @return Jpanel
     */
    public JPanel getButtonsJPanel() {
        return jtmpCb;
    }

	/**
     * Devuelve el JFileChooser JPanel
     * @return
     */
    public JFileChooser getFileChooser() {
        return fileChTxt;
    }
	/**
     * Devuelve el label file name
     * @return
     */
    public JLabel getLabelFileName() {
        return labFileName;
    }
	/**
     * Devuelve el boton Cancelar
     * @return
     */
    public JButton getBotonBack() {
        return butBack;
    }
	/**
	* Devuelve el JPanel media container
	* @return
	*/
	public JPanel getMediaContainer( ) {
		return drawMedia;
	}

}
