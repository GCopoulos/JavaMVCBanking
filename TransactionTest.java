import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * JUnit test for Transactions.
 *
 * @author Gerasimos Copoulos
 * @version Oct24, 2020
 */
public class TransactionTest
{
    @Test
    public void testTransactions() {
        // inquire balance - doesn't change balance
        Account acct1 = new CheckingsAccount(300, "123", new char[]{'1','2'});
        int originalBalance = acct1.getBalance();
        Request irq = new Request(acct1);
        Transaction inquiry = new Inquiry(irq);
        inquiry.process();
        int curBalance = acct1.getBalance();
        Assert.assertEquals(originalBalance, curBalance);
        
        // withdraw - deducts balance
        int transactionAmount = 100;
        originalBalance = acct1.getBalance();
        Request wrq1 = new Request(acct1, transactionAmount);
        Transaction withdrawl1 = new Withdrawal(wrq1);
        withdrawl1.process();
        curBalance = acct1.getBalance();
        Assert.assertEquals(originalBalance-transactionAmount, curBalance);
        
        // withdraw - doesn't deduct balance if no sufficient amount
        originalBalance = curBalance;
        transactionAmount = 300;
        Request wrq2 = new Request(acct1, transactionAmount);
        Transaction withdrawl2 = new Withdrawal(wrq2);
        withdrawl2.process();
        curBalance = acct1.getBalance();
        Assert.assertEquals(originalBalance, curBalance);
        
        // deposit - add to balance
        originalBalance = curBalance;
        transactionAmount = 1000;
        Request drq1 = new Request(acct1, transactionAmount);
        Transaction deposit1 = new Deposit(drq1);
        deposit1.process();
        curBalance = acct1.getBalance();
        Assert.assertEquals(originalBalance+transactionAmount, curBalance);
        
        // withdraw - doesn't deduct balance if greater than 1000 (MAX_AMOUNT)
        originalBalance = curBalance;
        transactionAmount = 1200;
        Request wrq3 = new Request(acct1, transactionAmount);
        Transaction withdrawl3 = new Withdrawal(wrq3);
        withdrawl3.process();
        curBalance = acct1.getBalance();
        Assert.assertEquals(originalBalance, curBalance);
        
        // transfer - deduct from acct1, add to acct2
        Account acct2 = new CheckingsAccount(500, "456", new char[]{'3','4'});
        int originalBalance2 = acct2.getBalance();
        originalBalance = curBalance;
        transactionAmount = 500;
        Request trq1 = new Request(acct1, acct2, transactionAmount);
        Transaction transfer1 = new Transfer(trq1);
        transfer1.process();
        curBalance = acct1.getBalance();
        int curBalance2 = acct2.getBalance();
        Assert.assertEquals(originalBalance - transactionAmount, curBalance);
        Assert.assertEquals(originalBalance2 + transactionAmount, curBalance2);
        
        // transfer - doesn't transfer balance if greater than 1000 (MAX_AMOUNT)
        originalBalance = curBalance;
        originalBalance2 = curBalance2;
        transactionAmount = 1200;
        Request trq2 = new Request(acct1, acct2, transactionAmount);
        Transaction transfer2 = new Transfer(trq2);
        transfer2.process();
        curBalance = acct1.getBalance();
        curBalance2 = acct2.getBalance();
        Assert.assertEquals(originalBalance, curBalance);
        Assert.assertEquals(originalBalance2, curBalance2);
       
        
        // transfer - doesn't transfer balance if no sufficient amount
        originalBalance = curBalance;
        originalBalance2 = curBalance2;
        transactionAmount = 1000;
        Request trq3 = new Request(acct1, acct2, transactionAmount);
        Transaction transfer3 = new Transfer(trq3);
        transfer3.process();
        curBalance = acct1.getBalance();
        curBalance2 = acct2.getBalance();
        Assert.assertEquals(originalBalance, curBalance);
        Assert.assertEquals(originalBalance2, curBalance2);
    }
}