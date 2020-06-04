import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * This program will prompt the user for a host address.
 * Unresolvable address will result in an error.
 * Leaving an empty address field will result in local hos address as identity.
 * Output will result in the description of local host. Followed by the address in binary format,
 * binary dotted-quad format and decimal dotted-quad format, respectively.
 *
 * @author Luis Cortez (lac0084@auburn.edu)
 * @version 060420201137
 */
public class MyInetAddressExample {

    public static void main(String[] args) {
        // Scanner object for host input
        Scanner hostScanner = new Scanner(System.in);
        System.out.print("Enter the host name (empty field will default to local host): ");
        String hostName = hostScanner.nextLine();

        // Get name and IP address of the local host
        try {
            InetAddress address = InetAddress.getByName(hostName);
            System.out.println("\t\tLocal Host:\t" + InetAddress.getLocalHost());
            System.out.println("-------------------- Addresses of Requested Host --------------------");
            System.out.println("\t     Binary Format:\t" + IpConverter.toBinary(address, false));
            String formattedBinaryQuadResult = IpConverter.toBinary(address, true);
            System.out.println(" Binary Dotted-Quad Format:\t"
                    + formattedBinaryQuadResult.substring(0, formattedBinaryQuadResult.length() - 1));
            System.out.println("Decimal Dotted-Quad Format:\t" + address.getHostAddress() + "\n\n\n\n\n");
        } catch (UnknownHostException e) {
            System.out.println("Unable to determine this host's address: (error: " + e + " )");
        }

        for (String arg : args) {
            // Get name(s)/address(es) of hosts given on command-line
            try {
                InetAddress[] addressList = InetAddress.getAllByName(arg);
                System.out.println(arg + ":");
                // Print the first name.  Assume array contains at least one entry.
                System.out.println("\t" + addressList[0].getHostName());
                for (InetAddress inetAddress : addressList) System.out.println("\t" + inetAddress.getHostAddress());
            } catch (UnknownHostException e) {
                System.out.println("Unable to find address for " + arg);
            }
        }
    }
}

/**
 * Helper class will convert Ip address into binary representation.
 */
class IpConverter {
    /**
     * Will return two different values based on bool.
     *  **Note** Dotted quad return value must be formatted to EXCLUDE last character.
     * @param ipIn - address to interpret
     * @param dottedQuad - true == plain binary value // false == dotted-quad binary value
     * @return - string representing value
     */
    public static String toBinary(InetAddress ipIn, boolean dottedQuad) {
        // Resolve host address as string ie. local host == "127.0.0.1"
        String ipString = ipIn.getHostAddress();

        // Split string into an array ie. local host == [[127], [0], [0], [1]]
        String[] ipStringArray = ipString.split("\\.");

        //String to hold result
        StringBuilder result = new StringBuilder();

        // For every string in ipStringArray
        for (String tmp : ipStringArray) {
            // create local int, convert string segment
            int segment = Integer.parseInt(tmp);
            // Convert parsed int to binary string
            String stringSegment = Integer.toBinaryString(segment);
            // Append to result string
            if (!dottedQuad) {
                result.append(stringSegment);
            } else {
                result.append(stringSegment).append(".");
            }
        }
        return result.toString();
    }
}
