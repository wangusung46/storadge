package transaction.model.unit;

import java.util.List;

/**
 *
 * @author Khanza
 */
public abstract interface UnitJdbc {
    
    public abstract List<Unit> selectUnits(String search);

    public abstract void insertUnit(Unit unit);

    public abstract void updateUnit(Unit unit);
    
    public abstract void deleteUnit(Long id);
    
    public abstract Unit selectUnitByName(String name);
    
    public abstract Boolean countTotal();
    
}
