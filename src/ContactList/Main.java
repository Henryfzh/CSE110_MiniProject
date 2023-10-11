package src.ContactList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.io.*;
import java.util.*;

class Contact extends HBox {

    private TextField contactName;
    private TextField contactEmail;
    private TextField contactPhone;
    private Button uploadButton;
    private Button delButton;
    private Label index;
    ImageView imageView;
    FileChooser fileChooser;

    Contact() {
        this.setPrefSize(500, 50); // sets size of task
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background
                                                                                                     // color of task

        uploadButton = new Button("Upload \n Image"); // creates a button for marking the task as done
        uploadButton.setPrefSize(100, 40);
        uploadButton.setAlignment(Pos.CENTER);
        uploadButton.setPrefHeight(Double.MAX_VALUE);
        uploadButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button
        this.getChildren().add(uploadButton);

        index = new Label();
        index.setText(""); // create index label
        index.setPrefSize(40, 20); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the task
        this.getChildren().add(index); // add index label to task

        contactName = new TextField(); // create task name text field
        contactName.setPrefSize(380, 50); // set size of text field
        contactName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        contactName.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(contactName); // add textlabel to task

        delButton = new Button("Delete");
        delButton.setPrefSize(100, 20);
        delButton.setPrefHeight(Double.MAX_VALUE);
        delButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        this.getChildren().add(delButton);

    }

    public TextField getContactName() {
        return this.contactName;
    }

    public TextField getContactEmail() {
        return this.contactEmail;
    }

    public TextField getContactPhone() {
        return this.contactPhone;
    }

    public Button getUploadButton() {
        return this.uploadButton;
    }

    public Button getDelButton() {
        return this.delButton;
    }

    public void uploadImage(Stage primaryStage) {
        imageView = new ImageView();
        fileChooser = new FileChooser();
        // Select which extensions are allowed
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            /*
             * Set the selected image in imageView i.e. display the image.
             * Hint: To implement this, you can use the setImage() method of ImageView and
             * pass the selected image as an argument.
             */
            imageView.setImage(image);
            // Resize the window to fit the image
            primaryStage.setWidth(image.getWidth() + 100);
            primaryStage.setHeight(image.getHeight() + 100);
        }
    }
}

class ContactList extends VBox {
    ContactList() {
        this.setSpacing(5); // sets spacing between tasks
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }

    public void exportContact() {
        String fileName = "contact.csv";
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            String line;
            for (int i = 0; i < this.getChildren().size(); i++) {
                if (this.getChildren().get(i) instanceof Contact) {
                    line = ((Contact) this.getChildren().get(i)).getContactName().getText();
                    bufferedWriter.write(line.toString());
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remove(String contactName) {
        this.getChildren().removeIf(
                contact -> contact instanceof Contact && ((Contact) contact).getContactName().equals(contactName));
    }
}

class Footer extends HBox {

    private Button addButton;
    private Button exportButton;
    private Button sortButton;

    Footer() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        addButton = new Button("New Contact"); // text displayed on add button

        exportButton = new Button("Export Contact"); // text displayed on add button
        exportButton.setStyle(defaultButtonStyle); // styling the button

        sortButton = new Button("Sort Contact"); // text displayed on add button
        sortButton.setStyle(defaultButtonStyle); // styling the button

        this.getChildren().add(addButton); // adding buttons to footer
        this.getChildren().add(exportButton);
        this.getChildren().add(sortButton);
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    public Button getAddButton() {
        return this.addButton;
    }

    public Button getExportButton() {
        return this.exportButton;
    }

    public Button getSortButton() {
        return this.sortButton;
    }
}

class Header extends HBox {

    Header() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("Contact List"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class AppFrame extends BorderPane {
    private Header header;
    public Footer footer;
    private ContactList contactList;
    private ScrollPane scroller;

    private Button addButton;
    private Button exportButton;
    private Button uploadButton;

    AppFrame() {
        header = new Header();
        footer = new Footer();
        contactList = new ContactList();
        scroller = new ScrollPane();
        scroller.setContent(contactList);
        scroller.setFitToWidth(true);

        this.setTop(header);
        this.setCenter(scroller);
        this.setBottom(footer);

        addButton = footer.getAddButton();
        exportButton = footer.getExportButton();
        addListeners();
    }

    public void addListeners() {
        addButton.setOnAction(e -> {
            Contact contact = new Contact();
            contactList.getChildren().add(contact);
        });

    }
}

public class Main extends Application {
    // To display images
    private ImageView imageView = new ImageView();

    // To open a file dialog for selecting images
    private FileChooser fileChooser = new FileChooser();
    Button uploadButton;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setting the Layout of the Window- Should contain a Header, Footer and the
        // ContactList
        AppFrame root = new AppFrame();

        // Set the title of the app
        primaryStage.setTitle("Contact List");
        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(root, 500, 600));
        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
