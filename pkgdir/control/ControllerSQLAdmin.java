
package pkgdir.control;

import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiDatabase;
import pkgdir.modelo.MysqlServices;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Component;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;



public class ControllerSQLAdmin implements ActionListener{

	private GuiMenu guiMenul;
	public GuiDatabase guiDatabasel;
	private MysqlServices mysqlServ;

	/**
     * Constructor sin parametros
     * @see empty
     */
    public ControllerSQLAdmin() {
        super();
    }

	/**
	* Constructor GuiMenu y GuiConfig como parametros
	* @param GuiMenu
	*/
	public ControllerSQLAdmin(GuiMenu guiMenu, GuiDatabase guiDatabase ) {
		super();
		this.guiMenul = guiMenu;
		this.guiDatabasel = guiDatabase;
		guiDatabasel.showPanel();
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
		* Evento sobre boton LeerDb
		*/
		if( ae.getSource() == guiDatabasel.getBotonReadDb()){
			Object[] objtemp = new Object[2];
			String stmp = (guiDatabasel.gettextAreaRead( ) ).getText();	
			mysqlServ = new MysqlServices();
			if(  stmp.toLowerCase().contains("insert".toLowerCase()) ||
				stmp.toLowerCase().contains("update".toLowerCase()) || 
				stmp.toLowerCase().contains("delete".toLowerCase()) ){
				System.out.println("Sentencia upate DB");
				objtemp = mysqlServ.updateDataToMysql( stmp );
			}else{
				objtemp = mysqlServ.getDataFromMysql( stmp );
			}
			String[] dataRows = ((String)objtemp[1]).split("\n");
			((DefaultTableModel)guiDatabasel.getTableRead( ).getModel()).setRowCount(0);
			((DefaultTableModel)guiDatabasel.getTableRead( ).getModel()).setColumnCount(0);
			for(int i = 0; i< ((String[])objtemp[0]).length; i++){
				((DefaultTableModel)guiDatabasel.getTableRead( ).getModel()).addColumn( ((String[])objtemp[0])[i] );
			}
			for(int i = 0; i< dataRows.length; i++){
				String[] dataCols = dataRows[i].split(",");
				((DefaultTableModel)guiDatabasel.getTableRead( ).getModel()).addRow( dataCols );
			}

			TableColumnModel columnModel = guiDatabasel.getTableRead( ).getColumnModel();
			for(int i = 0; i< ((String[])objtemp[0]).length; i++){
				int width = 550/((String[])objtemp[0]).length;
				for(int j = 0; j< dataRows.length; j++){
					TableCellRenderer renderer = guiDatabasel.getTableRead( ).getCellRenderer(j, i);
            			Component comp = guiDatabasel.getTableRead( ).prepareRenderer(renderer, j, i);
            			width = Math.max(comp.getPreferredSize().width +1 , width);
				}
				columnModel.getColumn( i ).setPreferredWidth( width);					
			}
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
	   	}
		/*
		* Evento sobre boton Cancelar
		*/
		if( ae.getSource() == guiDatabasel.getBotonCancel() ){
			guiDatabasel.getDBJPanel().removeAll();
			guiDatabasel.showPanel();
			agregarEventos();
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
	   	}
/*
		* Evento sobre item DB
		*/
		if( ae.getSource() == guiMenul.getItemDB()){
			guiMenul.getMainJPanel().removeAll();
			guiMenul.getMainJPanel().add(Box.createVerticalStrut(10));
			guiMenul.getMainJPanel().add(  guiDatabasel.getDBJPanel() );	
			guiMenul.getMainJPanel().revalidate();
			guiMenul.getMainJPanel().repaint();
	   	}	
	}
	/**
     * Metodo que agrega eventos sobre los componentes de GuiDatabase
     */
	private void agregarEventos(){
		guiMenul.getItemDB().addActionListener(this);
		guiDatabasel.getBotonReadDb().addActionListener(this);
		guiDatabasel.getBotonCancel().addActionListener(this);
		/*
		* Agrega eventos sobre las columnas de la tabla
		*/

		guiDatabasel.getTableRead( ).getColumnModel().addColumnModelListener(new TableColumnModelListener(){
			public void columnMarginChanged(ChangeEvent e){
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();
			}
			public void columnSelectionChanged(ListSelectionEvent e){
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();			
			}
			public void columnAdded(TableColumnModelEvent e){
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();			
			}
			public void columnMoved(TableColumnModelEvent e){
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();			
			}
			public void columnRemoved(TableColumnModelEvent e){
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();			
			}
		});	
		
		/*
		* Agrega eventos sobre el JScrollPane
		*/

		AdjustmentListener adjustmentListener = new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				guiMenul.getMainJPanel().revalidate();
				guiMenul.getMainJPanel().repaint();			

			}
		};
		guiDatabasel.getScrollTableRead( ).getVerticalScrollBar().addAdjustmentListener(adjustmentListener);
        	guiDatabasel.getScrollTableRead( ).getHorizontalScrollBar().addAdjustmentListener(adjustmentListener);

		/*
		* Agrega eventos sobre el TextArea
		*/
		guiDatabasel.gettextAreaRead( ).addKeyListener(
			new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					if(  guiDatabasel.gettextAreaRead( ).getText().length() > 9  ){
						guiDatabasel.getBotonReadDb().setEnabled(true);
					}
				}
		 	});	


	}




}
