/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tanweilok
 */
public class Issue implements Serializable {

    private int id;
    private String title;
    private int priority;
    private String status;
    private String[] tag;
    private String descriptionText;
    private String createdBy, assignee;
    private Timestamp timestamp;
    private ArrayList<Comment> comments;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Issue(int id, String title, int priority, String status, String[] tag, String descriptionText, String createdBy, String assignee, Timestamp timestamp) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.tag = tag;
        this.descriptionText = descriptionText;
        this.createdBy = createdBy;
        this.assignee = assignee;
        this.timestamp = timestamp;
        this.comments = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public String toString() {
        String res = "";
        res += String.format("%s %d \t\t\t %s %s\n", "Issue ID:", id, "Status:", status);
        res += String.format("%s ","Tag:");
        for(int i=0; i<tag.length; i++) {
            if(i>0)res+=", ";
            res += tag[i];
        }
        res += String.format("\t\t\t %s %d \t\t\t %s %s\n","Priority:", priority, "Created On:", sdf.format(timestamp));
        res += String.format("%s\n", title);
        res += String.format("%s %s \t\t\t\t\t\t\t\t %s %s\n\n", "Assigned to:", assignee, "Created By:", createdBy);
        res += String.format("%s\n%s\n", "Issue Description",
                                         "-----------------");
        res += String.format("%s\n\n",descriptionText);
        res += String.format("%s\n%s\n", "Comments",
                                         "---------");
        for(int i=0; i<comments.size(); i++) {
            res += String.format("%s%d \t\t\t %s\n", "#", i+1, comments.get(i));
        }
        return res;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tags) {
        this.tag = tags;
    }
    
    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
