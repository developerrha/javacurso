
package pkgdir.control;

import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiConfig;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Box;
import javax.swing.JTabbedPane;


public class ControllerConfig implements ActionListener{

	private GuiMenu guiMenul;
	private GuiConfig guiConfigl;

	/**
     * Constructor sin parametros
     * @see empty
     */
    public ControllerConfig() {
        super();
    }

	/**
	* Constructor GuiMenu y GuiConfig como parametros
	* @param GuiMenu
	*/
	public ControllerConfig(GuiMenu guiMenu, GuiConfig guiConfig ) {
		super();
		this.guiMenul = guiMenu;
		this.guiConfigl = guiConfig;
		guiConfigl.showPanel();
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
		* Evento sobre Menu Configuiracion
		*/
		if( ae.getSource() == guiMenul.getItemConfig()){
			System.out.println("Soy el menu Configuracion");
			guiMenul.getMainJPanel().removeAll();
			guiMenul.getMainJPanel().add(Box.createVerticalStrut(10));
			guiMenul.getMainJPanel().add(  guiConfigl.getConfigJPanel() );	
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
	   	}
	}
	/**
     * Metodo que administra los eventos sobre JTabbedPane
	* pertenece a la clase ChangeListener
     * @param te
     */
	ChangeListener changeListener = new ChangeListener() {
		public void stateChanged(ChangeEvent te) {
			JTabbedPane sourceTabbedPane = (JTabbedPane) te.getSource();
			int index = sourceTabbedPane.getSelectedIndex();
			System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
		}
	};
	
	/**
     * Metodo que agrega eventos sobre los componentes de GuiMenu
     */
	private void agregarEventos(){
		System.out.println("Eventos en configuracion");
		guiMenul.getItemConfig().addActionListener(this);
		guiConfigl.getConfigJTabbedPanel().addChangeListener(changeListener);
	}












}
