package destiny.deimachinae.blocks.block_entities;

import destiny.deimachinae.init.BlockEntityInit;
import destiny.deimachinae.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

/*public class AncientCryptBlockEntity extends BlockEntity {
    public boolean dropLoot = false;
    public int spawnTicker = 0;
    public int spawnedItems = 0;

    public AncientCryptBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityInit.ANCIENT_CRYPT.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, AncientCryptBlockEntity crypt) {
        if (!pLevel.isClientSide) {
            if (crypt.dropLoot) {
                BlockPos spawnPos = pPos.above();
                int amountToSpawn = pLevel.random.nextInt(2, 4);

                List<ItemStack> allItems = new ArrayList<>();
                allItems.add(new ItemStack(ItemInit.COGITATOR.get(), pLevel.random.nextInt(1, 2)));
                allItems.add(new ItemStack(ItemInit.MOTHERBOARD.get(), pLevel.random.nextInt(1, 2)));
                allItems.add(new ItemStack(ItemInit.WORN_GEARS.get(), pLevel.random.nextInt(1, 3)));
                allItems.add(new ItemStack(ItemInit.ARCHEOBRASS_PLATE.get(), pLevel.random.nextInt(1, 3)));
                allItems.add(new ItemStack(ItemInit.ARCHEOBRASS_NUGGET.get(), pLevel.random.nextInt(1, 5)));
                allItems.add(new ItemStack(ItemInit.OIL_FLASK.get(), 1));
                allItems.add(new ItemStack(Items.IRON_INGOT, pLevel.random.nextInt(1, 3)));
                allItems.add(new ItemStack(Items.IRON_NUGGET, pLevel.random.nextInt(1, 5)));
                allItems.add(new ItemStack(Items.COPPER_INGOT, pLevel.random.nextInt(1, 5)));
                allItems.add(new ItemStack(Items.REDSTONE, pLevel.random.nextInt(1, 5)));

                System.out.println("Trying to drop items");

                if (crypt.spawnTicker > 0) {
                    if (crypt.spawnTicker % 10 == 0) {
                        if (crypt.spawnedItems < amountToSpawn) {
                            ItemEntity item = new ItemEntity(pLevel, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), allItems.get(pLevel.random.nextInt(0, allItems.size())));
                            item.setDeltaMovement(pLevel.random.triangle(0.0F, 0.11485000171139836), pLevel.random.triangle(0.2, 0.11485000171139836), pLevel.random.triangle(0.0F, 0.11485000171139836));
                            pLevel.addFreshEntity(item);
                            pLevel.playSound(null, pPos, SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0f, 0.8f);

                            System.out.println("Dropped an item");
                        } else {
                            crypt.spawnedItems = 0;
                            crypt.spawnTicker = 0;
                            crypt.dropLoot = false;
                            return;
                        }
                    }
                    crypt.spawnedItems++;
                    crypt.spawnTicker++;
                }
            }
        }
    }
}*/
