package transaction.model.item;

import java.util.List;

/**
 *
 * @author Khanza
 */
public interface ItemJdbc {

    public abstract List<Item> selectItems(String search);

    public abstract void insertItem(Item item);

    public abstract void updateItem(Item item);
    
    public abstract void deleteItem(Long id);
    
    public abstract Boolean countTotal();

}
