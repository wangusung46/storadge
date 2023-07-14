package transaction.model.category;

import java.util.List;

/**
 *
 * @author Khanza
 */
public abstract interface CategoryJdbc {
    
    public abstract List<Category> selectCategorys(String search);

    public abstract void insertCategory(Category category);

    public abstract void updateCategory(Category category);
    
    public abstract void deleteCategory(Long id);
    
    public abstract Boolean countTotal();
    
}
