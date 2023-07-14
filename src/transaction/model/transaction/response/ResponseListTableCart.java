package transaction.model.transaction.response;

import java.math.BigDecimal;
import transaction.model.transaction.Buy;

/**
 *
 * @author Khanza
 */
public class ResponseListTableCart {

    private Long id;
    private String nameCategory;
    private String nameItem;
    private String nameUnit;
    private Integer amountSell;
    private BigDecimal priceSell;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }

    public Integer getAmountSell() {
        return amountSell;
    }

    public void setAmountSell(Integer amountSell) {
        this.amountSell = amountSell;
    }

    public BigDecimal getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(BigDecimal priceSell) {
        this.priceSell = priceSell;
    }

    @Override
    public String toString() {
        return "ResponseListTableCart{" + "id=" + id + ", nameCategory=" + nameCategory + ", nameItem=" + nameItem + ", nameUnit=" + nameUnit + ", amountSell=" + amountSell + ", priceSell=" + priceSell + '}';
    }

}
