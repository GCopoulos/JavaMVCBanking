import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 * Class ATMSystem: Class containing the menus and data for an ATM.
 *
 * @author Gerasimos Copoulos
 * @version Oct25, 2020
 */
public class ATMSystem extends JPanel
                             implements ActionListener 
{
    // instance variables 
    public Account ActiveAccount;
    public String ActiveAccountNum;
    public char[] ActiveAccountPIN;
    public boolean activeInquiry = false;
    
    public Account ActiveSecondaryAccount;
    public String ActiveSecondaryAccountNum;
    
    public Account FriendAccount;
    public String FriendAccountNum;
    
    String acctnumFieldString = new String("JTextField");
    String pinFieldString = new String("JPasswordField");
    String WFieldString = new String("WField");
    String DFieldString = new String("DField");
    String TFieldString = new String("TField");
    String T2FieldString = new String("T2Field");
    
    protected JLabel actionLabel;
    protected JFrame InfoFrame;
    protected JFrame MainMenuFrame;
    protected JTextField acctnumField;
    protected JPasswordField pinField;
    
    protected JFrame WFrame;
    protected JFrame DFrame;
    protected JFrame TFrame;
    
    protected JTextField WField;
    protected JTextField DField;
    protected JTextField TField;
    protected JTextField T2Field;
    
    String ExampleAcct = new String("12");
    //String ExamplePINString = new String("34");
    char[] ExamplePIN = new char[] {'3','4'};
    
    Account[] AccountDatabase = new Account[6];
    
    public static int numTrans = 0;
    
   

    /**
     * Constructor for objects of class ATMSystem
     */
    public ATMSystem()
    {
        
        
    }
    
    /**
     * Constructor for ATMSystem objects which species active and 
     * friend accounts on initialization.
     * 
     * @param ACT1  The active account used for this ATM instance
     * @param ACT2 The account used to represent a friend's account in this
     * @param Savings The secondary account used for this instance, which can be switched to.
     * @return No return, just constructs an ATMSystem object using the input parameters.
     */
    
    public ATMSystem(Account ACT1, Account ACT2, Account Savings){
        this.ActiveAccount = ACT1;
        this.FriendAccount = ACT2;
        this.ActiveAccountNum = ActiveAccount.getAccountNumber();
        this.ActiveAccountPIN = ActiveAccount.getPIN();
        this.FriendAccountNum = FriendAccount.getAccountNumber();
        this.ActiveSecondaryAccount = Savings;
        this.ActiveSecondaryAccountNum = Savings.getAccountNumber();
        WFrame = new JFrame();
        this.WFrame.setVisible(false);
        DFrame = new JFrame();
        this.DFrame.setVisible(false);
        TFrame = new JFrame();
        this.TFrame.setVisible(false);
        this.activeInquiry = false;
    }

    /**
     * Gets the account currently used by this ATMObject.
     *
     * @return    The active account object.
     */
    public Account GetActiveAccount()
    {   
        return this.ActiveAccount;
    }
    
    /**
     *  Displays the Main Menu of the ATM.
     *
     * @return    No return, just displays a GUI.
     */
    
    public void showMenu(){
      MainMenuFrame = new JFrame();
      MainMenuFrame.setLayout(new FlowLayout());
      MainMenuFrame.setLocationRelativeTo(null);
      
      JButton b1 = new JButton("Withdraw");
      JButton b2 = new JButton("Deposit");
      JButton b3 = new JButton("Transfer");
      JButton b4 = new JButton("Inquiry");
      JButton ChangeAcctB = new JButton("Change Account Type");
      JButton ExitB = new JButton("Exit");
      b1.setToolTipText("Click here to withdraw funds.");
      b2.setToolTipText("Click here to deposit funds.");
      b3.setToolTipText("Click here to transfer funds between accounts.");
      b4.setToolTipText("Click here to check the account balance.");
      ChangeAcctB.setToolTipText("Click here to switch to your other account.");
      ExitB.setToolTipText("Click here to exit the ATM program.");
      
      b1.addActionListener(event -> this.actionPerformedW());
      b2.addActionListener(event -> this.actionPerformedD());
      b3.addActionListener(event -> this.actionPerformedT());
      b4.addActionListener(event -> this.actionPerformedI());
      ChangeAcctB.addActionListener(event -> this.actionPerformedChange());
      ExitB.addActionListener(event -> this.actionPerformedExit());
      ExitB.setAlignmentX(Component.CENTER_ALIGNMENT);
      ExitB.setAlignmentY(Component.BOTTOM_ALIGNMENT);

      MainMenuFrame.add(b1);
      MainMenuFrame.add(b2);
      MainMenuFrame.add(b3);
      MainMenuFrame.add(b4);
      MainMenuFrame.add(ChangeAcctB);
      MainMenuFrame.add(ExitB);
      MainMenuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      MainMenuFrame.pack();
      MainMenuFrame.setVisible(true);
    }
    
    /**
     * The method called when the "Exit" button is pressed.
     *
     * @return    No return, just closes the program.
     */
    
    public void actionPerformedExit(){
        System.exit(0);
    }
    
    /**
     * The method called when the "Change Account Type" button is pressed.
     *
     * @return    No return, just changes which account the ATM is actively referring to.
     */
    
    public void actionPerformedChange(){
        Account ActiveSave = ActiveAccount;
        ActiveAccount = ActiveSecondaryAccount;
        ActiveSecondaryAccount = ActiveSave;
        ActiveSecondaryAccountNum = ActiveSecondaryAccount.getAccountNumber();
        JFrame TextFrame = new JFrame("Account Inquiry");
        TextFrame.setLayout(new BorderLayout());
        String thistype = ActiveAccount.getAccountType();
        String thisNum = ActiveAccount.getAccountNumber();
        JOptionPane.showMessageDialog(TextFrame,"Current account successfully switched to " + thistype + " account, Account #" + thisNum + ".");
    }
    
    /**
     * The method called when the "Withdraw" button is pressed.
     *
     * @return    No return, just opens the Withdrawal prompt window.
     */
    
    public void actionPerformedW(){
        if (!(DFrame.isVisible() || WFrame.isVisible() || TFrame.isVisible() || activeInquiry)){
            InfoFrame.setVisible(false);
            WFrame = new JFrame();
            WFrame.setLayout(new BorderLayout());
            WFrame.setLocationRelativeTo(null);

            this.WField = new JTextField(10);
            WField.setActionCommand(WFieldString);
            WField.addActionListener(this);
      
            JLabel WFieldLabel = new JLabel("Withdrawal Amount:");
            WFieldLabel.setLabelFor(WField);
 
            this.actionLabel = new JLabel("Enter the desired amount and press Enter.  Close window to cancel.");
            actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
      
            JPanel WPane = new JPanel();
            GridBagLayout Wgridbag = new GridBagLayout();
            GridBagConstraints Wc = new GridBagConstraints();
 
            WPane.setLayout(Wgridbag);
 
            JLabel[] labels = {WFieldLabel};
            JTextField[] textFields = {WField};
            addLabelTextRows(labels, textFields, Wgridbag, WPane);
 
            Wc.gridwidth = GridBagConstraints.REMAINDER; 
            Wc.anchor = GridBagConstraints.WEST;
            Wc.weightx = 1.0;
            WPane.add(actionLabel, Wc);
            WPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Withdrawal"),BorderFactory.createEmptyBorder(5,5,5,5)));
      
            WFrame.add(WPane, BorderLayout.PAGE_START);
      
            WFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            WFrame.pack();
            WFrame.setVisible(true);
      }
      else {
            JFrame NewFrame = new JFrame("Transaction already active.");
            NewFrame.setLayout(new BorderLayout());
            JOptionPane.showMessageDialog(NewFrame,"You cannot attempt multiple transactions at once.");
      }
    }
    
    /**
     * The method called when the "Deposit" button is pressed.
     *
     * @return    No return, just opens the Deposit prompt window.
     */
    
    public void actionPerformedD(){
        if (!(DFrame.isVisible() || WFrame.isVisible() || TFrame.isVisible() || activeInquiry)){
            InfoFrame.setVisible(false);
            DFrame = new JFrame();
            DFrame.setLayout(new BorderLayout());
            DFrame.setLocationRelativeTo(null);

            this.DField = new JTextField(10);
            DField.setActionCommand(DFieldString);
            DField.addActionListener(this);
      
            JLabel DFieldLabel = new JLabel("Deposit Amount:");
            DFieldLabel.setLabelFor(DField);
 
            this.actionLabel = new JLabel("Enter the desired amount and press Enter.  Close window to cancel.");
            actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
      
            JPanel DPane = new JPanel();
            GridBagLayout Dgridbag = new GridBagLayout();
            GridBagConstraints Dc = new GridBagConstraints();
 
            DPane.setLayout(Dgridbag);
 
            JLabel[] labels = {DFieldLabel};
            JTextField[] textFields = {DField};
            addLabelTextRows(labels, textFields, Dgridbag, DPane);
 
            Dc.gridwidth = GridBagConstraints.REMAINDER; 
            Dc.anchor = GridBagConstraints.WEST;
            Dc.weightx = 1.0;
            DPane.add(actionLabel, Dc);
            DPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Deposit"), BorderFactory.createEmptyBorder(5,5,5,5)));
      
            DFrame.add(DPane, BorderLayout.PAGE_START);
      
            DFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            DFrame.pack();
            DFrame.setVisible(true);
    }
    else {
            JFrame NewFrame = new JFrame("Transaction already active.");
            NewFrame.setLayout(new BorderLayout());
            JOptionPane.showMessageDialog(NewFrame,"You cannot attempt multiple transactions at once.");
      }
    }
    
    /**
     * The method called when the "Transfer" button is pressed.
     *
     * @return    No return, just opens the Transfer prompt window.
     */
    
    public void actionPerformedT(){
         if (!(DFrame.isVisible() || WFrame.isVisible() || TFrame.isVisible() || activeInquiry)){
             InfoFrame.setVisible(false);
             TFrame = new JFrame();
             TFrame.setLayout(new BorderLayout());
             TFrame.setLocationRelativeTo(null);

             this.TField = new JTextField(10);
             TField.setActionCommand(TFieldString);
             TField.addActionListener(this);
      
             this.T2Field = new JTextField(10);
             T2Field.setActionCommand(T2FieldString);
             T2Field.addActionListener(this);
      
             JLabel TFieldLabel = new JLabel("Destination Account #:");
             TFieldLabel.setLabelFor(TField);
             JLabel T2FieldLabel = new JLabel("Transfer Amount:");
             T2FieldLabel.setLabelFor(T2Field);
 
             this.actionLabel = new JLabel("Enter a valid destination account and transfer amount and press Enter.  Close window to cancel. \n Reminder: Your alternate account's # is: " + ActiveSecondaryAccountNum + " and your friend's account # is: " + FriendAccountNum + ".");
             actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
      
             JPanel WPane = new JPanel();
             GridBagLayout Wgridbag = new GridBagLayout();
             GridBagConstraints Wc = new GridBagConstraints();
 
             WPane.setLayout(Wgridbag);
 
             JLabel[] labels = {TFieldLabel, T2FieldLabel};
             JTextField[] textFields = {TField, T2Field};
             addLabelTextRows(labels, textFields, Wgridbag, WPane);
 
             Wc.gridwidth = GridBagConstraints.REMAINDER;
             Wc.anchor = GridBagConstraints.WEST;
             Wc.weightx = 1.0;
             WPane.add(actionLabel, Wc);
             WPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Transfer"), BorderFactory.createEmptyBorder(5,5,5,5)));
      
             TFrame.add(WPane, BorderLayout.PAGE_START);
      
             TFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
             TFrame.pack();
             TFrame.setVisible(true);
    }
    else {
            JFrame NewFrame = new JFrame("Transaction already active.");
            NewFrame.setLayout(new BorderLayout());
            JOptionPane.showMessageDialog(NewFrame,"You cannot attempt multiple transactions at once.");
      }
    }
    
    /**
     * The method called when the "Inquiry" button is pressed.
     *
     * @return    No return, just opens the Inquiry message window by calling the Inquiry class' process method.
     */
    
    public void actionPerformedI(){
        this.activeInquiry = true;
        Request IRequest = new Request(ActiveAccount);
        Inquiry thisInquiry = new Inquiry(IRequest);
        thisInquiry.process();
        this.activeInquiry = false;
    }
    
    /**
     *  Displays a prompt for the user to input their PIN and ACCT #.
     *  
     *  @return No return, just checks the inputs and moves on.
     */
    
    public void InfoPrompt(){
      InfoFrame = new JFrame();
      InfoFrame.setLayout(new BorderLayout());
      InfoFrame.setLocationRelativeTo(null);

      //Create an Account Number field.
      this.acctnumField = new JTextField(10);
      acctnumField.setActionCommand(acctnumFieldString);
      acctnumField.addActionListener(this);
 
      //Create a PIN field.
      this.pinField = new JPasswordField(10);
      pinField.setActionCommand(pinFieldString);
      pinField.addActionListener(this);
      
      JLabel acctnumFieldLabel = new JLabel("Account #:");
      acctnumFieldLabel.setLabelFor(acctnumField);
      JLabel pinFieldLabel = new JLabel("PIN #:");
      pinFieldLabel.setLabelFor(pinField);
 
      //Changeable text line
      this.actionLabel = new JLabel("Enter your information and press Enter.");
      actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
      
      JPanel textControlsPane = new JPanel();
      GridBagLayout gb = new GridBagLayout();
      GridBagConstraints c = new GridBagConstraints();
 
      textControlsPane.setLayout(gb);
 
      JLabel[] labels = {acctnumFieldLabel, pinFieldLabel};
      JTextField[] text = {acctnumField, pinField};
      addLabelTextRows(labels, text, gb, textControlsPane);
 
      c.gridwidth = GridBagConstraints.REMAINDER; 
      c.anchor = GridBagConstraints.WEST;
      c.weightx = 1.0;
      textControlsPane.add(actionLabel, c);
      textControlsPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("ATM Login"), BorderFactory.createEmptyBorder(5,5,5,5)));
      
      InfoFrame.add(textControlsPane, BorderLayout.PAGE_START);

      InfoFrame.pack();
      InfoFrame.setVisible(true);
    }
    
    /**
     *   Formats an input field to have text and labels associated
     *   with that text.
     *   
     *   @return No return, just helps formatting.
     */
    
    private void addLabelTextRows(JLabel[] labels, JTextField[] text, GridBagLayout gb, Container container) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        int numLabels = labels.length;
        // Builds the fields with the correct labels:
        for (int i = 0; i < numLabels; i++) {
            c.gridwidth = GridBagConstraints.RELATIVE; 
            c.fill = GridBagConstraints.NONE;      
            c.weightx = 0.0;                       
            container.add(labels[i], c);
            c.gridwidth = GridBagConstraints.REMAINDER;     
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            container.add(text[i], c);
        }
    }
    
    /**
     * Displays helpful information about what accounts are available to be referenced in this ATM instance.  This exists
     * purely to help the user in the simulation aspect of this program; in real life the user would already know this
     * information prior to using the ATM.
     *
     * @return    No return, just shows a message window with the relevant Accounts' information.
     */
    
    public void DisplayInfo(){
        JFrame TextFrame = new JFrame("Account Login Information");
        TextFrame.setLayout(new BorderLayout());
        String A1PINString = new String(ActiveAccountPIN);
        JOptionPane.showMessageDialog
        (TextFrame,"Checking Acct #: " + ActiveAccountNum + "\nSavings Acct #: " + ActiveSecondaryAccountNum + "\nPIN #: " + A1PINString + "\n Friend's Acct #: " + FriendAccountNum);
        this.InfoPrompt();
    }
    
    /**
     * The method called when the Enter key is pressed on any text field the program uses.
     *
     * @param   e   The ActionEvent (in this case, the Enter key press) that caused this method to be called.
     * @return    No return, just performs a variety of actions based on what text field is being used and what input
     *            is given in it.
     */
    
    public void actionPerformed(ActionEvent e) {
        // Log In text fields
        if (acctnumFieldString.equals(e.getActionCommand())) {
            String AcctNumInput = acctnumField.getText();
            char[] PINInput = pinField.getPassword();
            if (AcctNumInput.equals(ActiveAccountNum) && Arrays.equals(PINInput, ActiveAccountPIN)){
                InfoFrame.dispose();
                this.showMenu();
                InfoFrame.dispatchEvent(new WindowEvent(InfoFrame, WindowEvent.WINDOW_CLOSING));
            }
            else if (AcctNumInput.equals(ActiveSecondaryAccountNum) && Arrays.equals(PINInput, ActiveAccountPIN)){
                Account ActiveSave = ActiveAccount;
                ActiveAccount = ActiveSecondaryAccount;
                ActiveSecondaryAccount = ActiveSave;
                ActiveSecondaryAccountNum = ActiveSecondaryAccount.getAccountNumber();
                InfoFrame.dispose();
                this.showMenu();
                InfoFrame.dispatchEvent(new WindowEvent(InfoFrame, WindowEvent.WINDOW_CLOSING));
            }
            else {
                this.actionLabel.setText("That information is not valid.");
            }
        } else if (pinFieldString.equals(e.getActionCommand())) {
            String AcctNumInput = acctnumField.getText();
            char[] PINInput = pinField.getPassword();
            if (AcctNumInput.equals(ActiveAccountNum) && Arrays.equals(PINInput, ActiveAccountPIN)){
                InfoFrame.dispose();
                this.showMenu();
                InfoFrame.dispatchEvent(new WindowEvent(InfoFrame, WindowEvent.WINDOW_CLOSING));
            }
            else if (AcctNumInput.equals(ActiveSecondaryAccountNum) && Arrays.equals(PINInput, ActiveAccountPIN)){
                Account ActiveSave = ActiveAccount;
                ActiveAccount = ActiveSecondaryAccount;
                ActiveSecondaryAccount = ActiveSave;
                ActiveSecondaryAccountNum = ActiveSecondaryAccount.getAccountNumber();
                InfoFrame.dispose();
                this.showMenu();
                InfoFrame.dispatchEvent(new WindowEvent(InfoFrame, WindowEvent.WINDOW_CLOSING));
            }
            else {
                this.actionLabel.setText("That information is not valid.");
            }
        // Withdrawal text field
        } else if (WFieldString.equals(e.getActionCommand())) {      
            try {
                String WFieldInput = WField.getText();
                if (Integer.parseInt(WFieldInput) < 0){
                    this.actionLabel.setText("That destination account is invalid.");
                }
                else{
                    Request WRequest = new Request(ActiveAccount, Integer.parseInt(WFieldInput));
                    Withdrawal thisWithdrawal = new Withdrawal(WRequest);
                    if (thisWithdrawal.process()){
                        WFrame.dispatchEvent(new WindowEvent(WFrame, WindowEvent.WINDOW_CLOSING));
                        MainMenuFrame.dispatchEvent(new WindowEvent(MainMenuFrame, WindowEvent.WINDOW_CLOSING));
                    }
                }
            }
            catch(NumberFormatException excep){
                this.actionLabel.setText("That is not a valid amount.");
            }
        }  
        // Deposit text field
        else if (DFieldString.equals(e.getActionCommand())) {
            try {
                String DFieldInput = DField.getText();
                if (Integer.parseInt(DFieldInput) < 0){
                    this.actionLabel.setText("That destination account is invalid.");
                }
                else{
                    Request DRequest = new Request(ActiveAccount, Integer.parseInt(DFieldInput));
                    Deposit thisDeposit = new Deposit(DRequest);
                    if (thisDeposit.process()){
                        DFrame.dispatchEvent(new WindowEvent(DFrame, WindowEvent.WINDOW_CLOSING));
                        MainMenuFrame.dispatchEvent(new WindowEvent(MainMenuFrame, WindowEvent.WINDOW_CLOSING));
                    }   
                }
            }
            catch(NumberFormatException excep){
                this.actionLabel.setText("That is not a valid amount.");
            }
        }
        // Transfer Field #1
        else if (TFieldString.equals(e.getActionCommand())) {
            try {
                String TFieldInput = TField.getText();
                String T2Input = T2Field.getText();
                if (Integer.parseInt(T2Input) < 0){
                    this.actionLabel.setText("That destination account is invalid.");
                }
                else if (TFieldInput.equals(FriendAccountNum)){
                    String T2FieldInput = T2Field.getText();
                    Request TRequest = new Request(ActiveAccount, FriendAccount, Integer.parseInt(T2FieldInput));
                    Transfer thisTransfer = new Transfer(TRequest);
                    if (thisTransfer.process()){
                        TFrame.dispatchEvent(new WindowEvent(TFrame, WindowEvent.WINDOW_CLOSING));
                        MainMenuFrame.dispatchEvent(new WindowEvent(MainMenuFrame, WindowEvent.WINDOW_CLOSING));
                    }
                }
                else if (TFieldInput.equals(ActiveSecondaryAccountNum)){
                    String T2FieldInput = T2Field.getText();
                    Request TRequest = new Request(ActiveAccount, ActiveSecondaryAccount, Integer.parseInt(T2FieldInput));
                    Transfer thisTransfer = new Transfer(TRequest);
                    if (thisTransfer.process()){
                        TFrame.dispatchEvent(new WindowEvent(TFrame, WindowEvent.WINDOW_CLOSING));
                        MainMenuFrame.dispatchEvent(new WindowEvent(MainMenuFrame, WindowEvent.WINDOW_CLOSING));
                    }
                }
                else {
                    this.actionLabel.setText("That destination account is invalid.");
                }
            }
            catch(NumberFormatException excep){
                this.actionLabel.setText("That is not a valid amount.");
            }
        }
        // Transfer field #2
        else if (T2FieldString.equals(e.getActionCommand())) {
            try {
                String TFieldInput = TField.getText();
                String T2Input = T2Field.getText();
                if (Integer.parseInt(T2Input) < 0){
                    this.actionLabel.setText("That destination account is invalid.");
                }
                else if (TFieldInput.equals(FriendAccountNum)){
                    String T2FieldInput = T2Field.getText();
                    Request TRequest = new Request(ActiveAccount, FriendAccount, Integer.parseInt(T2FieldInput));
                    Transfer thisTransfer = new Transfer(TRequest);
                    if (thisTransfer.process()){
                        TFrame.dispatchEvent(new WindowEvent(TFrame, WindowEvent.WINDOW_CLOSING));
                        MainMenuFrame.dispatchEvent(new WindowEvent(MainMenuFrame, WindowEvent.WINDOW_CLOSING));
                    }
                }
                else if (TFieldInput.equals(ActiveSecondaryAccountNum)){
                    String T2FieldInput = T2Field.getText();
                    Request TRequest = new Request(ActiveAccount, ActiveSecondaryAccount, Integer.parseInt(T2FieldInput));
                    Transfer thisTransfer = new Transfer(TRequest);
                    if (thisTransfer.process()){
                        TFrame.dispatchEvent(new WindowEvent(TFrame, WindowEvent.WINDOW_CLOSING));
                        MainMenuFrame.dispatchEvent(new WindowEvent(MainMenuFrame, WindowEvent.WINDOW_CLOSING));
                    }
                }
                else {
                    this.actionLabel.setText("That destination account is invalid.");
                }
            }
            catch(NumberFormatException excep){
                this.actionLabel.setText("That is not a valid amount.");
            }
        }
    }

    
    /**
     * The main method for this program.
     *
     * @param args  A String Array used to specify user-inputted arguments; it is unused in this program.
     * @return    No return, just runs the program.
     */
    
    public static void main(String[] args){
        char[] CA1PIN = new char[] {'2', '4', '2','4'};
        CheckingsAccount CA1 = new CheckingsAccount(2000, "123", CA1PIN);
        SavingsAccount SA1 = new SavingsAccount(5000, "124", CA1PIN);
        char[] FA1PIN = new char[] {'5','3','1','5'};
        CheckingsAccount FA1 = new CheckingsAccount(1000, "876", FA1PIN);
        ATMSystem ATM1 = new ATMSystem(CA1, FA1, SA1);
        Receipt.ThisATM = ATM1;
        ATM1.DisplayInfo();
    }
}
