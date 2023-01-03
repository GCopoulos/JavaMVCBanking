
/**
 * Class Deposit: add amount to an account
 *
 * @author Gerasimos Copoulos
 * @version Oct23,2020
 */
public class Deposit extends Transaction
{
    private Request rq;
    
    /**
     * Constructor for objects of class Deposit
     */
    public Deposit(Request req){
        this.rq = req;
    }

    /**
     * validateRequest: check if a request is valid
     *
     * @param none
     * @return a boolean confirming if the request is valid
     */
    public boolean isValidRequest(){
        return true;
    }
    
    /**
     * performRequest: add amount to the account
     *
     * @param None
     * @return None
     */
    public void performRequest(){
        Account acct = rq.getFromAccount();
        int amount = rq.getAmount();
        acct.add(amount);
        String destinationType = "savings";
    }
    
    /**
     * printReceipt: print the change made by this transaction
     *
     * @param None
     * @return None
     */
    public void printReceipt(){
        String info = "Deposited $" + rq.getAmount() + " to account " + rq.getFromAccount().getAccountNumber() + ".";
        Receipt receipt = new Receipt(info, rq.getFromAccount());
    }
}
