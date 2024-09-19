package hi.hugboverkefni1.persistence.entities;


public class Note {
    private long ID;
    private String Title;
    private String Content;
    //meira


    public Note() {

    }

    public Note(String content, String title) {
        Content = content;
        Title = title;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
