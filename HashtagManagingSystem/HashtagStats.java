package HashtagManagingSystem;

import java.util.*;
import UsersManagingSystem.*;
import Posts.Post;

/**
 * Extends HashtagManager to provide hashtag analytics
 *
 * Responsibilities:
 * 1. Search posts using hashtags
 * 2. Show all posts containing a hashtag
 * 3. Count hashtag usage
 * 4. Count unique hashtags
 */
public class HashtagStats extends HashtagManager
{
    /**
     * Reference to user manager for accessing users 
     */
    private UserManager userManager;
    /** 
     * Stores all users temporarily 
     */
    private User users[];
    
     /**
     * Constructor
     * @param userManager: UserManager object
     */
    public HashtagStats(UserManager userManager)
    {
        this.userManager=userManager;
    }

    /**
     * Searches all posts of a specific user
     * and returns posts containing given hashtag
     *
     * @param hashtag: hashtag to search
     * @param user: user whose posts are checked
     * @return list of matching posts
     */
    public ArrayList<Post> searchPostsByHashtag(String hashtag,User user)
    {
        ArrayList<Post>posts=new ArrayList<Post>();
        for(Post p:user.getPosts())
        {
            if(p.containsHashtag(hashtag))
            {
                posts.add(p);
            }
        }
        return posts;
    }

    /**
     * Displays all posts from all users
     * containing the given hashtag
     *
     * Also shows total number of matching posts.
     *
     * @param hashtag: hashtag to search
     */
    public void allPostsWithHashtag(String hashtag)
    {
        users=userManager.getUsers();
        // Show total count first
        totalNumberOfPostsWithHashtag(hashtag);
        for(User user:users)
        {
            ArrayList<Post>posts=searchPostsByHashtag(hashtag,user);
            for(Post p:posts)
            {
                System.out.println(p.getSummary(hashtag));
                System.out.println();
            }
        }
    }

    /**
     * Displays number of posts containing hashtag
     *
     * @param hashtag: hashtag to count
     */
    public void totalNumberOfPostsWithHashtag(String hashtag)
    {
        if(hashtagFrequency.get(hashtag)==null)
            System.out.println(hashtag+" has been used in 0 posts");
        else
            System.out.println(hashtag+" has been used in "+hashtagFrequency.get(hashtag)+" posts");
    }

    /**
     * Returns total count of unique hashtags
     *
     * @return count of unique hashtags
     */
    public int numberOfUniqueHashtags()
    {
        return hashtagFrequency.size();
    }
}
