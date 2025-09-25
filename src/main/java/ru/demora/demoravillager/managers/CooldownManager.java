package ru.demora.demoravillager.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    

    public boolean hasCooldown(UUID playerUUID, long cooldownTime) {
        Long lastUsed = cooldowns.get(playerUUID);
        if (lastUsed == null) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastUsed) < cooldownTime;
    }
    

    public void setCooldown(UUID playerUUID) {
        cooldowns.put(playerUUID, System.currentTimeMillis());
    }
    

    public long getRemainingCooldown(UUID playerUUID, long cooldownTime) {
        Long lastUsed = cooldowns.get(playerUUID);
        if (lastUsed == null) {
            return 0;
        }
        
        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - lastUsed;
        long remaining = cooldownTime - elapsed;
        
        return Math.max(0, remaining / 1000);
    }
    

    public void removeCooldown(UUID playerUUID) {
        cooldowns.remove(playerUUID);
    }
}
