package Posts;
import UsersManagingSystem.User;
import java.util.*;
import HashtagManagingSystem.HashtagManager;

/**
 * Represents a normal user post.
 * Supports:
 * 1. Hashtags
 * 2. Metadata timestamps
 * 3. Content editing
 * 4. Summary of post
 */
public class Post extends Content implements Postable
{
    /** 
     * Unique post ID 
     */
    private int postId;
    /** 
     * Stores timestamps 
     */
    private PostMetadata metadata;
    /** 
     * Stores extracted hashtags 
     */
    HashSet<String>hashtags;
    
    /**
     * Nested class for post's metadata
     * Stores creation and editing time
     */
    public static class PostMetadata
    {
        private long createdAt;
        private long lastEdited;
        /**
         * Constructor sets both timestamps initially.
         * Used for new posts
         */
        public PostMetadata(long createdAt)
        {
            this.createdAt=createdAt;
            this.lastEdited=createdAt;
        }

        public long getCreatedAt()
        {
            return this.createdAt;
        }

        public long getLastEdited()
        {
            return this.lastEdited;
        }

         /**
         * Updates last edited time.
         */
        public void setLastEdited(long lastEdited)
        {
            this.lastEdited = lastEdited;
        }

        /**
         * Returns metadata in readable format
         */
        public String toString() 
        {
            return "Created at: " + new Date(createdAt) + " | Last edited at: " + new Date(lastEdited);
        }
    }

    /**
     * Constructor for Post
     */
    public Post(int postId,String content,User user)
    {
        super(content,user);
        this.postId=postId;
        this.metadata=new PostMetadata(System.currentTimeMillis());
        this.hashtags=new HashSet<String>();
    }

    /**
     * Returns post ID
     */
    public int getPostId()
    {
        return this.postId;
    }

    /**
     * Returns metadata of the post
     */
    public PostMetadata getMetadata()
    {
        return this.metadata;
    }

    /**
     * Updates content and edits timestamp
     */
    public void setContent(String content)
    {
        this.content=content;
        this.metadata.setLastEdited(System.currentTimeMillis());
    }

    /**
     * Displays post only if given hashtag exists in it
     */
    public String display(String hashtag)
    {
        if(this.containsHashtag(hashtag))
            return("Post #" + postId + "\n"+ "Author : " + this.getAuthor() +"\n"+"Content:\n" + content+"\n"+getMetadata());
        else
            return null;
    }
    
    /**
     * Displays post using given user object
     */
    public String display(User user)
    {
        return("Post #" + postId + "\n"+ "Author : " + user.getUsername() +"\n"+"Content:\n" + content+"\n"+getMetadata());
    }
    
    /**
     * General display method
     */
    public String display()
    {
        return("Post #" + postId +"\nContent:\n" + content+"\n"+getMetadata());
    }

    /**
    * Returns post content (upto 100 charcters) if hashtag exists
    */
    @Override
    public String getSummary(String hashtag) 
    {
        if(this.containsHashtag(hashtag))
        {
            String preview = content.length() > 100 ? content.substring(0, 100) + "..." : content;
            return "Post #" + this.postId + " by " + this.getAuthor() + ":\n" + preview;
        }
        else
            return null;
    }
    
    /**
     * Returns short summary (upto 100 charcters of post content)
     */
    public String getSummary() 
    {   
            String preview = content.length() > 100 ? content.substring(0, 100) + "..." : content;
            return "Post #" + this.postId+":\n" + preview;
    }
    
    /**
     * Returns summary with username
     */
    public String getSummary(User user) 
    {   
            String preview = content.length() > 100 ? content.substring(0, 100) + "..." : content;
            return "Post #" + this.postId + " by " + user.getUsername() + ":\n" + preview;
    }
    
    /**
     * Returns post author's username
     */
    public String getAuthor() 
    {
        if (user==null) 
        {
            return null;
        }
        return user.getUsername();
    }

    /**
     * Extracts hashtags from post content
     */
    public void extractHashtags()
    {
        String content=this.getContent();
        if(content==null || content.isEmpty())
        {
            return;
        }
        String words[]=content.split("\\s+");
        for(String word:words)
        {
            StringBuilder formHashtag=new StringBuilder();
            boolean tagStarted=false;
            for(int i=0;i<word.length();i++)
            {
                char ch=word.charAt(i);
                if(!tagStarted)
                {
                    if(ch=='#')
                    {
                        formHashtag.append(ch);
                        tagStarted=true;
                    }
                }
                else
                {
                    if(Character.isLetterOrDigit(ch) || ch=='_')
                    {
                        formHashtag.append(ch);
                    }
                    else
                    {
                        if(formHashtag.length()>1)
                            hashtags.add(formHashtag.toString());
                        i--;
                        tagStarted=false;
                        formHashtag.setLength(0);
                    }
                }
            }
            if(formHashtag.length()>1)
            {
                hashtags.add(formHashtag.toString());
            }
        }
    }

    /**
     * Returns all unique hashtags as array
     */
    public String[] getHashtags()
    {
        return hashtags.toArray(new String[0]);
    }

    /**
     * Updates hashtag frequencies.
     *
     * @param sign 1 add post, -1 remove post
     */
    public void updateHashtagCount(HashtagManager hashtagManager,int sign)
    {
     hashtagManager.updateFrequency(sign,hashtags.toArray(new String[0]));  
    }
    
    /**
     * @return true if post contains hashtag, false otherwise
     */
    public boolean containsHashtag(String hashtag)
    {
        return hashtags.contains(hashtag);
    }

}
