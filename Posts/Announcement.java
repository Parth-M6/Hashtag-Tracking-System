package Posts;


import UsersManagingSystem.User;
/**
 * Represents an announcement post
 * Used for important messages with priority level
 */
public class Announcement extends Content
{
    /** 
     * Priority of announcement (high,medium or low etc.) 
     */
    private String priorityLevel;
    public Announcement(String content, User user, String priorityLevel)
    {
        super(content,user);
        this.priorityLevel=priorityLevel;
    }
    
    /**
     * Returns short summary of announcement
     */
    @Override
    public String getSummary(String hashtag)
    {
        return "ANNOUNCEMENT ["+priorityLevel+"]: \n"+this.content;
    }
    
    /**
     * Returns formatted display text
     */
    @Override
    public String display()
    {
        return "--- ANNOUNCEMENT ---\nPriority: " + priorityLevel + "\nBy: " + user.getUsername() + "\nMessage: " + this.content + "\n--------------------";
    }
}
