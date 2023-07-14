package transaction.model.transaction;

/**
 *
 * @author Khanza
 */
public class Sell {

    private Long id;
    private Buy buy;
    private Integer sellAmount;
    private Payment payment;

    public Sell() {
        buy = new Buy();
        payment = new Payment();
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

    public Integer getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(Integer sellAmount) {
        this.sellAmount = sellAmount;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Sell{" + "id=" + id + ", buy=" + buy + ", sellAmount=" + sellAmount + ", payment=" + payment + '}';
    }
}
