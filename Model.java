
// The model represents all the actual content and functionality of the app
// For the ATM, it keeps track of the information showing in the display
// (the two message boxes), and the interaction with the bank, and executes
// commands provided by the controller (and tells the view to update when
// something changes)
public class Model 
{
    // the ATM model is always in one of three states - waiting for an account number, 
    // waiting for a password, or logged in and processing account requests. 
    // We use string values to represent each state:
    // (the word 'final' tells Java we won't ever change the values of these variables)
    final String ACCOUNT_NO = "account_no";
    final String PASSWORD = "password";
    final String LOGGED_IN = "logged_in";

    // variables representing the ATM model
    String state = ACCOUNT_NO;      // the state it is currently in
    int  number = 0;                // current number displayed in GUI (as a number, not a string)
    String display1 = null;         // The contents of the Message 1 box (a single line)
    String display2 = null;         // The contents of the Message 2 box (may be multiple lines)

    // The ATM talks to a bank, represented by the LocalBank object.
    LocalBank  bank = new LocalBank();

    // The other parts of the model-view-controller setup
    public View view;
    public Controller controller;

    // when we create a model, we set the messages to something useful
    public Model()
    {
        Debug.trace("Model::<constructor>");        
        initialise("Welcome to the ATM");   
    }
    
    // set state to ACCOUNT_NO, number to zero, and display message provided as argument
    // and standard instruction message
    public void initialise(String message) {
        setState(ACCOUNT_NO);
        number = 0;
        display1 = message; 
        display2 =  "Enter your account number\n" +
        "Followed by \"Ent\"";
    }

    // This is how the Controller talks to the Model - it calls this method
    // to say that a button in the GUI has been pressed
    // This is a bit intricate, but not actually complicated - there are a number
    // of ways you might improve it.
    public void process( String button )
    {
        Debug.trace("Model::process: state = " + state + " button = " + button );
        
        Boolean handled;
        
        // we use submethods to check for different kinds of
        // buttons - and re-initialise if something goes wrong
        handled = tryNumbers(button);
        if (!handled) {
            handled = tryCommands(button);
            if (! handled) {
                // unknown button, or invalid for this state - reset everything
                Debug.trace("Model::process: unhandled button, re-initialising");
                // go back to initial state
                initialise("Invalid command");
            }
        }
        display();  // update the GUI
    }

    // This method handles the digits, CLR and ENT (all the ones associated with the number field)
    public Boolean tryNumbers( String button ) 
    {
        // button is the label on the button which has been pressed      
        switch ( button )
        {
            case "1" : case "2" : case "3" : case "4" : case "5" :
            case "6" : case "7" : case "8" : case "9" : case "0" :
                // one of the digits - just add it to the number showing in the top message window
                char c = button.charAt(0);
                number = number * 10 + c-'0';           // Build number 
                display1 = "" + number;
                return true;
            case "CLR" :
                // clear the number stored in the model
                number = 0;
                display1 = "";
                return true;
            case "Ent" :
                // Enter was pressed - what we do depends what state the ATM is in
                switch ( state )
                {
                    case ACCOUNT_NO:
                        // we were waiting for a complete account number - send the number we have
                        // to the bank object, and change to the state where we are expecting a password
                        bank.setAccNumber( number );
                        number = 0;
                        setState(PASSWORD);
                        display1 = "";
                        display2 = "Now enter your password\n" +
                        "Followed by \"Ent\"";
                        break;
                    case PASSWORD:
                        // we were waiting for a password - send the number we have to the bank as a 
                        // password,
                        bank.setAccPasswd( number );
                        number = 0;
                        display1 = "";
                        // now check the account/password combination. If it's ok go into the LOGGED_IN
                        // state, otherwise go back to ask for another account number
                        if ( bank.checkValid() )
                        {
                            setState(LOGGED_IN);
                            display2 = "Accepted\n" +
                            "Now enter the transaction you require";
                        } else {
                            bank.loggedOut();
                            initialise("Unknown account/password");
                        }
                        break;
                    default : // do nothing in any other state (ie logged in)
                }    
                return true;
            }
            return false;       // it wasn't a number button
        }

    // This method handles the other buttons - only valid once logged in
    public Boolean tryCommands(String button)
    {        
        if (state.equals(LOGGED_IN) ) 
        {
            // Ok, we are logged in, and have been asked to do something ...
            switch ( button )
            {
                case "W/D" :               // Withdraw action
                    display1 = "";
                    if (bank.withdraw(number) )
                    {
                        display2 =   "Withdrawn: " + number;
                    } else {
                        display2 =   "You do not have sufficient funds";
                    }
                    number = 0;
                    break;
                case  "Bal" :               // Balance action
                    number = 0;
                    display2 = "Your balance is: " + bank.getBalance();
                    break;
                case "Dep" :               // Deposit action
                    bank.deposit( number );
                    display1 = "";
                    display2 = "Deposited: " + number;
                    number = 0;
                    break;
                case "Fin" :               // Finish transactions
                    // go back to the log in state
                    setState(ACCOUNT_NO);
                    number = 0;
                    display2 = "Welcome: Enter your account number";
                    bank.loggedOut();
                    break;
                default:
                    Debug.trace("Model::tryCommands: unknown button - " + button);
                    return false;
            }
            return true;
        } else {
            return false;
        }    
    }
    
    // use this method to set the state - mainly so we print a debugging message whenever the state changes
    public void setState(String newState) 
    {
        if ( !state.equals(newState) ) 
        {
            state = newState;
            Debug.trace("Model::setState: new state = " + state);
        }
    }
        
    // This is where the Model talks to the View, by calling the View's update method
    // The view will call back to the model to get new information to display on the screen
    public void display()
    {
        Debug.trace("Model::display");
        // Try setting a breakpoint here
        view.update();
    }
}
