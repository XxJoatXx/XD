import javafx.application.Application;
import javafx.stage.Stage;

// atmJavaFX2 project Main class
// The code here creates the ATM GUI interface and model functionality, but the methods
// in the LocalBank class which actually do the banking functions are incomplete.
// The lab class exercise (with tutor support) is to complete the LocalModel.
// The assessment project is to add further functionality as discussed in lectures and 
// seminars. Tutors may not help directly with this but will talk you through examples and
// answer questions.
public class Main extends Application
{
    public static void main( String args[] )
    {
        // The main method only gets used when launching from the command line
        // launch initialises the system and then calls start
        // In BlueJ, BlueJ calls start itself
        launch(args);
    }

    public void start(Stage window) 
    {
        // set up debugging and print initial debugging message
        Debug.set(true);             
        Debug.trace("atmJavaFX2 starting"); 
        Debug.trace("Main::start"); 

        // Create the Model, View and Controller objects
        Model model = new Model();
        View  view  = new View();
        Controller controller  = new Controller();

        // Link them together so they can talk to each other
        // Each one has instances variable for the other two
        model.view = view;
        model.controller = controller;
        controller.model = model;
        controller.view = view;
        view.model = model;
        view.controller = controller;

        // start up the GUI (view), and then tell the model to initialise itself
        view.start(window);
        model.display();   

        // application is now running
        Debug.trace("atmJavaFX2 running"); 
    }
}
