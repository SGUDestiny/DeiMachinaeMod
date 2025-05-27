package destiny.deimachinae.items;

import net.minecraft.world.item.Item;

import java.util.List;

public class MachineSpiritItem extends Item {
    private String type;
    private int tolerance;
    private List<String> traits;

    public MachineSpiritItem(Properties pProperties) {
        super(pProperties);
    }
}
