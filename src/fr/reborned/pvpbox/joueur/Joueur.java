package fr.reborned.pvpbox.joueur;

import fr.reborned.pvpbox.Main;
import fr.reborned.pvpbox.fileconfig.Fichier;
import fr.reborned.pvpbox.fileconfig.ListType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;

public abstract class Joueur {

    private int sizeofarrayArmor=0;
    private Location L1;
    private Location L2;

    public Location getL1() {
        return L1;
    }

    public Location getL2() {
        return L2;
    }

    private String grade;
    private Statistiques statistiques;

    private Main main;
    public Joueur(Main main) {
        this.main=main;
    }



    public void playerjoining(Player unJoueur) {
        restoreHealthAndFood(unJoueur);

        unJoueur.getInventory().clear();
        unJoueur.updateInventory();

        ItemStack[] armor= ConfigArmor().toArray(new ItemStack[sizeofarrayArmor]);
        ItemStack[] items= ConfigItems().toArray(new ItemStack[0]);


        try {
            unJoueur.getInventory().setArmorContents(armor);
            unJoueur.getInventory().setExtraContents(items);
        }catch (StackOverflowError e){
            unJoueur.sendMessage(e.toString());
        }

        unJoueur.updateInventory();
    }
    private File fileConfig() {

        return new File(this.main.getDataFolder(),"configpl.yml");
    }

    private ArrayList<ItemStack> ConfigItems(){
        ArrayList<ItemStack> itemStacks =new ArrayList<>();
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig());
        final String key="players.";
        final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);

        //Todo recup item from config
        

        return itemStacks;
    }

    private ArrayList<ItemStack> ConfigArmor() {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(fileConfig());
        final String key="players.";
        final ConfigurationSection configurationSection = configuration.getConfigurationSection(key);
        ArrayList<ItemStack> itemStacks =new ArrayList<>();

        ListType[] listTypes = ListType.values();


        if (configurationSection == null){
            System.out.println("Section not exist");
        }else{
            for (String itemString : configurationSection.getKeys(false)){
                if (configurationSection.getString(itemString+".type") != null && !configurationSection.getString(itemString + ".type").equals(" ")) {
                    Material material = Material.getMaterial(configurationSection.getString(itemString+".type"));
                    ItemStack item = new ItemStack(material);
                    itemStacks.add(item);
                }
            }

            ItemStack unItem ;
            ItemMeta itemMeta;

            for (String itemSpec : configurationSection.getKeys(false)){
                unItem = ElementDeLaListe(itemStacks,itemSpec);
                if (unItem !=null){
                    if (configurationSection.getString(itemSpec+".name") != null && !configurationSection.getString(itemSpec + ".name").equals(" ")){
                        itemMeta = unItem.getItemMeta();
                        itemMeta.setDisplayName(configurationSection.getString(itemSpec+".name"));
                        unItem.setItemMeta(itemMeta);
                    }

                    if (configurationSection.getStringList(itemSpec+".enchantement") != null){
                        itemMeta = unItem.getItemMeta();
                        for (String enchantString : configurationSection.getStringList(itemSpec+".enchantement")){
                            String enchName = enchantString.split(":")[0];
                            Integer enchlvl = Integer.valueOf(enchantString.split(":")[1]);
                            itemMeta.addEnchant(Enchantment.getByName(enchName),enchlvl,true);
                        }
                        unItem.setItemMeta(itemMeta);
                    }
                    if (configurationSection.getStringList(itemSpec+".lore") != null){
                        itemMeta = unItem.getItemMeta();
                        itemMeta.setLore(configurationSection.getStringList(itemSpec+".lore"));
                        unItem.setItemMeta(itemMeta);
                    }
                    if (configurationSection.getBoolean(itemSpec+".unbreakable")){
                        itemMeta = unItem.getItemMeta();
                        itemMeta.setUnbreakable(true);
                        unItem.setItemMeta(itemMeta);
                    }
                }
            }
        }
        sizeofarrayArmor = itemStacks.size();
        return itemStacks;
    }

    private ItemStack ElementDeLaListe(ArrayList<ItemStack> itemStacks , String StringItem){
        ItemStack itemStack = null;

        for (ItemStack itemStack1: itemStacks) {
            if(itemStack1.getData().getItemType().toString().endsWith(StringItem.toUpperCase())){
                itemStack = itemStack1;
            }
        }
        return itemStack;
    }

    private void restoreHealthAndFood(Player unjoueur){
        unjoueur.setHealth(20);
        unjoueur.setFoodLevel(20);
    }

    public String getGrade() {
        return grade;
    }

    public Statistiques getStatistiques() {
        return statistiques;
    }

    public void setStatistiques(Statistiques statistiques) {
        this.statistiques = statistiques;
    }

    public void setL1(Location l1) {
        L1 = l1;
    }

    public void setL2(Location l2) {
        L2 = l2;
    }

    public void swordsetter(Player player){
        ItemStack item = new ItemStack(Material.WOOD_SWORD);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName("PVPSWORD_SET");
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL,1,true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        player.getInventory().setItemInMainHand(item);
        player.updateInventory();
    }
    public void saveInfile(String key, Location o){
        Fichier fichier = new Fichier(this.main.getDataFolder(),"configpl.yml");
        fichier.saveInFile(key,o);
    }
}
