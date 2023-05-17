package au.edu.sydney.brawndo.erp.spfea.contactsenders;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.PhoneCall;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class PhoneCallSender implements ContactSender{
    @Override
    public boolean sendInvoice(AuthToken token, Customer customer, String data) {
        String phone = customer.getPhoneNumber();
        if (null != phone) {
            PhoneCall.sendInvoice(token, customer.getfName(), customer.getlName(), data, phone);
            return true;
        }
        return false;
    }
}
