package core.user;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Implementation of FilenameFilter for checking if a filename for spesific user
 * exists.
 * @author Casper Salminen Andreassen
 */
public class FindFile implements FilenameFilter {

    /**
     * A userID.
     */
    private String userId;

    /**
     * Constructor for findFile with a userid.
     * @param uuid
     */
    public FindFile(final String uuid) {
        this.userId = uuid;
    }

    /**
     * Overrides default accept method of FilenameFilter.
     * @param dir  - File
     * @param name - name
     */
    @Override
    public boolean accept(final File dir, final String name) {
        return name.startsWith(userId + ".json");
    }
}
