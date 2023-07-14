package transaction.model.config;

import java.sql.Connection;

/**
 *
 * @author Khanza
 */
public abstract interface GlobalConfigJdbc {
    
    public abstract String selectGlobalConfigValue(Long id);
    
    public abstract Connection getConnection();
    
}
