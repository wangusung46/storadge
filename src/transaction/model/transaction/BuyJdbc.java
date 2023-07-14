package transaction.model.transaction;

import transaction.model.transaction.response.ResponseListTableBuy;
import transaction.model.category.Category;
import transaction.model.item.Item;
import transaction.model.unit.Unit;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Khanza
 */
public interface BuyJdbc {

    public abstract List<ResponseListTableBuy> selectBuys(String search);

    public abstract void insertBuy(Buy buy);

    public abstract void updateBuy(Buy buy);
    
    public abstract void deleteBuy(Long id);
    
    public abstract Buy lastInsertId();
    
    public abstract List<Category> selectBuyCategory();

    public abstract List<Item> selectBuyItem(Long idCategory);

    public abstract List<Unit> selectBuyUnit(Long idCategory, Long idItem);
    
    public abstract List<Buy> selectDate(Long idCategory, Long idItem, Long idUnit);
    
    public abstract Buy selectBuy(Long idCategory, Long idItem, Long idUnit, Long id);
    
    public abstract Buy selectStock(Long idCategory, Long idItem, Long idUnit, Long id);

}
