package transaction.model.transaction;

import java.util.List;
import transaction.model.transaction.response.ResponseListCartId;
import transaction.model.transaction.response.ResponseListTableCart;

/**
 *
 * @author Khanza
 */
public abstract interface CartJdbc {
    
    public abstract List<ResponseListTableCart> selectCarts(String search);
    
    public abstract List<ResponseListCartId> selectCartsId();

    public abstract void insertCart(Cart cart);
    
    public abstract void deleteCart(Long id);
    
    public abstract void resetCart();
    
    public abstract Integer totalPriceCart();
    
}
