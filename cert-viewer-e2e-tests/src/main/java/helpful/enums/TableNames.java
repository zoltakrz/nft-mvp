package helpful.enums;

public enum TableNames {
    NAME("Name"),
    SURNAME("Surname"),
    TYPE("Certificate Type"),
    LEVEL("Certificate Level"),
    NFT("Minted NFT"),
    LOGO("Logo");

    public final String columnsNames;

    TableNames(String columnsNames) {
        this.columnsNames = columnsNames;
    }
}
