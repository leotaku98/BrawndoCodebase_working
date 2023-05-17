package au.edu.sydney.brawndo.erp.spfea.contactsenders;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public interface ContactSender {
    boolean sendInvoice(AuthToken token, Customer customer, String data);
}
