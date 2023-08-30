package pkgdir.modelo;

import pkgdir.graficos.GuiMenu;
import pkgdir.graficos.GuiMediaAdmin;
import pkgdir.graficos.changeTheme;
import javax.swing.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.BorderLayout;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.web.WebEvent;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import java.io.File;


//public class ServicePlayVideo extends JDesktopPane {
public class ServicePlayVideo extends JPanel{

	private static JFrame baseVideo;

	private WebView webComponent;
	private JFXPanel javafxPanel;
	private JInternalFrame temp;
	private StackPane webPane;
	private Scene scene;
	private URL url;
	private static String n_msource;
	private boolean flag = true;
	private int n = 0;
	private Thread fxThread;
	private static GuiMenu guiMenul;
	private static GuiMediaAdmin guiMediaAdminl;

	public static void main(String[] args){  
		n_msource = args[0];	
		SwingUtilities.invokeLater(new Runnable() {  
            @Override
            public void run() {  
			showWin();	
            }  
        });
    }  


			
	/**
	*Metodo constructor de la clase		
	*/
	public ServicePlayVideo( ) {
		try{
			baseVideo.setIconImage( new ImageIcon( getClass().getResource( "/res/img_icon_litle.jpg" ) ).getImage()  );
			baseVideo.revalidate();
			baseVideo.repaint();
			javafxPanel = new JFXPanel();
			add(javafxPanel, BorderLayout.CENTER);
			setBackground( new Color( 146, 168, 73, 50) );
			setVisible(true);
			loadJavaFXScene();
		}catch( Exception e){
			e.printStackTrace();
		}
	}

	/**
	*Metodo que pinta el JFrame		
	*Retorna vacio
	*/
	public static void showWin(){
		try{
			baseVideo = new JFrame();
               baseVideo.getContentPane().add(new ServicePlayVideo());  
			baseVideo.setUndecorated(true);
			baseVideo.getRootPane().setWindowDecorationStyle( JRootPane.FRAME );
			MetalLookAndFeel.setCurrentTheme( new changeTheme() );
			UIManager.setLookAndFeel(new MetalLookAndFeel());
			SwingUtilities.updateComponentTreeUI(baseVideo);
			baseVideo.setResizable(true);
			baseVideo.setSize(800, 500);
			baseVideo.setResizable(false);
			baseVideo.setLocationRelativeTo( null );
			baseVideo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			baseVideo.setTitle( n_msource );
			baseVideo.setBackground( new Color( 146, 168, 73, 50) );
			baseVideo.setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
     * Metodo que carga html en webview para play videos
	* /datos/laboratorio/java_mio/media_store/stressed.mp4
     */

	private void loadJavaFXScene(){
		try{
			webPane = new StackPane();
			url = getClass().getResource("/video.html");
			String vidUrl = url.toString() + "?"+n_msource;
			System.out.println("vidUrl: "+ vidUrl );
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try{
						webComponent = new WebView();
						WebEngine engine = webComponent.getEngine();
						engine.getLoadWorker().stateProperty().addListener(
						(ObservableValue<? extends State> ov, State oldState, State newState) -> {
							System.out.println("newState: "+newState+" State.SUCCEEDED: "+State.SUCCEEDED);
							if (newState == State.SUCCEEDED) {
							//	engine.executeScript("letout('"+n_msource+"');");
							}
						});
						engine.setOnAlert(event -> System.out.println(event.toString()));
						engine.load( vidUrl );
						webPane.getChildren().add(webComponent);
						scene = new Scene(webPane, 800, 500, javafx.scene.paint.Color.BLUE);
						javafxPanel.setScene(scene);
						System.out.println("setScene finalized ");
					}catch( Exception e){
						System.out.println("On Runlater..");
						e.printStackTrace();
					}	
				}
			});
		}catch( Exception e){
			System.out.println("On Method.."+e.toString());
			e.printStackTrace();
		}	
	}
	
}
