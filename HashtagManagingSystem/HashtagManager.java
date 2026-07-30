package HashtagManagingSystem;
import java.util.*;
import Posts.*;

/**
 * Abstract class used to manage hashtag statistics
 *
 * Responsibilities:
 * 1. Store hashtag frequencies
 * 2. Update counts when posts are added/removed
 * 3. Sort hashtags by popularity
 * 4. Return trending hashtags
 */
public abstract class HashtagManager 
{
    /**
     * Stores hashtag and its frequency.
     */
    HashMap<String,Integer>hashtagFrequency;
    
    /**
     * Stores hashtags sorted in descending frequency
     */
    ArrayList<String>hashtagsDescending;
    
    /**
     * Default constructor
     * Initializes empty data structures
     */
    public HashtagManager()
    {
        hashtagFrequency=new HashMap<String,Integer>();
        hashtagsDescending=new ArrayList<>();
    }

    /**
     * Returns hashtag frequency map
     */
    public HashMap<String,Integer> getHashtagFrequency()
    {
        return hashtagFrequency;
    }

    /**
     * Updates frequency of a single hashtag
     *
     * @param a: 1 to add usage, -1 to remove usage
     * @param hashtag: hashtag to update
     */
    public void updateFrequency(int a,String hashtag)
    {
        hashtagFrequency.put(hashtag,hashtagFrequency.getOrDefault(hashtag,0)+a);
        if(hashtagFrequency.get(hashtag)==0)
        {
            hashtagFrequency.remove(hashtag);
        }
    }

    /**
     * Updates frequency of multiple hashtags.
     *
     * @param a: 1 to add, -1 to remove
     * @param hashtags: list of unique hashtags
     */
    public void updateFrequency(int a,String... hashtags)
    {
        for(String hashtag:hashtags)
        {
            hashtagFrequency.put(hashtag,hashtagFrequency.getOrDefault(hashtag,0)+a);
            // Remove hashtag if count becomes zero
            if(hashtagFrequency.get(hashtag)==0)
            {
                hashtagFrequency.remove(hashtag);
            }
        }
    }

     /**
     * Sorts hashtags in descending order of frequency
     */
    public void sortDescending()
    {
        hashtagsDescending=new ArrayList<>(hashtagFrequency.keySet());
        Collections.sort(hashtagsDescending, new Comparator<String>()
            {
                public int compare(String key1,String key2)
                {
                    return hashtagFrequency.get(key2).compareTo(hashtagFrequency.get(key1));
                }
            });
    }

    /**
     * Returns top N trending hashtags
     *
     * @param n: number of hashtags required
     * @return array of top hashtags
     */
    public String[] getTopNTrending(int n)
    {
        if(n<=0)
        {
            throw new IllegalArgumentException("n cannot be <=0");
        }
        this.sortDescending();
        // If requested count exceeds available hashtags
        if(n>hashtagsDescending.size())
        {
            System.out.println("There are only "+hashtagsDescending.size()+" unique hashtags across all posts");
            n=hashtagsDescending.size();
        }
        String topNTrending[]=new String[n];
        for(int i=0;i<n;i++)
            topNTrending[i]=hashtagsDescending.get(i);
        return topNTrending;
    }

    /**
     * Displays frequency of all hashtags
     */
    public void showHashtagFrequency()
    {
        if(hashtagFrequency.size()==0)
        {
            System.out.println("None of the posts contained any hashtags");
            return;
        }
        System.out.println("The frequency statistics for each hashtag are:");
        for(Map.Entry<String,Integer>hashtag:hashtagFrequency.entrySet())
        {
            System.out.println(hashtag.getKey()+" : "+hashtag.getValue());
        }
    }
}
