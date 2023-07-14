package transaction.model.supplier;

import java.util.List;

/**
 *
 * @author Khanza
 */
public abstract interface SupplierJdbc {
    
    public abstract List<Supplier> selectSuppliers(String search);

    public abstract void insertSupplier(Supplier supplier);

    public abstract void updateSupplier(Supplier supplier);
    
    public abstract void deleteSupplier(Long id);
    
}
