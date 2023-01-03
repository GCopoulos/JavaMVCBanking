import javax.swing.*;
import java.awt.*;
/**
 * Class Inquiry: inquire the type and balance of an account
 *
 * @author Gerasimos Copoulos
 * @version Oct25,2020
 */
public class Inquiry extends Transaction
{
    Request rq;
    /**
     * Constructor for objects of class Inquiry
     */
    public Inquiry(Request req){
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
     * printReceipt: looks up the inquired balance and displays it
     *
     * @param  none
     * @return none
     */
    public void printReceipt() {
        Account curAcct = rq.getFromAccount();
        int thisbalance = curAcct.getBalance();
        JFrame TextFrame = new JFrame("Account Inquiry");
        TextFrame.setLayout(new BorderLayout());
        String thistype = curAcct.getAccountType();
        JOptionPane.showMessageDialog(TextFrame,"This is a " + thistype + " account with a balance of $" + thisbalance + ".");
    }
}
