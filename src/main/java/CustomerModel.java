public class CustomerModel {
    public int mCustomerID;
    public String mName, mAddress;
    public double mAge;

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(mCustomerID).append(",");
        sb.append("\"").append(mName).append("\"").append(",");
        sb.append("\"").append(mAddress).append("\"").append(")");
        return sb.toString();
    }
}