package local.host.thon;
import java.sql.*;
import java.util.ArrayList;

public class db {

    public static int dailyGeneratetotal() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        int temp=0;
        try(Connection conn = sql.getconnectio()){
            PreparedStatement stmt = conn.prepareStatement("select (select unit from dataSet where dates=? order by times desc limit 1) - (select unit from dataSet where dates =  (select  ? - interval 1 day) order by times desc limit 1) as data");
            stmt.setDate(1, new Date(System.currentTimeMillis()));
            stmt.setDate(2, new Date(System.currentTimeMillis()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                temp = rs.getInt(1);}
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return temp;
    }
    public static ArrayList<Integer> dailygenerate(String dates){
        ArrayList<Integer> res = new ArrayList<Integer>();
        try(Connection con = sql.getconnectio()){
            PreparedStatement stmt2 = con.prepareStatement("select unit from dataSet where dates = (select ? - interval  1 day) order by times desc limit 1");
            PreparedStatement stmt = con.prepareStatement("select times,unit from dataSet where dates=?");
            stmt.setDate(1,java.sql.Date.valueOf(dates));
            stmt2.setDate(1,java.sql.Date.valueOf(dates));
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()){
                res.add(rs2.getInt(1));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                res.add(rs.getInt(2));
            }
        }
        catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        ArrayList<Integer> resultf = new ArrayList<Integer>();
        for(int i=0;i<res.size()-1;i++){
            resultf.add(res.get(i+1)-res.get(i));
        }
        return (resultf);
    }
    public static int totalgeneration(){
        int temp=0;

        try(Connection con = sql.getconnectio()){
            PreparedStatement stmt = con.prepareStatement("select unit from dataSet order by ID desc limit 1");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                temp = rs.getInt(1);
            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return temp;
    }
    public static double totalave(){
        double temp=0;
        try(Connection con = sql.getconnectio()) {
            PreparedStatement stmt = con.prepareStatement("select unit/ID from dataSet order by ID desc limit 1");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                temp = rs.getDouble(1);
            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return temp;
    }


}
