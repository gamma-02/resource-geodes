package gamma02.resourcegeodes;

public interface worldGenRegionAccessor {
    default int getWriteRadiusCutoff(){
        return 0;
    }
}
