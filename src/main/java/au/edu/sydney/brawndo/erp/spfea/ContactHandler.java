package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.*;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.contactsenders.ContactSender;

import java.util.Arrays;
import java.util.List;

public class ContactHandler {
    public static boolean sendInvoice(AuthToken token, Customer customer, List<ContactSender> priority, String data) {
        for (ContactSender sender : priority) {
            if (sender.sendInvoice(token, customer, data)) {
                return true;
            }
        }
        return false;

    }
    public static List<String> getKnownMethods() {
        return Arrays.asList(
                "Carrier Pigeon",
                "Email",
                "Mail",
                "Merchandiser",
                "Phone call",
                "SMS"
        );
    }
}
