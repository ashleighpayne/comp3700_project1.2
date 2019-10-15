public class PurchaseModel {
    public int mPurchaseID;
    public int mProductID;
    public int mCustomerID;
    public int mQuantity;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(mPurchaseID).append(",");
        sb.append(mProductID).append(",");
        sb.append(mCustomerID).append(",");
        sb.append(mQuantity).append(")");

        return sb.toString();
    }
}