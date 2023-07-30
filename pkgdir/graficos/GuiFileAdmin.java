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
	private JPanel jtmpCb;
	private JFileChooser fileChTxt;
	private JButton butReadTxt;
	private JButton butWrite;
	private JButton butEncript;
	private JButton butZipFile;
	private JButton butCancel;
	private JTextArea areaRead;
	private JScrollPane scrollRead;
	private JLabel labFileName;
	private JList<String> listSelFiles;
	private JScrollPane scrollSelFiles;



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
			this.add( drawListFiles() );
			this.add(Box.createVerticalStrut(10));
			this.add( drawButtonReadTxt() );
			this.add(Box.createVerticalStrut(10));
			this.add( drawFileChooser() );
			this.add(Box.createVerticalStrut(10));
			this.add( drawAreaRead() );
			this.add(Box.createVerticalStrut(10));
			this.add( drawButtonsFileSave()  );
			this.add(Box.createVerticalStrut(10));
			this.add( drawButtonZipFiles() );
			this.add(Box.createVerticalStrut(10));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	*Metodo que pinta el JPanel botonera
	*Retorna JPanel
	*/
	private JPanel drawButtonsFileSave(){
		jtmpCb = new JPanel();
		try{
			jtmpCb.setLayout(new BoxLayout(jtmpCb,BoxLayout.X_AXIS));			
			jtmpCb.add(drawButtonWrite());
			jtmpCb.add(drawButtonEncript());
			jtmpCb.add(drawButtonCancel());
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
		fileChTxt = new JFileChooser(".");
		UIManager.put("FileChooser.readOnly", old);
		fileChTxt.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChTxt.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChTxt.setMultiSelectionEnabled(true);
		return fileChTxt;
    }
	/**
	*Metodo que pinta el JList dentro de un JScrollPane
	*Retorna JScrollPane
	*/
	private JScrollPane drawListFiles(){
		scrollSelFiles = new JScrollPane();
		try{
			listSelFiles = new JList<String>();
			listSelFiles.setBackground( new Color( 168, 168, 73 ) );
			scrollSelFiles = new JScrollPane(listSelFiles);
			listSelFiles.setFont(new Font("Serif", Font.PLAIN, 14));
			scrollSelFiles.setPreferredSize(new Dimension(550, 260));
			scrollSelFiles.setMaximumSize(new Dimension(550, 260));
			scrollSelFiles.getViewport().setOpaque(false);
			scrollSelFiles.setOpaque(false);
			scrollSelFiles.setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return scrollSelFiles;
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
		     URL url = GuiMenu.class.getResource("../../res/write_bl.png");
		     BufferedImage img = ImageIO.read(url);
			butReadTxt = new JButton("Editar Archivo",new ImageIcon(img));
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
	*Metodo que pinta el JButton butWrite File
	*Retorna JButton
	*/
	private JButton drawButtonWrite(){
		try{
		     URL url = GuiMenu.class.getResource("../../res/save_bl.png");
		     BufferedImage img = ImageIO.read(url);
			butWrite = new JButton("Guardar cambios",new ImageIcon(img));
			butWrite.setBackground( new Color( 168, 168, 73, 30 ) );
			butWrite.setFont(butWrite.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			butWrite.setAlignmentX(panel.CENTER_ALIGNMENT);
			butWrite.setFocusPainted(true);
			//butWrite.setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return butWrite;
	}
	/**
	*Metodo que pinta el JButton butEncript File
	*Retorna JButton
	*/
	private JButton drawButtonEncript(){
		try{
		     URL url = GuiMenu.class.getResource("../../res/encrypt_bl.png");
		     BufferedImage img = ImageIO.read(url);
			butEncript = new JButton("Encriptar archivo",new ImageIcon(img));
			butEncript.setBackground( new Color( 168, 168, 73, 30 ) );
			butEncript.setFont(butEncript.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			butEncript.setAlignmentX(panel.CENTER_ALIGNMENT);
			butEncript.setFocusPainted(true);
			//butEncript.setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return butEncript;
	}
	/**
	*Metodo que pinta el JButton butZip Files
	*Retorna JButton
	*/
	private JButton drawButtonZipFiles(){
		try{
		     URL url = GuiMenu.class.getResource("../../res/encrypt_bl.png");
		     BufferedImage img = ImageIO.read(url);
			butZipFile = new JButton("Crear contenedor Zip",new ImageIcon(img));
			butZipFile.setBackground( new Color( 168, 168, 73, 30 ) );
			butZipFile.setFont(butZipFile.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			butZipFile.setAlignmentX(panel.CENTER_ALIGNMENT);
			butZipFile.setFocusPainted(true);
			butZipFile.setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return butZipFile;
	}
	/**
	*Metodo que pinta el JButton butCancel Files
	*Retorna JButton
	*/
	private JButton drawButtonCancel(){
		try{
		     URL url = GuiMenu.class.getResource("../../res/cancel_bl.png");
		     BufferedImage img = ImageIO.read(url);
			butCancel = new JButton("Cancelar",new ImageIcon(img));
			butCancel.setBackground( new Color( 168, 168, 73, 30 ) );
			butCancel.setFont(butCancel.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			butCancel.setAlignmentX(panel.CENTER_ALIGNMENT);
			butCancel.setFocusPainted(true);
//			butCancel.setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return butCancel;
	}
	/**
	*Metodo que pinta el JTextArea dentro de un JScrollPane
	*Retorna JScrollPane
	*/
	private JScrollPane drawAreaRead(){
		scrollRead = new JScrollPane();
		try{
			areaRead = new JTextArea();
			areaRead.setEditable(false);
			areaRead.setBackground( new Color( 168, 168, 73 ) );
			scrollRead = new JScrollPane(areaRead);
			areaRead.setFont(new Font("Serif", Font.PLAIN, 14));
			scrollRead.setPreferredSize(new Dimension(700, 300));
			scrollRead.setMaximumSize(new Dimension(700, 300));
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
     * Devuelve el boton Encriptar archivo
     * @return
     */
    public JButton getBotonEncrypt() {
        return butEncript;
    }
	/**
     * Devuelve el boton Cancelar
     * @return
     */
    public JButton getBotonCancel() {
        return butCancel;
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
	/**
	* Devuelve el JScrollPane files
	* @return
	*/
	public JScrollPane getScrollSelFiles( ) {
		return scrollSelFiles;
	}
	/**
	* Devuelve el JList Files Selected
	* @return
	*/
	public JList getListSelFiles( ) {
		return listSelFiles;
	}
	/**
     * Devuelve el boton Crear zip con archivos
     * @return
     */
    public JButton getBotonZipFiles() {
        return butZipFile;
    }


}
