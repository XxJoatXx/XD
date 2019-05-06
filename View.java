import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.*;

// The View class creates and manages the GUI for the application.
// It doesn't know anything about the ATM itself, it just displays
// the current state of the Model, and handles user input
class View
{
    int H = 500;         // Height of window pixels 
    int W = 470;         // Width  of window pixels 

    // variables for components of the user interface
    Label      message;       // Message area 
    TextField  output1;       // Result area 
    TextArea   output2;       // Result area 
    ScrollPane scrollPane;    // scrollbars around the TextArea object  
    GridPane   grid;          // main layout grid
    TilePane   buttonPane;    // tiled area for buttons

    // The other parts of the model-view-controller setup
    public Model model;
    public Controller controller;

    // we don't really need a constructor method, but include one to print a 
    // debugging message if required
    public View()
    {
        Debug.trace("View::<constructor>");
    }

    // start is called from Main, to start the GI up
    // Note that it is important not to create controls etc here and
    // not in the constructor (or as initialisations to instance variables),
    // because we need things to be initialised in the right order
    public void start(Stage window)
    {
        Debug.trace("View::start");

        // create the user interface component objects
        // The ATM is a vertical grid of four components -
        // label, two text boxes, and a tiled panel
        // of buttons

        // layout objects
        grid = new GridPane();
        grid.setId("Layout");           // assign an id to be used in css file
        buttonPane = new TilePane();
        buttonPane.setId("Buttons");    // assign an id to be used in css file

        // controls
        message  = new Label();         // Message bar at the top
        grid.add(message, 0, 0);       // Add to GUI at the top       

        output1  = new TextField();     // text field for numbers 
        output1.setId("OutputPrimary");     
        grid.add(output1, 0, 1);       // Add to GUI on second row                      

        output2  = new TextArea();      // multi-line text area
        output2.setId("OutputSecondary");
        output2.setEditable(false);     // Read only (for the user)
        scrollPane  = new ScrollPane(); // create a scrolling window
        scrollPane.setFitToWidth(true);
        scrollPane.setId("ScrollLayout");
        scrollPane.setContent( output2 );  // put the text area 'inside' the scrolling window
        grid.add(scrollPane, 0, 2);    // add the scrolling window to GUI on third row

        // Buttons - these are layed out on a tiled pane, then
        // the whole pane is added to the main grid as the forth row

        // Button labels - empty strings are spacers
        // The number of button per row is set in the css file
        String labels[] = {
                "7",    "8",  "9",  "",  "Dep",  "C/P",
                "4",    "5",  "6",  "",  "W/D",  "",
                "1",    "2",  "3",  "",  "Bal",  "",
                "CLR",  "0",  "Ent",   "", "Fin",  "" };

        for ( int i=0; i<labels.length; i++ )
        {
            String label = labels[i];
            if ( label.length() >= 1 )
            {
                // non-empty string - make a button
                Button b = new Button( label );        
                b.setOnAction( this::buttonClicked ); // set the method to call when pressed
                buttonPane.getChildren().add( b );    // and add to tiled pane
            } else {
                // empty string - add an empty text element as a spacer
                buttonPane.getChildren().add( new Text() ); 

            }
        }
        grid.add(buttonPane,0,3); // add the tiled pane of buttons to the grid

        // add the complete GUI to the window and display it
        Scene scene = new Scene(grid, W, H);   
        scene.getStylesheets().add("atm.css"); // tell the app to use our css file
        window.setScene(scene);
        window.show();

        // set the opening message at the top
        message.setId("Header");
        message.setText( "Welcome to BIEDACTWO GURWA XDDD bank" );                     // Opening message
    }

    // This is how the View talks to the Controller
    // This method is called when a button is pressed
    // It fetches the label on the button and passes it to the controller's process method
    public void buttonClicked(ActionEvent event) {
        Button b = ((Button) event.getSource());
        if ( controller != null )
        {          
            String label = b.getText();   // get the button label
            Debug.trace( "View::buttonClicked: label = "+ label );
            // Try setting a breakpoint here
            controller.process( label );  // Pass it to the controller's process method
        }

    }

    // This is how the Model talks to the View
    // This method gets called BY THE MODEL, whenever the model changes
    // It has to do whatever is required to update the GUI to show the new model status
    public void update()
    {        
        if (model != null) {
            Debug.trace( "View::update" );
            // Try setting a breakpoint here
            String message1 = model.display1;     // get the new message1 from the model
            output1.setText( message1 );            // add it as text of GUI control output1
            String message2 = model.display2;     // get the new message2 from the model
            output2.setText( message2 );            // add it as text of GUI control output2
        }
    }
}
