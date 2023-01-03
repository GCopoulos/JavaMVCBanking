import javax.swing.*;
import java.awt.*;

/**
 * Class Withdrawal: withdraw amount from an account
 *
 * @author Gerasimos Copoulos
 * @version Oct23,2020
 */
public class Withdrawal extends Transaction
{
    private Request rq;
    
    /**
     * Constructor for objects of class Deposit
     */
    public Withdrawal(Request req){
        this.rq = req;
    }

    /**
     * validateRequest: check if a request is valid
     *
     * @param none
     * @return a boolean confirming if the request is valid
     */
    public boolean isValidRequest(){
        Account acct = rq.getFromAccount();
        int amount = rq.getAmount();
        int availableBalance = acct.getBalance();
        if (acct.getAccountType().equals("savings")) {
            availableBalance = acct.getBalance() - acct.getMinimumBalance();
        }
        if (amount > availableBalance){
            return false;
        }
        if (amount > MAX_AMOUNT){
            return false;
        }
        return true;
    }
    
    /**
     * performRequest: deduct amount from the account
     *
     * @param None
     * @return None
     */
    public void performRequest(){
        Account fromAcct = rq.getFromAccount();
        int amount = rq.getAmount();
        fromAcct.deduct(amount);
    }
    
    /**
     * printReceipt: print the change made by this transaction
     *
     * @param None
     * @return None
     */
    public void printReceipt(){
        String info = "Withdrew $" + rq.getAmount() + " from account " + rq.getFromAccount().getAccountNumber() + ".";
        Receipt receipt = new Receipt(info, rq.getFromAccount());
    }
    
    /**
     * printErrorMsg: inform the error occured while processing the transaction request
     *
     * @param None
     * @return None
     */
    public void printErrorMsg(){
        String msg = "";
        Account acct = rq.getFromAccount();
        if (rq.getAmount() > MAX_AMOUNT){
            msg = "Please keep your transaction amount under " + MAX_AMOUNT + " for each transaction.";
        } else{
            if (acct.getAccountType().equals("savings")){
                msg = "You cannot withdraw below the minimum Savings account balance of $500.";
            }
            else {
                msg = "No sufficient balance to support this transaction.";
            }
        }
        
        JFrame errorFrame = new JFrame("Error Message");
        errorFrame.setLayout(new BorderLayout());
        JOptionPane.showMessageDialog(errorFrame, "Transaction rejected. " + msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
