package UsersManagingSystem;

import java.util.*;
import java.time.*;
import java.time.format.*;
import Posts.Post;
import Posts.Announcement;
import HashtagManagingSystem.HashtagManager;

/**
 * Represents a registered user in Hashtag Tracking System.
 *
 * Each user has login credentials, profile, post history,
 * and hashtag information extracted from their posts.
 *
 * username and password validation is done externally by UserManager
 *
 * Fields:
 * username: Unique for every user. Max length is 15 charachters.
 * 				  	 	Only alphabets,digits, period ('.') and underscore ('_') permitted.
 * 				 		 Cannot be changed post creation.
 * 
 * email: must follow the format:  username@domain and must be unique.
 * 
 * password:  Min 8 characters long. Must have atleast
 * 						1 upper case alphabet
 * 						1 lower case alphabet
 * 						1 digit
 *                                             1 special character
 * 
 * loginStatus: true if user is logged in, false otherwise.
 * 
 * date:  Date used while creating post.
 * 
 * joinDate: Date of joining platform (dd/mm/yyyy).
 * 
 * nameOfUser: Display name of user.
 * 
 * userPosts: List of posts created by the user.
 * 
 * userAnnouncements: List of Announcements made by the user
 * 
 * hashtagCount: Total hashtags used across all posts.
 */
public class User
{
    private final String username;
    private String email;
    private String password;
    private boolean loginStatus;
    private LocalDate date;
    private String joinDate;
    private String nameOfUser;
    private ArrayList<Post>userPosts;
    private ArrayList<Announcement> userAnnouncements;
    private HashMap<String,Integer>hashtagFreq;
    private int hashtagCount=0;
    /**
     * Creates a new user account.
     *
     * @param username: unique username
     * @param email: user email (also unique)
     * @param password
     * @param nameOfUser: display name of user
     */
    public User(String username, String email, String password, String nameOfUser)
    { 
        this.username=username;
        this.email=email;
        this.password=password;
        this.loginStatus=false;
        this.date=LocalDate.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.joinDate=date.format(formatter);
        this.nameOfUser=nameOfUser;
        userPosts=new ArrayList<Post>();
        userAnnouncements=new ArrayList<Announcement>();
        hashtagFreq=new HashMap<String,Integer>();
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getEmail()
    {
        return this.email;
    }

    public boolean getStatus()
    {
        return this.loginStatus;
    }

    /**
     * Changes password if old password matches.
     * User is logged out if the password change is succesfull
     *
     * @param oldPassword- existing password
     * @param newPassword - new password
     * @return true if changed successfully
     */  
    public boolean changePassword(String oldPassword,String newPassword)
    {
        if(this.password.equals(oldPassword))
        {
            this.password=newPassword;
            System.out.println("User password change successful");
            System.out.println(this.getUsername()+" is now logged out");
            this.logout();
            return true;
        }
        else
        {
            System.out.println("User password change unsuccessful");
            return false;
        }
    }

    /**
     * User is logged into its account if the password matches
     *
     * @param password entered password
     * @return true if login successful
     */
    public boolean login(String password)
    {
        if(this.password.equals(password))
            {
                this.loginStatus=true;
                System.out.println("User login successful");
                return true;
            }
        else
            {
                this.loginStatus=false;
                System.out.println("User login unsuccessful");
                return false;
            }
    }

    /** 
    * logs out the user
    */
    public void logout()
    {
        this.loginStatus=false;
    }
    
    /** 
    * Shows the preview (upto 100 characters) of all posts created by user 
    */
    public void viewPosts()
    {
        if(userPosts.size()==0)
        {
            System.out.println(this.getUsername()+" made no posts");
        }
        for(Post p:userPosts)
        {
            System.out.println(p.getSummary());
        }
    }
    
    /**
     * Shows all the announcements made by the user
     */
    public void viewAnnouncements()
    {
        if(userAnnouncements.size()==0)
        {
            System.out.println(this.getUsername()+" made no announcements");
            return;
        }
        for(Announcement a: userAnnouncements)
        {
            System.out.println(a.display());
        }
    }
    
    /**
     * returns list of user's posts 
    */
    public ArrayList<Post> getPosts()
    {
        return this.userPosts;
    }
    
    /**
     * return list of user's announcements
     */
    public ArrayList<Announcement> getAnnouncements()
    {
        return this.userAnnouncements;
    }
    
    /**
     * @return total number of posts shared by the user
     */
    public int getPostCount()
    {
        return this.getPosts().size();
    }
    
    /**
     * maintains total hashtag count of the user
     */
    public void countHashtagFrequency()
    {
        hashtagCount=0;
        for(Post p:userPosts)
        {
            String[] hashtags=p.getHashtags();
            for(String tag:hashtags)
            {
                hashtagCount++;
            }
        }
    }
    
    /**
     * @return total hashtag count of the user
     */
    public int getHashtagCount()
    {
        return this.hashtagCount;
    }

    /** 
     * finds the post and @return true if editing of post is succesfull
     * and false otherwise
     */
    public boolean editPost(int postId,String content,HashtagManager hashtagManager)
    {
        if(content==null)
        {
            throw new NullPointerException("Post content cannot be empty");
        }
        for(Post p:userPosts)
        {
            if(p.getPostId()==postId)
            {
                p.updateHashtagCount(hashtagManager,-1);
                p.setContent(content);
                p.extractHashtags();
                p.updateHashtagCount(hashtagManager,1);
                return true;
            }
        }
        return false;
    }
    
     /** 
      * finds the post and @return true if deletion of post is succesfull
      * and false otherwise
      */
    public boolean deletePost(int postId,HashtagManager hashtagManager)
    {
        for(int i=0;i<userPosts.size();i++)
        {
         Post p=userPosts.get(i);
         if(p.getPostId()==postId)
         {
             userPosts.remove(i);
             p.updateHashtagCount(hashtagManager,-1);
             return true;
         }
        }
        return false;
    }
    /** 
     * @return the user account details
     */
    public String toString()
    { 
        String status = this.loginStatus ? "Online" : "Offline";
        return "Username: "+this.username+" Name of User: "+this.nameOfUser+" | Posts Shared: "+this.userPosts.size()+" | Status: "+status+" Joining Date: "+this.joinDate;
    }    
}
