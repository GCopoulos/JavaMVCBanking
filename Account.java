
/**
 * Abstract class Account: to be used in Checkings Account and Savings Account
 *
 * @author Gerasimos Copoulos
 * @version Oct25, 2020
 */
public abstract class Account{

    int balance;
    String accountNumber;
    double interestRate;
    char[] PIN;
    String type; 
    int minimumBalance = 0;
    
    public Account(){
        
        
    }
    /**
     * Constructor for objects of class Account
     */
    public Account(int balance, String accountNumber, char[] PIN)
    {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.PIN = PIN;
    }

    /**
     *
     * @param none
     * @return returns the current balance of an account
     */
    public int getBalance()
    {
        
        return balance;
    }
    
    /**
     * @param none
     * @return returns the security PIN of an account
     */
    public char[] getPIN(){
        return PIN;
    }
    
    /**
     * @param none
     * @return returns the account number
     */
    public String getAccountNumber(){
        return accountNumber;
    }
    
    /**
     * @param none
     * @return returns the account type
     */
    public String getAccountType(){
        return type;
    }
    
    /**
     * @param none
     * @return returns the minimum balance
     */
    public int getMinimumBalance(){
        
        return this.minimumBalance;
        
    }
    
    /**
     * @param amount to be deducted from account
     * @return none
     */
    public void deduct(int amount){
        this.balance = this.balance - amount;
    }
    
    /**
     * @param amount to be added to account
     * @return none
     */
    public void add(int amount){
        this.balance = this.balance + amount;
    }
}
