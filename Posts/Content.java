package Posts;
import UsersManagingSystem.User;
/**
 * Abstract parent class for all post objects
 * Base class for Post, Announcement, etc.
 */
public abstract class Content
{
   /**
    * Text content 
    */
   protected String content;
   /** 
    * User whose creating the content
    */
   protected User user;
   /**
    * Constructor for content.
    *
    * @param content: text content
    * @param user: Creator of content
    */
   public Content(String content,User user)
   {
       if(content==null)
       {
           throw new NullPointerException("Post content cannot be empty");
       }
       this.user=user;
       this.content=content;
   }
   
   /**
    * Returns full post content
    */
   public String getContent()
   {
       return content;
   }
   
   /**
    * Returns user object creating the post
    */
   public User getUser()
   {
       return this.user;
   }
   
   /**
    * Returns summary related to hashtag
    */
   public abstract String getSummary(String hashtag);
   
   /**
    * Returns formatted display 
    */
   public abstract String display();
}
