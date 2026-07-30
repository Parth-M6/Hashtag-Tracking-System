import java.util.*;
import UsersManagingSystem.*;
import Posts.Post;
import Posts.Announcement;
import HashtagManagingSystem.*;
import Analytics.*;
/**
 * Main menu for the system.
 *
 * Responsibilities:
 * 1. Accept user commands
 * 2. Call required modules
 * 3. Handle login/logout
 * .4 Manage posts
 * 5. Show analytics reports
 */
public class MainMenu implements Menu
{
    /** Scanner for user input */
    Scanner sc=new Scanner(System.in);
    /** Handles all user operations */
    private UserManager userManager;
    /** Handles hashtag statistics */
    private HashtagStats hashtagStats;
    /** Used to generate reports */
    private Reports report;
    /** Unique post id */
    private int postId;
    /** Currently logged in user */
    private User loggedinUser;
    
    /**
     * Constructor initializing all managers
     */
    MainMenu()
    {
        this.userManager=new UserManager();
        this.hashtagStats=new HashtagStats(this.userManager);
        this.report=new Reports(userManager,hashtagStats,hashtagStats);
        this.postId=1;
    }

    /**
     * Displays all commands
     */
    public void Options()
    {
        System.out.println("Type \"Register\" to register a new user");
        System.out.println("Type \"Login\" to log in to an exisiting user");
        System.out.println("Type \"Logout\" to log out of an exisiting user");
        System.out.println("Type \"Change\" to change password of logged in user");
        System.out.println("Type \"ViewProfile\" to view the profile of any user");
        System.out.println("Type \"CreatePost\" to create a new post for the logged in user");
        System.out.println("Type \"EditPost\" to edit a post of the logged in user");
        System.out.println("Type \"DeletePost\" to delete a post of the logged in user");
        System.out.println("Type \"ViewPost\" to view the post of any user");
        System.out.println("Type \"ViewSummary\" to view the post summary of any user");
        System.out.println("Type \"UserPosts\" to view all the posts of a specific user");
        
        System.out.println("Type \"MakeAnnouncement\" to make an announcement");
        System.out.println("Type \"ViewAnnouncements\" to view all the announcements of the logged in user");
        
        System.out.println("Type \"ViewTrending\" to view the top N trending hashtags");
        System.out.println("Type \"ViewFrequency\" to view the frequency of each hastag used");
        System.out.println("Type \"Active\" to view the top K active users");
        System.out.println("Type \"Hashtag\" to view all posts containing a specific hashtag");
        System.out.println("Type \"Report\" to view the summary report of the system");
        System.out.println("Type \"Menu\" to view this operations menu once again");
    }

    /**
     * Detects command and calls required method
     */
    public void  findMethod(String s)
    {
        if(s.equalsIgnoreCase("Register"))
        {
            RegisterUser();
        }
        else if(s.equalsIgnoreCase("Login"))
        {
            LoginUser();
        }
        else if(s.equalsIgnoreCase("Logout"))
        {
            LogoutUser();
        }
        else if(s.equalsIgnoreCase("Change"))
        {
            ChangePassword();
        }
        else if(s.equalsIgnoreCase("ViewProfile"))
        {
            ViewProfile();
        }
        else if(s.equalsIgnoreCase("CreatePost"))
        {
            CreatePost(); 
        }
        else if(s.equalsIgnoreCase("EditPost"))
        {
            EditPost();
        }
        else if(s.equalsIgnoreCase("DeletePost"))
        {
            DeletePost();
        }
        else if(s.equalsIgnoreCase("ViewPost"))
        {
            ViewPost();
        }
        else if(s.equalsIgnoreCase("ViewSummary"))
        {
            ViewPostSummary();
        }
        else if(s.equalsIgnoreCase("UserPosts"))
        {
            UserPosts();
        }
        else if(s.equalsIgnoreCase("MakeAnnouncement"))
        {
            MakeAnnouncement();
        }
        else if(s.equalsIgnoreCase("ViewAnnouncement"))
        {
            ViewAnnouncements();
        }
        else if(s.equalsIgnoreCase("ViewTrending"))
        {
            ViewTrending();
        }
        else if(s.equalsIgnoreCase("ViewFrequency"))
        {
            ViewHashtagFrequency();
        }
        else if(s.equalsIgnoreCase("Hashtag"))
        {
            PostsWithHashtag();
        }
        else if(s.equalsIgnoreCase("Report"))
        {
            SummaryReport();
        }
        else if(s.equalsIgnoreCase("Menu"))
        {
            Options();
        }
        else if(s.equalsIgnoreCase("Active"))
        {
            KMostActiveUsers();
        }
        else if(s.equalsIgnoreCase("Exit"))
        {
            System.out.println("System exited successfully"); 
            System.exit(0);
        }
        else
        {
            throw new IllegalArgumentException("Invalid Command");
        }
    }

