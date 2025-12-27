package me.jehn.sql;

import java.sql.*;
import java.util.ArrayList;

public class SQLMethods {


    public SQLMethods(){
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/subject", "sa", "");
            Statement stmt = connection.createStatement();
            //
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS subject(subject_name VARCHAR(40));");
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int methodSQL(String statement){
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/subject", "sa", "");
            Statement stmt = connection.createStatement();
            //
            ResultSet resultSet = stmt.executeQuery(statement);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e){
            e.printStackTrace();

        }
        return 0;
    }

    public void updateSQL(String statement){
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/subject", "sa", "");
            Statement stmt = connection.createStatement();
            //
            stmt.executeUpdate(statement);
        } catch (Exception e){
            e.printStackTrace();

        }
    }
    public ArrayList getSQL(String statement, String column){
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/subject", "sa", "");
            Statement stmt = connection.createStatement();
            //
            ResultSet resultset = stmt.executeQuery(statement);
            ArrayList<String> list = new ArrayList<>();
            while (resultset.next()){
                list.add(resultset.getString(column));
            }
            return list;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void deleteSQL(String statement){
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/subject", "sa", "");
            Statement stmt = connection.createStatement();
            //
            stmt.executeUpdate(statement);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
