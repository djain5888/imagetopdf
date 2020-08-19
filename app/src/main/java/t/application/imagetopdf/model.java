package t.application.imagetopdf;

import java.util.ArrayList;

public class model {
    ArrayList<String> total_pages;
    String text;
    Boolean added;
    String value;
    String position;

    public ArrayList<String> getTotal_pages() {
        return total_pages;
    }

    public Boolean getAdded() {
        return added;
    }

    public String getText() {
        return text;
    }

    public void setAdded(Boolean added) {
        this.added = added;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTotal_pages(ArrayList<String> total_pages) {
        this.total_pages = total_pages;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