    public static void main(String[] args)
    {
        MainMenu menu=new MainMenu();
        
        menu.Options();
        while(true)
        {
           try
           {
            System.out.println("\nEnter prompt for next operation");
            String s=menu.sc.nextLine();
            menu.findMethod(s);
           }
           catch(IllegalArgumentException e)
           {
                System.out.println(e.getMessage());
           }
        }
        
    }

    /** Registers new user */
    public void RegisterUser()
    {
        System.out.println("Enter the name of the user");
        String name=sc.nextLine();
        System.out.println("Enter a userid for the user");
        String username=sc.nextLine();
        System.out.println("Enter the emailid to be associated with this userid");
        String email=sc.nextLine();
        System.out.println("Enter a secure password for this userid");
        String password=sc.nextLine();
        if(userManager.registerUser(username,email,password,name))
        {
            System.out.println("User registration successful");
        }
        else
        {
            System.out.println("User registration unsuccessful");
        }
        
    }

    /** Logs in user */
    public void LoginUser()
    {
        if(loggedinUser!=null)
        {
            System.out.println("Another user is currently logged in. Please log them out first.");
        }
        else
        {
            System.out.println("Enter the userid");
            String username=sc.nextLine();
            try
            {
                if(username==null)
                {
                    System.out.println("userid cannot be empty");
                }
                else if(userManager.getUserMap().containsKey(username))
                {
                    System.out.println("Enter the password associated with this userid");
                    String password=sc.nextLine();
                    loggedinUser=userManager.loginUser(username,password);
                }
                else
                {
                    System.out.println("username does not exist");
                }
            }catch(NullPointerException e)
            {
                System.out.println(e.getMessage());
            }
        }
        
    }

    /** Logs out current user */
    public void LogoutUser()
    {
        if(loggedinUser==null)
        {
            System.out.println("No user is currently logged in");
        }
        else
        {
            if(userManager.logoutUser(loggedinUser.getUsername()))
            {
                System.out.println("User logout successful");
                loggedinUser=null;
            }
            else
            {
                System.out.println("User logout unsuccessful");
            }
        }
    }

    /** Changes password */
    public void ChangePassword()
    {
        if(loggedinUser==null)
        {
            System.out.println("No user is currently logged in");
            return;
        }
        System.out.println("Enter the userid");
        String username=sc.nextLine();
        if(!username.equals(loggedinUser.getUsername()))
        {
            System.out.println("The requested profile doesn't match your current login.");
        }
        else
        {
            System.out.println("Enter the old password associated with this userid");
            String oldPassword=sc.nextLine();
            System.out.println("Enter the new password associated with this userid");
            String newPassword=sc.nextLine();
            try
            {
                userManager.changePassword(username,oldPassword,newPassword);
            }catch(NullPointerException e)
            {
                System.out.println(e.getMessage());
            }
            loggedinUser=null;
        }
    }

    /** View profile of user */
    public void ViewProfile()
    {
        System.out.println("Enter the userid");
        String username=sc.nextLine();
        try
        {
            userManager.viewUserProfile(username);
        }catch(NullPointerException e)
        { System.out.println(e.getMessage());
        }
    }

