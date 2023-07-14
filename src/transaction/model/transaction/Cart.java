package transaction.model.transaction;

import transaction.model.transaction.Buy;

/**
 *
 * @author Khanza
 */
public class Cart {

    private Long id;
    private Buy buy;
    private Integer cartAmount;

    public Cart() {
        buy = new Buy();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Buy getBuy() {
        return buy;
    }

    public void setBuy(Buy buy) {
        this.buy = buy;
    }

    public Integer getCartAmount() {
        return cartAmount;
    }

    public void setCartAmount(Integer cartAmount) {
        this.cartAmount = cartAmount;
    }

    @Override
    public String toString() {
        return "Cart{" + "id=" + id + ", buy=" + buy + ", cartAmount=" + cartAmount + '}';
    }

    

}
