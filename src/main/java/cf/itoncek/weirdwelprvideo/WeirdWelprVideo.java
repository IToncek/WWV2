/*
 * MIT License
 *
 * Copyright (c) 2021 IToncek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cf.itoncek.weirdwelprvideo;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

public final class WeirdWelprVideo extends JavaPlugin {
    public static WeirdWelprVideo plugin;
    public static final Logger log = Bukkit.getLogger();
    public static final BukkitRunnable run = new BukkitRunnable() {
        @Override
        public void run() {
            try {
                WeirdWelprVideo.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    public static final BukkitRunnable pinger = new BukkitRunnable() {
        @Override
        public void run() {
            LocalTime before = LocalTime.now();
            try {
                erqst("https://WWVresourcepackhelper.madebyitoncek.repl.co/");
            } finally {
                LocalTime now = LocalTime.now();
                long ms = ChronoUnit.MILLIS.between(before, now);
                for (Player p:Bukkit.getOnlinePlayers()){
                    p.sendActionBar(Component.text(ChatColor.GREEN + "Ping: " + ms));
                }
            }
        }
    };

    @Override
    public void onEnable() {
        plugin = this;
        Objects.requireNonNull(Bukkit.getPluginCommand("start")).setExecutor(new StartCommand());
    }
    public static void run() throws IOException {
        Random rand = new Random();
        int number = rand.nextInt(8);
        for (Player p : Bukkit.getOnlinePlayers()){
            p.setResourcePack("https://wwvresourcepackhelper.madebyitoncek.repl.co/download/" + number, erqst("https://wwvresourcepackhelper.madebyitoncek.repl.co/sha1/"+number),true);
        }
    }


    @Override
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()){
            p.kickPlayer(ChatColor.RED + "Server is closed, thanks for playing " + ChatColor.YELLOW + ChatColor.BOLD + "★");
        }
    }
    public static String erqst(String url) {
        try {
            URL conn = new URL(url);
            URLConnection urlcon = conn.openConnection();
            BufferedReader out = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
            return out.readLine();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
