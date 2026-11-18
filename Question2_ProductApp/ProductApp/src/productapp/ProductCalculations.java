package productapp;

/**
 * Calculates VAT, commission and the total sale amount for a product price.
 */
public class ProductCalculations implements IProductCalculations {

    private static final double VAT_RATE = 0.15;        // 15% of the price
    private static final double COMMISSION_RATE = 0.05; // 5% of the price

    private final double price;

    public ProductCalculations(double price) {
        this.price = price;
    }

    @Override
    public double GetVat() {
        return price * VAT_RATE;
    }

    @Override
    public double GetCommission() {
        return price * COMMISSION_RATE;
    }

    // Total = price + VAT + commission
    @Override
    public double GetTotal() {
        return price + GetVat() + GetCommission();
    }
}
