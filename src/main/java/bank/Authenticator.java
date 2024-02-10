package bank;

import javax.security.auth.login.LoginException;

public class Authenticator {

  public static Customer login(String username, String password) throws LoginException{

    Customer customer = DataSource.getCustomer(username);
    // == compares the memory location
    if(customer == null){
      throw new LoginException("The username not found");
    }
    // } .equals() compares the value in the object
    if(password.equals(customer.getPassword())){
       customer.setAuthenticated(true);
       return customer;
    }
    else throw new LoginException("Incorrect password");
  }

  public static void logout(Customer customer){
    customer.setAuthenticated(false);
  }
}
