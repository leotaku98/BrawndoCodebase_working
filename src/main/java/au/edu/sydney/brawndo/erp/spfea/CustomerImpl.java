package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class CustomerImpl implements Customer {

    private final int id;
    private String fName;
    private String lName;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private String suburb;
    private String state;
    private String postCode;
    private String merchandiser;
    private String businessName;
    private String pigeonCoopID;

    private AuthToken token;

    public CustomerImpl(AuthToken token, int id) {

        this.id = id;
        this.token = token;
//        this.lName = TestDatabase.getInstance().getCustomerField(token, id, "lName");
//        this.phoneNumber = TestDatabase.getInstance().getCustomerField(token, id, "phoneNumber");
//        this.emailAddress = TestDatabase.getInstance().getCustomerField(token, id, "emailAddress");
//        this.address = TestDatabase.getInstance().getCustomerField(token, id, "address");
//        this.suburb = TestDatabase.getInstance().getCustomerField(token, id, "suburb");
//        this.state = TestDatabase.getInstance().getCustomerField(token, id, "state");
//        this.postCode = TestDatabase.getInstance().getCustomerField(token, id, "postCode");
//        this.merchandiser = TestDatabase.getInstance().getCustomerField(token, id, "merchandiser");
//        this.businessName = TestDatabase.getInstance().getCustomerField(token, id, "businessName");
//        this.pigeonCoopID = TestDatabase.getInstance().getCustomerField(token, id, "pigeonCoopID");
    }

    public int getId() {

        return id;
    }

    @Override
    public String getfName() {
        if (fName == null) {
            this.fName = TestDatabase.getInstance().getCustomerField(token, id, "fName");
        }
        return fName;
    }

    @Override
    public String getlName() {
        if (lName == null) {
            this.lName = TestDatabase.getInstance().getCustomerField(token, id, "lName");
        }
        return lName;
    }

    @Override
    public String getPhoneNumber() {
        if (phoneNumber == null) {
            this.phoneNumber = TestDatabase.getInstance().getCustomerField(token, id, "phoneNumber");
        }
        return phoneNumber;
    }

    @Override
    public String getEmailAddress() {
        if (emailAddress == null) {
            this.emailAddress = TestDatabase.getInstance().getCustomerField(token, id, "emailAddress");
        }
        return emailAddress;
    }

    @Override
    public String getAddress() {
        if (address == null) {
            this.address = TestDatabase.getInstance().getCustomerField(token, id, "address");
        }
        return address;
    }

    @Override
    public String getSuburb() {
        if (suburb == null) {
            this.suburb = TestDatabase.getInstance().getCustomerField(token, id, "suburb");
        }
        return suburb;
    }

    @Override
    public String getState() {
        if (state == null) {
            this.state = TestDatabase.getInstance().getCustomerField(token, id, "state");
        }
        return state;
    }

    @Override
    public String getPostCode() {
        if (postCode == null) {
            this.postCode = TestDatabase.getInstance().getCustomerField(token, id, "postCode");
        }
        return postCode;
    }

    @Override
    public String getMerchandiser() {
        if (merchandiser == null) {
            this.merchandiser = TestDatabase.getInstance().getCustomerField(token, id, "merchandiser");
        }
        return merchandiser;
    }

    @Override
    public String getBusinessName() {
        if (businessName == null) {
            this.businessName = TestDatabase.getInstance().getCustomerField(token, id, "businessName");
        }
        return businessName;
    }

    @Override
    public String getPigeonCoopID() {
        if (pigeonCoopID == null) {
            this.pigeonCoopID = TestDatabase.getInstance().getCustomerField(token, id, "pigeonCoopID");
        }
        return pigeonCoopID;
    }
}

