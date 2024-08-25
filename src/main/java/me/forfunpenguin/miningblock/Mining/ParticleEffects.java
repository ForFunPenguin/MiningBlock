package me.forfunpenguin.miningblock.Mining;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;


public class ParticleEffects implements Listener {

    @EventHandler
    public void onPlayerClickItem(PlayerInteractEvent event) {
        //必須右鍵點擊
        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            return;
        }
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType().toString().equals("STICK")) {
            Location loc1 = player.getEyeLocation();
            particleForward(player, Particle.DRIP_LAVA, loc1, 10, 50);
            //particleCircle(player, Particle.REDSTONE, loc1, 10, 6);
            createCircle(player, 0.75, 2);
            createCircle(player, 1.5, 4);
            createCircle(player, 2.25, 6);
        }
    }

    public void particlePointToPoint(Player player, Particle particle, Location loc1, Location loc2, int density) {
        Vector vector = loc2.toVector().subtract(loc1.toVector()); //算出兩距離向量
        vector.multiply((double) 1 / density); //算出兩粒子之間的距離
        Location target = loc1;
        for (int x=0; x < density; x++) {
            //player.spawnParticle(particle, target, 1);
            target.getWorld().spawnParticle(particle, target, 1);
            target.add(vector);
        }
    }

    public void particleForward(Player player, Particle particle, Location loc1, int distance, int density) {
        Vector vectorLoc1 = loc1.getDirection(); //算出第一點向量
        vectorLoc1.multiply(distance); //第二點等於 向量*距離
        Vector vector = vectorLoc1.subtract(loc1.getDirection()); //算出兩距離向量
        vector.multiply((double) 1 / density); //算出兩粒子之間的距離
        Location target = loc1;
        for (int x=0; x < density; x++) {
            //player.spawnParticle(particle, target, 1);
            target.getWorld().spawnParticle(particle, target, 1);
            target.add(vector);
        }
    }

    public void particleCircle(Player player, Particle particle, Location loc1, int range, Boolean isFull, int density) {
        for (int d = 0; d <= 90; d += 1) {
            Location particleLoc = new Location(loc1.getWorld(), loc1.getX(), loc1.getY(), loc1.getZ());
            particleLoc.setX(loc1.getX() + Math.cos(d) * range);
            particleLoc.setZ(loc1.getZ() + Math.sin(d) * range);
            loc1.getWorld().spawnParticle(particle, particleLoc, 1, new Particle.DustOptions(Color.WHITE, 5));
        }
    }
    private void createCircle(Player player,double radius ,int distance)
    {
        Vector dist = player.getEyeLocation().getDirection().multiply(distance);
        //mid is the middle location for the circle
        Location mid = player.getEyeLocation().add(dist);
        int particles = 50;
        for (int i = 0; i < particles; i++)
        {
            double angle, x, y;
            angle = 2 * Math.PI * i / particles;
            x = Math.cos(angle)*radius;
            y = Math.sin(angle)*radius;
            Vector v = rotateAroundAxisX(new Vector(x, y,0),player.getEyeLocation().getPitch());
            v = rotateAroundAxisY(v, player.getEyeLocation().getYaw());
            Location temp = mid.clone().add(v);
            //spawn particles at location temp using any method you like
            temp.getWorld().spawnParticle(Particle.REDSTONE, temp, 1, new Particle.DustOptions(Color.PURPLE, 2));
        }
    }

    private Vector rotateAroundAxisX(Vector v, double angle)
    {
        angle = Math.toRadians(angle);
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    private Vector rotateAroundAxisY(Vector v, double angle)
    {
        angle = -angle;
        angle = Math.toRadians(angle);
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }
}
