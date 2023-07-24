package pkgdir.modelo;

import com.mysql.cj.jdbc.Driver; 
import java.sql.*; 
import java.util.List;
import java.util.ArrayList;

public class MysqlServices{  
	public MysqlServices(){  
	}  

	public Object[] getDataFromMysql( String query ){
		Object[] objtemp = new Object[2];
		String stmp = "";
		try{  
			System.out.println("Into MysqlServices class");
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/almacen?useSSL=false","homzode","Mysqlpsql43*");  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery( query );  
			objtemp[0] = getColumnsName( rs );
			while(rs.next())  {
				for(int i=0;i < ((String[])objtemp[0]).length;i++){
					stmp += rs.getObject(i+1)+",";					
				}
				stmp += "\n";
			}
			con.close();  
			objtemp[1] = stmp;
		}catch(Exception e){ 
			e.printStackTrace();
		}
		return objtemp;
	}

	public String[] getColumnsName( ResultSet rstmp){
		String[] colNamesTmp = new String[ 1 ];
		List<String> colNames = new ArrayList<String>();
		try{
			 ResultSetMetaData rsMetaData = rstmp.getMetaData();
			 int count = rsMetaData.getColumnCount();
			 for(int i = 1; i<=count; i++) {
				String stmp = rsMetaData.getColumnName(i);
				colNames.add( stmp );
			 }
			colNamesTmp = new String[ colNames.size() ];
			colNames.toArray( colNamesTmp );
		}catch( Exception e){
			e.printStackTrace();
		}
		return colNamesTmp;
	}
	
	
}  
