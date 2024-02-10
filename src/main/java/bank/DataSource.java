package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {

  public static Connection connect(){
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;
    try{
      connection = DriverManager.getConnection(db_file);
    } catch(SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  public static Customer getCustomer(String username){
    String sql = "select * from customers where username = ?";
    Customer customer = null;
    try(Connection connection = connect(); 
        PreparedStatement statement = connection.prepareStatement(sql)){

          statement.setString(1, username);
          try(ResultSet resultSet = statement.executeQuery()){
            customer = new Customer(
              resultSet.getInt("id"),
              resultSet.getString("name"),
              resultSet.getString("username"),
              resultSet.getString("password"),
              resultSet.getInt("account_id"));
          }
    }catch(SQLException e){
      e.printStackTrace();
    }
    return customer;
  }

  public static Account getAccount(int accountId){
    String sql = "select * from accounts where id = ?";
    Account account =null;
    try(Connection connection = connect();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
          preparedStatement.setInt(1, accountId);
          try(ResultSet resultSet = preparedStatement.executeQuery()){
            account = new Account(
              resultSet.getInt("id"),
              resultSet.getString("type"),
              resultSet.getDouble("balance"));
          }
        }catch(SQLException e){
          e.printStackTrace();
        }
        return account;
  }

  public static void updateAccountBalance(double balance,int accountId ){
    String sql = "update accounts set balance = ? where is = ?";
    try (
      Connection connection = connect();
      PreparedStatement preparedStatement = connection.prepareStatement(sql)){
        preparedStatement.setDouble(1, balance);
        preparedStatement.setInt(2, accountId);
        preparedStatement.executeUpdate();
      }catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
