
public class LocalBankAccount
{
    private int account;
    private int password;
    private int balance;
    private int overdraftlimit;
    
    public LocalBankAccount(int a, int p)
    {
        this(a, p, 0, 0);
    }

    public LocalBankAccount(int a, int p, int b, int o)
    {
        account = a;
        password = p;
        balance = b;
        overdraftlimit = o;
    }

    //Setters and getters for use in
    //local bank to return the values
    //and set them accordingly where needed
    public void setPassword(int password){
        this.password = password;
    }

    public void setAccount(int account){
        this.account = account;
    }

    public int getAccount(){
        return this.account;
    }

    public void setBalance(int balance){
        this.balance = balance;
    }

    public int getBalance(){
        return this.balance;
    }

    public boolean checkPassword(int password){
        return this.password == password;
    }

    public void setOverdraftLimit(int overdraftlimit){
        this.overdraftlimit = overdraftlimit;
    }

    public int getOverdraftLimit(){
        return this.overdraftlimit;
    }

   
}
