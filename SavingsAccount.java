
/**
 * Class SavingsAccount: An Account object which the user should not be able to freely withdraw from.
 *
 * @author Gerasimos Copoulos
 * @version Oct25, 2020
 */
public class SavingsAccount extends Account
{
    int balance;
    String accountNumber;
    char[] PIN;
    public double interestRate = .02;
    int minimumBalance = 500;
    String type = "savings";

    /**
     * Constructor for objects of class SavingsAccount
     */
    public SavingsAccount(int balance, String accountNumber, char[] PIN)
    {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.PIN = PIN;
        //constructor that allows setting of field variables
    }

    
    /**
     *
     * @param none
     * @return returns the current balance of an account
     */
    public int getBalance()
    {
        return balance;
        //returns balance
    }
    
    
    /**
     * @param none
     * @return returns the security PIN of an account
     */
    public char[] getPIN(){
        return PIN;
        //returns PIN
    }
    
    /**
     * @param none
     * @return returns the account number
     */
    public String getAccountNumber(){
        return accountNumber;
        //returns account number
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
        if (ATMSystem.numTrans == 4){
            this.balance = this.balance + (int)(this.balance * this.interestRate);
            ATMSystem.numTrans = 0;
        }
        else{
            ATMSystem.numTrans++;
        }
        //takes current balance and subtracts amount passed as argument from balance
    }
    
    /**
     * @param amount to be added to account
     * @return none
     */
    public void add(int amount){
        this.balance = this.balance + amount;
        if (ATMSystem.numTrans == 4){
            this.balance = this.balance + (int)(this.balance * this.interestRate);
            ATMSystem.numTrans = 0;
        }
        else{
            ATMSystem.numTrans++;
        }
        //takes current balance and adds amount passed as argument to balance
    }
    
}
