package au.edu.sydney.brawndo.erp.spfea.contactsenders;


import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.SMS;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class SmsSender implements ContactSender {

    @Override
    public boolean sendInvoice(AuthToken token, Customer customer, String data) {
        String smsPhone = customer.getPhoneNumber();
        if (null != smsPhone) {
            SMS.sendInvoice(token, customer.getfName(), customer.getlName(), data, smsPhone);
            return true;
        }
        return false;
    }
}

