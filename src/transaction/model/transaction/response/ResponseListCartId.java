package transaction.model.transaction.response;

/**
 *
 * @author Khanza
 */
public class ResponseListCartId {
    
    private Long idCategory;
    private Long idItem;
    private Long idUnit;
    private Long idBuy;
    private Integer cartAmount;

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public Long getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(Long idUnit) {
        this.idUnit = idUnit;
    }

    public Long getIdBuy() {
        return idBuy;
    }

    public void setIdBuy(Long idBuy) {
        this.idBuy = idBuy;
    }

    public Integer getCartAmount() {
        return cartAmount;
    }

    public void setCartAmount(Integer cartAmount) {
        this.cartAmount = cartAmount;
    }

    @Override
    public String toString() {
        return "ResponseListCartId{" + "idCategory=" + idCategory + ", idItem=" + idItem + ", idUnit=" + idUnit + ", idBuy=" + idBuy + ", cartAmount=" + cartAmount + '}';
    }
}
