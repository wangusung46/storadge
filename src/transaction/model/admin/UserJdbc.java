package transaction.model.admin;

/**
 *
 * @author Khanza
 */
public interface UserJdbc {

    public Boolean login(String userName, String password);

    public void insertUser(User admin);

    public void updateUser(Long id, User admin);
    
    public Boolean selectUserExist(String userName);

}
