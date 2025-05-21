package de.verdox.mccreativelab.impl.vanilla.mixins;

import com.google.common.base.Joiner;
import com.mojang.logging.LogUtils;
import de.verdox.mccreativelab.impl.vanilla.data.NMSDataPackInterceptor;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.FileUtil;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Mixin(PathPackResources.class)
public class MixinPathPackResources {
    private static final Joiner PATH_JOINER = Joiner.on("/");
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * @author Verdox
     * @reason Injecting data pack interceptor
     */
    @Overwrite
    public static void listPath(String namespace, Path namespacePath, List<String> decomposedPath, PackResources.ResourceOutput resourceOutput){
        Path path = FileUtil.resolvePath(namespacePath, decomposedPath);

        try (Stream<Path> stream = Files.find(path, Integer.MAX_VALUE, (path1, file) -> file.isRegularFile())) {
            stream.forEach(path1 -> {
                String string = PATH_JOINER.join(namespacePath.relativize(path1));
                ResourceLocation resourceLocation = ResourceLocation.tryBuild(namespace, string);
                if (resourceLocation == null) {
                    Util.logAndPauseIfInIde(String.format(Locale.ROOT, "Invalid path in pack: %s:%s, ignoring", namespace, string));
                } else {
                    NMSDataPackInterceptor nmsDataPackInterceptor = (NMSDataPackInterceptor) MCCPlatform.getInstance().getDataPackInterceptor();
                    IoSupplier<InputStream> result = nmsDataPackInterceptor.evaluate(resourceLocation, path1, IoSupplier.create(path1));
                    if(result == null)
                        return;
                    resourceOutput.accept(resourceLocation, result);
                }
            });
        } catch (NotDirectoryException | NoSuchFileException var10) {
        } catch (IOException var11) {
            LOGGER.error("Failed to list path {}", path, var11);
        }
    }
}
