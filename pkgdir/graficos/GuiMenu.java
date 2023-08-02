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
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;


/**
  * Hereda de la clase JFrame
**/
public class GuiMenu extends JFrame{

	public String title;
	private JPanel panel;
	private JPanel jtmp;
	private JPanel panelTxt;
	private JPanel panelCommand;
	private JMenuBar menuBar;
	private JMenu menuTasks;
	private JMenu menuSet;
	private JMenuItem itemTxtFile;
	private JMenuItem itemDataBas;
	private JMenuItem itemExit;
	private JMenuItem itemCommand;
	private JMenuItem itemEncrypt;
	private JMenuItem itemConfig;
	private JButton butReadDb;
	private JButton butDel;
	private JButton butCommand;
	private JTextArea areaRead;
	private JScrollPane scrollRead;


	/**
	*Metodo constructor de la clase		
	*/
	public GuiMenu() {
	}

	/**
	*Metodo que pinta el JFrame		
	*Retorna vacio
	*/
	public void showWin(String titleG){
		try{
			this.setUndecorated(true);
			this.getRootPane().setWindowDecorationStyle( JRootPane.FRAME );
			MetalLookAndFeel.setCurrentTheme( new changeTheme() );
			UIManager.setLookAndFeel(new MetalLookAndFeel());
			SwingUtilities.updateComponentTreeUI(this);
			this.title = titleG;
			this.setSize(850, 550);
			this.setTitle(title);
			this.setLocationRelativeTo(null);
			this.setResizable(true);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.getContentPane().add(drawPanel());
			this.setJMenuBar( drawMenuBar() );
			this.setIconImage( new ImageIcon( GuiMenu.class.getResource( "../../res/img_icon_litle.jpg" ) ).getImage()  );
			this.setVisible(true);
			drawPanCommand();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	

	private JMenuBar drawMenuBar(){
		menuBar = new JMenuBar();
		menuBar.setBackground( new Color( 146, 168, 73, 50) );
		menuTasks = new JMenu("Tareas");
		menuSet = new JMenu("Configuracion");
		menuBar.add(menuTasks);
		menuBar.add(menuSet);
		itemTxtFile = new JMenuItem( "Archivo plano" );
		menuTasks.add( itemTxtFile );
		itemDataBas = new JMenuItem( "Base de datos" );
		menuTasks.add( itemDataBas );
		itemCommand = new JMenuItem( "Comandos" );
		menuTasks.add( itemCommand );
		itemEncrypt = new JMenuItem( "Descargas" );
		menuTasks.add( itemEncrypt );
		menuTasks.addSeparator();
		itemExit = new JMenuItem( "Salir" );
		menuTasks.add( itemExit );
		itemConfig = new JMenuItem( "Configuracion" );
		menuSet.add( itemConfig );
		return menuBar;
	}
	
	/**
	*Metodo que pinta el JButton butDel
	*Retorna JButton
	*/
	private JButton drawButtonDel(){
		try{
		     URL url = GuiMenu.class.getResource("../../res/borrar_bl.png");
		     BufferedImage img = ImageIO.read(url);
			butDel = new JButton("Borrar Seleccion",new ImageIcon(img));
			butDel.setBackground( new Color( 168, 168, 73, 50 ) );
			butDel.setFont(butDel.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			butDel.setAlignmentX(panel.CENTER_ALIGNMENT);
			butDel.setFocusPainted(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return butDel;
	}
	/**
	*Metodo que pinta el JButton butReadDb
	*Retorna JButton
	*/
	private JButton drawButtonReadDb(){
		try{
		     URL url = GuiMenu.class.getResource("../../res/read_bl.png");
		     BufferedImage img = ImageIO.read(url);
			butReadDb = new JButton("Ejecutar Query",new ImageIcon(img));
			butReadDb.setBackground( new Color( 168, 168, 73, 50 ) );
			butReadDb.setFont(butReadDb.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			butReadDb.setAlignmentX(panel.CENTER_ALIGNMENT);
			butReadDb.setFocusPainted(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return butReadDb;
	}
	/**
	*Metodo que pinta el JButton butCommand
	*Retorna JButton
	*/
	private JButton drawButtonCommand(){
		try{
		     URL url = GuiMenu.class.getResource("../../res/run_bl.png");
		     BufferedImage img = ImageIO.read(url);
			butCommand = new JButton("Ejecutar Comando",new ImageIcon(img));
			butCommand.setBackground( new Color( 168, 168, 73, 50 ) );
			butCommand.setFont(butCommand.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			butCommand.setAlignmentX(panel.CENTER_ALIGNMENT);
			butCommand.setFocusPainted(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return butCommand;
	}
	/**
	*Metodo que pinta el JTextField texffWrite Jlabel dentro de un JPanel
	*Retorna JTextField
	*/
	private JPanel drawTextfWrite(){
		jtmp = new JPanel();
		jtmp.setBackground( new Color( 168, 168, 73, 30 ) );
		try{
		     jtmp.setLayout(new BoxLayout(jtmp,BoxLayout.X_AXIS));			
			JLabel labWrite = new JLabel("Texto:  ");
			JTextField texfWrite = new JTextField();
			texfWrite.setBackground( new Color( 168, 168, 73 ) );
			labWrite.setLabelFor(texfWrite);
			texfWrite.setFont(new Font("Serif", Font.PLAIN, 16));
			texfWrite.setPreferredSize(new Dimension(350, 30));
		     texfWrite.setMaximumSize(new Dimension(350, 30));
			jtmp.add(labWrite);
			jtmp.add(texfWrite);
		}catch(Exception e){
			e.printStackTrace();
		}
		return jtmp;
	}
	/**
	*Metodo que pinta el JTextArea dentro de un JScrollPane
	*Retorna JScrollPane
	*/
	private JScrollPane drawAreaRead(){
		JScrollPane scrollReadL = new JScrollPane();
		try{
			JTextArea areaReadL = new JTextArea();
			areaReadL.setEditable(false);
			areaReadL.setBackground( new Color( 168, 168, 73 ) );
			scrollReadL = new JScrollPane(areaReadL);
			areaReadL.setFont(new Font("Serif", Font.PLAIN, 14));
			scrollReadL.setPreferredSize(new Dimension(550, 260));
			scrollReadL.setMaximumSize(new Dimension(550, 260));
			scrollReadL.getViewport().setOpaque(false);
			scrollReadL.setOpaque(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return scrollReadL;
	}

	/**
	*Metodo que pinta el JPanel de Command
	*Retorna JPanel
	*/

	private JPanel drawPanCommand(){
		try{
			panelCommand = new JPanel();
		     panelCommand.setLayout(new BoxLayout(panelCommand,BoxLayout.Y_AXIS));
			panelCommand.setBackground( new Color( 146, 168, 73 , 30 ) );
			panelCommand.setPreferredSize(new Dimension(750, 450));
		     panelCommand.setMaximumSize(new Dimension(750, 450));
			panelCommand.add(Box.createVerticalStrut(10));
			panelCommand.add( drawTextfWrite() );
			panelCommand.add(Box.createVerticalStrut(10));
			panelCommand.add( drawButtonCommand() );
			panelCommand.add(Box.createVerticalStrut(10));
			panelCommand.add(drawAreaRead());
		}catch(Exception e){
			e.printStackTrace();
		}
		return panelCommand;
	}
	/**
	*Metodo que pinta el JPanel
	*Retorna JPanel
	*/

	private JPanel drawPanel(){
		try{
			panel = new JPanel();
		     panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			panel.setBackground( Color.decode("#80805E") );
			panel.add(Box.createVerticalStrut(30));
			}catch(Exception e){
			e.printStackTrace();
		}
		return panel;
	}

	/**
     * Devuelve el Item salir
     * @return
     */
    public JMenuItem getItemExit() {
        return itemExit;
    }
	/**
     * Devuelve el Item Txt
     * @return
     */
    public JMenuItem getItemTxt() {
        return itemTxtFile;
    }

	/**
     * Devuelve el Item DB
     * @return
     */
    public JMenuItem getItemDB() {
        return itemDataBas;
    }
	/**
     * Devuelve el Item Command
     * @return
     */
    public JMenuItem getItemCommand() {
        return itemCommand;
    }
	/**
     * Devuelve el Item Encrypt
     * @return
     */
    public JMenuItem getItemEncr() {
        return itemEncrypt;
    }
	/**
     * Devuelve el JMenu Configuracion
     * @return
     */
    public JMenuItem getItemConfig() {
        return itemConfig;
    }
	/**
     * Devuelve el main JPanel
     * @return
     */
    public JPanel getMainJPanel() {
        return panel;
    }

	/**
     * Devuelve el Command JPanel
     * @return
     */
    public JPanel getCommandJPanel() {
        return panelCommand;
    }

	/**
     * Devuelve el boton borrar Txt
     * @return
     */
    public JButton getBotonDel() {
        return butDel;
    }
	/**
     * Devuelve el boton comando
     * @return
     */
    public JButton getBotonCommand() {
        return butCommand;
    }
	/**
     * Devuelve el texField write Txt
     * @return
     */
	public JTextField getTextField( JPanel pantemp ) {
		boolean exit = false;
		JPanel pantmpl;
		JTextField texttmp = new JTextField();
		Component lComps[] = pantemp.getComponents();
		for (int i=0; i<lComps.length; i++) {
			if( lComps[i] instanceof javax.swing.JPanel ) {
				pantmpl = (JPanel) lComps[i];
				Component[] comptxt = pantmpl.getComponents();
				for (int j=0; j<comptxt.length; j++) {
					if( comptxt[j] instanceof javax.swing.JTextField ) {
						texttmp = (JTextField) comptxt[j];
						exit = true;
					}
				}
			}
			if( exit )break;
		}   			
		return texttmp;
	}
	/**
	* Devuelve el textarea read
	* @return
	*/
	public JTextArea gettextAreaRead( JPanel pantemp ) {
		boolean exit = false;
		JScrollPane scrtmp;
		JTextArea texttmp = new JTextArea();
		Component lComps[] = pantemp.getComponents();
		for (int i=0; i<lComps.length; i++) {
			if( lComps[i] instanceof javax.swing.JScrollPane ) {
				scrtmp = (JScrollPane) lComps[i];
				JViewport viewport = (( JScrollPane )lComps[i]).getViewport();
				Component[] comptxt = viewport.getComponents();
				for (int j=0; j<comptxt.length; j++) {
					if( comptxt[j] instanceof javax.swing.JTextArea ) {
						texttmp = (JTextArea) comptxt[j];
						exit = true;
					}
				}
			}
			if( exit )break;
		}   			
		return texttmp;
	}
}

