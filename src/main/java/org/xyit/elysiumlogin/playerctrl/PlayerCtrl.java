package org.xyit.elysiumlogin.playerctrl;


import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;
import org.xyit.elysiumlogin.command.GetCommand;
import org.xyit.elysiumlogin.var.GlobalVar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static org.bukkit.Bukkit.*;
import static org.xyit.elysiumlogin.message.MessageOperate.Message_WorldNotFound;
import static org.xyit.elysiumlogin.message.MessageSender.console;
import static org.xyit.elysiumlogin.playerctrl.CtrlPlayerList.isPlayer;
import static org.xyit.elysiumlogin.var.GlobalVar.AllowChat;
import static org.xyit.elysiumlogin.var.GlobalVar.plugin;
import static org.xyit.elysiumlogin.var.OpenMethod.playerIsNotMinecraftPlayer;
import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueDouble;
import static org.xyit.elysiumlogin.yaml.YamlOperate.YamlValueString;

/**
 * 玩家控制：拒绝所有操作
 */
public class PlayerCtrl implements Listener {

    String CanMove = YamlValueString("plugins\\ElysiumLogin","setting.yml","AllowMove");
    String Return = YamlValueString("plugins\\ElysiumLogin","setting.yml","Return");
    String Back = YamlValueString("plugins\\ElysiumLogin","setting.yml","Back");
    String StringWorld = YamlValueString("plugins\\ElysiumLogin","setting.yml","Login.Location.World");

    //禁止玩家移动
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (isPlayer(player.getName())) {
            if (Objects.equals(CanMove, "no")){
                event.setCancelled(true);
                //判断是否在出生点之外，在出生点之外则传送
                try {
                    double X = YamlValueDouble("plugins\\ElysiumLogin","setting.yml","Login.Location.X");
                    double Y = YamlValueDouble("plugins\\ElysiumLogin","setting.yml","Login.Location.Y");
                    double Z = YamlValueDouble("plugins\\ElysiumLogin","setting.yml","Login.Location.Z");
                    World world = getWorld(StringWorld);
                    Location PlayerLocation = player.getLocation();
                    double PlayerX = PlayerLocation.getX();
                    double PlayerY = PlayerLocation.getY();
                    double PlayerZ = PlayerLocation.getZ();
                    if (PlayerX > X || PlayerX < X || PlayerY > Y || PlayerY < Y || PlayerZ > Z || PlayerZ < Z) {
                        Location location = new Location(world,X,Y,Z);
                        player.teleport(location);
                        return;
                    }
                    // 暂停当前线程
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    // 如果线程在睡眠期间被中断，将捕获InterruptedException
                }
            }
        }
    }

    //玩家加入时返回固定地点
    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!Objects.equals(Return, "no")) {
            double X = YamlValueDouble("plugins\\ElysiumLogin","setting.yml","Login.Location.X");
            double Y = YamlValueDouble("plugins\\ElysiumLogin","setting.yml","Login.Location.Y");
            double Z = YamlValueDouble("plugins\\ElysiumLogin","setting.yml","Login.Location.Z");
            World world = getWorld(StringWorld);
            Location PlayerLocation = player.getLocation();
            double PlayerX = PlayerLocation.getX();
            double PlayerY = PlayerLocation.getY();
            double PlayerZ = PlayerLocation.getZ();
            if (world == null) {
                Message_WorldNotFound();
                return;
            }
            Location location = new Location(world,X,Y,Z);
            player.teleport(location);
        }
    }

    //取消玩家破坏动作
    @EventHandler
    public void onPlayerBreakBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (isPlayer(player.getName())) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);
            }
        }
    }

    //禁止玩家攻击
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        String player = damager.getName();
        if (isPlayer(player)) {
            if (damager instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

    //禁止受到伤害
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        // 确保事件是由玩家触发的
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String name = player.getName();
            if (isPlayer(name)) {
                event.setCancelled(true);
            }
        }
    }

    //禁止玩家打开容器
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity whoClicked = event.getWhoClicked();
        if (isPlayer(whoClicked.getName())) {
            event.setCancelled(true);
        }
    }

    //禁止丢弃物品
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (isPlayer(player.getName())) {
            event.setCancelled(true);
        }
    }

    //禁止拾取物品
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event){
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (playerIsNotMinecraftPlayer(player)) return;
        if (isPlayer(player.getName())) {
            event.setCancelled(true);
        }
    }

    //禁止玩家执行命令
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (playerIsNotMinecraftPlayer(event.getPlayer())) return;
        Player player = event.getPlayer();
        String name = player.getName();
        if (isPlayer(name)) {
            String fullCommand = event.getMessage().substring(1);
            String[] parts = fullCommand.split(" ");
            String command = parts[0];
            String filePath = ("plugins/ElysiumLogin/" + YamlValueString("plugins/ElysiumLogin", "setting.yml", "AllowCommands"));
            boolean isAllow = false;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(command)) {
                        isAllow = true;
                        break;
                    } else {
                        isAllow = false;
                    }
                }
                if (!isAllow) {
                    event.setCancelled(true);
                }
            } catch (IOException e) {
                player.sendMessage("§cServer Error");
            }
        }
    }

    //禁止玩家聊天
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (playerIsNotMinecraftPlayer(event.getPlayer())) return;
        Player player = event.getPlayer();
        if (isPlayer(player.getName())) {
            if (Objects.equals(AllowChat, "no")) {
                event.setCancelled(true);
            } else {
                return;
            }
        }
    }

    //禁止玩家防止方块
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (playerIsNotMinecraftPlayer(event.getPlayer())) return;
        Player player = event.getPlayer();
        if (isPlayer(player.getName())) {
            event.setCancelled(true);
        }
    }


    //禁止玩家使用物品
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (playerIsNotMinecraftPlayer(event.getPlayer())) return;
        if (isPlayer(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

    //禁止玩家与实体交互
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (playerIsNotMinecraftPlayer(event.getPlayer())) return;
        if (isPlayer(event.getPlayer().getName())) {
            event.setCancelled(true);
        }
    }

}
