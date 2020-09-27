package logan.pickpocket.user;

import logan.config.MessageConfiguration;
import logan.pickpocket.main.PickpocketPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Tre on 12/14/2015.
 */
public class PickpocketUser {

    private Player player;
    private PickpocketUser victim;
    private PickpocketUser predator;
    private boolean playingMinigame;
    private boolean rummaging;
    private RummageInventory openRummageInventory;
    private boolean participating;

    private ProfileConfiguration profileConfiguration;
    private MinigameModule minigameModule;

    public PickpocketUser(Player player) {
        this.player = player;

        profileConfiguration = new ProfileConfiguration(PickpocketPlugin.getInstance().getDataFolder() + "/players/", player.getUniqueId().toString() + ".yml");
        profileConfiguration.createSections();

        participating = profileConfiguration.getParticipatingSectionValue();

        minigameModule = new MinigameModule(this);
    }

    public void performPickpocket(PickpocketUser victim) {
        if (!PickpocketPlugin.getCooldowns().containsKey(player)) {
            openRummageInventory = new RummageInventory(victim);
            openRummageInventory.show(this);
            setRummaging(true);
            setVictim(victim);
            victim.setPredator(this);
        } else {
            player.sendMessage(PickpocketPlugin.getMessageConfiguration().getMessage(MessageConfiguration.COOLDOWN_NOTICE_KEY, PickpocketPlugin.getCooldowns().get(player).toString()));
        }
    }

    public RummageInventory getOpenRummageInventory() {
        return openRummageInventory;
    }

    public boolean isPlayingMinigame() {
        return playingMinigame;
    }

    public boolean isRummaging() {
        return rummaging;
    }

    public void setRummaging(boolean value) {
        rummaging = value;
    }

    public void setPlayingMinigame(boolean value) {
        playingMinigame = value;
    }

    public void setVictim(PickpocketUser victim) {
        this.victim = victim;
    }

    public PickpocketUser getVictim() {
        return victim;
    }

    public void setPredator(PickpocketUser predator) {
        this.predator = predator;
    }

    public PickpocketUser getPredator() {
        return predator;
    }

    public boolean isPredator() {
        return victim != null;
    }

    public boolean isVictim() {
        return predator != null;
    }

    public void setParticipating(boolean participating) {
        this.participating = participating;
        profileConfiguration.setParticipatingSection(participating);
    }

    public boolean isParticipating() {
        return participating;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return player;
    }

    public ProfileConfiguration getProfileConfiguration() {
        return profileConfiguration;
    }

    public MinigameModule getMinigameModule() {
        return minigameModule;
    }

    public void sendMessage(String message, Object... args) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(message, args)));
    }

    public void playRummageSound() {
        player.playSound(player.getLocation(), PickpocketPlugin.getAPIWrapper().getSoundBlockSnowStep(), 1.0f, 0.5f);
    }
}