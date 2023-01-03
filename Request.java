
/**
 * Class Request: This class represents a transaction request made by the user
 *
 * @author Gerasimos Copoulos
 * @version Oct25,2020
 */
public class Request
{
    private Account fromAcct;
    private Account toAcct;
    private int amount;
    //private boolean valid;

    /**
     * Constructors for objects of class Request
     */
    public Request(Account fromAcct){
        this.fromAcct = fromAcct;
    }
    public Request(Account fromAcct, int amount){
        this.fromAcct = fromAcct;
        this.amount = amount;
    }
    public Request(Account fromAcct, Account toAcct, int amount){
        this.fromAcct = fromAcct;
        this.toAcct = toAcct;
        this.amount = amount;
    }

    /**
     * getAmount
     *
     * @param None
     * @return the requested amount of transaction, if any
     */
    public int getAmount()
    {
        return amount;
    }
    
    /**
     * getFromAccount
     *
     * @param None
     * @return the account that initiates the transaction
     */
    public Account getFromAccount()
    {
        return fromAcct;
    }
    
    /**
     * getToAccount
     *
     * @param None
     * @return the account that receives the transaction, if any
     */
    public Account getToAccount()
    {
        return toAcct;
    }
}
