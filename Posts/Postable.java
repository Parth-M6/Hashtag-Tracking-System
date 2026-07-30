package Posts;

/**
 * Interface representing common behavior for any post-like object.
 * Classes implementing this interface must provide methods for:
 * 1. Accessing content
 * 2. Updating content
 * 3. Getting author name
 * 4. Displaying formatted output
 */
public interface Postable
{
    /**
     * Returns content text.
     */
    String getContent();
    
    /**
     * Updates content text.
     */
    void setContent(String content);
    
    /**
     * Returns author username/name.
     */
    String getAuthor();
    
    /**
     * Returns formatted display string.
     */
    String display();
}
