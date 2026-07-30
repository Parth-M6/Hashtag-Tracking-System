
public interface Menu
{
    /** Display available menu options */
    void Options();
    /** Register a new user */
    void RegisterUser();
    /** Login existing user */
    void LoginUser();
    /** Logout current user */
    void LogoutUser();
    /** Change password of logged in user */
    void ChangePassword();
    /** View profile details of user */
    void ViewProfile();
    /** Create a new post */
    void CreatePost();
    /** Edit an existing post */
    void EditPost();
    /** Delete a post */
    void DeletePost();
    /** View complete post */
    void ViewPost();
    /** View short summary of post */
    void ViewPostSummary();
    /** View all posts of a specific user */
    void UserPosts();
    /** View top trending hashtags */
    void ViewTrending();
    /** View hashtag usage frequency */
    void ViewHashtagFrequency();
    /** View all posts containing a hashtag */
    void PostsWithHashtag();
    /** Display complete system report */
    void SummaryReport();
}
