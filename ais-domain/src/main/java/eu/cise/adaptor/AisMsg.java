package eu.cise.adaptor;

import eu.cise.adaptor.translate.utils.NavigationStatus;

import java.time.Instant;

public class AisMsg {

    private final Integer imoNumber;
    // POSITION
    private int messageType;
    private float latitude;
    private float longitude;
    private int userId;
    private float cog;
    private int trueHeading;
    private Instant timestamp;
    private float sog;
    private NavigationStatus navigationStatus;
    private int positionAccuracy;
    // VOYAGE
    private String destination;
    private Instant eta;
    private String callsign;
    private float draught;
    private Integer dimensionC;
    private Integer dimensionD;
    private Integer dimensionA;
    private Integer dimensionB;
    private Integer shipType;
    private String shipName;

    private AisMsg(AisMsg.Builder builder) {
        messageType = builder.messageType;
        latitude = builder.latitude;
        longitude = builder.longitude;
        positionAccuracy = builder.positionAccuracy;
        userId = builder.userId;
        cog = builder.cog;
        trueHeading = builder.trueHeading;
        timestamp = builder.timestamp;
        sog = builder.sog;
        navigationStatus = builder.navigationStatus;

        destination = builder.destination;
        eta = builder.eta;
        imoNumber = builder.imoNumber;
        callsign = builder.callSign;
        draught = builder.draught;
        dimensionC = builder.dimensionC;
        dimensionD = builder.dimensionD;
        dimensionA = builder.dimensionA;
        dimensionB = builder.dimensionB;
        shipType = builder.shipType;
        shipName = builder.shipName;
    }

    public int getMessageType() {
        return messageType;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public int getUserId() {
        return userId;
    }

    public float getCOG() {
        return cog;
    }

    public int getTrueHeading() {
        return trueHeading;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public float getSOG() {
        return sog;
    }

    public NavigationStatus getNavigationStatus() {
        return navigationStatus;
    }

    public Integer getPositionAccuracy() {
        return positionAccuracy;
    }

    public String getDestination() {
        return destination;
    }

    public Instant getEta() {
        return eta;
    }

    public Integer getImoNumber() {
        return imoNumber;
    }

    public String getCallSign() {
        return callsign;
    }

    public Float getDraught() {
        return draught;
    }

    public Integer getDimensionC() {
        return dimensionC;
    }

    public Integer getDimensionD() {
        return dimensionD;
    }

    public Integer getDimensionA() {
        return dimensionA;
    }

    public Integer getDimensionB() {
        return dimensionB;
    }

    public Integer getShipType() {
        return shipType;
    }

    public String getShipName() {
        return shipName;
    }

    public static class Builder {
        private int messageType;

        // POSITION
        private float latitude;
        private float longitude;
        private int positionAccuracy;
        private int userId;
        private float cog;
        private int trueHeading;
        private Instant timestamp = Instant.MIN;
        private float sog;
        private NavigationStatus navigationStatus;

        // VOYAGE
        private String destination;
        private Instant eta;
        private Integer imoNumber;
        private String callSign;
        private float draught;
        private Integer dimensionC;
        private Integer dimensionD;
        private Integer dimensionA;
        private Integer dimensionB;
        private Integer shipType;
        private String shipName;

        public Builder(int messageType) {
            this.messageType = messageType;
        }

        public AisMsg.Builder withLatitude(float l) {
            this.latitude = l;
            return this;
        }

        public AisMsg.Builder withLongitude(float l) {
            this.longitude = l;
            return this;
        }

        public AisMsg.Builder withPositionAccuracy(int la) {
            this.positionAccuracy = la;
            return this;
        }

        public AisMsg.Builder withUserId(int m) {
            this.userId = m;
            return this;
        }

        public AisMsg.Builder withCOG(float c) {
            this.cog = c;
            return this;
        }

        public AisMsg.Builder withTrueHeading(int t) {
            this.trueHeading = t;
            return this;
        }

        public AisMsg.Builder withTimestamp(Instant received) {
            this.timestamp = received;
            return this;
        }

        public AisMsg.Builder withSOG(float s) {
            this.sog = s;
            return this;
        }

        public AisMsg.Builder withNavigationStatus(NavigationStatus n) {
            this.navigationStatus = n;
            return this;
        }

        // VOYAGE
        public Builder withDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder withEta(Instant eta) {
            this.eta = eta;
            return this;
        }

        public Builder withIMONumber(Integer imoNumber) {
            this.imoNumber = imoNumber;
            return this;
        }

        public Builder withCallSign(String callsign) {
            this.callSign = callsign;
            return this;
        }

        public AisMsg build() {
            return new AisMsg(this);
        }

        public Builder withDraught(Float draught) {
            this.draught = draught;
            return this;
        }

        public Builder withDimensionC(Integer toPort) {
            this.dimensionC = toPort;
            return this;
        }

        public Builder withDimensionD(Integer toStarboard) {
            this.dimensionD = toStarboard;
            return this;
        }

        public Builder withDimensionA(Integer toBow) {
            this.dimensionA = toBow;
            return this;
        }

        public Builder withDimensionB(Integer toStern) {
            this.dimensionB = toStern;
            return this;
        }

        public Builder withShipType(Integer shipType) {
            this.shipType = shipType;
            return this;
        }

        public Builder withShipName(String shipName) {
            this.shipName = shipName;
            return this;
        }
    }

}
