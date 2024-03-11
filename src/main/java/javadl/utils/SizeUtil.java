package javadl.utils;

/**
 * Class to help you manipulate file sizes.
 *
 * TODO verificar a necessidade dessa classe
 */
public class SizeUtil {
    /**
     * Bytes to KB
     * @param bytes
     * @return
     */
    public static double toKBFB(int bytes) {
        return ((double) bytes / 1000);
    }

    /**
     * Bytes to MB
     * @param bytes
     * @return
     */
    public static double toMBFB(int bytes) {
        return ((double) bytes / 1000000);
    }

    /**
     * Bytes to GB
     * @param bytes
     * @return
     */
    public static double toGBFB(int bytes) {
        return ((double) bytes / 1000000000);
    }

    /**
     * Kb to MB
     * @param kb
     * @return
     */
    public static double toMBFKB(int kb) {
        return ((double) kb / 1000);
    }

    /**
     * KB to GB
     * @param kb
     * @return
     */
    public static double toGBFKB(int kb) {
        return ((double) kb / 1000000);
    }

    /**
     * MB to GB
     * @param mb
     * @return
     */
    public static double toGBFMB(int mb) {
        return ((double) mb / 1000);
    }
}
