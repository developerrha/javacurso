/*
 * 
 */
package xmlread;

import java.io.File;
import nu.xom.Builder;
import nu.xom.Element;
import nu.xom.Elements;

/**
 *
 * @author root
 */
public class XmlRead {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Start main application");
        try {
            String xmlName = "./conf/peers.xml";
            File xmlFile = new File(xmlName);
            if( xmlFile.exists() ){
                System.out.println("xmlFile.length: "+xmlFile.length());
                Element elem = new Builder().build(xmlFile).getRootElement();
                Elements elems = elem.getChildElements();
                for( int i = 0; i <  elems.size(); ++i ){
                    Element node = elems.get(i);
                    String name = node.getLocalName();
                    String val = node.getValue();
                    System.out.println("name: "+name+" Value: "+val);
                }
                
                
            }else{
                System.out.println("xmlFile null");
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
}
