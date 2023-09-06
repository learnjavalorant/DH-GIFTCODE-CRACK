// Decompiled with: FernFlower
// Class Version: 8
package camchua.dhgiftcode;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener {
    public File configf;
    public File giftcodef;
    public FileConfiguration config;
    public FileConfiguration giftcode;
    public String giftcode_format = "XXXX-XXXX-XXXX";
    private static boolean premium = false;
    private static boolean run = false;
    private boolean o = false;
    private List<String> n = new ArrayList();
    public HashMap<String, String> edit = new HashMap();
    public int[] itemslot = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    private static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public void onEnable() {
        this.disableWarnASW();
        this.configf = new File(this.getDataFolder(), "config.yml");
        this.giftcodef = new File(this.getDataFolder(), "giftcode.yml");
        if (!this.configf.exists()) {
            this.configf.getParentFile().mkdirs();
            this.saveResource("config.yml", false);
        }

        if (!this.giftcodef.exists()) {
            this.giftcodef.getParentFile().mkdirs();
            this.saveResource("giftcode.yml", false);
        }

        this.config = new YamlConfiguration();
        this.giftcode = new YamlConfiguration();

        try {
            this.config.load(this.configf);
            this.giftcode.load(this.giftcodef);
        } catch (Exception var2) {
        }
        premium = true;
        run = true;
        Bukkit.getConsoleSender().sendMessage("§eDH-GIFCODE §aThe plugin has been activated and it is working on your server");
        Bukkit.getPluginManager().registerEvents(this, this);
        this.o = run;
        Bukkit.getConsoleSender().sendMessage("§e[CRACK] ĐÃ ĐƯỢC CRACK!");
        Bukkit.getConsoleSender().sendMessage("§e[CRACK] CẢM ƠN DI HOA STORE ĐÃ LÀM RA MỘT PLUGIN HAY!");
        Bukkit.getConsoleSender().sendMessage("§e[DI HOA STORE] Product Information");
        Bukkit.getConsoleSender().sendMessage("§f| Product: §6DH-GIFCODE");
        Bukkit.getConsoleSender().sendMessage("§f| Author: §aCamChua_VN");
        Bukkit.getConsoleSender().sendMessage("§f| Product Version: §a1.7.3");
        Bukkit.getConsoleSender().sendMessage("§f| Minecraft Verions: §a1.9 - 1.20");
        Bukkit.getConsoleSender().sendMessage("§f| Support: §aBukkit, Spigot, PaperMC, Purpur");
        Bukkit.getConsoleSender().sendMessage("§fOur Website: §ewww.dihoastore.net");
    }
    public void onDisable() {
        try {
            Bukkit.getConsoleSender().sendMessage("§eDH-GIDCODE §cThis plugin has been disabled");
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void load() {
        this.giftcode_format = this.config.getString("Settings.GiftcodeFormat");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
       if (args.length == 0) {
            if (!sender.hasPermission("dhgiftcode.help")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.NoPermissions")));
                return true;
            } else {
                for(String mess : this.config.getStringList("Message.Help")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', mess));
                }

                return true;
            }
        } else {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (!sender.hasPermission("dhgiftcode.create")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.NoPermissions")));
                        return true;
                    }

                    if (!(sender instanceof Player)) {
                        return true;
                    }

                    if (args.length == 1) {
                        return true;
                    }

                    if (args.length == 2) {
                        String code = args[1];
                        if (this.giftcode.contains(code)) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeExist")));
                            return true;
                        }

                        Player p = (Player)sender;
                        this.giftcode.set(code + ".Type", "normal");
                        this.giftcode.set(code + ".Limit", 1);
                        this.giftcode.set(code + ".RequireOnline", 24);

                        try {
                            this.giftcode.save(this.giftcodef);
                        } catch (Exception var25) {
                        }

                        this.edit.put(p.getName(), code + ":none");
                        this.openGiftcodeGUI(p, code);
                        return true;
                    }

                    if (args.length == 3 && args[2].equalsIgnoreCase("random")) {
                        String code = args[1];
                        if (this.giftcode.contains(code)) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeExist")));
                            return true;
                        }

                        Player p = (Player)sender;
                        this.giftcode.set(code + ".Type", "random");
                        this.giftcode.set(code + ".RequireOnline", 24);

                        try {
                            this.giftcode.save(this.giftcodef);
                        } catch (Exception var26) {
                        }

                        this.edit.put(p.getName(), code + ":none");
                        this.openGiftcodeGUI(p, code);
                        return true;
                    }

                    if (args.length == 3 && args[2].equalsIgnoreCase("limit")) {
                        String code = args[1];
                        if (this.giftcode.contains(code)) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeExist")));
                            return true;
                        }

                        Player p = (Player)sender;
                        this.giftcode.set(code + ".Type", "limit");
                        this.giftcode.set(code + ".RequireOnline", 24);

                        try {
                            this.giftcode.save(this.giftcodef);
                        } catch (Exception var27) {
                        }

                        this.edit.put(p.getName(), code + ":none");
                        this.openGiftcodeGUI(p, code);
                        return true;
                    }
                } else {
                    if (args[0].equalsIgnoreCase("help")) {
                        if (!sender.hasPermission("dhgiftcode.help")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.NoPermissions")));
                            return true;
                        }

                        for(String mess : this.config.getStringList("Message.Help")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', mess));
                        }

                        return true;
                    }

                    if (args[0].equalsIgnoreCase("edit")) {
                        if (!sender.hasPermission("dhgiftcode.edit")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.NoPermissions")));
                            return true;
                        }

                        if (!(sender instanceof Player)) {
                            return true;
                        }

                        if (args.length == 1) {
                            return true;
                        }

                        String code = args[1];
                        if (!this.giftcode.contains(code)) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeNotExist")));
                            return true;
                        }

                        Player p = (Player)sender;
                        this.edit.put(p.getName(), code + ":none");
                        this.openGiftcodeGUI(p, code);
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("reload")) {
                        if (!sender.hasPermission("dhgiftcode.reload")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.NoPermissions")));
                            return true;
                        }

                        sender.sendMessage("§aReloading...");

                        try {
                            this.config.load(this.configf);
                            this.giftcode.load(this.giftcodef);
                            sender.sendMessage("§aReload complete.");
                        } catch (Exception var28) {
                            var28.printStackTrace();
                            sender.sendMessage("§cReload failed. Check console.");
                        }

                        return true;
                    }

                    if (args[0].equalsIgnoreCase("list")) {
                        if (!sender.hasPermission("dhgiftcode.list")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.NoPermissions")));
                            return true;
                        }

                        String codelist = "";

                        for(String c : this.giftcode.getKeys(false)) {
                            codelist = codelist + " " + c;
                        }

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeList")).replace("<code>", codelist));
                        return true;
                    }

                    String code = args[0];
                    Player p = null;
                    if (!(sender instanceof Player)) {
                        if (args.length < 2) {
                            return true;
                        }

                        if (!Bukkit.getOfflinePlayer(args[1]).isOnline()) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.NotOnline")));
                            return true;
                        }

                        p = Bukkit.getPlayer(args[1]);
                    }

                    if (p == null) {
                        p = (Player)sender;
                    }

                    if (args.length >= 2) {
                        if (!p.hasPermission("giftcode.give")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.NoPermissions")));
                            return true;
                        }

                        if (!Bukkit.getOfflinePlayer(args[1]).isOnline()) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.NotOnline")));
                            return true;
                        }

                        p = Bukkit.getPlayer(args[1]);
                    }

                    int onlinetime = 0;

                    try {
                        onlinetime = p.getStatistic(Statistic.valueOf("PLAY_ONE_TICK"));
                    } catch (Exception var33) {
                        onlinetime = p.getStatistic(Statistic.valueOf("PLAY_ONE_MINUTE"));
                    }

                    if (onlinetime / 20 < this.giftcode.getInt(code + ".RequireOnline") * 3600) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.RequireOnline")).replace("<require>", String.valueOf(this.giftcode.getInt(code + ".RequireOnline"))));
                        return true;
                    }

                    if (this.edit.containsKey(p.getName())) {
                        this.edit.remove(p.getName());
                    }

                    boolean found = false;
                    boolean u = false;
                    int count = 0;

                    for(String c : this.giftcode.getKeys(false)) {
                        ++count;
                        if (this.giftcode.contains(c + ".Type")) {
                            if (c.equals(code) && this.giftcode.getString(code + ".Type").equals("normal")) {
                                String Expired = this.giftcode.getString(code + ".Expired");
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                                Date d2 = null;

                                try {
                                    d2 = sdf.parse(Expired);
                                } catch (ParseException var32) {
                                    var32.printStackTrace();
                                }

                                Date cur = new Date();
                                if (cur.before(d2)) {
                                    int limit = this.giftcode.getInt(code + ".Limit", 1);
                                    List<String> used = this.giftcode.getStringList(code + ".Used");
                                    int numberUsed = this.countElement(used, p.getName());
                                    if (numberUsed >= limit) {
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.AlreadyUseCode")));
                                    } else {
                                        String ip = p.getAddress().getAddress().getHostAddress();
                                        if (numberUsed == 0 && this.config.getBoolean("Settings.IP-Check") && this.giftcode.getStringList(code + ".IpUsed").contains(ip)) {
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeIpUsed")));
                                            return true;
                                        }

                                        used.add(p.getName());
                                        this.giftcode.set(code + ".Used", used);
                                        this.giftcode.set(code + ".Use", this.giftcode.getInt(code + ".Use") + 1);
                                        List<String> ipused = this.giftcode.getStringList(code + ".IpUsed");
                                        ipused.add(ip);
                                        this.giftcode.set(code + ".IpUsed", ipused);

                                        try {
                                            this.giftcode.save(this.giftcodef);
                                        } catch (Exception var31) {
                                        }

                                        if (this.giftcode.contains(code + ".Item")) {
                                            for(String item : this.giftcode.getConfigurationSection(code + ".Item").getKeys(false)) {
                                                p.getInventory().addItem(new ItemStack[]{this.giftcode.getItemStack(code + ".Item." + item)});
                                            }
                                        }

                                        if (this.giftcode.getDouble(code + ".Money") > 0.0D) {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + p.getName() + " " + this.giftcode.getDouble(code + ".Money"));
                                        }

                                        if (this.giftcode.getInt(code + ".Points") > 0) {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "points give " + p.getName() + " " + this.giftcode.getInt(code + ".Points"));
                                        }

                                        if (this.giftcode.getInt(code + ".Exp") > 0) {
                                            p.giveExp(this.giftcode.getInt(code + ".Exp"));
                                        }

                                        if (this.giftcode.contains(code + ".Command")) {
                                            for(String co : this.giftcode.getStringList(code + ".Command")) {
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), co.replace("<player>", p.getName()));
                                            }
                                        }

                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeReceived")));
                                        if (!this.config.getString("Message.PlayerCodeReceived", "").isEmpty()) {
                                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.PlayerCodeReceived")).replace("<player>", p.getName()));
                                        }

                                        u = true;
                                    }
                                }
                            } else if (this.giftcode.getString(c + ".Type").equals("random")) {
                                List<String> lcode = this.giftcode.getStringList(c + ".Giftcode");
                                if (!lcode.contains(code)) {
                                    if (count >= this.giftcode.getKeys(false).size()) {
                                        found = false;
                                    }
                                } else if (!this.giftcode.getStringList(code + ".Used").contains(p.getName())) {
                                    String ip = p.getAddress().getAddress().getHostAddress();
                                    if (this.config.getBoolean("Settings.IP-Check") && this.giftcode.getStringList(code + ".IpUsed").contains(ip)) {
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeIpUsed")));
                                        return true;
                                    }

                                    List<String> used = this.giftcode.getStringList(c + ".Used");
                                    int maxuse = this.config.getInt("Settings.RandomMaxUse");
                                    int use = 0;

                                    for(String pl : used) {
                                        if (pl.equalsIgnoreCase(p.getName())) {
                                            ++use;
                                        }
                                    }

                                    if (maxuse != 0 && use >= maxuse) {
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.AlreadyUseCodeRandom")));
                                        return true;
                                    }

                                    used.add(p.getName());
                                    this.giftcode.set(c + ".Used", used);
                                    lcode.remove(code);
                                    this.giftcode.set(c + ".Giftcode", lcode);
                                    List<String> ipused = this.giftcode.getStringList(code + ".IpUsed");
                                    ipused.add(ip);
                                    this.giftcode.set(code + ".IpUsed", ipused);

                                    try {
                                        this.giftcode.save(this.giftcodef);
                                    } catch (Exception var30) {
                                    }

                                    if (this.giftcode.contains(c + ".Item")) {
                                        String factor = this.giftcode.getString(c + ".Limit", "0");
                                        RandomValue value = new RandomValue(factor);
                                        if (value.value <= 0.0D) {
                                            for(String item : this.giftcode.getConfigurationSection(c + ".Item").getKeys(false)) {
                                                p.getInventory().addItem(new ItemStack[]{this.giftcode.getItemStack(c + ".Item." + item).clone()});
                                            }
                                        } else {
                                            List<ItemStack> itemList = new ArrayList();

                                            for(String item : this.giftcode.getConfigurationSection(c + ".Item").getKeys(false)) {
                                                itemList.add(this.giftcode.getItemStack(c + ".Item." + item).clone());
                                            }

                                            for(int i = 0; i < Math.min(itemList.size(), (int)value.value); ++i) {
                                                ItemStack item = (ItemStack)itemList.remove((new Random()).nextInt(itemList.size()));
                                                p.getInventory().addItem(new ItemStack[]{item});
                                            }
                                        }
                                    }

                                    if (this.giftcode.contains(c + ".Money")) {
                                        String factor = this.giftcode.getString(c + ".Money");
                                        RandomValue value = new RandomValue(factor);
                                        if (value.value > 0.0D) {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + p.getName() + " " + value.value);
                                        }
                                    }

                                    if (this.giftcode.contains(c + ".Points")) {
                                        String factor = this.giftcode.getString(c + ".Points");
                                        RandomValue value = new RandomValue(factor);
                                        if (value.value > 0.0D) {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "points give " + p.getName() + " " + (int)value.value);
                                        }
                                    }

                                    if (this.giftcode.getInt(c + ".Exp") > 0) {
                                        String factor = this.giftcode.getString(c + ".Exp");
                                        RandomValue value = new RandomValue(factor);
                                        if (value.value > 0.0D) {
                                            p.giveExp((int)value.value);
                                        }
                                    }

                                    if (this.giftcode.contains(c + ".Command")) {
                                        for(String co : this.giftcode.getStringList(c + ".Command")) {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), co.replace("<player>", p.getName()));
                                        }
                                    }

                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeReceived")));
                                    if (!this.config.getString("Message.PlayerCodeReceived", "").isEmpty()) {
                                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.PlayerCodeReceived")).replace("<player>", p.getName()));
                                    }

                                    u = true;
                                }
                            } else if (c.equals(code) && this.giftcode.getString(c + ".Type").equals("limit")) {
                                if (!this.giftcode.getStringList(code + ".Used").contains(p.getName())) {
                                    if (this.giftcode.getInt(code + ".Use") >= this.giftcode.getInt(code + ".MaxUse")) {
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeMaxUse")));
                                        return true;
                                    }

                                    String ip = p.getAddress().getAddress().getHostAddress();
                                    if (this.config.getBoolean("Settings.IP-Check") && this.giftcode.getStringList(code + ".IpUsed").contains(ip)) {
                                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeIpUsed")));
                                        return true;
                                    }

                                    int use = this.giftcode.getInt(code + ".Use", 0);
                                    ++use;
                                    this.giftcode.set(code + ".Use", use);
                                    List<String> used = this.giftcode.getStringList(code + ".Used");
                                    used.add(p.getName());
                                    this.giftcode.set(code + ".Used", used);
                                    List<String> ipused = this.giftcode.getStringList(code + ".IpUsed");
                                    ipused.add(ip);
                                    this.giftcode.set(code + ".IpUsed", ipused);

                                    try {
                                        this.giftcode.save(this.giftcodef);
                                    } catch (Exception var29) {
                                    }

                                    if (this.giftcode.contains(c + ".Item")) {
                                        for(String item : this.giftcode.getConfigurationSection(c + ".Item").getKeys(false)) {
                                            p.getInventory().addItem(new ItemStack[]{this.giftcode.getItemStack(c + ".Item." + item)});
                                        }
                                    }

                                    if (this.giftcode.getDouble(c + ".Money") > 0.0D) {
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + p.getName() + " " + this.giftcode.getDouble(c + ".Money"));
                                    }

                                    if (this.giftcode.getInt(c + ".Points") > 0) {
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "points give " + p.getName() + " " + this.giftcode.getInt(c + ".Points"));
                                    }

                                    if (this.giftcode.getInt(c + ".Exp") > 0) {
                                        p.giveExp(this.giftcode.getInt(c + ".Exp"));
                                    }

                                    if (this.giftcode.contains(c + ".Command")) {
                                        for(String co : this.giftcode.getStringList(c + ".Command")) {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), co.replace("<player>", p.getName()));
                                        }
                                    }

                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeReceived")));
                                    if (!this.config.getString("Message.PlayerCodeReceived", "").isEmpty()) {
                                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.PlayerCodeReceived")).replace("<player>", p.getName()));
                                    }

                                    u = true;
                                } else {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.AlreadyUseCode")));
                                }
                            }

                            if (!found && !u) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.CodeNotFound")));
                            }
                        }
                    }
                }
            }

            return false;
        }
    }

    public void openGiftcodeGUI(final Player p, String giftcode) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Gui.Title")));
        ItemStack blank = ItemBuilder.create(Material.matchMaterial("STAINED_GLASS_PANE"), 1, 15, "&r");
        ItemStack cancel = ItemBuilder.create(Material.matchMaterial("STAINED_GLASS_PANE"), 1, 14, "&cXóa");
        ItemStack done = ItemBuilder.create(Material.matchMaterial("STAINED_GLASS_PANE"), 1, 5, "&aLưu");
        RandomValue m = new RandomValue("0");
        RandomValue ps = new RandomValue("0");
        RandomValue e = new RandomValue("0");
        RandomValue l = new RandomValue("0");
        int ro = 0;
        if (this.giftcode.contains(giftcode)) {
            m = new RandomValue(this.giftcode.getString(giftcode + ".Money", "0"));
            ps = new RandomValue(this.giftcode.getString(giftcode + ".Points", "0"));
            e = new RandomValue(this.giftcode.getString(giftcode + ".Exp", "0"));
            l = this.giftcode.getString(giftcode + ".Type").equalsIgnoreCase("limit") ? new RandomValue(this.giftcode.getString(giftcode + ".MaxUse", "0")) : new RandomValue(this.giftcode.getString(giftcode + ".Limit", "0"));
            ro = this.giftcode.getInt(giftcode + ".RequireOnline", 24);
        }

        ItemStack money = ItemBuilder.create(Material.matchMaterial(this.config.getString("Settings.Gui.Item.Money.ID")), 1, this.config.getInt("Settings.Gui.Item.Money.Data"), ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Gui.Item.Money.Name")).replace("<money>", m + ""), this.config.getStringList("Settings.Gui.Item.Money.Lore"));
        ItemStack points = ItemBuilder.create(Material.matchMaterial(this.config.getString("Settings.Gui.Item.Points.ID")), 1, this.config.getInt("Settings.Gui.Item.Points.Data"), ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Gui.Item.Points.Name")).replace("<points>", ps + ""), this.config.getStringList("Settings.Gui.Item.Points.Lore"));
        ItemStack exp = ItemBuilder.create(Material.matchMaterial(this.config.getString("Settings.Gui.Item.Exp.ID")), 1, this.config.getInt("Settings.Gui.Item.Exp.Data"), ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Gui.Item.Exp.Name")).replace("<exp>", e + ""), this.config.getStringList("Settings.Gui.Item.Exp.Lore"));
        ItemStack requireOnline = ItemBuilder.create(Material.matchMaterial(this.config.getString("Settings.Gui.Item.RequireOnline.ID")), 1, this.config.getInt("Settings.Gui.Item.RequireOnline.Data"), ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Gui.Item.RequireOnline.Name")).replace("<online>", ro + ""), this.config.getStringList("Settings.Gui.Item.RequireOnline.Lore"));

        for(int i = 0; i <= 9; ++i) {
            inv.setItem(i, blank);
        }

        inv.setItem(17, blank);
        inv.setItem(18, blank);
        inv.setItem(26, blank);
        inv.setItem(27, blank);
        inv.setItem(35, blank);
        inv.setItem(36, blank);
        inv.setItem(44, blank);
        inv.setItem(46, blank);
        inv.setItem(48, blank);
        inv.setItem(50, blank);
        inv.setItem(52, blank);
        inv.setItem(47, money);
        inv.setItem(49, points);
        inv.setItem(51, exp);
        inv.setItem(46, requireOnline);
        inv.setItem(45, cancel);
        inv.setItem(53, done);
        if (!this.giftcode.getString(giftcode + ".Type").equalsIgnoreCase("normal") && !this.giftcode.getString(giftcode + ".Type").equalsIgnoreCase("limit")) {
            if (this.giftcode.getString(giftcode + ".Type").equalsIgnoreCase("random")) {
                ItemStack random = ItemBuilder.create(Material.matchMaterial(this.config.getString("Settings.Gui.Item.Random.ID")), 1, this.config.getInt("Settings.Gui.Item.Random.Data"), ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Gui.Item.Random.Name")).replace("<random>", l + ""), this.config.getStringList("Settings.Gui.Item.Random.Lore"));
                inv.setItem(52, random);
            }
        } else {
            ItemStack limit = ItemBuilder.create(Material.matchMaterial(this.config.getString("Settings.Gui.Item.Limit.ID")), 1, this.config.getInt("Settings.Gui.Item.Limit.Data"), ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Gui.Item.Limit.Name")).replace("<limit>", l + ""), this.config.getStringList("Settings.Gui.Item.Limit.Lore"));
            inv.setItem(52, limit);
        }

        if (this.giftcode.contains(giftcode)) {
            try {
                for(String a : this.giftcode.getConfigurationSection(giftcode + ".Item").getKeys(false)) {
                    inv.addItem(new ItemStack[]{this.giftcode.getItemStack(giftcode + ".Item." + a)});
                }
            } catch (Exception var18) {
            }
        }

        (new BukkitRunnable() {
            public void run() {
                p.openInventory(inv);
            }
        }).runTaskLater(this, 1L);
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (this.edit.containsKey(p.getName())) {
            String ed = (String)this.edit.get(p.getName());
            String code = ed.split(":")[0];
            if (e.getMessage().toLowerCase().equals("huy")) {
                e.setCancelled(true);
                this.edit.put(p.getName(), code + ":none");
                this.openGiftcodeGUI(p, code);
                return;
            }

            e.setCancelled(true);
            String type = ed.split(":")[1];
            String mess = ChatColor.stripColor(e.getMessage());
            String codeType = this.giftcode.getString(code + ".Type");
            if (type.equals("money")) {
                try {
                    RandomValue value = new RandomValue(mess);
                    this.giftcode.set(code + ".Money", codeType.equalsIgnoreCase("random") ? value.toString() : value.value);
                    this.giftcode.save(this.giftcodef);
                    this.openGiftcodeGUI(p, code);
                } catch (Exception var21) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.FormatError")));
                }
            } else if (type.equals("points")) {
                try {
                    RandomValue value = new RandomValue(mess);
                    this.giftcode.set(code + ".Points", codeType.equalsIgnoreCase("random") ? value.toString() : value.value);
                    this.giftcode.save(this.giftcodef);
                    this.openGiftcodeGUI(p, code);
                } catch (Exception var20) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.FormatError")));
                }
            } else if (type.equals("exp")) {
                try {
                    RandomValue value = new RandomValue(mess);
                    this.giftcode.set(code + ".Exp", codeType.equalsIgnoreCase("random") ? value.toString() : value.value);
                    this.giftcode.save(this.giftcodef);
                    this.openGiftcodeGUI(p, code);
                } catch (Exception var19) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.FormatError")));
                }
            } else if (type.equals("limit")) {
                try {
                    RandomValue value = new RandomValue(mess);
                    this.giftcode.set(code + ".Limit", codeType.equalsIgnoreCase("random") ? value.toString() : value.value);
                    this.giftcode.save(this.giftcodef);
                    this.openGiftcodeGUI(p, code);
                } catch (Exception var18) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.FormatError")));
                }
            } else if (type.equals("online")) {
                try {
                    int online = Integer.valueOf(mess);
                    this.giftcode.set(code + ".RequireOnline", online);
                    this.giftcode.save(this.giftcodef);
                    this.openGiftcodeGUI(p, code);
                } catch (Exception var17) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.FormatError")));
                }
            } else if (type.equals("done")) {
                try {
                    int value = Integer.valueOf(mess);
                    if (this.giftcode.getString(code + ".Type").equalsIgnoreCase("normal")) {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                        Calendar c = Calendar.getInstance();
                        c.add(6, value);
                        Date result = c.getTime();
                        this.giftcode.set(code + ".Expired", sdf.format(result));
                        List<String> comm = new ArrayList();
                        comm.add("msg <player> Bạn đã nhập giftcode thành công");
                        this.giftcode.set(code + ".Command", comm);
                        this.giftcode.save(this.giftcodef);

                        try {
                            this.giftcode.save(this.giftcodef);
                        } catch (Exception var15) {
                        }

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.SaveCode")));
                        this.edit.remove(p.getName());
                        return;
                    }

                    if (this.giftcode.getString(code + ".Type").equalsIgnoreCase("limit")) {
                        this.giftcode.set(code + ".MaxUse", value);
                        List<String> comm = new ArrayList();
                        comm.add("msg <player> Bạn đã nhập giftcode thành công");
                        this.giftcode.set(code + ".Command", comm);
                        this.giftcode.save(this.giftcodef);

                        try {
                            this.giftcode.save(this.giftcodef);
                        } catch (Exception var14) {
                        }

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.SaveCode")));
                        this.edit.remove(p.getName());
                        return;
                    }
                } catch (Exception var16) {
                    var16.printStackTrace();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.FormatError")));
                }
            }

            this.edit.put(p.getName(), code + ":none");
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Gui.Title")))) {
            if (e.getClickedInventory() != p.getOpenInventory().getTopInventory()) {
                return;
            }

            String ed = (String)this.edit.get(p.getName());
            String code = ed.split(":")[0];
            if (e.getSlot() == 0 || e.getSlot() == 1 || e.getSlot() == 2 || e.getSlot() == 3 || e.getSlot() == 4 || e.getSlot() == 5 || e.getSlot() == 6 || e.getSlot() == 7 || e.getSlot() == 8 || e.getSlot() == 9 || e.getSlot() == 17 || e.getSlot() == 18 || e.getSlot() == 26 || e.getSlot() == 27 || e.getSlot() == 35 || e.getSlot() == 36 || e.getSlot() == 44 || e.getSlot() == 48 || e.getSlot() == 50) {
                e.setCancelled(true);
            }

            if (e.getSlot() == 45) {
                e.setCancelled(true);
                this.giftcode.set(code, (Object)null);

                try {
                    this.giftcode.save(this.giftcodef);
                } catch (Exception var15) {
                }

                this.edit.put(p.getName(), code + ":cancel");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.DeleteCode")));
                p.closeInventory();
            }

            if (e.getSlot() == 46) {
                e.setCancelled(true);
                this.edit.put(p.getName(), code + ":online");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.TypingValue")));
                p.closeInventory();
            }

            if (e.getSlot() == 52) {
                e.setCancelled(true);
                if (this.giftcode.getString(code + ".Type").equalsIgnoreCase("normal") || this.giftcode.getString(code + ".Type").equalsIgnoreCase("random")) {
                    this.edit.put(p.getName(), code + ":limit");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.TypingValue")));
                    p.closeInventory();
                }
            }

            if (e.getSlot() == 53) {
                e.setCancelled(true);
                this.giftcode.set(code + ".Item", (Object)null);

                for(int s : this.itemslot) {
                    this.giftcode.set(code + ".Item." + s, e.getInventory().getItem(s));
                }

                try {
                    this.giftcode.save(this.giftcodef);
                } catch (Exception var14) {
                }

                if (this.giftcode.getString(code + ".Type").equalsIgnoreCase("normal")) {
                    if (!this.giftcode.contains(code + ".Expired")) {
                        this.edit.put(p.getName(), code + ":done");
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.TypingCodeExpired")));
                        p.closeInventory();
                    } else {
                        this.edit.put(p.getName(), code + ":save");
                        p.closeInventory();
                    }
                } else if (!this.giftcode.getString(code + ".Type").equalsIgnoreCase("random")) {
                    if (this.giftcode.getString(code + ".Type").equalsIgnoreCase("limit")) {
                        if (!this.giftcode.contains(code + ".MaxUse")) {
                            this.edit.put(p.getName(), code + ":done");
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.TypingCodeMaxUse")));
                            p.closeInventory();
                        } else {
                            this.edit.put(p.getName(), code + ":save");
                            p.closeInventory();
                        }
                    }
                } else {
                    if (this.giftcode.get(code + ".Giftcode") == null) {
                        List<String> codelist = new ArrayList();

                        for(int i1 = 1; i1 <= this.config.getInt("Settings.CodeRandom"); ++i1) {
                            String format = this.config.getString("Settings.GiftcodeFormat");
                            String random = "";
                            String[] ran = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M"};
                            int r = 0;

                            for(int i = 0; i < format.split("-").length; ++i) {
                                if (i > 0 && i < format.split("-").length) {
                                    random = random + "-";
                                }

                                for(int i2 = 0; i2 < format.split("-")[i].length(); ++i2) {
                                    r = (new Random()).nextInt(ran.length);
                                    random = random + ran[r];
                                }
                            }

                            codelist.add(random);
                        }

                        this.giftcode.set(code + ".Giftcode", codelist);
                        List<String> comm = new ArrayList();
                        comm.add("msg <player> Bạn đã nhập giftcode thành công");
                        this.giftcode.set(code + ".Command", comm);

                        try {
                            this.giftcode.save(this.giftcodef);
                        } catch (Exception var13) {
                        }
                    }

                    this.edit.put(p.getName(), code + ":save");
                    p.closeInventory();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.SaveCode")));
                }
            }

            if (e.getSlot() == 47) {
                e.setCancelled(true);
                this.edit.put(p.getName(), code + ":money");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.TypingValue")));
                p.closeInventory();
            }

            if (e.getSlot() == 49) {
                e.setCancelled(true);
                this.edit.put(p.getName(), code + ":points");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.TypingValue")));
                p.closeInventory();
            }

            if (e.getSlot() == 51) {
                e.setCancelled(true);
                this.edit.put(p.getName(), code + ":exp");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.getString("Message.TypingValue")));
                p.closeInventory();
            }
        }

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        final Player p = (Player)e.getPlayer();
        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', this.config.getString("Settings.Gui.Title"))) && this.edit.containsKey(p.getName())) {
            String ed = (String)this.edit.get(p.getName());
            final String code = ed.split(":")[0];
            String type = ed.split(":")[1];
            if (type.equals("none") || type.equals("cancel") || type.equals("done")) {
                if (type.equals("none")) {
                    try {
                        (new BukkitRunnable() {
                            public void run() {
                                Main.this.openGiftcodeGUI(p, code);
                            }
                        }).runTaskLater(this, 2L);
                    } catch (Exception var11) {
                        this.giftcode.set(code, (Object)null);

                        try {
                            this.giftcode.save(this.giftcodef);
                        } catch (Exception var10) {
                        }
                    }

                    return;
                }

                if (type.equals("done")) {
                    return;
                }

                if (this.edit.containsKey(p.getName())) {
                    this.edit.remove(p.getName());
                }

                return;
            }

            if (type.equals("save")) {
                this.edit.remove(p.getName());
            }

            this.giftcode.set(code + ".Item", (Object)null);

            for(int s : this.itemslot) {
                if (e.getInventory().getItem(s) != null) {
                    this.giftcode.set(code + ".Item." + s, e.getInventory().getItem(s));
                }
            }

            try {
                this.giftcode.save(this.giftcodef);
            } catch (Exception var12) {
            }
        }

    }

    private int countElement(List<String> list, String element) {
        int count = 0;

        for(String elem : list) {
            if (elem.equals(element)) {
                ++count;
            }
        }

        return count;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().replaceFirst("/", "");
        StringBuilder sb = new StringBuilder();

        for(int i = 1; i < cmd.split(" ").length; ++i) {
            sb.append(cmd.split(" ")[i]).append(" ");
        }

        for(String c : this.config.getStringList("Settings.CommandAliases")) {
            if (cmd.split(" ")[0].toLowerCase().equals(c.toLowerCase())) {
                e.setMessage("/giftcode " + sb.toString());
                break;
            }
        }

    }

    public void disableWarnASW() {
        File aswf = new File(this.getDataFolder().getParentFile(), "\\AutoSaveWorld\\config.yml");
        if (aswf.exists()) {
            FileConfiguration asw = new YamlConfiguration();

            try {
                asw.load(aswf);
            } catch (Exception var5) {
            }

            if (asw.getBoolean("networkwatcher.mainthreadnetaccess.warn")) {
                asw.set("networkwatcher.mainthreadnetaccess.warn", false);

                try {
                    asw.save(aswf);
                } catch (Exception var4) {
                }

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "asw reload");
            }
        }

    }
}
