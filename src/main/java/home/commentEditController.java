package home;

import bugs.Comment;
import bugs.Issue;
import bugs.MySQLOperation;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static home.Controller.*;

public class commentEditController implements Initializable {
    ArrayList<Comment> possible_comments = new ArrayList<>();
    ArrayList<String> comment_list = new ArrayList<>();

    int selection_id;

    @FXML
    private TextField usernameDisplay;

    @FXML
    private ChoiceBox<String> commentSelection;

    @FXML
    private TextArea commentField;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton cancelBtn;

    private void clean() {
        commentField.clear();
    }

    @FXML
    void setCancelBtn(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void setSaveBtn(ActionEvent event) {
        String comment = commentField.getText();
        if (comment.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        } else {
            if (comment.equals(possible_comments.get(selection_id).getText())) {//no change

            } else {
                MySQLOperation.updateComment(MySQLOperation.connectionToDatabase(), getCurrentUser(), getSelectedProjectId(), getSelectedIssueId(), getSelectedCommentId(), comment);

            }
            clean();
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCommentSelection();
        if (possible_comments == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Wrong secret code");
            alert.showAndWait();
            Stage stage = (Stage) usernameDisplay.getScene().getWindow();
            // do what you have to do
            stage.close();
        } else {
            usernameDisplay.setText(Controller.getUsername());
            commentSelection.getItems().addAll(comment_list);
            commentSelection.setValue(comment_list.get(0));
            commentField.setText(possible_comments.get(0).getText());
            commentSelection.setOnAction(this::getCommentSelection);
        }

    }

    private void getCommentSelection(ActionEvent actionEvent) {
        selection_id = commentSelection.getSelectionModel().getSelectedIndex();
        setSelectedCommentId(selection_id);
        commentField.setText(possible_comments.get(selection_id).getText());
    }

    Issue issue_temp = null;

    public void setCommentSelection() {
        ArrayList<Issue> issueList = getFinalProjectList().get(getSelectedProjectId() - 1).getIssues();
        //Issue issue_temp = getFinalProjectList().get(getSelectedProjectId()-1).getIssues().get(getSelectedIssueId()-1);

        for (int i = 0; i < issueList.size(); i++) {
            if (issueList.get(i).getId() == getSelectedIssueId()) {
                issue_temp = issueList.get(i);
            }
        }
        for (int i = 0; i < issue_temp.getComments().size(); i++) {
            if (issue_temp.getComments().get(i).getUser().equals(Controller.getUsername())) {
                possible_comments.add(issue_temp.getComments().get(i));
                String temp_string = issue_temp.getComments().get(i).getText();
                if (temp_string.length() > 20)
                    comment_list.add(issue_temp.getComments().get(i).getComment_id() + " - " + issue_temp.getComments().get(i).getText().substring(0, 20));
                else
                    comment_list.add(issue_temp.getComments().get(i).getComment_id() + " - " + issue_temp.getComments().get(i).getText());
            }
        }
    }
}

