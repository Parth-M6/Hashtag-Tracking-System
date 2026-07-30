package Analytics;

import java.util.*;
import UsersManagingSystem.*;
import HashtagManagingSystem.*;

/**
 * Generates analytical reports 
 *
 * Responsibilities:
 * 1. Total posts count
 * 2. Total unique hashtags
 * 3. Most active users
 * 4. Trending hashtags
 * 5. Full summary report
 */
public class Reports
{
    /** 
     * Used to access users and user data 
     */
    private UserManager userManager;
    /** 
     * Used for hashtag statistics 
     */
    private HashtagStats hashtagStats;
    /**
     * Used for trending hashtag data
     */
    private HashtagManager hashtagManager;
    /** 
     * Temporary user array 
     */
    User users[];
    
    /**
     * Constructor.
     *
     * @param userManager: user manager object
     * @param hashtagStats: hashtag stats object
     * @param hashtagManager: hashtag manager object
     */
    public Reports(UserManager userManager,HashtagStats hashtagStats, HashtagManager hashtagManager)
    {
        this.userManager=userManager;
        this.hashtagStats=hashtagStats;
        this.hashtagManager=hashtagManager;
    }

    /**
     * Displays total number of posts
     * created by all users
     */
    public void displayTotalNumberOfPosts()
    {
        users=userManager.getUsers();
        int postCount=0;
        for(User user:users)
        {
            postCount+=user.getPostCount();
        }
        System.out.println("Total Posts Created: "+postCount);
    }

     /**
     * Displays total number of unique hashtags
     */
    public void displayTotalUniqueHashtags()
    {
        System.out.println("Total Unique Hashtags Tracked: "+hashtagStats.numberOfUniqueHashtags());
    }

    /**
     * Displays top K most active users.
     *
     * Activity measured using total hashtags used.
     *
     * @param k number of users to display
     */
    public void kMostActiveUser(int k)
    {
        users=userManager.getUsers();
        
        // Count hashtag usage for every user
        for(User u:users)
        {
            u.countHashtagFrequency();
        }
        
        // Sort descending by hashtag count
        Arrays.sort(users,(a,b)->b.getHashtagCount()-a.getHashtagCount());  
        if(users.length==0)
        {
            System.out.println("There are no  users in the system");
            return;
        }
        if(k>users.length)
        {
            System.out.println("There are only "+users.length+" users in the system");
            k=users.length;
        }
        System.out.println("The "+k+" most active users are: ");
        for(int i=1;i<=k;i++)
        {
            System.out.println(i+".  "+users[i-1].getUsername()+" ("+users[i-1].getHashtagCount()+" hashtags used)");
        }
    }

    /**
     * Displays top 3 most active users by default.
     */
    public void kMostActiveUser()
    {
        users=userManager.getUsers();
        for(User u:users)
        {
            u.countHashtagFrequency();
        }
        Arrays.sort(users,(a,b)->b.getHashtagCount()-a.getHashtagCount());  
        int k=3;
        if(k>users.length)
        {
            k=users.length;
            if(k==0)
            {
                System.out.println("There are no registered users in the system");
            }
            else
            System.out.println("There are only "+k+" registed users in the system");
        }
        
        for(int i=1;i<=k;i++)
        {
            users[i-1].countHashtagFrequency();
            System.out.println(i+".  "+users[i-1].getUsername()+" ("+users[i-1].getHashtagCount()+" hashtags used)");
        }
    }

    /**
     * Displays complete system summary report.
     *
     * Includes:
     * - Total users
     * - Total posts
     * - Unique hashtags
     * - Trending hashtags
     * - Most active users
     */
    public void displaySummaryReport()
    {
        users=userManager.getUsers();
        System.out.println("==================================================");
        System.out.println("            SYSTEM  SUMMARY  REPORT               ");
        System.out.println("==================================================");
        System.out.println();
        // Overall statistics
        System.out.println("--- OVERALL STATISTICS ---");
        System.out.println("Total Registered Users: "+users.length);
        displayTotalNumberOfPosts();
        displayTotalUniqueHashtags();
        System.out.println();
        // Trending hashtags
        System.out.println("--- TOP TRENDING HASHTAGS ---");
        String trending[]=hashtagManager.getTopNTrending(3);
        if(trending==null || trending.length==0)
        {
            System.out.println("None of the posts created included any hashtags");
        }
        else
        {
            for(int i=0;i<Math.min(trending.length,3);i++)
            {
                System.out.println((i+1)+". "+trending[i]+" (Used "+hashtagManager.getHashtagFrequency().get(trending[i])+" times)");
            }
        }
        System.out.println();
        // Active users
        System.out.println("--- MOST ACTIVE USERS ---");
        kMostActiveUser();
        System.out.println();
        System.out.println("==================================================");
        System.out.println("                  END OF REPORT                   ");
        System.out.println("==================================================");
    }
}
