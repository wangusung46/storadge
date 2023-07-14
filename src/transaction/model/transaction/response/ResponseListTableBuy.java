package transaction.model.transaction.response;

import transaction.model.category.Category;
import transaction.model.item.Item;
import transaction.model.supplier.Supplier;
import transaction.model.unit.Unit;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Khanza
 */
public class ResponseListTableBuy {

    private Long id;
    private Supplier supplier;
    private Category category;
    private Item item;
    private Unit unit;
    private Integer countItem;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private LocalDateTime date;

    public ResponseListTableBuy() {
        supplier = new Supplier();
        category = new Category();
        item = new Item();
        unit = new Unit();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getCountItem() {
        return countItem;
    }

    public void setCountItem(Integer countItem) {
        this.countItem = countItem;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ResponseListTableBuy{" + "id=" + id + ", supplier=" + supplier + ", category=" + category + ", item=" + item + ", unit=" + unit + ", countItem=" + countItem + ", buyPrice=" + buyPrice + ", date=" + date + '}';
    }

}
