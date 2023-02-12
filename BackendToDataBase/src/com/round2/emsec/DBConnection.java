package com.round2.emsec;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

 public static void main(String[] args) {
  String className = "oracle.jdbc.driver.OracleDriver";
  String url = "jdbc:oracle:thin:@localhost:1521:xe";
  String userName = "neha";
  String password = "neha";
  Connection conn = null;
  String QUERY = "select * from tablename";
  PreparedStatement preparedStatement = null;
  ResultSet resultSet = null;

  try {
   Class.forName(className);
   conn = DriverManager.getConnection(url, userName, password);
   if (conn != null) {
    preparedStatement = conn.prepareStatement(QUERY);
   }
   if(preparedStatement!=null) {
    resultSet = preparedStatement.executeQuery();
   }
   if(resultSet!=null) {
    while(resultSet.next()!=false) {
     System.out.println("print data");
    }
   }

  } catch (ClassNotFoundException e) {
   e.printStackTrace();
  } catch (SQLException e) {
   e.printStackTrace();
  }catch (Exception e) {
   e.printStackTrace();
  }
  finally {
   try {
    resultSet.close();
   } 
   catch (SQLException e) {
        e.printStackTrace();
   }
   try {
    preparedStatement.close();
   } 
   catch (SQLException e) {
    e.printStackTrace();
   }
   try {
    conn.close();
   }
   catch (SQLException e) {
    e.printStackTrace();
   }
   
  }

 }

}