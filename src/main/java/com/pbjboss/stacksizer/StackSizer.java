package com.pbjboss.stacksizer;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.item.Item;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Created by PbJBOSS on 2/19/2015.
 */
@Mod(modid = StackSizer.modid, name = StackSizer.name, version = StackSizer.version)
public
class StackSizer
{
    public static final String modid = "stacksizer";
    public static final String name = "Stack Sizer";
    public static final String version = "1.7.10-1.0";

    @Mod.EventHandler
    public void init(FMLPostInitializationEvent event)
    {
        changeStackSizeToMax(16);
    }

    public void changeStackSizeToMax(int stackSizeToChangeFrom)
    {
        Iterator itemList = Item.itemRegistry.iterator();
        while (itemList.hasNext())
        {
            Item item = (Item) itemList.next();

            Field[] fields = item.getClass().getSuperclass().getDeclaredFields();

            for (int i = 0; i < fields.length; i++)
            {
                try
                {
                    Field field = fields[i];
                    field.setAccessible(true);
                    if (field.getName().equals("maxStackSize") && field.getInt(item) == stackSizeToChangeFrom)
                    {
                        field.setInt(item, 64);
                        FMLLog.info("Changed stacksize of %s from %s to %s",item.getUnlocalizedName(),stackSizeToChangeFrom,64);
                    }
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
