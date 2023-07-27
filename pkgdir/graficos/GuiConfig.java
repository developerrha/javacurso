package pkgdir.graficos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.GridLayout;

public class GuiConfig extends JPanel{

	private JTabbedPane tabbConf;

	/**
	*Metodo constructor de la clase		
	*/
	public GuiConfig() {
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
			this.add( drawTabbPan()  );
			this.add(Box.createVerticalStrut(10));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	*Metodo que pinta el JTabbedPane labText
	*Retorna JLabel
	*/
	private JTabbedPane drawTabbPan(){
		try{
			tabbConf = new JTabbedPane();
			Component tabbLog = drawSimpleTabb( new JLabel( "Configuracion de logs de aplicacion") );
        		tabbConf.addTab( "Historial", tabbLog );
			Component tabbView = drawSimpleTabb( new JLabel( "Configuracion de graficas de aplicacion" ) );
        		tabbConf.addTab( "Graficas", tabbView );
			Component tabbSafe = drawSimpleTabb( new JLabel( "Activar Rirewall" ) );
        		tabbConf.addTab( "Seguridad", tabbSafe );
			Component tabbUsers = drawSimpleTabb( new JLabel( "Configuracion de usuarios de aplicacion" ) );
        		tabbConf.addTab( "Usuarios", tabbUsers );
		}catch(Exception e){
			e.printStackTrace();
		}
		return tabbConf;
	}
	/**
	*Metodo que pinta el JPane interno
	*Retorna JComponent
	*/
	private JComponent drawSimpleTabb( JComponent work ) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        panel.add( work );
        return panel;
    }

	/**
     * Devuelve el Config JPanel
     * @return
     */
    public JPanel getConfigJPanel() {
        return this;
    }
	/**
     * Devuelve el Tabbed Panel
     * @return
     */
    public JTabbedPane getConfigJTabbedPanel() {
        return tabbConf;
    }

}
