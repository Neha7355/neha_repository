package com.emsec.exam;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Crudoperation extends HttpServlet {

 PrintWriter pw = null;
 String tableName = null;
 String columnName = null;
 String oldData = null;
 String newData = null;
 String dData = null;
 //String message = null;
 String className = "oracle.jdbc.driver.OracleDriver";
 String url = "jdbc:oracle:thin:@localhost:1521:xe";
 String user = "dharam";
 String pass = "dharam";

 @Override
 public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
  res.setContentType("text/html");
  pw = res.getWriter();

  tableName = req.getParameter("tname");
  pw.println(tableName);
  columnName = req.getParameter("cname");
  oldData = req.getParameter("old");
  newData = req.getParameter("new");
  dData = req.getParameter("ddata");
  //message = req.getParameter("message");
  Connection connection = null;
  String message = "create";
  String createQuery = "create table ? (task varchar2(20),is_completed varchar2(5),end_date varchar2(10))";
  String updateQuery = "update  ? set ? = ? where ?=? ";
  String deleteQuery = "delete from ? where ? = ?";
  String selectQuery = "select * from ?";
  PreparedStatement preparedStatement = null;
  ResultSet resultSet = null;
  int result = 0;
  boolean rs = false;

  try {
   Class.forName(className);
   connection = DriverManager.getConnection(url, user, pass);
   if (message.equals("create")) {
    preparedStatement = connection.prepareStatement(createQuery);
    preparedStatement.setString(1, tableName);
   } else if (message.equals("update")) {
    preparedStatement = connection.prepareStatement(updateQuery);
    preparedStatement.setString(1, tableName);
    preparedStatement.setString(2, columnName);
    preparedStatement.setString(3, newData);
    preparedStatement.setString(4, columnName);
    preparedStatement.setString(5, oldData);
   } else if (message.equals("delete")) {
    preparedStatement = connection.prepareStatement(deleteQuery);
    preparedStatement.setString(1, tableName);
    preparedStatement.setString(2, columnName);
    preparedStatement.setString(3, dData);
   } else {
    preparedStatement = connection.prepareStatement(selectQuery);
    preparedStatement.setString(1, tableName);
   }

   if (message.equals("select")) {
    resultSet = preparedStatement.executeQuery();
   } else if (message.equals("create")) {
    rs = preparedStatement.execute();
   } else {
    result = preparedStatement.executeUpdate();
   }
   if (resultSet != null) {
    while (resultSet.next() != false) {
     pw.println();
    }
   }
   if (message.equals("delete") && result != 0) {
    pw.println("Record deleted");
   } else if (message.equals("update") && result != 0) {
    pw.println("Record updated");
   }
   if (rs) {
    pw.println("Table created");
   }

  } catch (ClassNotFoundException e) {
   e.printStackTrace();
  } catch (SQLException e) {
   e.printStackTrace();
  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   try {
    if(resultSet!=null) {
    resultSet.close();
    }
   } catch (SQLException e) {
    e.printStackTrace();
   }
   try {
    preparedStatement.close();
   } catch (SQLException e) {
    e.printStackTrace();
   }
   try {
    connection.close();
   } catch (SQLException e) {
    e.printStackTrace();
   }

  }
 }
 }