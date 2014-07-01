package pg;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class dbConnect {

	private List list = new ArrayList();
	private Connection con = null;

	public static void main(String[] args) {
		ArrayList sqlResult = new ArrayList();
		dbConnect dbcon = new dbConnect();
		try {
			sqlResult = (ArrayList)dbcon.selectJISSEKI();
			Iterator it = sqlResult.iterator();
			int i = 0;
			while(it.hasNext()){
				System.out.print(((JISSEKI)sqlResult.get(i)).getSAKUBAN_ID() + " : ");
				System.out.print(((JISSEKI)sqlResult.get(i)).getUSER_ID() + " : ");
				System.out.print(((JISSEKI)sqlResult.get(i)).getJISSEKI_DATE()+ " : ");
				System.out.print(((JISSEKI)sqlResult.get(i)).getJISSEKI_TIME_START()+ " : ");
				System.out.println(((JISSEKI)sqlResult.get(i)).getJISSEKI_TIME_END());
				it.next();
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private Connection getPgConnect() throws SQLException,ClassNotFoundException{

		Connection con = null;
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb"
				,"postgres"
				,"01ken014uMoka"
			);
		return con;
	}

	public List selectJISSEKI() throws SQLException,ClassNotFoundException{

		try{
			con = getPgConnect();
			final String sql = "select * from JISSEKI;";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(sql);

			 while (result.next()){
	             JISSEKI data = new JISSEKI(
	             	result.getInt(1),
	             	result.getInt(2),
	             	result.getDate(3),
	             	result.getTime(4),
	             	result.getTime(5)
	             );
	             list.add(data);
	         }
	         result.close();
	         statement.close();
	     }finally{
	         if (con != null){
	             con.close();
	         }
	     }
	     return list;
		}

	public List insertSAKUBAN(int SAKUBAN_ID,int USER_ID,Date date ,Time time_start,Time time_end) throws SQLException,ClassNotFoundException{

		try{ con = getPgConnect(); final String sql = "insert into JISSEKI ("
					+ "SAKUBAN_ID,"
					+ "USER_ID,"
					+ "JISSEKI_DATE,"
					+ "JISSEKI_TIME_START,"
					+ "JISSEKI_TIME_END"
					+ ") VALUES (" +
					+ SAKUBAN_ID
					+ USER_ID
					+ date
					+ time_start
					+ time_end
					+ ");";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(sql);
			 while (result.next()){
	             testTable data = new testTable(
	             	result.getInt(1),
	             	result.getString(2)
	             );
	             list.add(data);
	         }
	         result.close();
	         statement.close();
	     }finally{
	         if (con != null){
	             con.close();
	         }
	     }
	     return list;
		}
}
