import com.coremedia.cap.Cap;
import com.coremedia.cap.common.CapConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class ConnectionTestApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionTestApp.class);

    public static void main(String[] args){

        HashMap<String,String> argsValue = new HashMap<String, String>();
        for (String item: args) {
            String[] keyval = item.split("=",2);
            if(keyval.length != 2) continue;;
            String key = keyval[0];
            String value = keyval[1];
            argsValue.put(key, value);
        }

        String usage= "USAGE ([...] optional): java -jar ConnectionTest.jar username=<user> password=<pass>  [caas=\"http://localhost:40180/coremedia/ior\"] ";
        if(!(argsValue.containsKey("username") || argsValue.containsKey("password")))
        {
            LOGGER.error("Inavlid parameters");
            System.out.println(usage);
            return;

        }

        String username = ""+argsValue.get("username");
        String password = ""+argsValue.get("password");

        String serverUrl = argsValue.containsKey("caas") ? argsValue.get("caas") : "http://localhost:40180/coremedia/ior";

        CapConnection connection = null;
        try {
            LOGGER.info("Connecting into {} with username:{}", serverUrl, username);

            connection = Cap.connect(serverUrl, username, password);
            LOGGER.info("Connection OK");
        } catch (Exception e) {
            LOGGER.error("Connection failed for username:{} on {}", username,serverUrl);
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            if(connection != null){
                LOGGER.info("Closing connection");
                connection.close();
            }
        }
    }


}
