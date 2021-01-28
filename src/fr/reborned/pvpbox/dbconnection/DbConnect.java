package fr.reborned.pvpbox.dbconnection;

import fr.reborned.pvpbox.fileconfig.FileManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.sql.*;

public class DbConnect {

    /**
     * URL de connection
     */
    private static String url = "jdbc:mysql://192.168.1.2:3306/PluginPvpBox";

    /**
     * Nom du user
     */
    private static String user = "clement2";

    /**
     * Mot de passe du user
     */
    private static String passwd = "rootme";

    /**
     * Objet Connection
     */
    private static Connection connect = null;

    /**
     * Méthode qui va nous retourner notre instance
     * et la creer si elle n'existe pas...
     * @return un objet connection
     */
    public static Connection getInstance(){
        if(connect == null){
            try {
                connect = DriverManager.getConnection(url, user, passwd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connect;
    }

    /**
     * Méthode qui met fin à la connexion
     */
    public void close() {
        if (connect != null) {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setInfo(Plugin plugin){
        final FileManager fileManager = new FileManager(plugin.getDataFolder(),"/configdb.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(fileManager);

        try {
            fileManager.getParentFile().mkdirs();
            if (fileManager.createNewFile()){
                if (fichierVide(fileManager.getAbsoluteFile())){
                    fileManager.remplissageFileDb(fileManager);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean fichierVide(File file) {
        boolean ret = false;
        if (file != null) {
            if (file.length() == 0) {
                ret = true;
            }
        }
        return ret;
    }

    public boolean creationDB(Plugin plugin){
        boolean ret = false;
        File file = new File(plugin.getDataFolder(),"/PluginPvpBox.sql");
        Connection connection = getInstance();
        ScriptRunner scriptRunner = new ScriptRunner(connection);

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT `schema_name` from INFORMATION_SCHEMA.SCHEMATA WHERE `SCHEMA_NAME` LIKE '%Pl%'");
            if (statement.executeQuery().first()){
                System.out.println("test");
                ret =true;
            }else
            {
                Reader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                scriptRunner.runScript(reader);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void setUrl(String url) {
        DbConnect.url = url;
    }

    public void setUser(String user) {
        DbConnect.user = user;
    }

    public void setPasswd(String passwd) {
        DbConnect.passwd = passwd;
    }

    public void loadConfigDB(Plugin plugin){
        String key ="Connection";
        File file = new File(plugin.getDataFolder(),"configdb.yml");
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection confS = conf.getConfigurationSection(key);
        setUser(confS.getString("user"));
        setPasswd(confS.getString("passwd"));
        setUrl(confS.getString("url"));
    }

}
