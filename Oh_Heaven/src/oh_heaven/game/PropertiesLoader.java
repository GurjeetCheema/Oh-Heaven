package oh_heaven.game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesLoader {
    public static final String DEFAULT_DIRECTORY_PATH = "properties/";
    public static Properties loadPropertiesFile(String propertiesFile) {

        if (propertiesFile == null) {
            try (InputStream input = new FileInputStream( DEFAULT_DIRECTORY_PATH + "runmode.properties")) {

                Properties prop = new Properties();

                // load a properties file
                prop.load(input);

                propertiesFile = DEFAULT_DIRECTORY_PATH + prop.getProperty("current_mode");
                System.out.println(propertiesFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try (InputStream input = new FileInputStream(propertiesFile)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static int loadNumberofCards(Properties properties) {
        return Integer.parseInt(properties.getProperty("nbStartCards"));
    }

    public static int loadNumberofRound(Properties properties){
        return Integer.parseInt(properties.getProperty("rounds"));
    }
    public static List<String> loadPlayerTypes(Properties properties) {
        List<String> PlayerType = new ArrayList<String>();

        int numPlayers = 4;

        for(int i = 0; i < numPlayers; i++){

            String type = properties.getProperty("players." + i);
            PlayerType.add(type);
        }

        return PlayerType;
    }

    public static int loadSeed(Properties properties){
        int seed = Integer.parseInt(properties.getProperty("seed"));
        return seed;
    }
    public static Boolean loadEnforceRules(Properties properties){
        String EnforceRules =properties.getProperty("enforceRules");
        if(EnforceRules.equals("true")){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
