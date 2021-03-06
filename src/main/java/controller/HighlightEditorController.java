package controller;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import model.FileManager;
import model.FileManagerInterface;
import model.LineInterface;

import java.io.IOException;

/**
 * Created by SH on 2016-05-19.
 */
public class HighlightEditorController extends AnchorPane implements HighlightEditorInterface {

    @FXML private TextArea editor;
    @FXML private ListView<LineInterface> highlightList;

    private boolean isEditMode = false;
    private final BooleanProperty isFocusedProperty = new SimpleBooleanProperty(false);

    /* 생성자 */
    public HighlightEditorController() {
        super();

        try {
            FXMLLoader loader = new FXMLLoader(HighlightEditorController.class.getResource("/fxml/highlightEditor.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();

            Platform.runLater(this::_initEditor);

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setEditable(boolean value){
        editor.setEditable(value);
    }

    @Override
    public boolean isEditable(){
        return editor.isEditable();
    }



    @Override
    public void setText(FileManagerInterface.SideOfEditor side, String s){
        this.editor.setText(s);

        if( side == FileManagerInterface.SideOfEditor.Left ) {
            update(FileManagerInterface.SideOfEditor.Left);
        } else {
            update(FileManagerInterface.SideOfEditor.Right);
        }

    }


    @Override
    public void setEditMode(boolean isEditMode) {
        if( isEditMode ){
            this.highlightList.setVisible(false);
            this.editor.setVisible(true);
            this.isEditMode = true;
        } else {
            this.highlightList.setVisible(true);
            this.editor.setVisible(false);
            this.isEditMode = false;
        }
    }

    @Override
    public boolean isEditMode() {
        return this.isEditMode;
    }

    @Override
    public String getText(){
        return editor.getText();
    }

    @Override
    public TextArea getTextArea() {
        return this.editor;
    }

    @Override
    public ListView<LineInterface> getHighlightListView() {
        return this.highlightList;
    }

    @Override
    public ReadOnlyBooleanProperty isFocusedProperty() {
        return this.isFocusedProperty;
    }

    @Override
    public void update(FileManagerInterface.SideOfEditor side) {
        FileManager.getFileManagerInterface().updateLineArrayList(editor.getText(), side);
    }

    @Override
    public void reset() {
        this.editor.setText("");
    }


    private void _initEditor(){

        if( highlightList != null ) {

            highlightList.setCellFactory(list -> new ListCell<LineInterface>() {

                @Override
                public void updateSelected(boolean selected) {
                    super.updateSelected(selected);
                    if(selected){

                        LineInterface item = getItem();

                        switch(item.getHighlight()){
                            case selected:
                            case isDifferent:
                            case whitespace:
                                FileManager.getFileManagerInterface().clickLine(getIndex()); break;
                        }

                    }
                }

                @Override
                protected void updateItem(LineInterface item, boolean empty) {
                    super.updateItem(item, empty);

                    setStyle(null); setText(null); getStyleClass().removeAll("isDifferent");

                    if( item != null && !empty ) {
                        setText(item.getContent(false));

                        switch(item.getHighlight()){
                            case unHighlighted: setStyle("-fx-background-color:transparent;"); break;
                            case isDifferent: getStyleClass().add("isDifferent"); setStyle("-fx-text-fill:#000000; -fx-background-color: #EDE98D;"); break;
                            case whitespace: setStyle("-fx-background-color: #EEEEEE"); break;
                            case selected: setStyle("-fx-background-color: #44d7ba"); break;
                        }
                    }

                }

            });

        }

    }

    @FXML
    private void onMouseEntered(){
        this.isFocusedProperty.set(true);
    }

    @FXML
    private void onMouseExited(){
        this.isFocusedProperty.set(false);
    }

}
