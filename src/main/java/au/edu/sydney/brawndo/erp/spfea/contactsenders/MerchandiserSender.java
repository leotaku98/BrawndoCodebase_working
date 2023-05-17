package au.edu.sydney.brawndo.erp.spfea.contactsenders;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.Merchandiser;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class MerchandiserSender implements ContactSender{
    @Override
    public boolean sendInvoice(AuthToken token, Customer customer, String data) {
        String merchandiser = customer.getMerchandiser();
        String businessName = customer.getBusinessName();
        if (null != merchandiser && null != businessName) {
            Merchandiser.sendInvoice(token, customer.getfName(), customer.getlName(), data, merchandiser,businessName);
            return true;
        }
        return false;
    }
}
