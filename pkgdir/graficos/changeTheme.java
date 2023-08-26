package pkgdir.graficos;

import java.awt.Color;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;


//Theme Class to Change the default color to green color
public class changeTheme extends DefaultMetalTheme {
    public ColorUIResource getWindowTitleInactiveBackground() {
      return new ColorUIResource( new Color( 146, 168, 73, 50) );
    }
  
    public ColorUIResource getWindowTitleBackground() {
      return new ColorUIResource( new Color( 146, 168, 73, 50) );
    }
  
    public ColorUIResource getPrimaryControlHighlight() {
      return new ColorUIResource( new Color( 146, 168, 73, 50) );
    }
  
    public ColorUIResource getPrimaryControlDarkShadow() {
		return new ColorUIResource(java.awt.Color.white);
 //     return new ColorUIResource( new Color( 146, 168, 73, 50) );
    }
  
    public ColorUIResource getPrimaryControl() {
      return new ColorUIResource( new Color( 146, 168, 73, 50) );
    }
  
    public ColorUIResource getControlHighlight() {
      return new ColorUIResource( new Color( 146, 168, 73, 50) );
    }
  
    public ColorUIResource getControlDarkShadow() {
      return new ColorUIResource( new Color( 146, 168, 73, 50) );
    }
  
    public ColorUIResource getControl() {
      return new ColorUIResource( new Color( 146, 168, 73, 50) );
    }
}
