package core.rest;

import java.util.ArrayList;
import java.util.List;

public class DecodeTimeManager {

    /**
     * A list of decodeTime-objects.
     */
    private List<DecodeTime> data;

    /**
     * @return List<DecodeTime>
     */
    public List<DecodeTime> getData() {
        return new ArrayList<>(this.data);
    }
}
