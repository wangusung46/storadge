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
public class ResponseListTableStock {

    private Supplier supplier;
    private Category category;
    private Item item;
    private Unit unit;
    private Integer countItem;

    public ResponseListTableStock() {
        supplier = new Supplier();
        category = new Category();
        item = new Item();
        unit = new Unit();
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

    @Override
    public String toString() {
        return "ResponseListTableStock{" + "supplier=" + supplier + ", category=" + category + ", item=" + item + ", unit=" + unit + ", countItem=" + countItem + '}';
    }

}
