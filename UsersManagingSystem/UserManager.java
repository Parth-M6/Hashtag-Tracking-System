package UsersManagingSystem;

import java.util.*;
/**
 * Manages all users and performs following operations:
 * 1. User registration
 * 2. Login and logout
 * 3. Password updation
 * 4. Profile viewing
 * 5. Username, email and password validation
 */
public class UserManager
{
    /** 
     * Stores a map of users by using username as key. 
     */
    private HashMap<String,User>users;
    /** 
     * Stores emails to prevent duplicate accounts 
     */
    private HashSet<String>emails;
    /**
     * Allowed special characters for password validation 
     */
    HashSet<Character>special=new HashSet<>(Set.of('!','@','#','$','%','^','&','*','-','_','+','='));
     /**
     * Default constructor that initializes empty user database.
     */
    public UserManager()
    {
        users=new HashMap<String,User>();
        emails=new HashSet<>();
    }
    
    /**
     * Parameterized constructor
     * Creates manager using existing users
     *
     * @param users Existing user records
     */
    public UserManager(HashMap<String,User>users)
    {
        this.users=new HashMap<>(users);
        this.emails=new HashSet<>();
        for(User user : users.values())
        {
            this.emails.add(user.getEmail().toLowerCase());
        }
    }

    /**
     * Registers a new user after verifying:
     * 1. Username availability and validity
     * 2. Email validity and uniqueness
     * 3. Password strength
     *
     * @return true if registration successful, else false
     */
    public boolean registerUser(String username, String email, String password, String nameOfUser)
    {
        try
        {
            if(username==null || email==null || password==null || nameOfUser==null)
            {
                throw new NullPointerException("userid,email,password and name of user cannot be empty");
            }
        }
        catch(NullPointerException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        if(isUsernameAvailable(username) && isUsernameValid(username))
        {
            if(isEmailValid(email) && isEmailAvailable(email))
            {
                if(isPasswordValid(password))
                {

                    User newUser = new User(username.toLowerCase(),email.toLowerCase(),password,nameOfUser);
                    users.put(username.toLowerCase(),newUser);
                    emails.add(email.toLowerCase());
                    System.out.println("Total registered user: "+users.size());
                    return true;

                }
            }
        }
        return false;
    }

    /**
     * User is logged in if username exists and its corresponding password is verified
     *
     * @return User object if found, else null
     */
    public User loginUser(String username, String password)
    {
        if(username==null || password==null)
        {
            throw new NullPointerException("userid or password cannot be empty");
        }
        if(this.users.containsKey(username.toLowerCase()))
        {
            this.users.get(username.toLowerCase()).login(password);
            return users.get(username.toLowerCase());
        }
        System.out.println("User not found");
        return null;
    }

    /**
     * Logs out a user
     *
     * @return true if successful, false otherwise
     */
    public boolean logoutUser(String username)
    {
        if(username==null)
        {
            throw new NullPointerException("username cannot be empty");
        }
        if(this.users.containsKey(username.toLowerCase()))
        {
            this.users.get(username.toLowerCase()).logout();
            return true;
        }
        return false;
    }

    /**
     * Checks if username is available (or unused)
     */
    public boolean isUsernameAvailable(String username)
    {
        if(username==null)
        {
            return false;
        }
        return !users.containsKey(username.toLowerCase());
    }

    /**
     * Validates username rules:
     * 1. Max 15 characters
     * 2. Only alphabets, digits, '.' and '_'
     * 3. No consecutive dots
     * @return true if username valid, false otherwise
     */
    public boolean isUsernameValid(String username)
    {
        if(username==null)
        {
            System.out.println("username cannot be empty");
            return false;
        }
        if(username.length()>15)
            {
                System.out.println("username length cannot be more than 15");
                return false;
            }
        ArrayList<Integer>dots_pos=new ArrayList<>();
        username.toLowerCase();
        int countAlphaNumeric=0,underscore=0,dot=0;
        for(int i=0;i<username.length();i++)
        {
            char ch=username.charAt(i);
            if(Character.isLetterOrDigit(ch))
            {
                countAlphaNumeric++;
            }
            if(ch=='_')
            {
                underscore++;
            }
            if(ch=='.')
            {
                dot++;
                dots_pos.add(i);
            }
        }
        //checking for consecutive dots
        for(int i=0;i<dots_pos.size()-1;i++)
        {
            if(dots_pos.get(i+1)-dots_pos.get(i)==1)
            {
                System.out.println("username cannot have consecutive \'.\'");
                return false;
            }
        }

        if(countAlphaNumeric+underscore+dot==username.length())
        {
            return true;
        }
        else
        {
            System.out.println("username can only have letters,digits,\'.\' and \'_\'");
            return  false;
        }
    }

    /**
     * Validates email format ( username@domain). Rules:
     * 1. Exactly one '@'
     * 2. Domain must contain '.' but must not begin with '.'
     * @return true if email is valid, false otherwise
     */
    private boolean isEmailValid(String email)
    {
        if (email==null) 
        {
            System.out.println("email cannot be empty");
            return false;
        }
        int index=email.indexOf('@');
        if (index<=0 || index!=email.lastIndexOf('@') || index==email.length()-1) {
            return false;
        }
        int dot = email.substring(index + 1).lastIndexOf('.');
        if (dot <= 0 || dot == email.substring(index + 1).length() - 1) {
            return false;
        }
        if (email.substring(index + 1).startsWith(".")) 
        {
            return false;
        }
        return true;
    }

    /**
     * @return true if email is available (or unused), false otherwise
     */
    public boolean isEmailAvailable(String email)
    {
        if(email==null)
        {
            System.out.println("email cannot be empty");
            return false;
        }
        if(emails.contains(email.toLowerCase()))
        {
            System.out.println(email+" is associated to another username");
            return false;
        }
        return true;
    }

    /**
     * Validates password rules:
     * 1. Minimum 8 characters
     * 2. At least one uppercase letter
     * 3. At least one lowercase letter
     * 4. At least one digit
     * 5. At least one special character
     * @return true if email is valid, false otherwise
     */
    public boolean isPasswordValid(String password)
    {
        if(password==null)
        {
            System.out.println("password cannot be empty");
            return false;
        }
        if(password.length()>=8)
        {
            int countSmall=0,countBig=0,countDigit=0,countSpecial=0;
            for(int i=0;i<password.length();i++)
            {
                if(Character.isLowerCase(password.charAt(i)))
                {
                    countSmall++;
                }
                else if(Character.isUpperCase(password.charAt(i)))
                {
                    countBig++;
                }
                if(Character.isDigit(password.charAt(i)))
                {
                    countDigit++;
                }
                if(special.contains(password.charAt(i)))
                {
                    countSpecial++;
                }
            }
            if(countSmall+countBig+countDigit+countSpecial==password.length())
                if(countSmall>0 && countBig>0 && countDigit>0 && countSpecial>0)
                {
                    return true;
                }
        }
        System.out.println("Password length should be atleast 8");
        System.out.println("Password should contain atleast one lowercase letter");
        System.out.println("Password should contain atleast one uppercase letter");
        System.out.println("Password should contain atleast one digit");
        System.out.println("Password should contain atlast one special character "+special);
        return false;
    }

    /**
     * Changes password after verifying old password
     * @return true if succesfull, false otherwise
     */
    public boolean changePassword(String username, String oldPassword, String newPassword) 
    {
        if(oldPassword==null || newPassword==null)
        {
            throw new NullPointerException("old and new passwords cannot be empty");
        }
        if (username == null || !users.containsKey(username.toLowerCase())) 
        {
            System.out.println("User password change unsuccessful");
            return false;
        }
        return users.get(username.toLowerCase()).changePassword(oldPassword, newPassword);
    }

    /**
     * Displays user profile
     */
    public void viewUserProfile(String username)
    {
        if(username==null)
        {
            throw new NullPointerException("Userid cannot be empty");
        }
        if(users.containsKey(username.toLowerCase()))
        {
            System.out.println(this.users.get(username.toLowerCase()).toString());
        }
        else
        {
            System.out.println("User not found");
        }
    }

    /**
     * @return all users as array
     */
    public User[] getUsers()
    {
        ArrayList<User>arr=new ArrayList<User>();
        for(User user: users.values())
        {
            arr.add(user);
        }
        return arr.toArray(new User[0]);
    }

    /**
     * @return complete user HashMap
     */
    public HashMap<String,User> getUserMap()
    {
        return this.users;
    }

}
