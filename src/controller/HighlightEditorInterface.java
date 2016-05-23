package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import model.FileManagerInterface;
import model.Line;
import model.LineInterface;

import java.util.ArrayList;

/**
 * Created by SH on 2016-05-21.
 */
public interface HighlightEditorInterface {

    public boolean isEditable();
    public void setEditable(boolean value);

    public void setText(FileManagerInterface.SideOfEditor side, String s);
    public void setHighlightLines(ArrayList<LineInterface> lines);

    public void setEditMode(boolean isEditMode);
    public boolean isEditMode();

    public String getText();
    public Parent getParent();
    public Scene getScene();

    public TextArea getTextArea();
    public ListView getHighlightListView();

    public void update(FileManagerInterface.SideOfEditor side);


}
