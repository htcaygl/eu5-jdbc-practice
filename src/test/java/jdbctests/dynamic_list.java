package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dynamic_list {

    String dbUrl="jdbc:oracle:thin:@52.87.154.190:1521:xe";
    String dbUsername="hr";
    String dbPassword="hr";

    @Test
    public void dynamicList() throws SQLException {

        //create connection
        Connection connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        // create statement object
        Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet=statement.executeQuery("select first_name, last_name, salary, job_id from employees where rowNum<6");

        // get the resultset object metadata
        ResultSetMetaData rsMetaData= resultSet.getMetaData();

        //list for keeping all rows in a Map
        List<Map<String, Object>> queryData = new ArrayList<>();

        //number of columns
        int colCount= rsMetaData.getColumnCount();

        //loop through each row
        while (resultSet.next()) {

            Map<String, Object> row= new HashMap<>();

            for(int i=1;i<=colCount;i++){

                row.put(rsMetaData.getColumnName(i),resultSet.getObject(i));

            }

            //add your map to your list
            queryData.add(row);
        }

        //print result
        for ( Map<String, Object> row : queryData) {
            System.out.println(row.toString());
        }

                //close all connections
        resultSet.close();
        statement.close();
        connection.close();

    }

}
