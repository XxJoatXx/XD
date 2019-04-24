import java.util.ArrayList;

// This class contains methods which you need to complete to make the basic ATM work.
// Tutors can help you get this part working in lab sessions. Other modifications
// to the system you should do yourselves, based on similar examples we will cover
// in lectures and labs.

// a simple implementation of a bank
public class LocalBank 
{
    int theAccNumber = 0;        // The current account number
    int theAccPasswd = 0;        // The current account password
    LocalBankAccount currentAccount = null;
    LocalBankAccount currentPasswd = null;
    ArrayList<LocalBankAccount> accounts = new ArrayList<>();

    protected LocalBank()
    {
        Debug.trace( "LocalBank::<constructor"); 

        accounts.add(new LocalBankAccount(24601, 12345, 100, -50));
        accounts.add(new LocalBankAccount(31416, 22222, 50, 0));
    }

    // Set the account number to the number provided
    public void setAccNumber( int accNumber ) 
    { 
        Debug.trace( "LocalBank::setAccNumber: accNumber = " + accNumber ); 
            
        theAccNumber = accNumber;
        

    }

    // Set the account password to the number provided
    public void setAccPasswd( int accPasswd ) 
    { 
        Debug.trace( "LocalBank::setAccPasswd: accPassword = " + accPasswd ); 
           
        theAccPasswd = accPasswd;
        

    }

    // Check whether the current saved account and password correspond to 
    // an actual bank account, and if so set currentAccount to it, and return true.
    // Otherwise, reset the bank account and return false
    public boolean checkValid() 
    { 

        Debug.trace( "LocalBank::checkValid" ); 
           
        for (LocalBankAccount b: accounts) 
        {
            if(b.account == theAccNumber && b.password ==theAccPasswd){
                currentAccount = b;
                return true;
            } 
        } 
        theAccNumber = -1;
        theAccPasswd = -1;    
        currentAccount = null;
        return false;
    
}

    // Reset the bank to a 'logged out' state
    public void loggedOut() 
    {
        Debug.trace( "LocalBank::loggedOut" ); 

        theAccNumber = -1;
        theAccPasswd = -1;     
        currentAccount = null;
    }

    // withdraw the amount of money from the account. Return true if successful, or 
    // false if the amount is negative, or less than the amount in the account 
    public boolean withdraw(int amount) 
    { 
    Debug.trace( "LocalBank::withdraw: amount =" + amount ); 
            
        if(amount < 0 || currentAccount.balance < amount) {
        return false; 
    } else  { 
        currentAccount.balance -= amount;
        return true;
    }
}

    // deposit the amount of money into the account. Return true if successful,
    // or false if the amount is negative 
public boolean deposit( int amount )
    { 
        Debug.trace( "LocalBank::deposit: amount = " + amount ); 
        if(amount >= 0) {
            currentAccount.balance += amount;
            return true;
        } else{
        
        return false;
    }
    }

    // validate current account's password to allow to change it if returned true,
    // return false if the current password is incorrect
    public boolean changePassVal()
{
    Debug.trace("LocalBank::changePassVal");
    for(LocalBankAccount b: accounts) 
{
        if(b.password == theAccPasswd) {
            currentPasswd = b;
            return true;

}
}
        currentPasswd = null;
        theAccPasswd = -1;    
        return false;
}

    public boolean changePass()
{ 
        for(LocalBankAccount b: accounts)
        {
        if(b.newPass > 0 && b.newPass != theAccPasswd) {
            accounts.set(1, b);
            return true;
        }
    }
        return false;
}
        
        

    // Return the current balance in the account
    public int getBalance() 
    { 
        Debug.trace( "LocalBank::getBalance" ); 
         
        
        return currentAccount.balance;
    }
}