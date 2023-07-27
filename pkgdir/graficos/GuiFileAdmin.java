package pkgdir.graficos;

import javax.swing.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.Component;
import java.awt.Toolkit;

/**
*
**/
public class GuiFileAdmin extends JPanel{
	
	private JPanel panel;
	private JFileChooser fileChTxt;
	private JButton butReadTxt;
	private JButton butWrite;
	private JTextArea areaRead;
	private JScrollPane scrollRead;
	private JLabel labFileName;


	/**
	*Metodo constructor de la clase		
	*/
	public GuiFileAdmin() {
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
			this.add( drawButtonReadTxt() );
			this.add(Box.createVerticalStrut(10));
			this.add( drawButtonWrite() );
			this.add(Box.createVerticalStrut(10));
			this.add( drawFileChooser() );
			this.add(Box.createVerticalStrut(10));
			this.add( drawAreaRead() );
			this.add(Box.createVerticalStrut(10));
		}catch(Exception e){
			e.printStackTrace();
		}
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
		fileChTxt = new JFileChooser(".");
		UIManager.put("FileChooser.readOnly", old);
		fileChTxt.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChTxt.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChTxt.setMultiSelectionEnabled(true);
		return fileChTxt;
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
	*Metodo que pinta el JButton butReadTxt
	*Retorna JButton
	*/
	private JButton drawButtonReadTxt(){
		try{
		     URL url = GuiMenu.class.getResource("../../res/read_bl.png");
		     BufferedImage img = ImageIO.read(url);
			butReadTxt = new JButton("Leer Archivo",new ImageIcon(img));
			butReadTxt.setBackground( new Color( 168, 168, 73, 50 ) );
			butReadTxt.setFont(butReadTxt.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			butReadTxt.setAlignmentX(panel.CENTER_ALIGNMENT);
			butReadTxt.setFocusPainted(true);
			butReadTxt.setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return butReadTxt;
	}
	/**
	*Metodo que pinta el JButton butWrite Txt
	*Retorna JButton
	*/
	private JButton drawButtonWrite(){
		try{
		     URL url = GuiMenu.class.getResource("../../res/write_bl.png");
		     BufferedImage img = ImageIO.read(url);
			butWrite = new JButton("Escribir en Archivo",new ImageIcon(img));
			butWrite.setBackground( new Color( 168, 168, 73, 30 ) );
			butWrite.setFont(butWrite.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			butWrite.setAlignmentX(panel.CENTER_ALIGNMENT);
			butWrite.setFocusPainted(true);
			butWrite.setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return butWrite;
	}
	/**
	*Metodo que pinta el JTextArea dentro de un JScrollPane
	*Retorna JScrollPane
	*/
	private JScrollPane drawAreaRead(){
		JScrollPane scrollReadL = new JScrollPane();
		try{
			areaRead = new JTextArea();
			areaRead.setEditable(false);
			areaRead.setBackground( new Color( 168, 168, 73 ) );
			scrollRead = new JScrollPane(areaRead);
			areaRead.setFont(new Font("Serif", Font.PLAIN, 14));
			scrollRead.setPreferredSize(new Dimension(550, 260));
			scrollRead.setMaximumSize(new Dimension(550, 260));
			scrollRead.getViewport().setOpaque(false);
			scrollRead.setOpaque(false);
			scrollRead.setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return scrollRead;
	}

	/**
     * Devuelve el File JPanel
     * @return Jpanel
     */
    public JPanel getFileJPanel() {
        return this;
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
     * Devuelve el boton leerTxt
     * @return
     */
    public JButton getBotonReadTxt() {
        return butReadTxt;
    }
	/**
     * Devuelve el boton escribir Txt
     * @return
     */
    public JButton getBotonWrite() {
        return butWrite;
    }

	/**
	* Devuelve el textarea read
	* @return
	*/
	public JTextArea gettextAreaRead( ) {
		return areaRead;
	}
	/**
	* Devuelve el JScrollPane read
	* @return
	*/
	public JScrollPane getScrollAreaRead( ) {
		return scrollRead;
	}


}