    /** Creates post */
    public void CreatePost()
    {
        if(loggedinUser==null)
        {
            System.out.println("No user is currently logged in");
        }
        else
        {
            System.out.println("Enter the post content");
            String content=sc.nextLine();
            try
            {
                Post p=new Post(postId,content,loggedinUser);
                p.extractHashtags();
                p.updateHashtagCount(hashtagStats,1);
                loggedinUser.getPosts().add(p);
                System.out.println("Post created successfully with postId: "+postId++);
            }
            catch(NullPointerException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    /** Edits post */
    public void EditPost()
    {
        if(loggedinUser==null)
        {
            System.out.println("No user is currently logged in");
        }
        else
        {
            System.out.println("Enter the postId");
            try
            {
                int x=Integer.parseInt(sc.nextLine());
                System.out.println("Enter the post content");
                String content=sc.nextLine();
                try
                {
                    if(loggedinUser.editPost(x,content,hashtagStats))
                    {
                        System.out.println("Post edited successfully");
                    }
                    else
                    {
                        System.out.println("Post not found");
                    }
                }catch(NullPointerException e)
                {
                    System.out.println(e.getMessage());
                }
            }catch(NumberFormatException e)
            {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    /** Deletes post */
    public void DeletePost()
    {
        if(loggedinUser==null)
        {
            System.out.println("No user is currently logged in");
        }
        else
        {
            try
            {
                System.out.println("Enter the postId");
                int x=Integer.parseInt(sc.nextLine());
                if(loggedinUser.deletePost(x,hashtagStats))
                {
                    System.out.println("Post deletion successful");
                }
                else
                {
                    System.out.println("Post not found");
                }
            }catch(NumberFormatException e)
            {
                System.out.println("Invalid input. Please enter a valid  number.");
            }
        }
    }

    /** View full post */
    public void ViewPost()
    {
        System.out.println("Enter the postId");
        try{
            int postId=Integer.parseInt(sc.nextLine());
            User users[]=userManager.getUsers();
            boolean found=false;
            for(User user:users)
            {
                for(Post p:user.getPosts())
                {
                    if(p.getPostId()==postId)
                    {
                        System.out.println(p.display(user));
                        found=true;
                        break;
                    }
                }
            }
            if(!found)
            {
                System.out.println("Post not found");
            }
        }catch(NumberFormatException e)
        {
            System.out.println("Invalid input. Please enter a valid  number.");
        }
    }

    /** View post summary */
    public void ViewPostSummary()
    {
        System.out.println("Enter the postId");
        try
        {
            int postId=Integer.parseInt(sc.nextLine());
            User users[]=userManager.getUsers();
            boolean found=false;
            for(User user:users)
            {
                for(Post p:user.getPosts())
                {
                    if(p.getPostId()==postId)
                    {
                        System.out.println(p.getSummary(user));
                        found=true;
                        break;
                    }
                }
            }
            if(!found)
            {
                System.out.println("Post not found");
            }
        }catch(NumberFormatException e)
        {
            System.out.println("Invalid input. Please enter a valid  number.");
        }
    }

    /** Shows all posts of user */
    public void UserPosts()
    {
        System.out.println("Enter the userid");
        String userId=sc.nextLine();
        System.out.println("The posts of "+userId+" are:");
        if(userManager.getUserMap().containsKey(userId.toLowerCase()))
        {
            userManager.getUserMap().get(userId.toLowerCase()).viewPosts();
        }
        else
        {
            System.out.println("User not found");
        }
    }
    
    /** Create announcement */
    public void MakeAnnouncement()
    {
        if(loggedinUser==null)
        {
            System.out.println("No user is currently logged in");
            return;
        }
        System.out.println("Enter the announcement content:");
        String content=sc.nextLine();
        System.out.println("Enter priority level (e.g., High, Medium, Low):");
        String priority=sc.nextLine();
        
        try{
            Announcement a=new Announcement(content,loggedinUser,priority);
            loggedinUser.getAnnouncements().add(a);
            System.out.println("Announcement created successfully");
        }catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /** View announcements */
    public void ViewAnnouncements()
    {
        if(loggedinUser==null)
        {
            System.out.println("No user is currently logged in");
            return;
        }
        System.out.println("Your Announcements:");
        loggedinUser.viewAnnouncements();
    }

    /** Show trending hashtags */
    public void ViewTrending()
    {
        try
        {
            System.out.println("Enter the number of top Hashtags you want to view");
            int n=Integer.parseInt(sc.nextLine());
            String trending[]=hashtagStats.getTopNTrending(n);
            if(trending.length>0)
            {
            System.out.println("The top "+trending.length+" trending Hashtags are");
            for(int i=0;i<trending.length;i++)
            {
                System.out.println((i+1)+" "+trending[i]+" ("+(hashtagStats.getHashtagFrequency().get(trending[i]))+"mentions)");
            }
        }
        }catch(NumberFormatException e)
        {
            System.out.println("Invalid input. Please enter a valid  number.");
        }catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /** Show top K active users */
    public void KMostActiveUsers()
    {
        try
        {
            System.out.println("Enter the value of K to find the K most active users in the  system");
            int k=Integer.parseInt(sc.nextLine());
            report.kMostActiveUser(k);
        }catch(NumberFormatException e)
        {
            System.out.println("Invalid input. Please enter a valid  number.");
        }
    }

    /** Show hashtag frequencies */
    public void ViewHashtagFrequency()
    {
        hashtagStats.showHashtagFrequency();
    }

    /** Search posts using hashtag */
    public void PostsWithHashtag()
    {
        System.out.println("Enter the specific Hashtag");
        String hashtag=sc.nextLine();
        hashtagStats.allPostsWithHashtag(hashtag);
    }
    
    /** Display final system report */
    public void SummaryReport()
    {
        report.displaySummaryReport();
    }
}
