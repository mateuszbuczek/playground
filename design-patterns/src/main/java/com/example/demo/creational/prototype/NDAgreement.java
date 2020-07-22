package com.example.demo.creational.prototype;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NDAgreement extends PrototypeCapableDocument {

    private AuthorizedSignatory authorizedSignatory;

    @Override
    public PrototypeCapableDocument cloneDocument() throws CloneNotSupportedException {
        NDAgreement ndAgreement;
        ndAgreement = (NDAgreement) super.clone();
        AuthorizedSignatory clonedSignatory = (AuthorizedSignatory) ndAgreement.authorizedSignatory.clone();
        ndAgreement.setAuthorizedSignatory(clonedSignatory);
        return ndAgreement;
    }
}
