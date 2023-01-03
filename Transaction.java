import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Abstract class Transaction: This class provides basic functions a transaction has.
 *
 * @author Gerasimos Copoulos
 * @version Oct25,2020
 */
public abstract class Transaction
{
    private Request rq;
    public static final int MAX_AMOUNT = 1000;
    
    /**
     * process: a method for all subclasses, representing the steps to process a request
     *
     * @param None
     * @return a boolean representing if the request is successfully processed
     * @print a receipt if the request is successful, or an error message if not
     */
    public boolean process(){
        if (this.isValidRequest()){
            performRequest();
            printReceipt();
            return true;
        } else{
            printErrorMsg();
            return false;
        }
    }
    
    /**
     * validateRequest: check if a request is valid
     *
     * @param none
     * @return a boolean confirming if the request is valid
     */
    public boolean isValidRequest(){
        return false;
    }
    
    /**
     * performRequest: perform the request
     *
     * @param None
     * @return None
     */
    public void performRequest(){
        return;
    }
    
    /**
     * printReceipt: print the change made by this transaction
     *
     * @param None
     * @return None
     */
    public void printReceipt(){
    }
    
    /**
     * printErrorMsg: inform the error occured while processing the transaction request
     *
     * @param None
     * @return None
     */
    public void printErrorMsg(){
        String msg = "";
        JFrame errorFrame = new JFrame("Error Message");
        errorFrame.setLayout(new BorderLayout());
        JOptionPane.showMessageDialog(errorFrame, "Transaction rejected.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
