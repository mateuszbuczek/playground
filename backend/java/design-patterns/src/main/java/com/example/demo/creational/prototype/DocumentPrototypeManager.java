package com.example.demo.creational.prototype;

import java.util.HashMap;
import java.util.Map;

public class DocumentPrototypeManager {

    private static Map<String, PrototypeCapableDocument> prototypes = new HashMap<>();

    static {
        TAndC tAndC = new TAndC();
        tAndC.setVendorName("Vendor Name");
        /*Retrieve Terms and Conditions from database/network call/disk I/O here*/
        tAndC.setContent("Content");
        prototypes.put("tandc", tAndC);
        AuthorizedSignatory authorizedSignatory = new AuthorizedSignatory();
        authorizedSignatory.setName("Andrew Clark");
        authorizedSignatory.setDesignation("Operation Head");
        NDAgreement nda = new NDAgreement();
        nda.setVendorName("Vendor Name Placeholder");
        /*Retrieve Non Disclosure Agreement from database/network call/disk I/O here*/
        nda.setContent("Please read and accept the NDA...");
        nda.setAuthorizedSignatory(authorizedSignatory);
        prototypes.put("nda", nda);
    }

    public static PrototypeCapableDocument getClonedDocument(final String type) {
        PrototypeCapableDocument clonedDoc = null;
        try {
            PrototypeCapableDocument doc = prototypes.get(type);
            clonedDoc = doc.cloneDocument();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clonedDoc;
    }
}
